INSERT INTO branch (id, title) VALUES (1, 'Медозон-Ульяновск, ул. Карбышева. 5.1');
INSERT INTO branch (id, title) VALUES (2, 'Медозон-Ульяновск, ул. Стасова. 18');
INSERT INTO branch (id, title) VALUES (3, 'Медозон-Ульяновск, пр. Ульяновский, д.11');

ALTER SEQUENCE branch_seq RESTART WITH 4;

INSERT INTO photo (id, title) VALUES (1, 'Фото');

ALTER SEQUENCE photo_seq RESTART WITH 2;

INSERT INTO profession (id, title) VALUES (1, 'Учредитель');
INSERT INTO profession (id, title) VALUES (2, 'Администратор');
INSERT INTO profession (id, title) VALUES (3, 'Врач-гинеколог');
INSERT INTO profession (id, title) VALUES (4, 'Врач-отоларинголог');
INSERT INTO profession (id, title) VALUES (5, 'Врач-уролог');
INSERT INTO profession (id, title) VALUES (6, 'Врач-дерматолог');
INSERT INTO profession (id, title) VALUES (7, 'Врач-УЗД');

ALTER SEQUENCE profession_seq RESTART WITH 8;

