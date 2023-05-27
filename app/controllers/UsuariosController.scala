package controllers

import utils.Fila0Connection
import models.Usuario

import javax.inject._
import play.api.libs.json._
import play.api.mvc._

import java.sql.ResultSet

class UsuariosController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  private val ID_COLUMN = "id"
  private val TIPO_DOCUMENTO_COLUMN = "tipo_documento"
  private val NUMERO_DOCUMENTO_COLUMN = "numero_documento"
  private val PRIMER_NOMBRE_COLUMN = "primer_nombre"
  private val SEGUNDO_NOMBRE_COLUMN = "segundo_nombre"
  private val PRIMER_APELLIDO_COLUMN = "primer_apellido"
  private val SEGUNDO_APELLIDO_COLUMN = "segundo_apellido"

  private def mapearUsuario(resultSet: ResultSet): Usuario = {
    val usuario = new Usuario()
    usuario.id = resultSet.getInt(ID_COLUMN)
    usuario.tipoDocumento = resultSet.getString(TIPO_DOCUMENTO_COLUMN)
    usuario.numeroDocumento  = resultSet.getString(NUMERO_DOCUMENTO_COLUMN)
    usuario.primerNombre  = resultSet.getString(PRIMER_NOMBRE_COLUMN)
    usuario.segundoNombre = resultSet.getString(SEGUNDO_NOMBRE_COLUMN)
    usuario.primerApellido = resultSet.getString(PRIMER_APELLIDO_COLUMN)
    usuario.segundoApellido = resultSet.getString(SEGUNDO_APELLIDO_COLUMN)
    usuario
  }

  def validarIngreso(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val jsonBody = request.body.asJson.getOrElse(Json.obj())
    val tipoDocumento = (jsonBody \ "tipoDocumento").as[String]
    val numeroDocumento = (jsonBody \ "numeroDocumento").as[String]
    val sql = "SELECT * FROM usuarios WHERE tipo_documento = ? AND numero_documento = ?"
    val values = List(tipoDocumento, numeroDocumento)

    Fila0Connection.executeQuery(sql, values) match {
      case resultSet if resultSet.next() =>
        val usuario = mapearUsuario(resultSet)
        Ok(Json.toJson(usuario))
      case resultSet if resultSet.getFetchSize == 0 =>
        Ok(Json.toJson(Map("warning" -> "Los datos de ingreso son inválidos.")))
      case _ =>
        InternalServerError(Json.toJson(Map("error" -> "Ha ocurrido un error al intentar validar los datos de ingreso.")))
    }
  }
}
