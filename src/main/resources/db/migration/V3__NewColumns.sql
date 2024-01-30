-- Roles
ALTER TABLE Roles
ADD COLUMN criado_em timestamp not null default now();
ALTER TABLE Roles
ADD COLUMN atualizado_em timestamp not null default now();
ALTER TABLE Roles
ADD COLUMN ativo boolean not null default true;

-- Usuários
ALTER TABLE Usuarios
ADD COLUMN criado_em timestamp not null default now();
ALTER TABLE Usuarios
ADD COLUMN atualizado_em timestamp not null default now();
ALTER TABLE Usuarios
ADD COLUMN ativo boolean not null default true;

-- Itens
ALTER TABLE Itens
ADD COLUMN criado_em timestamp not null default now();
ALTER TABLE Itens
ADD COLUMN atualizado_em timestamp not null default now();
ALTER TABLE Itens
ADD COLUMN ativo boolean not null default true;