CREATE TYPE CLIMATE AS ENUM ('MONSOON', 'HUMIDCONTINENTAL', 'MEDITERRANIAN', 'TUNDRA');
CREATE TYPE GOVERNMENT AS ENUM ('KRITARCHY', 'PUPPET_STATE', 'JUNTA');
CREATE TYPE STANDARD_OF_LIVING AS ENUM ('VERY_HIGH', 'HIGH', 'LOW', 'VERY_LOW');

CREATE TABLE users (
    username text PRIMARY KEY,
    password_hash text NOT NULL
);

CREATE TABLE coordinates (
    id serial PRIMARY KEY,
    x bigint NOT NULL,
    y bigint NOT NULL,
    element_id integer UNIQUE REFERENCES collection(id)
);

CREATE TABLE human (
    id serial PRIMARY KEY,
    name text NOT NULL,
    age integer NOT NULL,
    birthday timestamp,
    element_id integer UNIQUE REFERENCES collection(id)
);

CREATE TABLE collection (
    username text REFERENCES users(username),
    key integer NOT NULL,
    id serial PRIMARY KEY,
    name text NOT NULL,
    creation_date DATE NOT NULL,
    creation_date_hash integer NOT NULL,
    area decimal NOT NULL,
    population integer NOT NULL,
    meters_above_sea_level integer,
    climate CLIMATE NOT NULL,
    government GOVERNMENT NOT NULL,
    standard_of_living STANDARD_OF_LIVING NOT NULL
);



