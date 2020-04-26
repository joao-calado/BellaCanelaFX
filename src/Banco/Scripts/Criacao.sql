CREATE TABLE Funcionarios (
	fun_cod SERIAL,
	fun_nome varchar(50) NOT NULL,
	fun_idade INTEGER NOT NULL,
	fun_telefone varchar(11) NOT NULL,
	fun_salario decimal(10,2),
	CONSTRAINT pk_fun PRIMARY KEY (fun_cod)
);

CREATE TABLE Usuarios (
	user_login varchar(16),
	user_senha varchar(32),
	CONSTRAINT pk_usuarios PRIMARY KEY (user_login)
);

create table cliente(
    cli_cod SERIAL,
    cli_nome varchar(50) not null,
    cli_cpf varchar(14) not null,
    cli_email varchar(30) not null,
    cli_fone varchar(14) not null,
    constraint pk_cli primary key (cli_cod)
);

CREATE TABLE Fornecedor (
    for_cod SERIAL,
    for_nome varchar(50) NOT NULL,	
    for_telefone varchar(11) NOT NULL,
    for_email varchar(30) not null,
    for_desc varchar(100) NOT NULL,
    CONSTRAINT pk_for PRIMARY KEY (for_cod)
);


CREATE TABLE Categoria (
    cat_cod SERIAL,
    cat_nome varchar(50) NOT NULL,
    CONSTRAINT pk_cat PRIMARY KEY (cat_cod)
);

CREATE TABLE Medida (
    med_cod SERIAL,
    med_nome varchar(50) NOT NULL,
    CONSTRAINT pk_med PRIMARY KEY (med_cod)
);


CREATE TABLE Produto (
    prod_cod SERIAL,
    prod_nome varchar(50) NOT NULL,
    prod_cat INTEGER NOT NULL,
    prod_med INTEGER NOT NULL,	
    CONSTRAINT pk_prod PRIMARY KEY (prod_cod),	
    CONSTRAINT fK_prodcat FOREIGN KEY (prod_cat) REFERENCES Categoria,
    CONSTRAINT fK_prodmed FOREIGN KEY (prod_med) REFERENCES Medida    
);