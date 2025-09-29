package servicocursos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import servicocursos.dto.AulaDTO;
import servicocursos.dto.CursoDTO;
import servicocursos.dto.ModuloDTO;
import servicocursos.model.Aula;
import servicocursos.model.Curso;
import servicocursos.model.Modulo;
import servicocursos.repository.AulaRepository;
import servicocursos.repository.CursoRepository;
import servicocursos.repository.ModuloRepository;
import servicocursos.exception.ItemNotFoundException;
import servicocursos.dto.CursoDetalhesDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private ModuloRepository moduloRepository;
    @Autowired
    private AulaRepository aulaRepository;

    public CursoDTO createCurso(CursoDTO cursoDTO) {
        Curso curso = new Curso();
        curso.setTitulo(cursoDTO.titulo());
        curso.setDescricao(cursoDTO.descricao());
        curso.setInstrutor(cursoDTO.instrutor());
        Curso novoCurso = cursoRepository.save(curso);
        return toDTO(novoCurso);
    }

    public List<CursoDTO> getAllCursos() {
        return cursoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public CursoDTO getCursoById(UUID id) {
        Curso curso = findCursoById(id);
        return toDTO(curso);
    }

    public CursoDetalhesDTO getCursoDetalhesById(UUID id) {
        Curso curso = findCursoById(id);
        return new CursoDetalhesDTO(curso.getId(), curso.getTitulo());
    }

    public CursoDTO updateCurso(UUID id, CursoDTO cursoDTO) {
        Curso curso = findCursoById(id);
        curso.setTitulo(cursoDTO.titulo());
        curso.setDescricao(cursoDTO.descricao());
        curso.setInstrutor(cursoDTO.instrutor());
        Curso cursoAtualizado = cursoRepository.save(curso);
        return toDTO(cursoAtualizado);
    }

    public void deleteCurso(UUID id) {
        if (!cursoRepository.existsById(id)) {
            throw new ItemNotFoundException("Curso não encontrado com o id: " + id);
        }
        cursoRepository.deleteById(id);
    }

    public ModuloDTO adicionarModulo(UUID cursoId, ModuloDTO moduloDTO) {
        Curso curso = findCursoById(cursoId);

        Modulo modulo = new Modulo();
        modulo.setTitulo(moduloDTO.titulo());
        modulo.setCurso(curso);

        Modulo novoModulo = moduloRepository.save(modulo);
        return toDTO(novoModulo);
    }

    public AulaDTO adicionarAula(UUID moduloId, AulaDTO aulaDTO) {
        Modulo modulo = findModuloById(moduloId);

        Aula aula = new Aula();
        aula.setTitulo(aulaDTO.titulo());
        aula.setUrlVideo(aulaDTO.urlVideo());
        aula.setModulo(modulo);

        Aula novaAula = aulaRepository.save(aula);
        return toDTO(novaAula);
    }

    private Curso findCursoById(UUID id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Curso não encontrado com o id: " + id));
    }

    private Modulo findModuloById(UUID id) {
        return moduloRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Módulo não encontrado com o id: " + id));
    }

    private CursoDTO toDTO(Curso curso) {
        return new CursoDTO(curso.getId(), curso.getTitulo(), curso.getDescricao(), curso.getInstrutor());
    }

    private ModuloDTO toDTO(Modulo modulo) {
        return new ModuloDTO(modulo.getId(), modulo.getTitulo());
    }

    private AulaDTO toDTO(Aula aula) {
        return new AulaDTO(aula.getId(), aula.getTitulo(), aula.getUrlVideo());
    }
}

