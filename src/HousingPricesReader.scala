import HousingPricesReader.instructions

import java.io._
import java.io.FileNotFoundException
import scala.collection.mutable.{ArrayBuffer, ListBuffer, Map}
import scala.io.BufferedSource

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
  //instructions
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
    for (ln <- file.getLines().drop(1)) {
      val Array(index, sqft, beds, baths, zip, year, price) = ln.split(",").map(_.trim)
      var house = House(s"$index", s"$sqft", s"$beds", s"$baths", s"$zip", s"$year", s"$price")
      housesArrayBuffer.addOne(house)
    }
    housesArrayBuffer
  }

  def avgPrice(): Int = {
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
    argArrayBuffer.foreach(House => bw.write(House.toString+"\n"))
    bw.close()
    println("Done")
  }

  def addHouse(sqft: String, beds: String, baths: String, zip: String, year: String, price: String): Unit = {
    val index = (housesArrayBuffer.length+1).toString
    housesArrayBuffer += new House(index, sqft, beds, baths, zip, year, price)
  }

  def findUndervalued(housesArrayBuffer: ArrayBuffer[House]):ArrayBuffer[House]={
    val bestValueHouses = housesArrayBuffer.filter(House => (House.price.toInt < avgPrice()))
    bestValueHouses
  }

  def printHouses(hab: ArrayBuffer[House]) = {
    println("#, sqft, beds, baths, zip, year, price")
    hab foreach println
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
        case "max price" => val s = "Max price of a house is $" + "%,d".format(maxPrice()); println(s)
        case "min price" => val s = "Min price of a house is $" + "%,d".format(minPrice()); println(s)
        case "average" => val s = "Average price is $" + "%,d".format(avgPrice()); println(s)
        case "find under" => printHouses(findUndervalued(housesArrayBuffer))
        case s"save" => outputToFile(fileName, housesArrayBuffer)
        case s"save as $name" => outputToFile(name, housesArrayBuffer)
        case s"save undervalued houses as $bVName" => outputToFile(bVName, findUndervalued(housesArrayBuffer))
        case "i"|"?"|"help" => instructions
        case ("q"|"exit") => println("Exiting");
        case _ => println("Invalid option. Press i for list of instructions")
      }
    }
  }

  def printHouses() = {
    println("#, sqft, beds, baths, zip, year, price")
    housesArrayBuffer foreach println
  }

  def maxPrice(): Int = {
    var max = 0
    for (h <- housesArrayBuffer) {
      val price = h.price
      if (max <= price.toInt) max = price.toInt
    }
    max
  }

  def minPrice(): Int = {
    var min = Int.MaxValue
    for (h <- housesArrayBuffer) {
      val price = h.price
      if (min >= price.toInt) min = price.toInt
    }
    min
  }

  def instructions = {
    println("Here is a list of commands: " +
      "\nprint     - Print houses" +
      "\nadd       - Add house with specified sqft, beds, baths, zip, year, and price"+
      "\nmax price - Find max price of house" +
      "\nmin price - Find min price of house" +
      "\naverage   - Find average price" +
      "\nfind under- Find houses that are undervalued"+
      "\nsave      - Save current file" +
      "\nsave as   - Save current file as <specifiedName>" +
      "\ni         - Instructions" +
      "\nq         - exit")

  }
}
