# Raízes do Nordeste — API Back-end

API REST desenvolvida em Java/Spring Boot para gerenciamento 
da rede de lanchonetes Raízes do Nordeste.

## Requisitos
- Java 17
- MySQL 8
- Maven

## Como configurar

### 1. Clone o repositório
git clone https://github.com/FilipeMiguelsilva/raizes-do-nordeste.git

## Instalando dependências
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
mysql -u root -p raizes_do_nordeste < seed.sql

O seed cria:
- Usuário ADMIN: admin@email.com / senha: 123456
- Usuário CLIENTE: cliente@email.com / senha: 123456
- Unidade Centro (Vinhedo/SP)
- Produto: Hamburguer (R$ 25,00)
- Cardápio e Estoque configurados

### 5. Inicie a API
./mvnw spring-boot:run

A API sobe em: http://localhost:8080

### 6. Testes com Postman
Importe o arquivo da coleção disponível no repositório:
raizes-do-nordeste.postman_collection.json

Execute nessa ordem:
1. Auth → Login
2. Usuarios → Cadastrar
3. Pedidos → Criar pedido
4. Pagamentos → Aprovar/Recusar
5. Fidelização → Cadastrar

### 7. Credenciais de acesso
ADMIN:
  email: admin@email.com
  senha: 123456

CLIENTE:
  email: cliente@email.com
  senha: 123456

## Repositório
https://github.com/FilipeMiguelsilva/raizes-do-nordeste

## Como usar a API

### 1. Faça login para obter o token
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