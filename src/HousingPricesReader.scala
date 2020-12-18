import scala.collection.mutable.ArrayBuffer
import scala.io.BufferedSource

/** HousingPricesReader
 *
 */
object HousingPricesReader extends App {

  var fileName = ""
  val helpInstructions = "HelpInstructions"
  val helpFile = io.Source.fromFile(helpInstructions)

  //INITIATE APP
  val file = readFile()
  readInst(helpFile)
  initState(file)

  //POST FUNCTIONS HERE
  //make a read instructions function
  def readInst(f: BufferedSource) = {
    println("Here are a list of commands: ")
    for (ln <- f.getLines()) println(ln)
  }
  def readFile(): BufferedSource = {
    println("What file would you like to read?")
    fileName = scala.io.StdIn.readLine()
    return io.Source.fromFile(fileName)
  }

  def initState(f: BufferedSource)= {
    var cmd = ""
    while (cmd != "q") {
      println("What would you like to do?")
      cmd = scala.io.StdIn.readLine()
      cmd match {
        case "1" => parseHouses() //use this to parse data
        case "2" => findMax()
        case "3" => findMin()
        case "4" => printHouses(f)//this prints houses
        case "q" => println("Exiting")
        case _ => println("Invalid option")
      }
    }

  }

  def printHouses(f: BufferedSource) = {
    println("entered printHouses()")
    f.getLines() foreach println
    println(f.length)
  }

  def parseHouses() = {
    val housesArrayBuffer = new ArrayBuffer[House]()
    for (ln <- file.getLines().drop(1)) {
      val Array(index, sqft, beds, baths, zip, year, price) = ln.split(",").map(_.trim)
      var house = House(s"$index", s"$sqft", s"$beds", s"$baths", s"$zip", s"$year", s"$price")
      housesArrayBuffer.addOne(house)
    }
    housesArrayBuffer foreach println
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

    val input =  scala.io.StdIn.readLine()
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

    val input =  scala.io.StdIn.readLine()
    var min = Int.MaxValue
    for (ln <- file.getLines().drop(1)) {
      val price = ln.split(",")(input.toInt).trim
      if (min >= price.toInt) min = price.toInt
    }
    println(min)
  }

}
