# Trabalho Prático - Teste de Software

### Nomes:

- Leandro Marques Venceslau de Souza
- Thiago Almeida Campos
- João Vitor Tavares de Almeida Trindade
- Bernardo Gomes Nunes
- Pedro Henrique Bertolini Barbosa

### Explicação do Sistema

O sistema criado trata-se de um sistema para marcação de consultas médicas ou testes de covid. Dentre as funcionalidades, o usuário consegue verificar os horários disponíveis, ver as consultas marcadas, marcar uma consulta médica ou um teste de covid, podendo selecionar o horário da consulta a partir de uma lista de horários disponíveis. Além disso, o usuário também pode escolher um médico de sua preferência e verificar as informações da consulta/teste marcado, cancelar uma consultar e até filtrar por dia. Caso o usuário do sistema seja um profissional, ele pode ver as suas consultas, verificar se algum equipamento está disponível, adicionar unidades de um equipamento, dentre outros.

Foram criados uma classe para o médico (Doctor), enfermeiro (Nurse) e o paciente (Patient) em que o usuário consegue marcar uma consulta com um médico (DoctorAppointment) ou um teste de covid (CovidTestAppointment) com um enfermeiro. Ele pode marcar um horário com as funções da classe Calendar. As classes Doctor e Nurse são extendidas da superclasse Professional, as classes DoctorAppointment e CovidTestAppointment extendem de Appointment e foram criadas as classes Equipment e Storage para fazer o controle dos equipamentos.

O sistema não possui interface gráfica, mas é exibido um menu para o usuário onde ele pode fazer as suas escolhas. 

### Explicação das Tecnologias Utilizadas

O sistema foi construindo em [Java]('https://www.java.com') e utiliza um banco de dados [MySQL]('https://www.mysql.com/'). Para realizar a conexão com o banco de dados, utilizamos o ORM [Hibernate]('https://hibernate.org/'). Para a criação de testes, usamos os frameworks [JUnit]('https://junit.org/') e [Mockito]('https://site.mockito.org/'). Por fim, utilizamos o [Gradle]('https://gradle.org/') para automatizar os builds

### Como rodar a aplicação

#### 1. Importar o projeto na IDE de sua preferência (Ex.: Eclipse, IntelliJ, etc).

Obs.: Caso você esteja usando o Eclipse e a IDE não reconheça e instale as dependência automaticamente, pode ser necessário executar o comando abaixo:

```bash
$ ./gradlew cleanEclipse eclipse
```

#### 2. Inicializar o banco de dados

Utilizamos o [Docker]('https://www.docker.com/') para criar o banco de dados local. Primeiramente, você deve criar uma imagem com o seguinte comando


```bash
$ sudo docker build -t clinica-covid-db .
```

Por fim, falta inicializar o container:

```bash
$ docker-compose up
```Para a construção do sistema, utilizou-se a linguagem Java.
