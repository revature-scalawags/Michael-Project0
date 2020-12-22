case class House(index: String, sqft: String, beds: String, baths: String, zip: String, year: String, price: String) {
  override def toString: String = {
    return s"$index, $sqft, $beds, $baths, $zip, $year, $price"
  }
}

//object House {
//  def apply(index: String, sqft: String, beds: String, baths: String, zip: String, year: String, price: String): House =
//    House(index, sqft, beds, baths, zip, year, price)
//}

