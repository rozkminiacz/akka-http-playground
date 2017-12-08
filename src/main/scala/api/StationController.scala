package api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import serializers.Formats._
import station.{Station, StationAlreadyExists, StationNotFound, StationService}

import scala.util.{Failure, Success, Try}

class StationController(stationService: StationService) {
  def route: Route = {
    pathPrefix("stations") {
      routes
    }
  }

  private def routes = pathEndOrSingleSlash {
    get {
      complete(StatusCodes.OK -> stationService.get)
    } ~ post {
      entity(as[Station]) { newStation =>
        tryResponseHandling(stationService.create(newStation))
      }
    }
  } ~ path(Segment) { id =>
    get {
      tryResponseHandling(stationService.getOne(id))
    } ~ put {
      entity(as[Station]) { stationToUpdate => {
        complete(StatusCodes.OK -> stationService.update(id, stationToUpdate))
      }
      }
    } ~ delete {
      stationService.delete(id)
      complete(StatusCodes.NoContent)
    }
  }


  private def tryResponseHandling(resp: Try[Station]) = {
    resp match {
      case Success(s) => complete(StatusCodes.OK -> s)
      case Failure(e@StationNotFound(_)) => complete(StatusCodes.NotFound -> e.getMessage)
      case Failure(e@StationAlreadyExists(_)) => complete(StatusCodes.Conflict -> e.getMessage)
    }
  }
}
