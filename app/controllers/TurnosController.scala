package controllers

import utils.Fila0Connection
import models.{Turno, TurnoUsuario}

import javax.inject._
import play.api.libs.json._
import play.api.mvc._

import java.sql.ResultSet

class TurnosController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  private val ID_COLUMN = "id"
  private val USUARIO_COLUMN = "usuario"
  private val TIPO_DOCUMENTO_COLUMN = "tipo_documento"
  private val NUMERO_DOCUMENTO_COLUMN = "numero_documento"
  private val PRIMER_NOMBRE_COLUMN = "primer_nombre"
  private val SEGUNDO_NOMBRE_COLUMN = "segundo_nombre"
  private val PRIMER_APELLIDO_COLUMN = "primer_apellido"
  private val SEGUNDO_APELLIDO_COLUMN = "segundo_apellido"
  private val MODULO_COLUMN = "modulo"
  private val FECHA_COLUMN = "fecha"
  private val CATEGORIA_COLUMN = "categoria"
  private val CODIGO_COLUMN = "codigo"
  private val ESTADO_COLUMN = "estado"
  private val FECHA_ASIGNADO_COLUMN = "fecha_asignado"
  private val FECHA_CAMBIO_COLUMN = "fecha_cambio"

  private def mapearTurno(resultSet: ResultSet): Turno = {
    val turno = new Turno()
    turno.id = resultSet.getInt(ID_COLUMN)
    turno.usuario = resultSet.getInt(USUARIO_COLUMN)
    turno.modulo = resultSet.getInt(MODULO_COLUMN)
    turno.fecha = resultSet.getTimestamp(FECHA_COLUMN)
    turno.categoria = resultSet.getString(CATEGORIA_COLUMN)
    turno.codigo = resultSet.getString(CODIGO_COLUMN)
    turno.estado = resultSet.getString(ESTADO_COLUMN)
    turno.fechaAsignado = resultSet.getTimestamp(FECHA_ASIGNADO_COLUMN)
    turno.fechaCambio = resultSet.getTimestamp(FECHA_CAMBIO_COLUMN)
    turno
  }

  private def mapearListaTurno(resultSet: ResultSet): List[Turno] = {
    var listaTurno: List[Turno] = List(mapearTurno(resultSet))

    while (resultSet.next()) {
      val turno = mapearTurno(resultSet)
      listaTurno = listaTurno :+ turno
    }

    listaTurno
  }

  private def mapearTurnoUsuario(resultSet: ResultSet): TurnoUsuario = {
    val turnoUsuario = new TurnoUsuario()
    turnoUsuario.id = resultSet.getInt(ID_COLUMN)
    turnoUsuario.usuario = resultSet.getInt(USUARIO_COLUMN)
    turnoUsuario.tipoDocumento = resultSet.getString(TIPO_DOCUMENTO_COLUMN)
    turnoUsuario.numeroDocumento = resultSet.getString(NUMERO_DOCUMENTO_COLUMN)
    turnoUsuario.primerNombre = resultSet.getString(PRIMER_NOMBRE_COLUMN)
    turnoUsuario.segundoNombre = resultSet.getString(SEGUNDO_NOMBRE_COLUMN)
    turnoUsuario.primerApellido = resultSet.getString(PRIMER_APELLIDO_COLUMN)
    turnoUsuario.segundoApellido = resultSet.getString(SEGUNDO_APELLIDO_COLUMN)
    turnoUsuario.modulo = resultSet.getInt(MODULO_COLUMN)
    turnoUsuario.fecha = resultSet.getTimestamp(FECHA_COLUMN)
    turnoUsuario.categoria = resultSet.getString(CATEGORIA_COLUMN)
    turnoUsuario.codigo = resultSet.getString(CODIGO_COLUMN)
    turnoUsuario.estado = resultSet.getString(ESTADO_COLUMN)
    turnoUsuario.fechaAsignado = resultSet.getTimestamp(FECHA_ASIGNADO_COLUMN)
    turnoUsuario.fechaCambio = resultSet.getTimestamp(FECHA_CAMBIO_COLUMN)
    turnoUsuario
  }

  private def mapearListaTurnoUsuario(resultSet: ResultSet): List[TurnoUsuario] = {
    var listaTurnoUsuario: List[TurnoUsuario] = List(mapearTurnoUsuario(resultSet))

    while (resultSet.next()) {
      val turnoUsuario = mapearTurnoUsuario(resultSet)
      listaTurnoUsuario = listaTurnoUsuario :+ turnoUsuario
    }

    listaTurnoUsuario
  }

  def registrar(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val jsonBody = request.body.asJson.getOrElse(Json.obj())
    val usuario = (jsonBody \ "usuario").as[Int]
    val categoria = (jsonBody \ "categoria").as[String]
    val sql = "INSERT INTO turnos(usuario, categoria) VALUES (?, ?) RETURNING *"
    val values = List(usuario, categoria)

    Fila0Connection.executeUpdate(sql, values) match {
      case resultSet if resultSet.next() =>
        val turno = mapearTurno(resultSet)
        Ok(Json.toJson(turno))
      case resultSet if resultSet.getFetchSize == 0 =>
        Ok(Json.toJson(Map("warning" -> "No se encontró el turno registrado.")))
      case _ =>
        InternalServerError(Json.toJson(Map("error" -> "Ha ocurrido un error al intentar registrar el turno.")))
    }
  }

  def buscarPendientes(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val sql = "SELECT * FROM turnos INNER JOIN usuarios ON usuarios.id = turnos.usuario WHERE turnos.estado = 'Pendiente' ORDER BY turnos.id ASC LIMIT 32"

    Fila0Connection.executeQuery(sql) match {
      case resultSet if resultSet.next() =>
        val listaTurnoUsuario = mapearListaTurnoUsuario(resultSet)
        Ok(Json.toJson(listaTurnoUsuario))
      case resultSet if resultSet.getFetchSize == 0 =>
        Ok(Json.toJson(Map("warning" -> "No se encontraron turnos pendientes.")))
      case _ =>
        InternalServerError(Json.toJson(Map("error" -> "Ha ocurrido un error al intentar buscar los últimos turnos pendientes.")))
    }
  }

  def buscarAsignados(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val sql = "SELECT * FROM turnos INNER JOIN usuarios ON usuarios.id = turnos.usuario WHERE turnos.estado = 'Asignado' ORDER BY turnos.fecha_asignado DESC LIMIT 8"

    Fila0Connection.executeQuery(sql) match {
      case resultSet if resultSet.next() =>
        val listaTurnoUsuario = mapearListaTurnoUsuario(resultSet)
        Ok(Json.toJson(listaTurnoUsuario))
      case resultSet if resultSet.getFetchSize == 0 =>
        Ok(Json.toJson(Map("warning" -> "No se encontraron turnos asignados.")))
      case _ =>
        InternalServerError(Json.toJson(Map("error" -> "Ha ocurrido un error al intentar buscar los últimos turnos asignados.")))
    }
  }

  def buscarCompletados(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val sql = "SELECT * FROM turnos INNER JOIN usuarios ON usuarios.id = turnos.usuario WHERE turnos.estado = 'Completado' ORDER BY turnos.fecha_cambio DESC LIMIT 32"

    Fila0Connection.executeQuery(sql) match {
      case resultSet if resultSet.next() =>
        val listaTurnoUsuario = mapearListaTurnoUsuario(resultSet)
        Ok(Json.toJson(listaTurnoUsuario))
      case resultSet if resultSet.getFetchSize == 0 =>
        Ok(Json.toJson(Map("warning" -> "No se encontraron turnos completados.")))
      case _ =>
        InternalServerError(Json.toJson(Map("error" -> "Ha ocurrido un error al intentar buscar los últimos turnos completados.")))
    }
  }

  def buscarCancelados(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val sql = "SELECT * FROM turnos INNER JOIN usuarios ON usuarios.id = turnos.usuario WHERE turnos.estado = 'Cancelado' ORDER BY turnos.fecha_cambio DESC LIMIT 32"

    Fila0Connection.executeQuery(sql) match {
      case resultSet if resultSet.next() =>
        val listaTurnoUsuario = mapearListaTurnoUsuario(resultSet)
        Ok(Json.toJson(listaTurnoUsuario))
      case resultSet if resultSet.getFetchSize == 0 =>
        Ok(Json.toJson(Map("warning" -> "No se encontraron turnos cancelados.")))
      case _ =>
        InternalServerError(Json.toJson(Map("error" -> "Ha ocurrido un error al intentar buscar los últimos turnos cancelados.")))
    }
  }

  def asignar(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val jsonBody = request.body.asJson.getOrElse(Json.obj())
    val modulo = (jsonBody \ "modulo").as[Int]
    val categoria = (jsonBody \ "categoria").as[String]
    val sql = "UPDATE turnos SET estado = 'Asignado', modulo = ?, fecha_asignado = CURRENT_TIMESTAMP WHERE id = (SELECT MIN(id) FROM turnos WHERE estado = 'Pendiente' AND (categoria = ? OR ? = 'N/A'))"
    val values = List(modulo, categoria, categoria)

    Fila0Connection.executeUpdate(sql, values) match {
      case resultSet if resultSet.next() =>
        val turnoId = mapearTurno(resultSet).id
        val sql2 = "SELECT * FROM turnos INNER JOIN usuarios ON usuarios.id = turnos.usuario WHERE turnos.id = ?"
        val values2 = List(turnoId)

        Fila0Connection.executeQuery(sql2, values2) match {
          case resultSet if resultSet.next() =>
            val turnoUsuario = mapearTurnoUsuario(resultSet)
            Ok(Json.toJson(turnoUsuario))
          case resultSet if resultSet.getFetchSize == 0 =>
            Ok(Json.toJson(Map("warning" -> "No se encontraron los datos del turno asignado.")))
          case _ =>
            InternalServerError(Json.toJson(Map("error" -> "Ha ocurrido un error al intentar buscar los datos del turno asignado.")))
        }
      case resultSet if resultSet.getFetchSize == 0 =>
        Ok(Json.toJson(Map("warning" -> "No se encontró ningún turno para asignar.")))
      case _ =>
        InternalServerError(Json.toJson(Map("error" -> "Ha ocurrido un error al intentar asignar un turno.")))
    }
  }

  def actualizarEstado(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val jsonBody = request.body.asJson.getOrElse(Json.obj())
    val id = (jsonBody \ "id").as[Int]
    val modulo = (jsonBody \ "modulo").as[Int]
    val estado = (jsonBody \ "estado").as[String]
    val sql = "UPDATE turnos SET estado = ?, modulo = ?, fecha_cambio = CURRENT_TIMESTAMP WHERE id = ? RETURNING *"
    val values = List(estado, modulo, id)

    Fila0Connection.executeUpdate(sql, values) match {
      case resultSet if resultSet.next() =>
        val turno = mapearTurno(resultSet)
        Ok(Json.toJson(turno))
      case resultSet if resultSet.getFetchSize == 0 =>
        Ok(Json.toJson(Map("warning" -> "No se encontró el turno a actualizar.")))
      case _ =>
        InternalServerError(Json.toJson(Map("error" -> "Ha ocurrido un error al intentar actualizar el estado del turno.")))
    }
  }

  def devolverAPendientes(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val jsonBody = request.body.asJson.getOrElse(Json.obj())
    val id = (jsonBody \ "id").as[Int]
    val sql = "UPDATE turnos SET estado = 'Pendiente', modulo = NULL, fecha_cambio = CURRENT_TIMESTAMP WHERE id = ? RETURNING *"
    val values = List(id)

    Fila0Connection.executeUpdate(sql, values) match {
      case resultSet if resultSet.next() =>
        val turno = mapearTurno(resultSet)
        Ok(Json.toJson(turno))
      case resultSet if resultSet.getFetchSize == 0 =>
        Ok(Json.toJson(Map("warning" -> "No se encontró el turno a devolver a pendientes.")))
      case _ =>
        InternalServerError(Json.toJson(Map("error" -> "Ha ocurrido un error al intentar devolver el turno a los turnos pendientes.")))
    }
  }
}
