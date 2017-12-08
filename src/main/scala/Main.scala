package com.workshops

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import api.StationController
import station.{StationRepository, StationService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Main extends App {
  implicit val system = ActorSystem("sys")

  implicit val materializer = ActorMaterializer()

  val repository = new StationRepository
  val service = new StationService(repository)
  val ctrl = new StationController(service)

  val host = "localhost"
  val port = 8080

  val route = ctrl.route

  val binding = Http().bindAndHandle(route, host, port)

  binding.onComplete {
    case Success(_) => println(s"Server start at $host:$port")
    case Failure(_) => system.terminate()
  }
}