-- Inserindo clientes
INSERT INTO tb_cliente (nome, cpf, senha) VALUES ('João Silva', '111.111.111-11', '111');
INSERT INTO tb_cliente (nome, cpf, senha) VALUES ('Maria Souza', '222.222.222-22', '222');
INSERT INTO tb_cliente (nome, cpf, senha) VALUES ('Carlos Pereira', '333.333.333-33', '333');

-- Inserindo contas
-- Agora com os saldos já ajustados considerando os lançamentos
INSERT INTO tb_conta (numero, id_cliente, saldo) VALUES ('JS-000001', 1, 1800.00);
INSERT INTO tb_conta (numero, id_cliente, saldo) VALUES ('JS-000002', 1, 3350.00);
INSERT INTO tb_conta (numero, id_cliente, saldo) VALUES ('MS-000003', 2, 2900.00);
INSERT INTO tb_conta (numero, id_cliente, saldo) VALUES ('CP-000004', 3, 700.00);
INSERT INTO tb_conta (numero, id_cliente, saldo) VALUES ('CP-000005', 3, 1500.00);

-- Inserindo lançamentos
-- Supondo que as contas receberam ids de 1 a 5
INSERT INTO tb_lancamento (valor, id_conta, tipo) VALUES (200.00, 1, 'SAQUE');
INSERT INTO tb_lancamento (valor, id_conta, tipo) VALUES (500.00, 1, 'DEPOSITO');
INSERT INTO tb_lancamento (valor, id_conta, tipo) VALUES (150.00, 2, 'SAQUE');
INSERT INTO tb_lancamento (valor, id_conta, tipo) VALUES (1000.00, 2, 'DEPOSITO');
INSERT INTO tb_lancamento (valor, id_conta, tipo) VALUES (300.00, 3, 'SAQUE');
INSERT INTO tb_lancamento (valor, id_conta, tipo) VALUES (200.00, 4, 'DEPOSITO');
INSERT INTO tb_lancamento (valor, id_conta, tipo) VALUES (100.00, 5, 'SAQUE');
INSERT INTO tb_lancamento (valor, id_conta, tipo) VALUES (400.00, 5, 'DEPOSITO');