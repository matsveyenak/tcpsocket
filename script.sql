CREATE TABLE users (
   id bigserial PRIMARY KEY,
   nickname text NOT NULL,
   login text UNIQUE NOT NULL,
   password text NOT NULL,
   insert_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE vehicles (
   id bigserial PRIMARY KEY,
   user_id bigint REFERENCES users(id) ,
   brand text NOT NULL,
   model text NOT NULL,
   insert_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE insurers (
   id bigserial PRIMARY KEY,
   name text NOT NULL,
   email text NOT NULL,
   phone_number text NOT NULL,
   insert_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE insurance_offers (
   id bigserial PRIMARY KEY,
   vehicle_id bigint REFERENCES vehicles(id) ,
   insurer_id bigint REFERENCES insurers(id) ,
   price text NOT NULL,
   start_date date,
   end_date date,
   insert_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (nickname, login, password) VALUES ('Cameron Michaels', 'cammichaels', '123456');
INSERT INTO users (nickname, login, password) VALUES ('Eureka OHara', 'eureka', '091006');
INSERT INTO users (nickname, login, password) VALUES ('Valentina', 'valentina', 's09as04');

INSERT INTO vehicles(user_id, brand, model)
    VALUES (1, 'Audi', 'A7 Sportback');
INSERT INTO vehicles(user_id, brand, model)
    VALUES (2, 'Volvo', 'C40 Recharge Pure Electric');
INSERT INTO vehicles(user_id, brand, model)
    VALUES (2, 'Volvo', 'XC60');
INSERT INTO vehicles(user_id, brand, model)
    VALUES (2, 'Volkswagen', 'Touareg');
INSERT INTO vehicles(user_id, brand, model)
    VALUES (3, 'Toyota', 'Camry');
INSERT INTO vehicles(user_id, brand, model)
    VALUES (3, 'Citroen', 'C5 Aircross');

INSERT INTO insurers(name, email, phone_number)
    VALUES ('Wells Insurance', 'wellsins@gmail.com', '+80 450-44-11');
INSERT INTO insurers(name, email, phone_number)
    VALUES ('Liberty', 'liberty@gmail.com', '+74 123-12-12');

INSERT INTO insurance_offers(vehicle_id, insurer_id, price, start_date, end_date)
    VALUES (1, 1, 320.0, '2022-05-18', '2024-05-18');
INSERT INTO insurance_offers(vehicle_id, insurer_id, price, start_date, end_date)
    VALUES (1, 2, 350.5, '2022-05-18', '2024-05-18');
INSERT INTO insurance_offers(vehicle_id, insurer_id, price, start_date, end_date)
    VALUES (2, 1, 290.0, '2022-06-01', '2023-06-01');
INSERT INTO insurance_offers(vehicle_id, insurer_id, price, start_date, end_date)
    VALUES (2, 2, 335.0, '2022-06-15', '2023-12-15');
INSERT INTO insurance_offers(vehicle_id, insurer_id, price, start_date, end_date)
    VALUES (3, 2, 550.0, '2022-05-26', '2025-05-26');
INSERT INTO insurance_offers(vehicle_id, insurer_id, price, start_date, end_date)
    VALUES (4, 1, 190.0, '2022-05-20', '2022-11-20');
INSERT INTO insurance_offers(vehicle_id, insurer_id, price, start_date, end_date)
    VALUES (5, 1, 475.25, '2022-05-18', '2024-05-18');
INSERT INTO insurance_offers(vehicle_id, insurer_id, price, start_date, end_date)
    VALUES (5, 2, 480.5, '2022-05-18', '2024-05-18');