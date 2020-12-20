import java.io.FileNotFoundException
import scala.collection.mutable.ArrayBuffer
import scala.io.BufferedSource
import scala.collection.mutable.Map

/** HousingPricesReader
 *
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
  initState()

  //POST FUNCTIONS HERE
  //IO, FILE HANDLING
  def readInst(f: BufferedSource) = {
    println("Here are a list of commands: ")
    try {
      for (ln <- f.getLines()) println(ln)
    } catch {
      case e: FileNotFoundException => println("File not found")
    } finally {
      f.close()
    }
    //for (ln <- f.getLines()) println(ln)
  }

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

    for(a <- 0 until housesArrayBuffer.length){
      m.update(housesArrayBuffer(a).index, housesArrayBuffer(a).price.toInt)
    }
    m.foreach(x => avg += x._2)
    avg/housesArrayBuffer.length
  }

  //FUNCTIONALITY
  def initState() = {
    var cmd = ""
    while (cmd != "q") {
      println("What would you like to do?")
      try {
        cmd = scala.io.StdIn.readLine()
        cmd match {
          case "1" => printHouses() //this prints houses
          case "2" => println("Max price of a house is $" + maxPrice())
          case "3" => println("Min price of a house is $" + minPrice())
          case "4" => println("Average price is $"+ avgPrice())
          case "5" => instructions
          case "q" => println("Exiting")
          case _ => println("Invalid option")
        }
      } catch {
        case e: FileNotFoundException => println("File not found")
      } finally {
        file.close()
      }

    }

  }

  def printHouses() = {
    println("House(index, sqft, beds, baths, zip, year, price)")
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
      "\n1 - Print houses" +
      "\n2 - Find max price of house" +
      "\n3 - Find min price of house" +
      "\n4 - Find average price"+
      "\n5 - Instructions" +
      "\nq - Exit")

  }

  def findMax() = {
    println("entered findMax()")
    println("What do you want to find the max number of?" +
      "\n1 - Living Space (sqft)" +
      "\n2 - Beds" +
      "\n3 - Baths" +
      "\n4 - Zip" +
      "\n5 - Year" +
      "\n6- List Price")

    val input = scala.io.StdIn.readLine()
    var max = 0
    for (ln <- file.getLines().drop(1)) {
      val price = ln.split(",")(input.toInt).trim
      if (max <= price.toInt) max = price.toInt
    }
    println(max)
  }

  def findMin() = {
    println("What do you want to find the min number of?" +
      "\n1 - Living Space (sqft)" +
      "\n2 - Beds" +
      "\n3 - Baths" +
      "\n4 - Zip" +
      "\n5 - Year" +
      "\n6- List Price")

    val input = scala.io.StdIn.readLine()
    var min = Int.MaxValue
    for (ln <- file.getLines().drop(1)) {
      val price = ln.split(",")(input.toInt).trim
      if (min >= price.toInt) min = price.toInt
    }
    println(min)
  }

}
