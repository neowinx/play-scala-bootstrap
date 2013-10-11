package controllers

import play.api._
import play.api.mvc._
import scala.concurrent.Future
import models.defaultPersistenceContext._
import models.Persona

object PersonaController extends Controller {

  def index = Action.async { implicit request =>
    asyncTransactionalChain { implicit ctx =>
      asyncAll[Persona].map {
        personas => Ok(views.html.persona.list(personas))
      }
    }
  }

}
