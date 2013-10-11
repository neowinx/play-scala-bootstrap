package models

import play.api.Play.current

import anorm._
import anorm.SqlParser._

import java.util.Date
import play.api.db.DB

case class Persona(id: Int, descripcion: String, fechaInicio: Option[Date])

object Persona {

  val simple = {
    get[Int]("persona.id") ~
    get[String]("persona.descripcion") ~
    get[Option[Date]]("persona.fecha_inicio") map {
      case id~descripcion~fechaInicio=> Persona(id, descripcion, fechaInicio)
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

  def find(id: Int) : Seq[Persona] = {
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
            {id}, {descripcion}, {fechaInicio}
          )
        """
      ).on(
        'id -> persona.id,
        'descripcion -> persona.descripcion,
        'fechaInicio -> persona.fechaInicio
      ).executeUpdate()

      persona

    }
  }

}
