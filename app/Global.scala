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
        Persona(2, "Maria Victoria Celeste", Some(date("2011-11-19"))),
        Persona(3, "Diego Hernan Cortes", Some(date("2011-10-13"))),
        Persona(4, "Lupita Lopez", Some(date("2013-09-18"))),
        Persona(5, "Petrona Rodriguez", Some(date("2011-11-28"))),
        Persona(6, "Juan Melgarejo", Some(date("2012-11-08"))),
        Persona(7, "Marcelo Marques", Some(date("2011-01-03"))),
        Persona(8, "Carlos Gonzalez", Some(date("2009-11-30"))),
        Persona(9, "Juan Rivarola Matto", Some(date("2013-10-22"))),
        Persona(10, "Laura Gimenez", Some(date("2008-05-10"))),
        Persona(11, "Patricia Lamas Carissimo", Some(date("2009-03-01"))),
        Persona(12, "Mariscal Francisco Solano López", Some(date("2012-11-12"))),
        Persona(13, "Ing. Agustín Pío Paiva", Some(date("2010-10-10"))),
        Persona(14, "Pedro Ivarrola Martínez", Some(date("2011-11-04")))
      ).foreach(Persona.create)

    }

  }

}