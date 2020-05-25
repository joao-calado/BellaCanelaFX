/*
 * Author:  joao
 * Created: 26/04/2020
 */

alter table parametrizacao alter column par_cor1 type varchar(10);
alter table parametrizacao alter column par_cor1 type varchar(10);

alter table recebimento add rec_mesa integer;

alter table Comanda add com_aberta boolean;