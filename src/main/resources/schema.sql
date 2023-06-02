--drop table if exists items, bookings, users,  requests, comments cascade ;
drop type if exists booking_status cascade;


create table if not exists users(
    id  bigint generated always as identity primary key,
    name  varchar(50) not null,
    email varchar(50) not null unique
);


create table if not exists  requests (
    id bigint generated always as identity primary key,
    description  varchar(500) not null,
    requestor_id bigint not null,
    created      timestamp,
        constraint fk_requests_to_users foreign key(requestor_id)
        references users(id)

);


create table if not exists  items(
    id bigint generated always as identity primary key,
    name         varchar(50) not null,
    description  varchar(500),
    is_available boolean     not null,
    owner_id     integer     not null,
    request_id   integer,
        constraint fk_items_users foreign key(owner_id)
        references users(id) on delete cascade,
        constraint fk_items_requests foreign key(request_id)
        references requests(id) on delete cascade
);


create type  booking_status as enum ('WAITING', 'APPROVED', 'REJECTED', 'CANCELED');


create table if not exists  bookings(
    id bigint generated always as identity primary key,
    start_date timestamp with time zone not null,
    end_date   timestamp with time zone not null,
    item_id    integer   not null,
    booker_id  integer   not null,
    status     varchar(50)   not null,
    constraint fk_bookings_items foreign key(item_id)
        references items(id) on delete cascade,
    constraint fk_bookings_users foreign key(booker_id)
        references users(id) on delete cascade
);


create table if not exists  comments (
    id bigint generated always as identity primary key,
    text      varchar(1000) not null,
    item_id   integer,
    author_id integer,
    created timestamp,
    constraint fk_comments_items foreign key(item_id)
        references items(id) on delete cascade,
    constraint fk_comments_users foreign key(author_id)
        references users(id) on delete cascade
);
