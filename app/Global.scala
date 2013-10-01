import java.util.Date
import model.Persona
import play.api._

import models._
import anorm._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    InitialData.insert()
  }

}

/**
 * Initial set of data to be imported 
 * in the sample application.
 */
object InitialData {

  def date(str: String) = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(str)

  def insert() = {

    if(Persona.findAll().isEmpty) {

      Seq(
        Persona(1, "Juan Perez Cardozo", Some(date("2011-11-18"))),
        Persona(2, "Maria Victoria Celeste", Some(date("2011-11-18"))),
        Persona(3, "Diego Hernan Cortes", Some(date("2011-11-18")))
      ).foreach(Persona.create)

    }

  }

}