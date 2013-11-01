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

  val PersonaList = Redirect("/persona")

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

  def create = Action.async {
    Future.successful(Ok("AAAA"))
  }

  def save = Action.async { implicit request =>
    asyncTransactionalChain { implicit ctx =>
        personaForm.bindFromRequest.fold(
          formWithErrors =>
              Future.successful(BadRequest(views.html.persona.edit("",personaForm))),
          personaData => {
              personaData.asyncCreateEntity.map { persona =>
                  PersonaList.flashing("success" -> "Persona has been created")
              }
          }
        )
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
          formWithErrors => Future.successful(BadRequest( "Error al actualizar" )),
          personaData => {
            personaData.asyncUpdateEntity(id).map { persona =>
              PersonaList.flashing("success" -> "Persona editada correctamente")
            }
          }
      )    
    }
  }

  def delete(id: String) = Action.async {
      asyncTransactionalChain { implicit ctx =>
          asyncById[Persona](id).map { personaOption =>
              personaOption.map { 
                persona => persona.delete
              }.getOrElse(NotFound)
              PersonaList.flashing("success" -> "Persona eliminada correctamente")
          }
      }
  }

}
