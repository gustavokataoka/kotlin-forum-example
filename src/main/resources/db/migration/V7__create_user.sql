-- Senha: 123456
insert into usuario (nome, email, password)
values ('Gustavo Kataoka', 'gustavo.kataoka@gmail.com', '$2y$10$u7rzjXER7TAUyOEohK55neJZd9J8B3Cs602pImJFJe89syc.u7kqe'),
       ('Gustavo', 'gustavo.kataoka@ufms.br', '$2y$10$u7rzjXER7TAUyOEohK55neJZd9J8B3Cs602pImJFJe89syc.u7kqe');

insert into usuario_role(usuario_id, role_id)
values (1, 2);
