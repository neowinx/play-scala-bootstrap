package controllers

import play.api.mvc._
import model.Persona
import play.api.data._
import play.api.data.Forms._

object PersonaController extends Controller {

  val personaForm = Form(
    mapping(
      "id" -> number,
      "descripcion" -> text,
      "fechaInicio" -> optional(date)
    )
  (Persona.apply)(Persona.unapply))

  val list = Ok(views.html.persona(Persona.findAll()))

  def index = Action {
    list
  }

  def edit(id: Int) = Action { implicit request =>
    val persona = Persona.find(id)
    Ok(views.html.persona_edit(persona, personaForm.fill(persona(0))))
  }

  def update(id: Int) = Action {
    /* Actualizar, luego ir al listado */
    list
  }

}
