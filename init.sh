#!/bin/sh
mongoimport -d testdb -c House --file "/docker-entrypoint-initdb.d/zillow.csv" --type csv --fields= index, sqft, beds, baths, zip, year, price
