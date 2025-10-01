INSERT INTO tb_cliente (id, nome, cpf, email, telefone) VALUES (1, 'Maria Joaquina', '111.222.333-44', 'maria.j@email.com', '41999998888');
INSERT INTO tb_cliente (id, nome, cpf, email, telefone) VALUES (2, 'João da Silva', '555.666.777-88', 'joao.silva@email.com', '41988889999');

INSERT INTO tb_endereco (id, id_cliente, cep, logradouro, numero, bairro, cidade, estado) VALUES (1, 1, '80050-350', 'Rua das Flores', '123', 'Jardim Botânico', 'Curitiba', 'PR');
INSERT INTO tb_endereco (id, id_cliente, cep, logradouro, numero, bairro, cidade, estado) VALUES (2, 2, '82590-300', 'Avenida Principal', '456', 'Bacacheri', 'Curitiba', 'PR');

INSERT INTO tb_tecnico (id, nome, matricula, telefone) VALUES (1, 'Carlos Alberto', 'T12345', '41977776666');

INSERT INTO tb_ordem_servico (id, id_cliente, id_endereco, id_tecnico, tipo, prioridade, status, dataAbertura) VALUES (1, 1, 1, 1, 'TROCA_MEDIDOR', 'ALTA', 'ABERTA', SYSDATE);

-- Cliente: 2 inserts
SELECT cliente_seq.NEXTVAL FROM DUAL
SELECT cliente_seq.NEXTVAL FROM DUAL

-- Endereco: 2 inserts
SELECT endereco_seq.NEXTVAL FROM DUAL
SELECT endereco_seq.NEXTVAL FROM DUAL

-- Tecnico: 1 insert
SELECT tecnico_seq.NEXTVAL FROM DUAL

-- Ordem de Servico: 1 insert
SELECT ordem_servico_seq.NEXTVAL FROM DUAL