INSERT INTO categoria(nome, ativo) VALUES ('Adulto', true), ('Filhote', true), ('Adulto Castrado', true),
                                          ('Acima do Peso', false),('Saborizado', true);

INSERT INTO raca(nome, ativo) VALUES ('Husky Siberiano', true), (' Fila Brasileiro', true), ('Fox Paulistinha', true),
                                          ('Dálmata', false),('Doberman', true);

INSERT INTO pessoa(nome) VALUES ('Maria');
INSERT INTO pessoafisica(id, cpf, email, data_nascimento) VALUES (1, '47652930090', 'maria@gmail', '2002-10-10');
INSERT INTO usuario(login, senha, id_pessoa_fisica, ativo) VALUES ('maria', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==', 1, true);

INSERT INTO  perfis (id_usuario, perfil) VALUES (1, 'Admin');
INSERT INTO  perfis (id_usuario, perfil) VALUES (1, 'Comum');

INSERT INTO pessoa(nome) VALUES ('João');
INSERT INTO pessoafisica(id, cpf, email, data_nascimento) VALUES (2, '05319824144', 'joaao@gmail', '2002-10-05');
INSERT INTO usuario(login, senha, id_pessoa_fisica, ativo) VALUES ('joao', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==', 2, true);

INSERT INTO perfis (id_usuario, perfil) VALUES (2, 'Funcionario');
INSERT INTO perfis (id_usuario, perfil) VALUES (2, 'Comum');

insert into metododerecebimento(id) values (default);
insert into metododerecebimento(id) values (default);
insert into metododerecebimento(id) values (default);
insert into metododerecebimento(id) values (default);
insert into metododerecebimento(id) values (default);
insert into metododerecebimento(id) values (default);
insert into metododerecebimento(id) values (default);
insert into metododerecebimento(id) values (default);

insert into pixrecebimento(id, chave, tipo_chave, ativo) values (1, '63984883349', 4, true);
insert into pixrecebimento(id, chave, tipo_chave, ativo) values (2, 'petisco@gmail.com', 3, false);
insert into pixrecebimento(id, chave, tipo_chave, ativo) values (3, '000000213233', 2, false);
insert into pixrecebimento(id, chave, tipo_chave, ativo) values (4, 'petiscopetisco', 1, false);

insert into boletorecebimento(id, banco, nome, cnpj, agencia, conta, ativo) values (5, '123', 'Pet Isco Comércio e Participações S/A', '111233232231', '12345', '1234', true);
insert into boletorecebimento(id, banco, nome, cnpj, agencia, conta, ativo) values (6, '123', 'Pet Isco LTDA', '111233232231', '12345', '1234', false);
insert into boletorecebimento(id, banco, nome, cnpj, agencia, conta, ativo) values (7, '001', 'Pet Isco LTDA', '111233232231', '53412', '3412', false);
insert into boletorecebimento(id, banco, nome, cnpj, agencia, conta, ativo) values (8, '001', 'Pet Isco LTDA - S/A', '111233232231', '53412', '3412', false);