package models

import java.util.Date
import defaultPersistenceContext._
import net.fwbrasil.radon.transaction.TransactionalExecutionContext
import scala.concurrent.Future

class Persona(var nombre:String, var fechaNacimiento:Option[Date]) extends Entity

case class Page[A](items: Seq[A], page: Int, offset: Long, total: Long) {
  lazy val prev = Option(page - 1).filter(_ >= 0)
  lazy val next = Option(page + 1).filter(_ => (offset + items.size) < total)
}

object Persona {

  def list(page:Int = 0, pageSize: Int = 10, orderBy:Int = 1, filter:String)(implicit ctx: TransactionalExecutionContext): Future[Page[(Persona)]] = {
    val pagination =
      asyncPaginatedQuery {
        (p: Persona) =>
          where(toUpperCase(p.nombre) like filter.toUpperCase) select p orderBy {
            orderBy match {
              case 1 => p.id
              case 2 => p.nombre
            }
          }
      }

    pagination.navigator(pageSize).flatMap { navigator =>
      if(navigator.numberOfResults > 0)
        navigator.page(page).map(p => Page(p.map(p => (p)), page, page * pageSize, navigator.numberOfResults))
      else
        Future(Page(Nil, 0, 0, 0))
    }
  }

}