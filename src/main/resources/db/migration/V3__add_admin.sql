INSERT INTO users (id, address, archive, email, fio, name, password, role, telephone, branch_id, photo_id)
VALUES (1, 'г. Инза, ул. Мира, д.1', false, 'admin@eshop.ru', 'Иванов Иван Иванович', 'admin',
        '$2a$10$/15YGbeq9mYTyzo6rDKxfOC9mVJYgUmPBMDTooctlO/KvUoEsPF4G', 'OWNER', '79991234567', 1, 1);

ALTER SEQUENCE user_seq RESTART WITH 2;