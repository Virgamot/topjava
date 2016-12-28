DROP INDEX IF EXISTS idx_id;
DROP INDEX IF EXISTS idx_uid;
DROP INDEX IF EXISTS idx_time;
DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;
DROP SEQUENCE IF EXISTS global_seq_meal;


CREATE SEQUENCE global_seq START 100000;
CREATE SEQUENCE global_seq_meal START 100000;

CREATE TABLE users
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR              NOT NULL,
  email            VARCHAR              NOT NULL,
  password         VARCHAR              NOT NULL,
  registered       TIMESTAMP           DEFAULT now(),
  enabled          BOOL                DEFAULT TRUE,
  calories_per_day INTEGER DEFAULT 2000 NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
  ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE meals
(
  id          INTEGER   DEFAULT nextval('global_seq_meal'),
  user_id     INTEGER           NOT NULL,
  description VARCHAR           NOT NULL,
  date        TIMESTAMP DEFAULT now(),
  calories    INTEGER DEFAULT 0 NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX idx_id
  ON meals (id);
CREATE INDEX idx_uid
  ON meals (user_id);
CREATE INDEX idx_time
  ON meals (date);
