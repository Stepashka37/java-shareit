drop table if exists items, bookings, users,  requests, comments cascade ;
drop type if exists booking_status cascade;


create table if not exists users
(
    id    serial
        constraint users_pk
            primary key,
    name  varchar(50) not null,
    email varchar(50)
);

alter table if exists  users
    owner to postgres;

create unique index if not exists  users_email_uindex
    on users (email);

create unique index if not exists  users_id_uindex
    on users (id);

create table if not exists  requests
(
    id           serial
        constraint requests_pk
            primary key,
    description  varchar(500) not null,
    requestor_id integer      not null
        constraint requests_users_id_fk
            references users,
            created      timestamp
);

alter table if exists  requests
    owner to postgres;

create table if not exists  items
(
    id           serial
        constraint items_pk
            primary key,
    name         varchar(50) not null,
    description  varchar(500),
    is_available boolean     not null,
    owner_id     integer     not null
        constraint items_users_id_fk
            references users
            on delete cascade,
    request_id   integer
        constraint items_requests_id_fk
            references requests
            on delete cascade
);

alter table if exists items
    owner to postgres;

create unique index if not exists  items_id_uindex
    on items (id);


create type  booking_status as enum ('WAITING', 'APPROVED', 'REJECTED', 'CANCELED');

create table if not exists  bookings
(
    id         serial
        constraint bookings_pk
            primary key,
    start_date timestamp with time zone not null,
    end_date   timestamp with time zone not null,
    item_id    integer   not null
        constraint bookings_items_id_fk
            references items
            on delete cascade,
    booker_id  integer   not null
        constraint bookings_users_id_fk
            references users
            on delete cascade,
    status     varchar   not null
);

alter table if exists bookings
    owner to postgres;

create unique index if not exists  bookings_id_uindex
    on bookings (id);

create unique index if not exists  requests_id_uindex
    on requests (id);

create table if not exists  comments
(
    id        serial
        constraint comments_pk
            primary key,
    text      varchar(500) not null,
    item_id   integer
        constraint comments_items_id_fk
            references items
            on delete cascade,
    author_id integer
        constraint comments_users_id_fk
            references users
            on delete cascade,
    created timestamp
);

alter table if exists  comments
    owner to postgres;

create unique index if not exists   comments_id_uindex
    on comments (id);