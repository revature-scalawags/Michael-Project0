import org.scalatest.funsuite.AnyFunSuite

import scala.collection.mutable.ArrayBuffer

class HousingPriceTest extends AnyFunSuite {
  test("maxPrice properly returns max price") {
    val housesArrayBuffer = new ArrayBuffer[House]()
    housesArrayBuffer.addOne(House.apply("1", "1000", "1", "1", "000000", "1950", "500000"))
    val num = HousingPricesReader.maxPrice(housesArrayBuffer)

    assert(num == 500000)
  }

  test("minPrice properly returns min price") {
    val housesArrayBuffer = new ArrayBuffer[House]()
    housesArrayBuffer.addOne(House.apply("1", "1000", "1", "1", "000000", "1950", "500000"))
    val num = HousingPricesReader.minPrice(housesArrayBuffer)

    assert(num == 500000)
  }
  test("avgPrice properly returns average price") {
    val housesArrayBuffer = new ArrayBuffer[House]()
    housesArrayBuffer.addOne(House.apply("1", "1000", "1", "1", "000000", "1950", "500000"))
    val num = HousingPricesReader.avgPrice(housesArrayBuffer)

    assert(num == 500000)
  }
}
