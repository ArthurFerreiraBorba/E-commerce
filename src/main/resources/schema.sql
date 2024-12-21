CREATE TABLE IF NOT EXISTS perfis_usuarios (
    id SERIAL PRIMARY KEY,
    perfil VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS status_pedidos (
    id SERIAL PRIMARY KEY,
    status VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(60) NOT NULL,
    perfil_usuario_id BIGINT NOT NULL REFERENCES perfis_usuarios(id)
);

CREATE TABLE IF NOT EXISTS produtos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    descricao VARCHAR(1500) NOT NULL,
    imagem VARCHAR(500) NOT NULL,
    preco NUMERIC(8, 2) NOT NULL,
    estoque INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS pedidos (
    id SERIAL PRIMARY KEY,
    data_criacao DATE NOT NULL,
    preco_total NUMERIC(8, 2) NOT NULL,
    status_pedido_id BIGINT NOT NULL REFERENCES status_pedidos(id),
    usuario_id BIGINT NOT NULL REFERENCES usuarios(id)
);

CREATE TABLE IF NOT EXISTS itens_pedidos (
    id SERIAL PRIMARY KEY,
    preco NUMERIC(8, 2) NOT NULL,
    quantidade INTEGER NOT NULL,
    pedido_id BIGINT NOT NULL REFERENCES pedidos(id),
    produto_id BIGINT NOT NULL REFERENCES produtos(id)
);