package model

import play.api.Play.current

import anorm._
import anorm.SqlParser._

import java.util.Date
import play.api.db.DB

import scala.language.postfixOps

case class Persona(id: Pk[Long] = NotAssigned, nombre: String, apellido: String, fechaNacimiento: Option[Date])

object Persona {

  val simple = {
    get[Pk[Long]]("persona.id") ~
    get[String]("persona.nombre") ~
    get[String]("persona.apellido") ~ 
    get[Option[Date]]("persona.fecha_nacimiento") map {
      case id~nombre~apellido~fechaNacimiento=> Persona(id, nombre, apellido, fechaNacimiento)
    }
  }

  def findAll(limit: Int = 10, offset: Int = 0, sort: Int = 1, order: String = "asc"): Seq[Persona] = {
    DB.withConnection { implicit connection =>
      SQL(""" select *
              from persona
              order by {sort} %s
              limit {limit} offset {offset}
          """
        .format(order match { case "asc" => "asc" case _ => "desc" })
      )
      .on(
        'limit -> limit,
        'offset -> offset,
        'sort -> sort
      ).as(Persona.simple *)
    }
  }

  def find(id: Long) : Seq[Persona] = {
    DB.withConnection { implicit connection =>
      SQL("select * from persona where id = {id} ")
        .on("id" -> id).as(Persona.simple *)
    }
  }

  def create(persona: Persona): Persona = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          insert into persona values (
            {id}, {nombre}, {apellido}, {fechaNacimiento}
          )
        """
      ).on(
        'id -> persona.id,
        'nombre -> persona.nombre,
        'apellido -> persona.apellido,
        'fechaNacimiento -> persona.fechaNacimiento
      ).executeUpdate()

      persona

    }
  }

}
