package model

import play.api.Play.current
import net.fwbrasil.activate.ActivateContext

import java.util.Date
import play.api.db.DB
import net.fwbrasil.activate.storage.relational.async.AsyncPostgreSQLStorage
import com.github.mauricio.async.db.Configuration
import com.github.mauricio.async.db.postgresql.pool.PostgreSQLConnectionFactory

object persistenceContext extends ActivateContext {
  val storage = new AsyncPostgreSQLStorage {
    def configuration =
      new Configuration(
        username = "user",
        host = "localhost",
        password = Some("password"),
        database = Some("database_name"))
    lazy val objectFactory = new PostgreSQLConnectionFactory(configuration)
}

import persistenceContext._

case class Persona(descripcion: String, fechaInicio: Option[Date]) extends Entity

object Persona {

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
