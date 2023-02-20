CREATE TABLE atp.driver
(
    id         BIGINT NOT NULL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    CONSTRAINT pk_driver PRIMARY KEY (id)
);