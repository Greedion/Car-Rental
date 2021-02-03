
# Car-Rental

> Car rental application

### Clone

- Clone this repo to your local machine using `https://github.com/Greedion/Car-Rental.git`



## Features
- View the list of all brands
- Adding the brand to the base
- Brand data update
- Removal of the brand
- View the list of all cars
- Adding the car to the base
- Car data update
- Removal of the car
- View a list of all car rentals
- Creating a binding car reservation
- Transfer money to your account
- View a list of all accounts
- Create account
- Blockade against renting a rented car
- Automatic calculation of whether a customer can rent a car based on the account balance
- Possibility to log in (2 types of accounts)

## Technology
 - Spring Boot
 - Spring Security
 - Java JWT Token
 - Spring Validation
 - Spring Web
 - Spring Data JPA
 - H2 Database
 - Maven
 - Lombok
 
 #Credentaials:
 ## Admin/Admin
 ## User/User
 # Api Endpoints: 
> Default adress: http://localhost:8080/api
## Access for everyone
### Authorization
- POST: /auth/signin 
### Create account
- POST /user/createaccount
### Get brand/s
- GET /brand
- GET /brand/{id}
### Get car/s
- GET /car
- GET /car/{id}
## Access for user
- GET /loan
- POST /loan/createreservation
- POST /user/moneytransfer
## Access for admin
- POST /brand
- PUT /brand
- DELETE /brand/{id}
- POST /car
- PUT /car
- DELETE /car/{id}
- GET /loan
- POST /loan/createreservation
- POST /user/moneytransfer
- GET /user









