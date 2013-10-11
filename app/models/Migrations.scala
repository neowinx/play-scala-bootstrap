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
      new Persona("Jaja", Some(date("2013-03-21")))
      new Persona("Jojo", Some(date("2013-04-10")))
    }
  }
}