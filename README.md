# Michael-Project0
A Housing Price Reader used to extract data from a CSV file for analytics
## Prepare MongoDB
Build a new docker image
>docker build -t mongotestdb .

Run the image
>docker run -p 27017:27017 -d --name mongotestdb --rm mongotestdb

## Compile
>sbt compile

## Test
>sbt test

## Run
>stb --error run

# Requirements
- [ ] Written in Scala/SBT
- [ ] Takes input from CLI
    - [ ] CSV or JSON simple datasets
    - [ ] Flags/environment variables
    - [ ] arguments
- [ ] Parses datasets into a Collection
    - [ ] Every line parsed into arrays
    - [ ] Every item in a line is gathered into a Map
- [ ] Analysis
    - [ ] Count every instance of a key from the Map
- [ ] Output analysis
    - [ ] Output to STDOUT
    - [ ] Output to File
    - [ ] Output to MongoDB

# Features
- [ ] CLI that takes dataset as file input
- [ ] Use Scala Collection to parse data
- [ ] Perform data analytics on collection (find min/ max/ average/...)
- [ ] Return list of undervalued houses
- [ ] Well documented and extensive code coverage with unit tests
- [ ] Logs events and output to files and NoSQL databases
