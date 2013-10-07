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

  def edit(id: Int) = Action { implicit request =>
    val persona = Persona.find(id)
    Ok(views.html.persona.edit(persona, personaForm.fill(persona(0))))
  }

  def update(id: Int) = Action {
    /* Actualizar, luego ir al listado */
    //list()
    Ok("implement me")
  }

}
