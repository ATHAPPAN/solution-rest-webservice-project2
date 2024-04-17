# Order Service

The Order Service is a REST WebService that simulates a backend that
would store and Order details of a vehicle given a vehicle id as
input and retive the order details based on vehicle id and customer id.


## Features

- REST WebService integrated with Spring Boot


### Create an Order

`POST` `http://localhost:8080/orders`

{
    "vehicleId":1,
    "customerId":1,
     "status":"Booked"
}

GET http://localhost:8080/orders?vId=1&custId=1


