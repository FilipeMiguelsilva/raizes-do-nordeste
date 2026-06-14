-- Usuarios
INSERT INTO usuario (id, nome, email, senha, perfil, status, data_cadastro) VALUES
(6, 'Admin', 'admin@email.com', '$2a$10$ibwMAOTODj8IcV6Nl9UOZO7uwqfpnj0sGh/9Ii3zMKW.0Wp3BYWoi', 'ADMIN', 1, NULL),
(7, 'Cliente Teste', 'cliente@email.com', '$2a$10$jKGwNnJNgTbjjZJ4Ox1yv.8CVoTH9849Pqqs2fRvD1292xvdoT.xa', 'CLIENTE', 1, NULL);

-- Unidade
INSERT INTO unidade (id, nome, endereco, cidade, estado, telefone, status) VALUES
(1, 'Unidade Centro', 'Rua X', 'Vinhedo', 'SP', '11999999999', 1);

-- Produto
INSERT INTO produto (id, nome, descricao, categoria, preco, status) VALUES
(1, 'Hamburguer', 'Artesanal', 'LANCHE', 25.00, 1);

-- Cardapio
INSERT INTO cardapio (id, id_produto, id_unidade, status) VALUES
(1, 1, 1, 1);

-- Estoque
INSERT INTO estoque (id, id_produto, id_unidade, quantidade) VALUES
(1, 1, 1, 44);