-- drop table authorities;
-- drop table users;
drop table oauth2_authorization;
drop table oauth2_authorization_consent;
drop table oauth2_registered_client;
drop table rsa_key_pairs;

-- clients
create table if not exists oauth2_registered_client
(
    id                            varchar(100)                            not null,
    client_id                     varchar(100)                            not null,
    client_id_issued_at           timestamp     default CURRENT_TIMESTAMP not null,
    client_secret                 varchar(200)  default null,
    client_secret_expires_at      timestamp     default null,
    client_name                   varchar(200)                            not null,
    client_authentication_methods varchar(1000)                           not null,
    authorization_grant_types     varchar(1000)                           not null,
    redirect_uris                 varchar(1000) default null,
    post_logout_redirect_uris     varchar(1000) default null,
    scopes                        varchar(1000)                           not null,
    client_settings               varchar(2000)                           not null,
    token_settings                varchar(2000)                           not null,
    primary key (id)
);

-- users
-- create table if not exists users
-- (
--     username varchar(200) not null primary key,
--     password varchar(500) not null,
--     enabled  boolean      not null
-- );
--
-- -- authorities
-- create table if not exists authorities
-- (
--     username  varchar(200) not null,
--     authority varchar(50)  not null,
--     constraint fk_authorities_users foreign key (username) references users (username),
--     constraint username_authority unique (username, authority)
-- );

create table if not exists oauth2_authorization_consent
(
    registered_client_id varchar(100)  not null,
    principal_name       varchar(200)  not null,
    authorities          varchar(1000) not null,
    primary key (registered_client_id, principal_name)
);

CREATE TABLE oauth2_authorization
(
    id                            varchar(100) not null,
    registered_client_id          varchar(100) not null,
    principal_name                varchar(200) not null,
    authorization_grant_type      varchar(100) not null,
    authorized_scopes             varchar(1000) default null,
    attributes                    text          default null,
    state                         varchar(500)  default null,
    authorization_code_value      text          default null,
    authorization_code_issued_at  timestamp     default null,
    authorization_code_expires_at timestamp     default null,
    authorization_code_metadata   text          default null,
    access_token_value            text          default null,
    access_token_issued_at        timestamp     default null,
    access_token_expires_at       timestamp     default null,
    access_token_metadata         text          default null,
    access_token_type             varchar(100)  default null,
    access_token_scopes           varchar(1000) default null,
    oidc_id_token_value           text          default null,
    oidc_id_token_issued_at       timestamp     default null,
    oidc_id_token_expires_at      timestamp     default null,
    oidc_id_token_metadata        text          default null,
    refresh_token_value           text          default null,
    refresh_token_issued_at       timestamp     default null,
    refresh_token_expires_at      timestamp     default null,
    refresh_token_metadata        text          default null,
    user_code_value               text          default null,
    user_code_issued_at           timestamp     default null,
    user_code_expires_at          timestamp     default null,
    user_code_metadata            text          default null,
    device_code_value             text          default null,
    device_code_issued_at         timestamp     default null,
    device_code_expires_at        timestamp     default null,
    device_code_metadata          text          default null,
    primary key (id)
);

-- rsa_key_pairs
create table if not exists rsa_key_pairs
(
    id          varchar(1000) not null primary key,
    private_key text          not null,
    public_key  text          not null,
    created_at  date          not null,
    unique (id, created_at)
);