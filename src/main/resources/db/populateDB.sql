DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories) VALUES
  (100000, '2020-06-23 15:06:01', 'Описание еды', 15),
  (100001, '2020-06-23 15:06:01', 'Админская еда', 30),
  (100001, '2020-06-22 23:59:59', 'Админская еда прошлого дня', 150),
  (100001, '2020-06-23 00:00:00', 'Админская еда в начале суток', 100),
  (100001, '2020-06-23 23:59:00', 'Админская еда в 23:59:00', 1000),
  (100001, '2020-06-23 23:59:59', 'Админская еда в 23:59:59', 270),
  (100001, '2020-06-23 23:59:59', 'Админская еда в 23:59:59.999999', 600),
  (100001, '2020-06-24 13:00:05', 'Админская еда следующего дня', 500);