import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.MongoClient
import org.bson.codecs.configuration.CodecRegistries.{fromRegistries, fromProviders}
import org.mongodb.scala.MongoCollection
import scala.concurrent.Await
import org.mongodb.scala.Observable
import scala.concurrent.duration.{Duration, SECONDS}
import org.mongodb.scala.model.Filters.equal

case class HouseDao(mongoClient: MongoClient) {
  val codecRegistry = fromRegistries(
    fromProviders(classOf[House]),
    MongoClient.DEFAULT_CODEC_REGISTRY
  )

  val db = mongoClient.getDatabase("mongotestdb").withCodecRegistry(codecRegistry)
  val collection: MongoCollection[House] = db.getCollection("House")

  private def getResults[T](obs: Observable[T]): Seq[T] = {
    Await.result(obs.toFuture(), Duration(20, SECONDS))
  }

  def getAll(): Seq[House] = {
    getResults(collection.find())
  }

  def getByName(name: String): Seq[House] = {
    getResults(collection.find(equal("index", name)))
  }

  def addToMongo(house: House) ={
    getResults(collection.insertOne(house))
  }

}
