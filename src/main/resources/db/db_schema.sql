create table user (
  id       bigint primary key                not null,
  username varchar(20)                       not null,
  password char(200)                         not null
);