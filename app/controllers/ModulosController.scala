package controllers

import utils.Fila0Connection
import models.Modulo

import javax.inject._
import play.api.libs.json._
import play.api.mvc._

import java.sql.ResultSet

@Singleton
class ModulosController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  private val ID_COLUMN = "id"
  private val USUARIO_COLUMN = "usuario"
  private val CLAVE_COLUMN = "clave"

  private def mapearModulo(resultSet: ResultSet): Modulo = {
    val modulo = new Modulo()
    modulo.id = resultSet.getInt(ID_COLUMN)
    modulo.usuario = resultSet.getString(USUARIO_COLUMN)
    modulo.clave = resultSet.getString(CLAVE_COLUMN)
    modulo
  }

  def validarLogin(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val jsonBody = request.body.asJson.getOrElse(Json.obj())
    val usuario = (jsonBody \ "usuario").as[String]
    val clave = (jsonBody \ "clave").as[String]
    val sql = "SELECT * FROM modulos WHERE usuario = ? AND clave = ?"
    val values = List(usuario, clave)

    Fila0Connection.executeQuery(sql, values) match {
      case resultSet if resultSet.next() =>
        val modulo = mapearModulo(resultSet)
        Ok(Json.toJson(modulo))
      case resultSet if resultSet.getFetchSize == 0 =>
        Ok(Json.toJson(Map("warning" -> "Los datos de inicio de sesión son inválidos.")))
      case _ =>
        InternalServerError(Json.toJson(Map("error" -> "Ha ocurrido un error al intentar validar los datos de inicio de sesión.")))
    }
  }
}
