create table curso
(
    id        bigint not null generated by default as identity primary key,
    nome      varchar(255) not null,
    categoria varchar(255) not null
);

insert into curso(nome, categoria) VALUES ('Kotlin', 'Programação');