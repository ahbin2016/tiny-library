#Tiny Library
##Introduction
Develop a management system for a tiny library. Generally, it serves RESTful APIs which accept admin's requests to add, update and delete books; users can register accounts into the system, borrow up to 5 books at most and return books through another set of RESTful APIs; running only one instance is enough, no need to worry about scalability; all data doesn't need to be persisted, save in memory is acceptable.

##Requirements
Detailed requirements as below:

1.Book management

 - A book contains information about ID, ISBN(can be a string of any number, duplicatable), name(duplicatable), author, publish date and summary.
 - There're three book modification APIs - add, update and delete a book. To make things simple, authentication is not required, anyone knows the API can call.
 - Books can be searched by ISBN or name. For both searching by ISBN and name, only exact match is required. Anyone can search books, no need authentication.
 - Below are the paths and methods for the RESTful APIs, but developers need to add more details to complete them.
| | Path | Method | |---|---|---| | Add book | /books | POST | | Update book | /books | PUT | | Delete book | /books | DELETE | | Search books | /books | GET |

 - All book data can be just saved in memory

2.User management

 - A user has unique ID and name(duplicatable).
 - Any user can register himself with preferred user ID and name.
 - User cannot be removed.
 - Below are the path and method for the RESTful API, but developers need to add more details to complete it.
| | Path | Method | |---|---|---| | Register user | /users | POST |

 - All user data can be just saved in memory
 
3.Borrow system

 - There're two RESTful APIs for borrow system - borrow and return. Both of them accept user ID and book ID.
 - Each borrow record should include user ID, book ID, borrow date, expiry date and return date(if returned).
 - A book can be only borrowed to a single user.
 - Below are the paths and methods for the RESTful APIs, but developers need to add more details to complete them.
| | Path | Method | |---|---|---| | Borrow book | /books/borrow | POST | | Return book | /books/return | POST |

 - All borrow data can be just saved in memory
 
4.Log system

 - Each RESTful API call's request and response have to be logged.
 - Logs should be leveled, all errors and warnings need to be logged.
 - Write logs to both console and file system (bonus).
 
5.Concurrency (bonus)

 - Service can serve multiple users at the same time.
 - Pay attention to thread safty.
 - As we are developing a tiny library, only single instance of the service is needed, so no need distributed concurrency.

6.Unit test (bonus)

 - Create unit tests for code written. Choose one unit to write complete tests, others can have just one or two example tests.

7.Save data in Database (bonus)

 - Save book data in external database.
 - Save user data in external database.
 - Save borrow data in external database.

8.Configuration (bonus)

 - Read configuration from a file. The file can be in any format, such as JSON, YAML.
 - Put dynamic configuration such as
 - Maximum number of books a user can borrow, 5 by default.
 - Borrow duration - how long a book can be borrowed, 30 days by default.
 - Log file base directory.
 - And other things as needed.


##Build and deployment
 - Developer needs to write a README file to describe how to build(if applicable) and deploy the project. It is greatly appreciated that by following the README file, customer can get the service run without any problem.
 
 
##Building the Application
1. tiny-library - build as mvn clean install and run as springboot application
2. It will run in default port 8080
3. Application is running using H2-in memory database. To access the H2 Console, navigate to 
```
http://localhost:8080/h2
```
 - Look for the url and username in the application properties

## To Test the Application
###BOOK API
1.To create book details
```
curl --location --request POST 'http://localhost:8080/api/books' \
--header 'Content-Type: application/json' \
--data-raw '{
    "isbn" : "S12345",
    "name" : "HunterX",
    "author" : "Gon",
    "summary" : "Test Summaryy",
    "publishDate" : "2019-12-12"
}'
```

2.To update Book Details
```
curl --location --request PUT 'http://localhost:8080/api/books' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 1,
    "isbn": "S123456",
    "name": "HunterX",
    "author": "Killua",
    "summary": "Test Summaryy",
    "publishDate": "2019-12-12"
}'
```
3.To Delete A Book
```
curl --location --request DELETE 'http://localhost:8080/api/books/{bookId}'
```

4.To Search for A Book
```
curl --location --request GET 'http://localhost:8080/api/books?isbn=S12345&name=HunterX'
```
 - Request Param isbn and name both optional, if left null, it will get all book records
 
### USER API

1.To create a User
```
curl --location --request POST 'http://localhost:8080/api/users' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "Arvin"
}'
```

### Book Activity Record API
1. To Borrow a Book
```
curl --location --request POST 'http://localhost:8080/api/books/borrow' \
--header 'Content-Type: application/json' \
--data-raw '{
    "userId": 1,
    "bookId" :1
}'
```
 - Book can be borrow to a Single User. API will thrown an error message
 ```
{
    "errorCode": "BAD_REQUEST",
    "errorMsg": "Book currently not yet returned!",
    "status": 400,
    "timestamp": "2020-08-22 07:58:18"
}
```
 - The maximum number of books a user can be borrowed is 5. It is set in the application properties. Can be configure dynamically
   without code change

```
{
    "errorCode": "BAD_REQUEST",
    "errorMsg": "Maximum borrowed book reached!",
    "status": 400,
    "timestamp": "2020-08-22 08:27:20"
}
```

2.To Return a Book
```
curl --location --request POST 'http://localhost:8080/api/books/return' \
--header 'Content-Type: application/json' \
--data-raw '{
   "id": 6,
    "userId": 2,
    "bookId": 6,
    "borrowDate": "2020-08-22",
    "expiryDate": "2020-09-21"
}'
```




