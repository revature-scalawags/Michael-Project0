import java.io.{BufferedWriter, File, FileWriter}
import scala.collection.mutable.{ArrayBuffer, Map}
import scala.io.BufferedSource

import scala.collection.mutable.Map
import org.mongodb.scala.MongoClient

import org.mongodb.scala.bson.collection._
import org.mongodb.scala.MongoClient
import org.mongodb.scala.MongoDatabase
import org.mongodb.scala.MongoCollection
import org.mongodb.scala.Observer
//import org.mongodb.scala.Completed

/** HousingPricesReader
 * AUTHOR: Michael Tsoumpariotis
 */
object HousingPricesReader extends App {

  var fileName = ""
  val helpInstructions = "HelpInstructions"
  val helpFile = io.Source.fromFile(helpInstructions)

  //INITIATE APP
  val file = readFile()
  val housesArrayBuffer = parseHouses()

  //MONGO
  val houseDao = new HouseDao(MongoClient())

  instructions
  cli

  //POST FUNCTIONS HERE
  //IO, FILE HANDLING

  def readFile(): BufferedSource = {
    println("What file would you like to read?")
    fileName = scala.io.StdIn.readLine()
    return io.Source.fromFile(fileName)
  }

  def parseHouses() = {
    val housesArrayBuffer = new ArrayBuffer[House]()

    //drop(1)
    for (ln <- file.getLines()) {
      val Array(index, sqft, beds, baths, zip, year, price) = ln.split(",").map(_.trim)
      val house = House.apply(index, sqft, beds, baths, zip, year, price)
      housesArrayBuffer.addOne(house)
    }
    housesArrayBuffer
  }

  def avgPrice(housesArrayBuffer: ArrayBuffer[House]): Int = {
    var avg = 0
    var m = Map[String, Int]()
    for (a <- 0 until housesArrayBuffer.length) {
      m.update(housesArrayBuffer(a).index, housesArrayBuffer(a).price.toInt)
    }
    m.foreach(x => avg += x._2)
    avg / housesArrayBuffer.length
  }

  def outputToFile(name: String, argArrayBuffer: ArrayBuffer[House]) = {
    val file = new File(name)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write("Index, Living Space (sq ft), Beds, Baths, Zip, Year, List Price ($)\n")
    argArrayBuffer.foreach(House => bw.write(House.toString + "\n"))
    bw.close()
    println("Done")
  }

  def addHouse(sqft: String, beds: String, baths: String, zip: String, year: String, price: String): Unit = {
    val index = (housesArrayBuffer.length + 1).toString
    housesArrayBuffer += House.apply(index, sqft, beds, baths, zip, year, price)
  }

  def findUndervalued(hab: ArrayBuffer[House]): ArrayBuffer[House] = {
    val bestValueHouses = hab.filter(h => (h.price.toInt < avgPrice(housesArrayBuffer)))
    bestValueHouses
  }

  def printHouses(hab: ArrayBuffer[House]) = {
    println("#, sqft, beds, baths, zip, year, price")
    hab foreach println
  }

  def printToMongo(hAB: ArrayBuffer[House]) = {
    val houseDao = new HouseDao(MongoClient())

    hAB foreach( h => houseDao.addToMongo(h))
    var result = houseDao.getAll()
    result foreach println
  }


  //FUNCTIONALITY
  def cli() = {
    var cmd = ""
    val formatter = java.text.NumberFormat.getIntegerInstance
    while (cmd != "q") {
      println("What would you like to do?")
      cmd = scala.io.StdIn.readLine()
      cmd match {
        case "print" => printHouses() //this prints houses
        case s"add $sqft $beds $baths $zip $year $price" => addHouse(sqft, beds, baths, zip, year, price)
        case "max price" => val s = "Max price of a house is $" + "%,d".format(maxPrice(housesArrayBuffer)); println(s)
        case "min price" => val s = "Min price of a house is $" + "%,d".format(minPrice(housesArrayBuffer)); println(s)
        case "average" => val s = "Average price is $" + "%,d".format(avgPrice(housesArrayBuffer)); println(s)
        case "find under" => printHouses(findUndervalued(housesArrayBuffer))
        case "mongo" => printToMongo(housesArrayBuffer)
        case "mongo undervalue" => printToMongo(findUndervalued(housesArrayBuffer))
        case s"save" => outputToFile(fileName, housesArrayBuffer)
        case s"save as $name" => outputToFile(name, housesArrayBuffer)
        case s"save undervalued houses as $bVName" => outputToFile(bVName, findUndervalued(housesArrayBuffer))
        case "i" | "?" | "help" => instructions
        case ("q" | "exit") => println("Exiting");
        case _ => println("Invalid option. Press i for list of instructions")
      }
    }
  }

  def printHouses() = {
    println("#, sqft, beds, baths, zip, year, price")
    housesArrayBuffer foreach println
  }

  def maxPrice(hab: ArrayBuffer[House]): Int = {
    var max = 0
    for (h <- hab) {
      val price = h.price
      if (max <= price.toInt) max = price.toInt
    }
    max
  }

  def minPrice(hab: ArrayBuffer[House]): Int = {
    var min = Int.MaxValue
    for (h <- hab) {
      val price = h.price
      if (min >= price.toInt) min = price.toInt
    }
    min
  }

  def instructions = {
    println("Here is a list of commands: " +
      "\n\tprint                  - Print houses" +
      "\n\tadd                    - Add house with specified sqft, beds, baths, zip, year, and price" +
      "\n\tmax price              - Find max price of house" +
      "\n\tmin price              - Find min price of house" +
      "\n\taverage                - Find average price" +
      "\n\tfind under             - Find houses that are undervalued" +
      "\n\tmongo                  - Outputs to mongo" +
      "\n\tmongo undervalue       - Outputs undervalued houses to mongo" +
      "\n\tsave                   - Save current file" +
      "\n\tsave as                - Save current file as <specifiedName>" +
      "\n\ti                      - Instructions" +
      "\n\tq                      - exit")

  }
}
