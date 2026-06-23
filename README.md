## Raízes do Nordeste — API Back-end

API REST desenvolvida em Java/Spring Boot para gerenciamento 
da rede de lanchonetes Raízes do Nordeste.

### Funcionalidades
- Autenticação com JWT
- Controle de acesso por perfis (ADMIN, CLIENTE, ATENDENTE e COZINHA)
- Gerenciamento de pedidos
- Aprovação de pagamento (mock)
- Controle de estoque
- Programa de fidelização
- Consulta de pedidos por canal
- Tratamento padronizado de erros
- Consentimento LGPD

### Tecnologias Utilizadas
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT
- MySQL 8
- Maven
- Lombok
- Postman

### Requisitos
- Java 17
- MySQL 8
- Maven

## Como configurar

### 1. Clone o repositório
git clone https://github.com/FilipeMiguelsilva/raizes-do-nordeste.git

### Instalando dependências
./mvnw install

### 2. Crie o banco de dados
mysql -u root -p
CREATE DATABASE raizes_do_nordeste;

### 3. Configure o application.properties
src/main/resources/application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/raizes_do_nordeste
spring.datasource.username=root
spring.datasource.password=SUA_SENHA

### 4. Execute o seed de dados
O arquivo seed.sql encontra-se na raiz do projeto   
execute: mysql -u root -p raizes_do_nordeste < seed.sql

O seed cria:
- Usuário ADMIN: admin@email.com / senha: 123456
- Usuário CLIENTE: cliente@email.com / senha: 123456
- Unidade Centro (Vinhedo/SP)
- Produto: Hamburguer (R$ 25,00)
- Cardápio e Estoque configurados

### 5. Inicie a API
./mvnw spring-boot:run

A API sobe em: http://localhost:8080

### 6. Credenciais de acesso
ADMIN:
email: admin@email.com
senha: 123456

CLIENTE:
email: cliente@email.com
senha: 123456

### 7. Testes com Postman
Importe o arquivo da coleção disponível no repositório:
raizes-do-nordeste.postman_collection.json

Execute nessa ordem:
1. Auth → Login
2. Usuarios → Cadastrar
3. Pedidos → Criar pedido
4. Executar T03 — anota o ID retornado,
   Atualiza T04 e T05 com esse ID
5. Pagamentos → Aprovar/Recusar
6. Fidelização → Cadastrar (obs: caso entregue o pedido antes de cadastrar na fidelização
não somara pontos, para isso cadastre para somar os pontos. )
7. Pedidos  → Entregar pedido


### Utilizando o JWT 
- Faça login em /auth/login
- Copie o accessToken
- Adicione no Postman:
Authorization
Bearer Token
SEU_TOKEN

Os tokens possuem validade de 24 horas.

## Exemplo

### 1. Request
POST http://localhost:8080/auth/login
{
"email": "admin@email.com",
"senha": "123456"
}

### 2. Copie o accessToken da resposta
{
"accessToken": "eyJhbGci...",
"tokenType": "Bearer"
}

### 3. Use o token nas requisições protegidas
No Postman:
- Clique em Authorization
- Type: Bearer Token
- Token: cole o accessToken aqui

Todas as rotas exceto /auth/login e /usuarios
requerem o token no header:
Authorization: Bearer {seu_token}

### Atenção — Tokens JWT
Os tokens JWT expiram em 24 horas.
Caso os testes retornem 401:

1. Execute T01 - Login Válido
2. Copie o accessToken da resposta
3. Cole o token em cada requisição:
  - Clique na requisição
  - Aba Authorization
  - Type: Bearer Token
  - Cole o token

## Limitações conhecidas

- A documentação Swagger/OpenAPI não foi implementada nesta versão.
- Os logs de auditoria permanecem como melhoria futura.