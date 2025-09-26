package servicoinscricoes.service;

import servicoinscricoes.dto.InscricaoDTO;
import servicoinscricoes.dto.InscricaoStatusUpdateDTO;
import servicoinscricoes.entity.Inscricao;
import servicoinscricoes.entity.StatusInscricao;
import servicoinscricoes.repository.InscricaoRepository;
import servicoinscricoes.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    public InscricaoDTO createInscricao(InscricaoDTO inscricaoDTO) {
        Inscricao inscricao = new Inscricao();
        inscricao.setAlunoId(inscricaoDTO.alunoId());
        inscricao.setCursoId(inscricaoDTO.cursoId());
        inscricao.setDataInscricao(LocalDateTime.now());
        inscricao.setStatus(StatusInscricao.ATIVA);

        Inscricao novaInscricao = inscricaoRepository.save(inscricao);
        return toDTO(novaInscricao);
    }

    public InscricaoDTO getInscricaoById(UUID id) {
        Inscricao inscricao = findInscricaoById(id);
        return toDTO(inscricao);
    }

    public List<InscricaoDTO> getInscricoesByAlunoId(UUID alunoId) {
        return inscricaoRepository.findByAlunoId(alunoId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public InscricaoDTO updateInscricaoStatus(UUID id, InscricaoStatusUpdateDTO statusUpdateDTO) {
        Inscricao inscricao = findInscricaoById(id);
        inscricao.setStatus(statusUpdateDTO.status());
        Inscricao inscricaoAtualizada = inscricaoRepository.save(inscricao);
        return toDTO(inscricaoAtualizada);
    }

    public void deleteInscricao(UUID id) {
        if (!inscricaoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inscrição não encontrada com o id: " + id);
        }
        inscricaoRepository.deleteById(id);
    }

    private Inscricao findInscricaoById(UUID id) {
        return inscricaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscrição não encontrada com o id: " + id));
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