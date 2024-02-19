--liquibase formatted sql
--changeset flmaster:1


CREATE TABLE element_type
(
    id   integer primary key autoincrement,
    name text not null unique
        check ( length(name) > 0 )
);
CREATE TABLE element
(
    id      integer primary key autoincrement,
    name    text    not null unique
        check ( length(name) > 0 ),
    level   integer not null
        check ( level >= 0 ),
    score   integer not null
        check ( score > 0 ),
    type_id integer not null references element_type (id)

);
CREATE TABLE exercise
(
    id   integer primary key autoincrement,
    name text not null unique
        check ( length(name) > 0 )
);

CREATE TABLE category
(
    id    integer primary key autoincrement,
    name  text    not null unique
        check ( length(name) > 0 ),
    level integer not null
        check ( level >= 0 ),
    requirement_min_combo integer not null
        check ( requirement_min_combo > 0 ),
    requirement_min_elements_of_same_level integer not null
        check ( requirement_min_elements_of_same_level > 0 ),
    requirement_min_female_score integer not null
        check ( requirement_min_female_score > 0 ),
    requirement_min_male_score integer not null
        check ( requirement_min_male_score > 0 )
);

CREATE TABLE category_exercise_requirement
(
    category_id integer not null references category (id),
    exercise_id integer not null references exercise (id),
    count       integer not null
        check ( count > 0 ),
    primary key (category_id, exercise_id)
);