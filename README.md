# Plataforma de Cursos Online - Spring Cursos

Este projeto implementa uma plataforma de cursos online utilizando uma arquitetura de microsserviços. Foi desenvolvido como parte das disciplinas de **Sistemas Distribuídos** e **Desenvolvimento de Software Corporativo** para demonstrar de forma prática conceitos fundamentais como Balanceamento de Carga, Replicação de Dados, Tolerância a Falhas e Segurança com JWT.

## Arquitetura e Conceitos Abordados

A aplicação é dividida em três microsserviços principais, orquestrados por um API Gateway (NGINX) e utilizando uma base de dados partilhada para garantir a consistência dos dados.

* **Serviço de Alunos e Autenticação**: Responsável pela gestão dos dados dos alunos e por todo o fluxo de segurança, incluindo o registo, o login e a emissão de tokens JWT.

* **Serviço de Cursos**: Responsável pela gestão dos cursos, módulos e aulas. Este serviço é replicado em duas instâncias (servico-cursos-1 e servico-cursos-2) para demonstrar os conceitos de balanceamento de carga e tolerância a falhas.

* **Serviço de Inscrições**: Atua como o serviço agregador, orquestrando a lógica de negócio de inscrever um aluno num curso, comunicando com os outros serviços para validar dados e enriquecer as respostas.

* **API Gateway (NGINX)**: Atua como o único ponto de entrada para a aplicação, encaminhando as requisições para os microsserviços apropriados e realizando o balanceamento de carga entre as réplicas do serviço de cursos.

## Tecnologias Utilizadas

* **Linguagem**: Java 17
* **Framework**: Spring Boot 3
* **Persistência de Dados**: PostgreSQL
* **Segurança**: Spring Security 6, JWT (JSON Web Tokens), BCrypt
* **Orquestração e Virtualização**: Docker e Docker Compose
* **API Gateway e Balanceamento de Carga**: NGINX
* **Documentação da API**: Swagger
* **Build Tool**: Maven

## Requerimentos para Executar

Para executar este projeto, você precisará de ter o seguinte software instalado:

- Java 17 (ou superior)
- Apache Maven
- Docker
- Docker Compose
- Postman (ou qualquer cliente HTTP, como o curl) para testar a API.

## Como Utilizar

Siga este passo a passo para compilar e executar a aplicação.

### Passo 1: Compilar Todos os Microsserviços

Antes de iniciar o ambiente Docker, é crucial compilar cada microsserviço com o Maven. Isto irá gerar os ficheiros `.jar` executáveis que serão copiados para dentro dos contentores.

Execute estes comandos a partir da pasta raiz do projeto:

```bash
# Compilar o serviço de alunos
cd servico-alunos
mvn clean install
cd ..

# Compilar o serviço de cursos
cd servico-cursos
mvn clean install
cd ..

# Compilar o serviço de inscrições
cd servico-inscricoes
mvn clean install
cd ..
```

> Talves o comando `mvn` dê erro de autorização, mas basta executá-lo como adm (sudo no ubuntu) que funcionará

Certifique-se de que cada um dos três comandos termina com **[INFO] BUILD SUCCESS**.

### Passo 2: Iniciar o Ambiente com Docker Compose

Com todos os serviços compilados, agora pode iniciar todo o ambiente com um único comando a partir da pasta raiz do projeto:

```bash
# O --build força o Docker a reconstruir as imagens, garantindo que as últimas alterações são utilizadas.
docker-compose up --build
```

> Talves o comando `docker-compose` também dê erro de autorização, mas basta executá-lo como adm (sudo no ubuntu) que funcionará

Aguarde até que os logs de todos os serviços se estabilizem e mostrem a mensagem "Started ... Application".

## Serviços e Autorização

A API está protegida e a maioria dos endpoints requer um token de autenticação JWT.

### Endpoints Públicos (Não Requerem Autorização)

Estes endpoints podem ser acedidos sem um token JWT.

* POST `/api/auth/register`: Regista um novo utilizador no `servico-alunos`.
* POST `/api/auth/login`: Autentica um utilizador e retorna um token JWT.
* Todos os endpoints em `/api/cursos`: Permite a visualização e gestão de cursos sem necessidade de autenticação.

### Endpoints Protegidos (Requerem Autorização)

Todos os endpoints para `/api/alunos` (exceto os de autenticação) e `/api/inscricoes` são protegidos. Para os aceder, é necessário incluir o cabeçalho de autorização em cada requisição:

```bash
Authorization: Bearer <SEU_TOKEN_JWT>
``` 
## Documentação da API

Depois de colocar o projeto para "rodar" é possível acessar a documentação dos serviços pelos links:

- Serviço Cursos: http://localhost:8081/swagger-ui/index.html#/

- Serviço Alunos: http://localhost:8082/swagger-ui/index.html#/

- Serviço Inscrições: http://localhost:8083/swagger-ui/index.html#/
