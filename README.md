# Catálogo do Sábio
Este repositório contém a implementação de uma API REST dedicada ao gerenciamento de livros, oferecendo funcionalidades para consultar dados de livros fictícios. O projeto faz uso de tecnologias avançadas para melhorar o desempenho e assegurar a escalabilidade da aplicação.

## Tecnologias Utilizadas
* Java 17
* Spring Boot 3.x
* Maven (para controle e gerenciamento de dependências)
* Spring Data JPA (para gerenciar a persistência de dados)
* PostgreSQL (sistema de gerenciamento de banco de dados relacional)
* Redis (cache foi implementado para otimizar a leitura dos dados e armazenar os itens visualizados recentemente.)
* Docker (utilizado para simplificar a execução da aplicação em qualquer ambiente)
* JUnit 5 e Mockito (para testes unitários)

## Funcionalidades
A API oferece uma série de endpoints para interagir com o catálogo de livros:

* GET /books: Recupera todos os livros. Suporta paginação opcional para grandes volumes de dados.
* GET /books/{id}: Recupera um livro específico pelo seu ID.
* GET /books/genre?genre="": Recupera livros de um gênero específico.
* GET /books/author?author="": Recupera livros de um autor específico.
* GET /books/recent: Recupera livros visualizados recentemente.

## Plano de Implementação
### Estrutura do Projeto com Clean Architecture
A aplicação será estruturada em camadas, seguindo os princípios da Clean Architecture, com o objetivo de garantir a separação de responsabilidades, o que facilita tanto a manutenção quanto a escalabilidade do sistema. As camadas principais serão as seguintes:

* Application: Esta camada é responsável pela lógica de aplicação. Ela contém os casos de uso, que definem as regras e operações específicas que o sistema deve executar. Sua principal função é orquestrar a execução das operações necessárias, sem depender de detalhes de implementação.


* Config: Aqui ficam as configurações gerais do sistema, como setup de dependências, configurações de banco de dados, ou integrações com outros sistemas. Ela tem o objetivo de centralizar as definições que não pertencem diretamente ao modelo de domínio, mas são essenciais para a execução do sistema.


* Domain: Esta camada representa o coração do sistema, contendo as entidades, regras de negócio e a lógica de domínio que define o comportamento do negócio. Ela deve ser independente de frameworks e tecnologias externas, focando puramente nas regras que regem o problema que o software resolve.


* Infra: A camada de infraestrutura é onde ficam as implementações concretas de coisas como persistência de dados, comunicação com APIs externas e outros detalhes técnicos. Ela serve como ponte entre o domínio e os sistemas externos, sem afetar a lógica de negócio.


* Mapper: Responsável por fazer a conversão entre os modelos do domínio e os modelos de dados que serão consumidos ou persistidos, geralmente em formato como DTOs (Data Transfer Objects). Ela garante que o sistema consiga interagir com dados de uma forma que não quebre a lógica de negócio.


* Web: A camada Web lida com a interação do sistema com o usuário ou com APIs externas. Ela pode envolver controllers, rotas e endpoints, sendo responsável por traduzir as requisições e respostas para o formato que o sistema usa e aplicar os casos de uso adequados.

## Banco de Dados
* O PostgreSQL está configurado no arquivo application.yml e pode ser executado através do Docker.
* Redis foi implementado como cache, além de ser utilizado para armazenar os livros acessados recentemente. Dessa forma, dados frequentemente consultados são guardados, o que ajuda a reduzir o tempo de resposta em leituras subsequentes.
<p>Para gerar os dados fictícios, foi utilizado o CHAT GPT-4 para simular as informações. A aplicação realiza automaticamente a criação e inserção desses dados no sistema.<p>

## Testes
Os testes unitários foram implementados com JUnit 5 e Mockito, visando garantir o funcionamento adequado dos serviços e casos de uso. Os testes são essenciais para garantir que as funcionalidades permaneçam intactas à medida que o projeto evolui.

# Docker
Para garantir a reprodutibilidade da solução, o projeto utiliza Docker, incluindo um arquivo docker-compose.yml que configura o ambiente com PostgreSQL e Redis. Isso possibilita a execução da aplicação de forma consistente em qualquer ambiente.

## Instruções de Execução com Docker
1. Garanta que o [Docker](https://docs.docker.com/get-started/get-docker/) esteja instalado.
2. Escolha uma IDE de sua preferência: [IntelliJ](https://www.jetbrains.com/pt-br/idea/download/?section=windows)/[Eclipse](https://eclipseide.org/)
3. Faça o clone deste repositório.
5. Instale as dependências executando o comando `mvn dependency:resolve -U`.
6. Execute o seguinte comando para iniciar os serviços: `docker-compose up`
7. Inicie a aplicação utilizando o modo `start/debug` ou, alternativamente, pelo terminal com o comando `mvn spring-boot:run`

Você pode acessar a aplicação por meio dos endpoints disponíveis, como, por exemplo, localhost:8080/catalogoDoSabio/api/v1/books.

### Dicas sobre o projeto

A aplicação utiliza alguns recursos interessantes que certamente vale a pena explorar.

* [Estrutura Rest](https://restfulapi.net/)
* [Clean Arch](https://medium.com/@gabrielfernandeslemos/clean-architecture-uma-abordagem-baseada-em-princ%C3%ADpios-bf9866da1f9c)
* [Redis](https://redis.io/docs/latest/develop/get-started/data-store/)
* [Clean Arch](https://medium.com/@gabrielfernandeslemos/clean-architecture-uma-abordagem-baseada-em-princ%C3%ADpios-bf9866da1f9c)
---
