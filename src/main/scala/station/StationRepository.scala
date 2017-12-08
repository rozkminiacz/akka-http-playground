package station

class StationRepository {
  private var stations = Map.empty[String, Station]

  def get = stations

  def getOne(id: String) = stations.get(id)

  def create(station: Station) = stations += (station.id -> station)

  def update(id: String, station: Station) = stations += (id -> station)

  def delete(id: String) = stations -= id
}
