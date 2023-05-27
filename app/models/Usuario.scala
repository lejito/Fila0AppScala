package models

import play.api.libs.json._

class Usuario {
  private var _id: Integer = _
  private var _tipoDocumento: String = _
  private var _numeroDocumento: String = _
  private var _primerNombre: String = _
  private var _segundoNombre: String = _
  private var _primerApellido: String = _
  private var _segundoApellido: String = _

  def id: Integer = _id

  def id_=(value: Integer): Unit = _id = value

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
}

object Usuario {
  implicit val writes: OWrites[Usuario] = new OWrites[Usuario] {
    def writes(usuario: Usuario): play.api.libs.json.JsObject = Json.obj(
      "id" -> Json.toJson(usuario.id.intValue()),
      "tipoDocumento" -> usuario.tipoDocumento,
      "numeroDocumento" -> usuario.numeroDocumento,
      "primerNombre" -> usuario.primerNombre,
      "segundoNombre" -> usuario.segundoNombre,
      "primerApellido" -> usuario.primerApellido,
      "segundoApellido" -> usuario.segundoApellido
    )
  }
}
