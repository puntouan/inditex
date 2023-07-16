# Technical test for Inditex

The service requested in the test description has been implemented trying to follow a clean architecture approach.

The URL of the endpoint for this service has been designed following this scheme:
```
/brand/{brandId}/product/{productId}/prices?date={applicationDate}
```

### Tests
The 5 tests mentioned in the test description are programmed as end-to-end tests and can be found
in the class __*com.challenge.inditex.product.GetProductRateByDateShould.*__

These 5 tests, along with the rest of the tests that provide coverage to the code, can be executed 
with (__it requires java 17__):
```
./gradlew test
```
### Execution
The application can be started with (__it requires java 17__):
```
./gradlew bootRun
```
Once the application is started, it would be possible to run the following example:

### Example of request and response:
#### request:
```
curl --location 'http://localhost:8080/brand/1/product/35455/prices?date=2020-06-14T17%3A00%3A00'
```
#### response:
```
HTTP/1.1 200 OK
Content-Type: application/json

{
    "brandId": 1,
    "productId": 35455,
    "rateId": 2,
    "startDateTime": "2020-06-14T15:00:00",
    "endDateTime": "2020-06-14T18:30:00",
    "price": "25.45€"
}
```
