# challengezedelivery
# Install
1. You will need to install [Docker](https://docs.docker.com/compose/install/) to run the database and the app.

2. Next, run ```docker-compose up``` to start the application.

### (Optional) 
You can build the app image using the following command: ```docker build -t challengezedelivery_app:latest ./```.

### (Optional) 
If you want to recompile the source code, you will need to install [Maven](https://maven.apache.org/install.html) on your local machine and run the command:```mvn clean install```.

### (Important) 
Make sure that the ports 3306 and 8080 are not being used, if you wish you can change them on the ```docker-compose.yml``` file.

# Run
1. Open the directory that you have cloned the master branch and run on a new terminal `docker-compose up`.

2. Once the database and the application is up, you can continue and test the application.

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
