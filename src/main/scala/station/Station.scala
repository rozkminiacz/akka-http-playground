package station

final case class Station(id: String, name: String, lat: Double, lon: Double, airState: Int)

case class StationNotFound(id :String) extends Exception(s"Station with $id doesn't exists")

case class StationAlreadyExists(id :String) extends Exception(s"Station with $id already exists")