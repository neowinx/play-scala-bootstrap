package controllers

import play.api._
import play.api.mvc._
import model.Persona
import play.api.data._
import play.api.data.Forms._
import anorm._

object PersonaController extends Controller {

  val personaForm = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "nombre" -> text,
      "apellido" -> text,
      "fechaInicio" -> optional(date)
    )
  (Persona.apply)(Persona.unapply))

  def list(limit: Int, offset: Int, sort: Int, order: String) = {
    Ok(
      views.html.persona.list(
        Persona.findAll(limit, offset, sort, order), limit, offset, sort, order
      )
    )
  }

  def index(limit: Int, offset: Int, sort: Int, order: String) = Action {
    list(limit, offset, sort, order)
  }

  def edit(id: Long) = Action { implicit request =>
    val persona = Persona.find(id)
    Ok(views.html.persona.edit(persona, personaForm.fill(persona(0))))
  }

}
