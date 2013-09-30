# --- First database schema

# --- !Ups

create table persona (
  id                     integer not null primary key,
  descripcion            varchar(255) not null,
  fecha_inicio            date
);

# --- !Downs

drop table if exists prueba;