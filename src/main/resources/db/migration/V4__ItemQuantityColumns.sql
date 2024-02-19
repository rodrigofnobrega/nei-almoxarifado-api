ALTER TABLE Itens 
ADD COLUMN quantidade_disponiveis integer NOT NULL default 1;

ALTER TABLE Itens
ADD COLUMN quantidade_emprestados integer NOT NULL default 1;

ALTER TABLE Itens
DROP COLUMN disponivel;