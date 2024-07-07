CREATE TABLE role(
    id SERIAL NOT NULL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

CREATE TABLE usuario_role(
    id SERIAL NOT NULL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY(usuario_id) REFERENCES usuario (id),
    FOREIGN KEY(role_id) REFERENCES role (id)
);

INSERT INTO role(nome) values ('ADMIN'), ('USER');