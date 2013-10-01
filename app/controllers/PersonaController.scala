package controllers

import play.api.mvc._
import model.Persona

object PersonaController extends Controller {

  def index = Action {
    Ok(views.html.persona(Persona.findAll()))
  }

}
