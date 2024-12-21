INSERT INTO perfis_usuarios (perfil)
VALUES
('administrador'),
('cliente')
ON CONFLICT (perfil) DO NOTHING;

INSERT INTO status_pedidos (status)
VALUES
('novo'),
('finalizado'),
('cancelado')
ON CONFLICT (status) DO NOTHING;


INSERT INTO usuarios (nome, email, senha, perfil_usuario_id)
SELECT 'administrador', 'administrador@gmail.com', '$2a$09$p7ay7ydkqUwN/xe73uqn3.OwvqVoAkuGZeNZ6XgwMBfHAvzmQphBq', 1
WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE perfil_usuario_id = 1
);