CREATE TABLE RecuperacaoTokens ( 
  id_usuario int primary key references Usuarios(id),
  token varchar(255) not null,
  usado boolean not null default false,
  criado_em timestamp not null default now(),
  alterado_em timestamp not null default now(),
  valido_ate timestamp not null default now()
);