create table usuario
(
    id    bigint not null generated by default as identity primary key,
    nome  varchar(255) not null,
    email varchar(255) not null
);