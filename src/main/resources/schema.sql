CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    UNIQUE (first_name, last_name)
);

CREATE TABLE IF NOT EXISTS credentials (
    user_id uuid REFERENCES users(id) ON DELETE CASCADE,
    username VARCHAR(10) NOT NULL UNIQUE,
    hashed_password VARCHAR(60) NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
    id INTEGER PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price NUMERIC(2) NOT NULL,
    amount INTEGER NOT NULL,
    image_paths VARCHAR(255) NOT NULL
);