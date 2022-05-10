CREATE TABLE sub_regioes(
	id int not null primary key auto_increment,
	pais_id varchar(35) not null unique
	nome varchar(125) not null unique,
	capital varchar(45) not null unique,
	area decimal(4,2) not null,
	regiao_id int not null,
	foreign key(pais_id) references regioes(id)
);