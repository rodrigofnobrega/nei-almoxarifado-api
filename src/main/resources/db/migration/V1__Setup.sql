CREATE TABLE Roles (
        id serial primary key,
        encargo varchar(50) not null
);

CREATE TABLE Itens (
        id serial primary key,
        nome varchar(255) not null,
        tombamento bigint not null,
        disponivel boolean not null default true
);

CREATE TABLE Usuarios (
        id serial primary key,
        nome varchar(255) not null,
        email varchar(150) not null,
        password_hash varchar(255) not null,
        id_role integer,

        FOREIGN KEY (id_role) REFERENCES Roles (id)
);

CREATE TABLE Registro (
        id serial primary key,
        id_usuario integer,
        id_item integer,
        data timestamp default NOW(),

        FOREIGN KEY (id_usuario) REFERENCES Usuarios (id),
        FOREIGN KEY (id_item) REFERENCES Itens (id)
);