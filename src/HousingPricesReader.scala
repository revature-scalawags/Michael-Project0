import scala.io.BufferedSource

/**HousingPricesReader
 *
 */
object HousingPricesReader extends App{

  println("What file would you like to read?")

  val fileName = scala.io.StdIn.readLine()
  val file = io.Source.fromFile(fileName)
  val helpInstructions = "HelpInstructions"
  val helpFile = io.Source.fromFile(helpInstructions)

  //INITIATE APP
  println("Here are a list of commands: " )
  readInst(helpFile)
  initState()

  //POST FUNCTIONS HERE
  //make a read instructions function
  def readInst (f : BufferedSource) ={
    for (ln <- f.getLines()) println(ln)
  }

  def initState() = {
    println("What would you like to do?")
    val cmd = scala.io.StdIn.readLine()
    val init =  cmd match {
      case "1" => printHouses()
      case "2" => findMax()
      case "3" => findMin()
    }
  }

  def printHouses() = {
    for (ln <- file.getLines().drop(1)) {
      val Array(index, sqft, beds, baths, zip, year, price) = ln.split(",").map(_.trim)
      println(s"#$index ${sqft}sqft ${beds}beds ${baths}baths $zip $year ${price}")
    }
  }
  def findMax() = {
    var max = 0
    for (ln <- file.getLines().drop(1)){
      val price = ln.split(",")(6).trim
      if (max<=price.toInt) max = price.toInt
    }
    println(max)
  }
  def findMin() = {
    var min = 10000000
    for (ln <- file.getLines().drop(1)){
      val price = ln.split(",")(6).trim
      if (min>=price.toInt) min = price.toInt
    }
    println(min)
  }

}
