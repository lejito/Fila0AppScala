package models

import java.sql.Timestamp
import play.api.libs.json.{Json, JsNull, OWrites}

class Turno {
  private var _id: Integer = _
  private var _usuario: Integer = _
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

object Turno {
  implicit val writes: OWrites[Turno] = new OWrites[Turno] {
    def writes(turno: Turno): play.api.libs.json.JsObject = {
      Json.obj(
        "id" -> Json.toJson(turno.id.intValue()),
        "usuario" -> Json.toJson(turno.usuario.intValue()),
        "modulo" -> Json.toJson(turno.modulo.intValue()),
        "fecha" -> Option(turno.fecha).fold[play.api.libs.json.JsValue](JsNull)(fecha => Json.toJson(fecha.toString)),
        "categoria" -> Json.toJson(turno.categoria),
        "codigo" -> Json.toJson(turno.codigo),
        "estado" -> Json.toJson(turno.estado),
        "fechaAsignado" -> Option(turno.fechaAsignado).fold[play.api.libs.json.JsValue](JsNull)(fecha => Json.toJson(fecha.toString)),
        "fechaCambio" -> Option(turno.fechaCambio).fold[play.api.libs.json.JsValue](JsNull)(fecha => Json.toJson(fecha.toString))
      )
    }
  }
}
