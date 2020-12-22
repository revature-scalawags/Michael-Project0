import org.bson.types.ObjectId

case class House(_id: ObjectId,index: String, sqft: String, beds: String, baths: String, zip: String, year: String, price: String) {

  override def toString: String = {
    return s"$index, $sqft, $beds, $baths, $zip, $year, $price"
  }
}


object House {
  def apply(index: String, sqft: String, beds: String, baths: String, zip: String, year: String, price: String ): House =
    House(new ObjectId(), index, sqft, beds, baths, zip, year, price)
}

