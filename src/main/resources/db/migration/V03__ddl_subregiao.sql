CREATE TABLE sub_regioes(
	id int not null primary key auto_increment,
	sub_regiao_id varchar(35) not null unique,
	nome varchar(35) not null unique,
	pais_id int not null,
	foreign key(pais_id) references paises(id)
);