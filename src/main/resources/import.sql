INSERT INTO categoria(nome, ativo) VALUES ('Adulto', true), ('Filhote', true), ('Adulto Castrado', true),
                                          ('Acima do Peso', false),('Saborizado', true);

INSERT INTO raca(nome, ativo) VALUES ('Husky Siberiano', true), (' Fila Brasileiro', true), ('Fox Paulistinha', true),
                                          ('Dálmata', false),('Doberman', true);

INSERT INTO pessoa(nome) VALUES ('Maria');
INSERT INTO pessoafisica(id, cpf, email, data_nascimento) VALUES (1, '47652930090', 'maria@gmail', '2002-10-10');
INSERT INTO usuario(login, senha, id_pessoa_fisica, ativo) VALUES ('maria', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==', 1, true);

INSERT INTO  perfis (id_usuario, perfil) VALUES (1, 'Admin');
INSERT INTO  perfis (id_usuario, perfil) VALUES (1, 'Comum');


INSERT INTO  especie (nome, ativo) VALUES ('Gato',true);
INSERT INTO  especie (nome, ativo) VALUES ('Cachorro',true);
INSERT INTO  especie (nome, ativo) VALUES ('Pinguim',true);

INSERT INTO pessoa(nome) VALUES ('João');
INSERT INTO pessoafisica(id, cpf, email, data_nascimento) VALUES (2, '05319824144', 'joaao@gmail', '2002-10-05');
INSERT INTO usuario(login, senha, id_pessoa_fisica, ativo) VALUES ('joao', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==', 2, true);

INSERT INTO  perfis (id_usuario, perfil) VALUES (2, 'Funcionario');
INSERT INTO  perfis (id_usuario, perfil) VALUES (2, 'Comum');