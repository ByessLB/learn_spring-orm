
DROP TABLE if exists public.client CASCADE;
DROP TABLE if exists public.account CASCADE;

/* TODO : ajouter la création de la table "client" */

CREATE TABLE client (
	id UUID PRIMARY KEY,
	first_name VARCHAR(100),
	last_name VARCHAR(50),
	birthdate DATE,
	email VARCHAR(50) UNIQUE
);

CREATE TABLE account (
	id SERIAL PRIMARY KEY,
	creationTime TIMESTAMP,
	balance NUMERIC(25,2),
	active BOOLEAN,
	client_id UUID,
	FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE Insurance(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50)  NOT NULL
);

CREATE TABLE subsribes(
    id_client UUID PRIMARY KEY,
    id_insurance PRIMARY KEY,
    FOREIGN KEY(id_client) REFERENCES Client(id),
    FOREIGN KEY(id_insurance) REFERENCES Insurance(id)
);
