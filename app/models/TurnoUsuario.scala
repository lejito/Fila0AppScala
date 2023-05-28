package models

import java.sql.Timestamp
import play.api.libs.json.{Json, JsNull, OWrites}

class TurnoUsuario {
  private var _id: Integer = _
  private var _usuario: Integer = _
  private var _tipoDocumento: String = _
  private var _numeroDocumento: String = _
  private var _primerNombre: String = _
  private var _segundoNombre: String = _
  private var _primerApellido: String = _
  private var _segundoApellido: String = _
  private var _modulo: Integer = _
  private var _fecha: Timestamp = _
  private var _categoria: String = _
  private var _codigo: String = _
  private var _estado: String = _
  private var _fechaAsignado: Timestamp = _
  private var _fechaCambio: Timestamp = _

  def id: Integer = _id
  def id_=(value: Integer): Unit = _id = value

  def usuario: Integer = _usuario
  def usuario_=(value: Integer): Unit = _usuario = value

  def tipoDocumento: String = _tipoDocumento
  def tipoDocumento_=(value: String): Unit = _tipoDocumento = value

  def numeroDocumento: String = _numeroDocumento
  def numeroDocumento_=(value: String): Unit = _numeroDocumento = value

  def primerNombre: String = _primerNombre
  def primerNombre_=(value: String): Unit = _primerNombre = value

  def segundoNombre: String = _segundoNombre
  def segundoNombre_=(value: String): Unit = _segundoNombre = value

  def primerApellido: String = _primerApellido
  def primerApellido_=(value: String): Unit = _primerApellido = value

  def segundoApellido: String = _segundoApellido
  def segundoApellido_=(value: String): Unit = _segundoApellido = value

  def modulo: Integer = _modulo
  def modulo_=(value: Integer): Unit = _modulo = value

  def fecha: Timestamp = _fecha
  def fecha_=(value: Timestamp): Unit = _fecha = value

  def categoria: String = _categoria
  def categoria_=(value: String): Unit = _categoria = value

  def codigo: String = _codigo
  def codigo_=(value: String): Unit = _codigo = value

  def estado: String = _estado
  def estado_=(value: String): Unit = _estado = value

  def fechaAsignado: Timestamp = _fechaAsignado
  def fechaAsignado_=(value: Timestamp): Unit = _fechaAsignado = value

  def fechaCambio: Timestamp = _fechaCambio
  def fechaCambio_=(value: Timestamp): Unit = _fechaCambio = value
}

object TurnoUsuario {
  implicit val writes: OWrites[TurnoUsuario] = new OWrites[TurnoUsuario] {
    def writes(turnoUsuario: TurnoUsuario): play.api.libs.json.JsObject = {
      Json.obj(
        "id" -> Json.toJson(turnoUsuario.id.intValue()),
        "usuario" -> Json.toJson(turnoUsuario.usuario.intValue()),
        "tipoDocumento" -> Json.toJson(turnoUsuario.tipoDocumento),
        "numeroDocumento" -> Json.toJson(turnoUsuario.numeroDocumento),
        "primerNombre" -> Json.toJson(turnoUsuario.primerNombre),
        "segundoNombre" -> Json.toJson(turnoUsuario.segundoNombre),
        "primerApellido" -> Json.toJson(turnoUsuario.primerApellido),
        "segundoApellido" -> Json.toJson(turnoUsuario.segundoApellido),
        "modulo" -> Json.toJson(turnoUsuario.modulo.intValue()),
        "fecha" -> Option(turnoUsuario.fecha).fold[play.api.libs.json.JsValue](JsNull)(fecha => Json.toJson(fecha.toString)),
        "categoria" -> Json.toJson(turnoUsuario.categoria),
        "codigo" -> Json.toJson(turnoUsuario.codigo),
        "estado" -> Json.toJson(turnoUsuario.estado),
        "fechaAsignado" -> Option(turnoUsuario.fechaAsignado).fold[play.api.libs.json.JsValue](JsNull)(fecha => Json.toJson(fecha.toString)),
        "fechaCambio" -> Option(turnoUsuario.fechaCambio).fold[play.api.libs.json.JsValue](JsNull)(fecha => Json.toJson(fecha.toString))
      )
    }
  }
}
