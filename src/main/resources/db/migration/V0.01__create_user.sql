CREATE SEQUENCE seq_users_id
    START 1;

CREATE TABLE users (
    id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('seq_users_id') ,
    username VARCHAR(32) NOT NULL UNIQUE ,
    firstname VARCHAR(32) ,
    lastname VARCHAR(32) ,
    email VARCHAR(64) NOT NULL UNIQUE ,
    password VARCHAR(32) NOT NULL ,
    created_date DATE DEFAULT CURRENT_DATE,
    last_updated_date DATE DEFAULT CURRENT_DATE
);

-- COMMENT ON TABLE users IS 'Таблица пользователей';
-- COMMENT ON COLUMN users.id IS 'Идентификатор';
-- COMMENT ON COLUMN users.username IS 'Идентификатор';
-- COMMENT ON COLUMN users.firstname IS 'Идентификатор';
-- COMMENT ON COLUMN users.lastname IS 'Идентификатор';
-- COMMENT ON COLUMN users.email IS 'Идентификатор';
-- COMMENT ON COLUMN users.password IS 'Идентификатор';
-- COMMENT ON COLUMN users.created IS 'Идентификатор';

CREATE SEQUENCE seq_workflow_id
    START 1;

CREATE TABLE workflow (
   id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('seq_workflow_id') ,
   gcal_id VARCHAR(254) ,
   summary VARCHAR(254) NOT NULL,
   description VARCHAR(200) ,
   timeZone VARCHAR(64),
   color VARCHAR(7)
);

CREATE TYPE enum_access_role AS ENUM ('none', 'reader', 'writer', 'owner');

CREATE TABLE workflow_user_access_role (
    user_id BIGINT REFERENCES users(id) NOT NULL ,
    workflow_id BIGINT REFERENCES workflow(id) NOT NULL ,
    role enum_access_role DEFAULT 'none' NOT NULL
);

CREATE SEQUENCE seq_project_id
    START 1;


CREATE TABLE project (
    id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('seq_project_id') ,
    workflow_id BIGINT REFERENCES workflow(id) NOT NULL ,
    summary VARCHAR(254) NOT NULL,
    description VARCHAR(200),
    color VARCHAR(7)
);

CREATE SEQUENCE seq_project_statistics_id
    START 1;

CREATE TABLE project_statistics (
    id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('seq_project_statistics_id') ,
    project_id BIGINT REFERENCES project(id) NOT NULL ,
    date DATE NOT NULL DEFAULT CURRENT_DATE ,
    time TIME WITHOUT TIME ZONE NOT NULL DEFAULT '00:00:00'
);

CREATE SEQUENCE seq_event_id
    START 1;

CREATE TABLE event (
    id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('seq_event_id') ,
    project_id BIGINT REFERENCES project(id) NOT NULL ,
    gcal_event_id VARCHAR(32) ,
    ical_uid VARCHAR(26) ,
    summary VARCHAR(254) NOT NULL,
    description VARCHAR CHECK (length(description) <= 8192),
    created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP ,
    updated TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP ,
    creator BIGINT REFERENCES users(id) NOT NULL ,
    schedule_start TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP ,
    schedule_end TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    user_start TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP ,
    user_end TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    finished BOOLEAN NOT NULL DEFAULT FALSE,
    recurrence TEXT[],
    recurring_event_id BIGINT REFERENCES event(id) NOT NULL,
    color VARCHAR(7)
);
