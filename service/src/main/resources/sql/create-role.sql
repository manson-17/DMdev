CREATE TABLE atp.employee_role
(
    employee_id BIGINT NOT NULL,
    role_id     BIGINT NOT NULL,
    CONSTRAINT pk_employee_role PRIMARY KEY (employee_id, role_id)
);

CREATE TABLE atp.roles
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

ALTER TABLE atp.employee_role
    ADD CONSTRAINT fk_emprol_on_employee FOREIGN KEY (employee_id) REFERENCES atp.driver (id);

ALTER TABLE atp.employee_role
    ADD CONSTRAINT fk_emprol_on_role FOREIGN KEY (role_id) REFERENCES atp.roles (id);