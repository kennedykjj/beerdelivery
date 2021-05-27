# challengezedelivery
#Install
You will need to install [Docker-compose](https://docs.docker.com/compose/install/) to run the database and the app.

# Run
Open the directory that you have cloned the master branch, and run on a prompt `docker-compose up`.

Once the database and the application is up, you can continue and test the application.

# Test suggestion
There's a Postman Collection on the root of the source code named: `challenge-ze-delivery.postman_collection.json`.
On that Collection will have all the three available endpoints.

# Available endpoints

> ## **GET** /partner/{id}
> ## **GET** /partner/nearby/{lon}/{lat}

> ## **POST** /partner
```json
{
  "id": 1, 
  "tradingName": "Adega da Cerveja - Pinheiros",
  "ownerName": "Zé da Silva",
  "document": "1432132123891/0001",
  "coverageArea": { 
    "type": "MultiPolygon", 
    "coordinates": [
      [[[30, 20], [45, 40], [10, 40], [30, 20]]], 
      [[[15, 5], [40, 10], [10, 20], [5, 10], [15, 5]]]
    ]
  },
  "address": { 
    "type": "Point",
    "coordinates": [-46.57421, -21.785741]
  }
}
```
