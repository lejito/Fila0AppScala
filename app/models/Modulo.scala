package models

import play.api.libs.json.{Json, OWrites}

class Modulo {
  private var _id: Integer = _
  private var _usuario: String = _
  private var _clave: String = _

  def id: Integer = _id

  def id_=(value: Integer): Unit = _id = value

  def usuario: String = _usuario

  def usuario_=(value: String): Unit = _usuario = value

  def clave: String = _clave

  def clave_=(value: String): Unit = _clave = value
}

object Modulo {
  implicit val writes: OWrites[Modulo] = new OWrites[Modulo] {
    def writes(modulo: Modulo): play.api.libs.json.JsObject = {
      Json.obj(
        "id" -> Json.toJson(modulo.id.intValue()),
        "usuario" -> Json.toJson(modulo.usuario),
        "clave" -> Json.toJson(modulo.clave)
      )
    }
  }
}
