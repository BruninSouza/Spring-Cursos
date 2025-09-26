package servicocursos.service;

import servicocursos.dto.CursoDTO;
import servicocursos.entity.Curso;
import servicocursos.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

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
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com o id: " + id));
        return toDTO(curso);
    }

    public CursoDTO updateCurso(UUID id, CursoDTO cursoDTO) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com o id: " + id));

        curso.setTitulo(cursoDTO.titulo());
        curso.setDescricao(cursoDTO.descricao());
        curso.setInstrutor(cursoDTO.instrutor());

        Curso cursoAtualizado = cursoRepository.save(curso);
        return toDTO(cursoAtualizado);
    }

    public void deleteCurso(UUID id) {
        if (!cursoRepository.existsById(id)) {
            throw new RuntimeException("Curso não encontrado com o id: " + id);
        }
        cursoRepository.deleteById(id);
    }

    private CursoDTO toDTO(Curso curso) {
        return new CursoDTO(curso.getId(), curso.getTitulo(), curso.getDescricao(), curso.getInstrutor());
    }
}

