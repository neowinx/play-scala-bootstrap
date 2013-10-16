package models

import defaultPersistenceContext._
import net.fwbrasil.activate.migration.Migration
import scala.collection.mutable.{ Map => MutableMap }
import java.text.SimpleDateFormat

class CreateSchema extends Migration {
    
    def timestamp = System.currentTimeMillis

    def up = {
        removeAllEntitiesTables
            .ifExists
        createTableForAllEntities
            .ifNotExists
    }
}

class SeedData extends Migration {

  def timestamp = System.currentTimeMillis + 100

  def up = {
    customScript {
      def date(fecha: String) = new SimpleDateFormat("yyyy-MM-dd").parse(fecha)
      new Persona("Radamel Flacao", Some(date("2013-03-21")))
      new Persona("Lionel Messi", Some(date("2013-04-10")))
      new Persona("Peter Cantropus", Some(date("2012-11-01")))
      new Persona("Jorge Ozuna", Some(date("2012-11-01")))
      new Persona("Mili Britez", Some(date("2010-02-05")))
      new Persona("Ted Merges", Some(date("2009-03-22")))
      new Persona("Rodrigo Mendez", Some(date("2008-09-13")))
      new Persona("Diego Torres", Some(date("2012-12-05")))
      new Persona("Laurent Black", Some(date("2011-05-09")))
      new Persona("Mary Watson", Some(date("2004-07-12")))
      new Persona("Natalia Verges", Some(date("2010-04-24")))
      new Persona("Julio Falcon", Some(date("2012-03-22")))
      new Persona("Mario Ferreiro", Some(date("2014-03-22")))
    }
  }
}