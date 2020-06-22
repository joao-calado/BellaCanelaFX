<<<<<<< HEAD
CREATE TABLE Funcionarios (
	fun_cod SERIAL,
	fun_nome varchar(50) NOT NULL,
	fun_datanascimento DATE NOT NULL,
	fun_telefone varchar(14) NOT NULL,
	fun_salario decimal(10,2),
	CONSTRAINT pk_fun PRIMARY KEY (fun_cod)
);

CREATE TABLE Usuarios (
	user_login varchar(16),
	user_senha varchar(32),
        user_nivel INTEGER,
        user_habilitado BOOLEAN,
	CONSTRAINT pk_usuarios PRIMARY KEY (user_login)
);

CREATE TABLE Mesa (
    mes_cod SERIAL,

    mes_liberada BOOLEAN NOT NULL,

    CONSTRAINT pk_mes PRIMARY KEY (mes_cod)
);

CREATE TABLE Comanda (
    com_num INTEGER NOT NULL,
    mes_cod INTEGER NOT NULL,

    cli_cod INTEGER NOT NULL,
    com_data DATE NOT NULL,
    com_descricao VARCHAR(200),
    com_aberta BOOLEAN,

    CONSTRAINT pk_com PRIMARY KEY (com_num, mes_cod),
    CONSTRAINT fk_mes_com FOREIGN KEY (mes_cod) REFERENCES Mesa,
    CONSTRAINT fk_cli_com FOREIGN KEY (cli_cod) REFERENCES Cliente
);

CREATE TABLE ItensDaComanda (
    com_num INTEGER NOT NULL,
    mes_cod INTEGER NOT NULL,
    prod_cod INTEGER NOT NULL,

    itens_qtde INTEGER NOT NULL,
    
    CONSTRAINT pk_itens PRIMARY KEY (com_num, mes_cod, prod_cod),
    CONSTRAINT fk_com_itens FOREIGN KEY (com_num, mes_cod) REFERENCES Comanda,
    CONSTRAINT fk_prod_itens FOREIGN KEY (prod_cod) REFERENCES Produto
);

CREATE TABLE BaixaDeProduto (
    bai_cod SERIAL,
    
    prod_cod INTEGER NOT NULL,
    fun_cod INTEGER NOT NULL,
    bai_qtde INTEGER NOT NULL,
    bai_data DATE NOT NULL,
    bai_motivo VARCHAR(200),

    CONSTRAINT pk_baixa PRIMARY KEY (bai_cod),
    CONSTRAINT fk_baifun FOREIGN KEY (fun_cod) REFERENCES Funcionarios,
    CONSTRAINT fk_baiprod FOREIGN KEY (prod_cod) REFERENCES Produto
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
    prod_preco decimal(10,2),	
    prod_avenda character(1),
    prod_estoque integer,
    CONSTRAINT pk_prod PRIMARY KEY (prod_cod),	
    CONSTRAINT fK_prodcat FOREIGN KEY (prod_cat) REFERENCES Categoria,
    CONSTRAINT fK_prodmed FOREIGN KEY (prod_med) REFERENCES Medida    
);

/* <jão> */

create table cliente(
    
    cli_cod SERIAL,
    cli_nome varchar(50) not null,
    cli_cpf varchar(14) not null,
    cli_email varchar(30) not null,
    cli_fone varchar(14) not null,
    constraint pk_cli primary key (cli_cod)
);

create table parametrizacao (
    
    par_cod SERIAL,
    par_nome varchar(30) not null,
    par_cep varchar(10) not null,
    par_endereco varchar(50) not null,
    par_cidade varchar(20) not null,
    par_uf varchar(2) not null,
    par_cnpj varchar(18) not null,
    par_razao_social varchar(50) not null,
    par_fone varchar(14) not null,
    par_email varchar(30) not null,
    par_cor1 varchar(7),
    par_cor2 varchar(7),
    par_icone bytea,
    constraint pk_par primary key(par_cod)
);

create table recebimento (
    
    rec_cod serial,
    rec_cli integer,
    rec_tipo varchar(20),
    rec_valor decimal(10,2),
    rec_recebimento date not null,
    rec_vencimento date,
    rec_status varchar(1),
    check (rec_status='S' or rec_status='N'),
    constraint pk_rec primary key(rec_cod),
    constraint fk_cli foreign key(rec_cli) references cliente
);

/* </jão> */

CREATE TABLE notaFiscal (
        not_cod SERIAL,
        not_NF varchar(50) NOT NULL,
	not_fornecedor integer NOT NULL,
	not_desc varchar(50),
	not_vencimento date,
	not_parcelas integer NOT NULL,
	not_total decimal(10,2)NOT NULL,

    CONSTRAINT pk_not PRIMARY KEY (not_cod)
);

CREATE TABLE itensNF (
    ite_cod SERIAL,
    ite_notafiscal integer NOT NULL,
    ite_produto integer NOT NULL,
    ite_qtde integer NOT NULL,
    ite_preco decimal(10,2)NOT NULL,
    ite_total decimal(10,2)NOT NULL,
	
    CONSTRAINT pk_ite PRIMARY KEY (ite_cod),
	CONSTRAINT fk_iteprod FOREIGN KEY (ite_notafiscal) REFERENCES notaFiscal
);

create table Pagamentos (
	pag_cod SERIAL,
	pag_parcela integer NOT NULL,
	pag_desc varchar(50),
	pag_valor decimal(10,2)NOT NULL,
	pag_valorpago decimal(10,2),
	pag_DesJur decimal(10,2),
	pag_vencimento date NOT NULL,
	pag_pagamento date,
	
	CONSTRAINT pk_pag PRIMARY KEY (pag_cod)
);


>>>>>>> 45a86786c15eb411328c09ee115319f344ba7cc7
