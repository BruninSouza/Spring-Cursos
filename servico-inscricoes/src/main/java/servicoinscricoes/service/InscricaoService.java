package servicoinscricoes.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import servicoinscricoes.dto.AlunoDetalhesDTO;
import servicoinscricoes.dto.CursoDetalhesDTO;
import servicoinscricoes.dto.InscricaoDetalhesDTO;
import servicoinscricoes.dto.InscricaoDTO;
import servicoinscricoes.dto.InscricaoStatusUpdateDTO;
import servicoinscricoes.model.Inscricao;
import servicoinscricoes.model.StatusInscricao;
import servicoinscricoes.exception.ItemNotFoundException;
import servicoinscricoes.repository.InscricaoRepository;
import servicoinscricoes.exception.BusinessException;
import servicoinscricoes.security.SecurityConstants;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private RestTemplate restTemplate;

    public InscricaoDTO createInscricao(InscricaoDTO inscricaoDTO) {
        validarAlunoExistente(inscricaoDTO.alunoId());
        validarCursoExistente(inscricaoDTO.cursoId());

        if (inscricaoRepository.existsByAlunoIdAndCursoId(inscricaoDTO.alunoId(), inscricaoDTO.cursoId())) {
            throw new BusinessException("O aluno já está inscrito neste curso.");
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setAlunoId(inscricaoDTO.alunoId());
        inscricao.setCursoId(inscricaoDTO.cursoId());

        Inscricao novaInscricao = inscricaoRepository.save(inscricao);
        return toDTO(novaInscricao);
    }

    private HttpEntity<String> getAuthenticatedHeaders() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpHeaders headers = new HttpHeaders();
        if (attributes != null) {
            final String authHeader = attributes.getRequest().getHeader(SecurityConstants.HEADER_STRING);
            if (authHeader != null && authHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
                headers.set(SecurityConstants.HEADER_STRING, authHeader);
            }
        }
        return new HttpEntity<>(headers);
    }

    private void validarAlunoExistente(UUID alunoId) {
        String url = "http://nginx/api/alunos/" + alunoId;
        try {
            restTemplate.exchange(url, HttpMethod.GET, getAuthenticatedHeaders(), String.class);        } catch (HttpClientErrorException.NotFound e) {
            throw new BusinessException("Aluno com o ID fornecido não existe: " + alunoId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("Não foi possível validar o aluno. O serviço de alunos pode estar indisponível.");
        }
    }

    private void validarCursoExistente(UUID cursoId) {
        String url = "http://nginx/api/cursos/" + cursoId;
        try {
            restTemplate.exchange(url, HttpMethod.GET, getAuthenticatedHeaders(), String.class);        } catch (HttpClientErrorException.NotFound e) {
            throw new BusinessException("Curso com o ID fornecido não existe: " + cursoId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("Não foi possível validar o curso. O serviço de cursos pode estar indisponível.");
        }
    }

    public List<InscricaoDetalhesDTO> getInscricoesByAlunoIdComDetalhes(UUID alunoId) {
        List<Inscricao> inscricoes = inscricaoRepository.findByAlunoId(alunoId);

        return inscricoes.stream().map(inscricao -> {
            AlunoDetalhesDTO aluno = getAlunoDetalhes(inscricao.getAlunoId());
            CursoDetalhesDTO curso = getCursoDetalhes(inscricao.getCursoId());

            return new InscricaoDetalhesDTO(
                    inscricao.getId(),
                    aluno.nome(),
                    curso.titulo(),
                    inscricao.getDataInscricao(),
                    inscricao.getStatus()
            );
        }).collect(Collectors.toList());
    }

    private AlunoDetalhesDTO getAlunoDetalhes(UUID alunoId) {
        String url = "http://servico-alunos:8082/api/alunos/" + alunoId + "/detalhes";
        try {
            return restTemplate.getForObject(url, AlunoDetalhesDTO.class);
        } catch (Exception e) {
            return new AlunoDetalhesDTO(alunoId, "Nome Indisponível");
        }
    }

    private CursoDetalhesDTO getCursoDetalhes(UUID cursoId) {
        String url = "http://servico-cursos-1:8081/api/cursos/" + cursoId + "/detalhes";
        try {
            return restTemplate.getForObject(url, CursoDetalhesDTO.class);
        } catch (Exception e) {
            return new CursoDetalhesDTO(cursoId, "Título Indisponível");
        }
    }

    public InscricaoDTO getInscricaoById(UUID id) {
        Inscricao inscricao = findInscricaoById(id);
        return toDTO(inscricao);
    }

    public InscricaoDTO updateStatus(UUID id, StatusInscricao newStatus) {
        Inscricao inscricao = findInscricaoById(id);
        inscricao.setStatus(newStatus);
        Inscricao inscricaoAtualizada = inscricaoRepository.save(inscricao);
        return toDTO(inscricaoAtualizada);
    }

    public void deleteInscricao(UUID id) {
        if (!inscricaoRepository.existsById(id)) {
            throw new ItemNotFoundException("Inscrição não encontrada com o id: " + id);
        }
        inscricaoRepository.deleteById(id);
    }

    private Inscricao findInscricaoById(UUID id) {
        return inscricaoRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Inscrição não encontrada com o id: " + id));
    }

    private InscricaoDTO toDTO(Inscricao inscricao) {
        return new InscricaoDTO(
                inscricao.getId(),
                inscricao.getAlunoId(),
                inscricao.getCursoId(),
                inscricao.getDataInscricao(),
                inscricao.getStatus()
        );
    }
}
