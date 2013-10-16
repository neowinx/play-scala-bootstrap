package controllers

import play.api._
import play.api.mvc._
import scala.concurrent.Future
import models.defaultPersistenceContext._
import models.Persona
import play.api.data._
import play.api.data.Forms._

object PersonaController extends Controller {

  def index(page: Int, orderBy: Int, filter: String) = Action.async { implicit request =>
    asyncTransactionalChain { implicit ctx =>
      Persona.list(page, orderBy, filter = ("*" + filter + "*")).map {
        page => Ok(views.html.persona.list(page, orderBy, filter))
      }
    }
  }

}
