package serializers

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import station.Station

object Formats extends SprayJsonSupport with DefaultJsonProtocol{
  implicit val stationFormat = jsonFormat5(Station)
}
