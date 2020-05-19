/*======================TABELA DE USUARIOS=======================*/
INSERT INTO Usuarios(user_login, user_senha, user_nivel, user_habilitado) VALUES('admin', 'admin', 1, true);
INSERT INTO Usuarios(user_login, user_senha, user_nivel, user_habilitado) VALUES('evandro', '123', 1, true);
INSERT INTO Usuarios(user_login, user_senha, user_nivel, user_habilitado) VALUES('joao', '123'   , 1, true);
INSERT INTO Usuarios(user_login, user_senha, user_nivel, user_habilitado) VALUES('daniel', '123' , 1, true);
INSERT INTO Usuarios(user_login, user_senha, user_nivel, user_habilitado) VALUES('ze', '123'     , 0, true);
INSERT INTO Usuarios(user_login, user_senha, user_nivel, user_habilitado) VALUES('ana', '123'    , 0, false);

/*====================TABELA DE FUNCIONARIOS=====================*/
INSERT INTO Funcionarios(fun_cod, fun_nome, fun_datanascimento, fun_telefone, fun_salario) VALUES(DEFAULT, 'Evandro', '10/02/1999', '(18)99105-8156', 2000.00); 
INSERT INTO Funcionarios(fun_cod, fun_nome, fun_datanascimento, fun_telefone, fun_salario) VALUES(DEFAULT, 'Carlos', '31/01/2000', '(12)99205-4156', 1356.75);
INSERT INTO Funcionarios(fun_cod, fun_nome, fun_datanascimento, fun_telefone, fun_salario) VALUES(DEFAULT, 'Ana', '15/07/1997', '(12)45678-9988', 1532);

/*========================Tabela cliente=========================*/
insert into cliente(cli_cod, cli_nome, cli_cpf, cli_email, cli_fone) values (default, 'Jorginho', '321.321.456-65', 'jorginho@gmail.com', '(34)94324-4323');
insert into cliente(cli_cod, cli_nome, cli_cpf, cli_email, cli_fone) values (default, 'Carlos', '345.456.067-68', 'carlos@gmail.com', '(65)94678-7863');
insert into cliente(cli_cod, cli_nome, cli_cpf, cli_email, cli_fone) values (default, 'Alberto', '895.345.768-78', 'alberto@gmail.com', '(65)95435-5663');

