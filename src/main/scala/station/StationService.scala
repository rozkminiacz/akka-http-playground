package station

import scala.util.Try

class StationService(repository: StationRepository) {
  def get: List[Station] = repository.get.values.toList

  def getOne(id: String) = {
    Try {
      repository.getOne(id).getOrElse(throw StationNotFound(id))
    }
  }

  def create(station: Station) = {
    val id = station.id
    Try {
      repository.getOne(id)
        .map(_ => throw StationAlreadyExists(id))
        .getOrElse {
          repository.create(station)
          station
        }
    }
  }

  def update(id: String, station: Station): Station = {
    repository.update(id, station)
    station
  }

  def delete(id: String) = {
    repository.delete(id)
  }
}
