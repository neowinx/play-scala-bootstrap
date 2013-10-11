package models

import java.util.Date
import defaultPersistenceContext._
import net.fwbrasil.radon.transaction.TransactionalExecutionContext
import scala.concurrent.Future
import models.Page

class Persona(var name: String, var fechaNacimiento: Option[Date]) extends Entity

case class Page[A](items: Seq[A], page: Int, offset: Long, total: Long) {
  lazy val prev = Option(page - 1).filter(_ >= 0)
  lazy val next = Option(page + 1).filter(_ => (offset + items.size) < total)
}

object Persona {

  def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, filter: String = "*")(implicit ctx: TransactionalExecutionContext): Future[Page[Persona]] = {
    val pagination =
      asyncPaginatedQuery {
        (c: Persona) =>
          where(toUpperCase(c.name) like filter.toUpperCase) select (c) orderBy {
            orderBy match {
              case -2 => c.name desc
              case -3 => c.fechaNacimiento desc
              case  2 => c.name
              case  3 => c.fechaNacimiento
            }
          }
      }

    pagination.navigator(pageSize).flatMap { navigator =>
      if (navigator.numberOfResults > 0)
        navigator.page(page).map(p => Page(p.map(c => (c)), page, page * pageSize, navigator.numberOfResults))
      else
        Future(Page(Nil, 0, 0, 0))

    }
  }
}