CREATE TABLE IF NOT EXISTS dm_dev.public.trip
(
    type                  VARCHAR(31) NOT NULL,
    id                    BIGSERIAL PRIMARY KEY,
    email                 VARCHAR(255) UNIQUE,
    firstname             VARCHAR(255),
    lastname              VARCHAR(255),
    password              VARCHAR(255),
    role                  VARCHAR(255),
    category              VARCHAR(255),
    driving_rights_number VARCHAR(255)
);