package model

import net.fwbrasil.activate.ActivateContext
import com.github.mauricio.async.db.Configuration
import com.github.mauricio.async.db.postgresql.pool.PostgreSQLConnectionFactory
import net.fwbrasil.activate.storage.relational.async.AsyncPostgreSQLStorage

object playScalaBootstrapPersistenceContext extends ActivateContext {

  val storage = new AsyncPostgreSQLStorage {
    def configuration =
      new Configuration(
        username = "postgres",
        host = "localhost",
        password = Some("postgres"),
        database = Some("computer"))
    lazy val objectFactory = new PostgreSQLConnectionFactory(configuration)
  }

}
