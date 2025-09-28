package servicoalunos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import servicoalunos.dto.AlunoDTO;
import servicoalunos.dto.RegisterDTO;
import servicoalunos.model.Aluno;
import servicoalunos.exception.ItemNotFoundException;
import servicoalunos.repository.AlunoRepository;
import servicoalunos.dto.AlunoDetalhesDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(RegisterDTO registerDTO) {
        Aluno aluno = new Aluno();
        aluno.setNome(registerDTO.getNome());
        aluno.setEmail(registerDTO.getEmail());
        aluno.setSenha(passwordEncoder.encode(registerDTO.getPassword()));
        aluno.setRole("ROLE_USER");
        alunoRepository.save(aluno);
    }

    public UserDetails findUserDetailsByEmail(String email) {
        return alunoRepository.findByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Utilizador não encontrado com o email: " + email));
    }

    public AlunoDTO createAluno(AlunoDTO alunoDTO) {
        Aluno aluno = new Aluno();
        aluno.setNome(alunoDTO.nome());
        aluno.setEmail(alunoDTO.email());
        Aluno novoAluno = alunoRepository.save(aluno);
        return toDTO(novoAluno);
    }

    public List<AlunoDTO> getAllAlunos() {
        return alunoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AlunoDTO getAlunoById(UUID id) {
        Aluno aluno = findAlunoById(id);
        return toDTO(aluno);
    }

    public AlunoDTO updateAluno(UUID id, AlunoDTO alunoDTO) {
        Aluno aluno = findAlunoById(id);
        aluno.setNome(alunoDTO.nome());
        aluno.setEmail(alunoDTO.email());
        Aluno alunoAtualizado = alunoRepository.save(aluno);
        return toDTO(alunoAtualizado);
    }

    public void deleteAluno(UUID id) {
        if (!alunoRepository.existsById(id)) {
            throw new ItemNotFoundException("Aluno não encontrado com o id: " + id);
        }
        alunoRepository.deleteById(id);
    }

    private Aluno findAlunoById(UUID id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Aluno não encontrado com o id: " + id));
    }

    public AlunoDetalhesDTO getAlunoDetalhesById(UUID id) {
        Aluno aluno = findAlunoById(id);
        return new AlunoDetalhesDTO(aluno.getId(), aluno.getNome());
    }

    private AlunoDTO toDTO(Aluno aluno) {
        return new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getEmail());
    }
}