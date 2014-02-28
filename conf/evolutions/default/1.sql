# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table ciudad (
  id                        bigint not null,
  nombre                    varchar(255),
  departamento_id           bigint,
  constraint pk_ciudad primary key (id))
;

create table departamento (
  id                        bigint not null,
  nombre                    varchar(255),
  pais_id                   bigint,
  constraint pk_departamento primary key (id))
;

create table pais (
  id                        bigint not null,
  nombre                    varchar(255),
  constraint pk_pais primary key (id))
;

create table persona (
  id                        bigint not null,
  nombre                    varchar(255),
  apellido                  varchar(255),
  fecha_nacimiento          timestamp,
  usuario_id                bigint,
  constraint pk_persona primary key (id))
;

create table usuario (
  id                        bigint not null,
  usuario                   varchar(255),
  persona_id                bigint,
  constraint pk_usuario primary key (id))
;

create sequence ciudad_seq;

create sequence departamento_seq;

create sequence pais_seq;

create sequence persona_seq;

create sequence usuario_seq;

alter table ciudad add constraint fk_ciudad_departamento_1 foreign key (departamento_id) references departamento (id) on delete restrict on update restrict;
create index ix_ciudad_departamento_1 on ciudad (departamento_id);
alter table departamento add constraint fk_departamento_pais_2 foreign key (pais_id) references pais (id) on delete restrict on update restrict;
create index ix_departamento_pais_2 on departamento (pais_id);
alter table persona add constraint fk_persona_usuario_3 foreign key (usuario_id) references usuario (id) on delete restrict on update restrict;
create index ix_persona_usuario_3 on persona (usuario_id);
alter table usuario add constraint fk_usuario_persona_4 foreign key (persona_id) references persona (id) on delete restrict on update restrict;
create index ix_usuario_persona_4 on usuario (persona_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists ciudad;

drop table if exists departamento;

drop table if exists pais;

drop table if exists persona;

drop table if exists usuario;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists ciudad_seq;

drop sequence if exists departamento_seq;

drop sequence if exists pais_seq;

drop sequence if exists persona_seq;

drop sequence if exists usuario_seq;