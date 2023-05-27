package utils

import java.sql.{Connection, DriverManager, ResultSet}
import scala.util.Using

object Fila0Connection {
  private val url = "jdbc:postgresql://localhost:5432/fila0"
  private val user = "fila0_crud"
  private val password = "1234567a"

  def withConnection[T](block: Connection => T): T = {
    Using(DriverManager.getConnection(url, user, password)) { connection =>
      block(connection)
    }.get // Si ocurre un error, se lanzará una excepción
  }

  def executeQuery(query: String, values: List[Any] = List.empty): ResultSet = {
    withConnection { connection =>
      val preparedStatement = connection.prepareStatement(query)

      // Asigna los valores a los parámetros del PreparedStatement
      values.zipWithIndex.foreach { case (value, index) =>
        preparedStatement.setObject(index + 1, value)
      }

      preparedStatement.executeQuery()
    }
  }
}
