CREATE TABLE Solicitações (
    id serial PRIMARY KEY,
    user_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    quantidade BIGINT NOT NULL,
    descrição VARCHAR(255) NOT NULL,
    status VARCHAR(10) NOT NULL,
    criado_em TIMESTAMP NOT NULL,
    atualizado_em TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Usuarios(id),
    FOREIGN KEY (item_id) REFERENCES Itens(id)
);
