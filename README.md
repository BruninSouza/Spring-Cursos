# Spring-Cursos
Projeto criado para as disciplinas de Desenvolvimento de Software Corporativos e Sistemas Distribuidos

## Estrutura do projeto

```
plataforma-cursos/
├── .gitignore
├── docker-compose.yml
├── nginx/
│   └── nginx.conf
├── servico-alunos/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/com/example/servicoalunos/
│           │   ├── ServicoAlunosApplication.java
│           │   ├── controller/
│           │   │   └── AlunoController.java
│           │   ├── entity/
│           │   │   └── Aluno.java
│           │   └── repository/
│           │       └── AlunoRepository.java
│           └── resources/
│               └── application.properties
├── servico-cursos/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/com/example/servicocursos/
│           │   ├── ServicoCursosApplication.java
│           │   ├── controller/
│           │   │   └── CursoController.java
│           │   ├── entity/
│           │   │   ├── Aula.java
│           │   │   ├── Curso.java
│           │   │   └── Modulo.java
│           │   └── repository/
│           │       ├── AulaRepository.java
│           │       ├── CursoRepository.java
│           │       └── ModuloRepository.java
│           └── resources/
│               └── application.properties
└── servico-inscricoes/
    ├── Dockerfile
    ├── pom.xml
    └── src/
        └── main/
            ├── java/com/example/servicoinscricoes/
            │   ├── ServicoInscricoesApplication.java
            │   ├── controller/
            │   │   └── InscricaoController.java
            │   ├── entity/
            │   │   └── Inscricao.java
            │   └── repository/
            │       └── InscricaoRepository.java
            └── resources/
                └── application.properties
```
