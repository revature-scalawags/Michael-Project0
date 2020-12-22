#!/bin/sh
mongoimport -d mongotestdb -c house --file "/docker-entrypoint-initdb.d/zillow.csv" --type csv --fields= index, sqft, beds, baths, zip, year, price