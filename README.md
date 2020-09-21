
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
 - Spring Seciurity
 - Java JWT Token
 - Spring Validation
 - Spring Web
 - Spring Data JPA
 - H2 Database
 - Maven
 - Lombok
 
 
 # Api Endpoints: 
> Default adress: http://localhost:8080/
## Access for everyone
### GET
- loan/getAll
- car/getAll
### POST
- user/createAccount
- logIn
- brand/getAll
- car/getOne
- brand/getOne
 ## Access for any authenticated user
### POST
- loan/createReservation
- user/moneyTransfer
 ## Access for any Admin
### GET
- user/getAll

### POST
- car/add
- brand/add
### PUT
- car/update
- brand/update
### DELETE
- car/delete
- brand/delete
