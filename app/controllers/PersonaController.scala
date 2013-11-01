package controllers

import play.api._
import play.api.mvc._
import scala.concurrent.Future
import models.defaultPersistenceContext._
import models.{Persona}
import play.api.data._
import play.api.data.Forms._
import views.html
import net.fwbrasil.activate.play.EntityForm

object PersonaController extends Controller {

  val personaForm = EntityForm[Persona](
    _.nombre -> nonEmptyText,
    _.fechaNacimiento -> optional(date("yyyy-MM-dd"))
  )

  def index(page: Int, orderBy: Int, filter: String) = Action.async { implicit request =>
    asyncTransactionalChain { implicit ctx =>
      Persona.list(page, orderBy, filter = ("*" + filter + "*")).map {
        page => Ok(views.html.persona.list(page, orderBy, filter))
      }
    }
  }

  def edit(id: String) = Action.async { implicit request =>
    asyncTransactionalChain { implicit ctx =>
      asyncById[Persona](id).map { personaOption =>
        personaOption.map { persona =>
          Ok(html.persona.edit(id, personaForm.fillWith(persona)))
        }.getOrElse(NotFound)
      }
    }
  }

  def update(id: String) = Action.async { implicit request =>
    asyncTransactionalChain { implicit ctx =>
      personaForm.bindFromRequest.fold (
          formWithErrors => Future.successful(BadRequest( "You need to pass a 'xxx' value!" )),
          { personaOption =>
              Future.successful(Ok("Yay"))
          }
      )    
    }
  }

}
