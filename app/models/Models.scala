package models

import java.util.Date
import defaultPersistenceContext._

class Persona(var nombre:String, var fechaNacimiento:Option[Date]) extends Entity