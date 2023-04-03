<h1 align="center"> 
  Desafio Java Jr Stefanini
</h1>


## 💻 Sobre o projeto

 A aplicação, é uma API REST de cadastro, alteração, remoção e consulta de autores e obras


O desafio foi tirado do link: https://gitlab.com/selecao.stefanini.cpg/desafio-java-jr

Deverá ser criada uma aplicação de gerenciamento de **autores** e **obras**, seguindo as regras de relacionamento abaixo:

- Cada autor poderá ter 0 (zero) ou n obra(s);
- Cada obra deverá ter 1 (um) ou n autor(es);
- A partir de uma obra deverá ser posível acessar o(s) autor(es);
- A partir de um autor deverá ser possível acessar a(s) obra(s).


1) Back-end
A aplicação, a ser desenvolvida em Java, deverá expor uma API REST de cadastro, alteração, remoção e consulta de autores e obras com as seguintes propriedades básicas definidas para cada entidade:

**Autor**

- Nome - obrigatório
- Sexo
- E-mail - não obrigatório, deve ser validado caso preenchido (não pode haver dois cadastros com mesmo e-mail)
- Data de nascimento - obrigatório, deve ser validada
- País de origem - obrigatório (deve ser um país existente)
- CPF - somente deve ser informado caso país de origem seja o Brasil, desta forma torna-se obrigatório. Deve ser validado (formatado e não pode haver dois cadastros com mesmo CPF)


**Obra**

- Nome - obrigatório
- Descrição - obrigatório (deve conter no máximo 240 caracteres)
- Data de publicação - obrigatória caso a data de exposição não seja informada (é utilizada mais para livros e demais publicações escritas)
- Data de exposição - obrigatória caso a data de publicação não seja informada (é utilizada mais para obras que são expostas, como pinturas, esculturas e demais)


**Regra(s)**

A data de publicação e a data de exposição não podem ser nulas ao mesmo tempo, devendo sempre uma ou outra ser informada.

**Regras de exclusão**


**Autor:** somente pode ser excluído caso não possua obras associadas.

**Obra:** não há restrição para exclusão.

---

## 🔥Endpoints dispoiveis
Após executar o projeto você terá a aplicação rodando em **http://localhost:8080//stefannini/library/<endpoint>**

| Endpoint Autores             |  Endpoint Livros         | Método HTTP |                         Função                            |
|:----------------------------:|:------------------------:|:-----------:|:---------------------------------------------------------:|
| /authors                     | /books                   |     POST    |                  Cria um novo recurso                     |
| /authors                     | /books                   |     GET     |                 Lista todos os recursos                   |                      
| /authors/author/{id}         | /books /book/{id}        |     GET     |                Retorna um recurso pelo id                 |
| /authors /author?name={name} | /books/book?name={name}  |     GET     |               Retorna um recurso pelo nome                |
| /authors /author/{id}        | /books/book/{id}         |     PUT     |                    Atualiza um recurso                    |
| /authors /author/{id}      	 | /books/book/{id}         |    DELETE   |                     Deleta um recurso                     |
| /authors/author/{id}/book    | /books/book/{id}/author  |     POST    |     Associa um autor a um livro e um livro a um autor     |
              

## 🚀 Como executar o projeto

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
**Maven** e o **MySQL**

#### 🎲 Rodando o projeto

```bash
# Clone o projeto
$ git@github.com:MaxOliverrr/desafio-java-jr-stefanini.git  
  
# Instale as dependências
$ mvn clean install

# Execute a aplicação em modo de desenvolvimento
$ mvn spring-boot:run

# Para rodar os testes digite o comando
$ mvn test -Dspring.config.name=application-test
  
```

---

## 🛠 Tecnologias

As seguintes ferramentas foram usadas na construção do projeto:

- **SpringBoot (Data JPA, Validation)**
- **MySQL**
- **H2**
- **Lombok**
- **ModelMapper**
- **JUnit 5**
- **Mockito**
- **Flyway**

---
