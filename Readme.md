# Docker Desktop Version New version: 4.35.0

# CS 6650 Scalable Distributed System 
# Project 2: Multi-threaded Key-Value Store using RPC

For this project, you will extend Project 1: Key-Value Store Application (TCP and UDP) in two distinct ways. 
1) You need to enable your client and server to communicate using Remote Procedure Calls (RPC) instead of sockets.  If you’ve implemented Project #1 in Java, you may want to look into and leverage Java RMI for RPC communication.  However, there are multiple other RPC frameworks you can leverage (with their own IDLs) to provide the stubs/skeletons necessary across the network.  An additional example that enables the use of multiple languages is Apache Thrift (http://thrift.apache.org/
 (Links to an external site.)

2) You need to make your server multi-threaded such that you can handle multiple outstanding client requests at once.  You may decide how to thread your server.  One approach may be to use thread pools similar to other servers, although there are certainly many ways to do this. The key result is that your servers should be able to handle requests from multiple running instances of you client doing concurrent PUT, GET, and DELETE operations.  Due to the addition of multi-threading, you will need to handle mutual exclusion. 

Why using RPC framework? 
Transitioning from TCP/UDP socket-based communication to an RPC framework like Java RMI (Remote Method Invocation) significantly simplifies the communication layer between your client and server. With RMI, much of the boilerplate code associated with socket programming is abstracted away, allowing you to focus more on the core functionality of your application.

## Project Structure
```bash

Project 2/src/
├── Readme.md
├── Executive Summary.txt
├── docker-compose.yml
├── client/
│   ├── ClientApp.java
│   └── Main.java
├── server/
│   ├── KeyValueStoreInterface.java
│   ├── KeyValueStoreImpl.java
│   └── ServerApp.java
├── utils/
│   ├── ILogger.java
│   └── Logger.java
└── .gitignore


## Requirements
- **Java Development Kit (JDK) 8 or higher**
  - Ensure `javac` and `java` commands are available in your terminal.
- **Operating System**
  - The application should work on any OS with Java support (e.g., macOS, Linux, Windows).
- **Terminal or Command Prompt**
  - Required to compile and run the application.

## Compilation Instructions

Open a terminal window and navigate to the root directory containing the `src` folder, e.g: my `src` pathname on Mac is: /Users/weiwenwu/Desktop/CS 6650/Project2/src


**Compile server Package First: **
```bash
javac server/*.java

**Compile utils Package Second: **
```bash
javac utils/*.java

**Compile client Package Lastly: **
```bash
javac client/*.java





## Running the Server and Client Applications Locally with RMI Registry
### 1. **Start the RMI Registry**
- Open a same or new terminal and navigate to the project directory, cd /path/to/your/project2/src, e.g. /Users/weiwenwu/Desktop/CS 6650/Project2/src: 

	```bash
	rmiregistry 1099

- if port 1099 is already in use, the error message would be like:
java.rmi.server.ExportException: Port already in use: 1099; nested exception is: 
    java.net.BindException: Address already in use

- follow the instructions to kill port 1099
	- Open a same or new terminal and navigate to the project directory, cd /path/to/your/project2/src
	```bash
	lsof -i :1099
	
	```bash
	kill -9 <PID>

- after kill the port, verify that port is free
	- Open a same or new terminal and navigate to the project directory, cd /path/to/your/project2/src

	```bash
	lsof -i :1099




### 2. **Run the Server**
- Open a same or new terminal and navigate to the project directory, cd /path/to/your/project2/src, e.g. /Users/weiwenwu/Desktop/CS 6650/Project2/src: 
  
  	```bash
  	java server.ServerApp 1099

Expected Output: Server is ready on port 1099.


### 3. **Run the Client**
- Open a same or new terminal and navigate to the project directory cd /path/to/your/project2/src, e.g. /Users/weiwenwu/Desktop/CS 6650/Project2/src: 
  
  	```bash
  	java client.ClientApp localhost 1099

- Sample Output: 
Here I have pre-population and also performed 15 operations.

Connected to the server at localhost:1099.
Pre-populating the key-value store with initial data...
Executed: PUT apple red | Response: OK: Key added successfully.
Executed: PUT banana yellow | Response: OK: Key added successfully.
Executed: PUT grape purple | Response: OK: Key added successfully.
Executed: PUT lemon yellow | Response: OK: Key added successfully.
Executed: PUT cherry red | Response: OK: Key added successfully.
Pre-population completed.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put neu sj
Server response: OK: Key added successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put nyu nyc
Server response: OK: Key added successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put bu bos
Server response: OK: Key added successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put penn pa
Server response: OK: Key added successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put ucsd sd
Server response: OK: Key added successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get ucsd
Server response: OK: sd
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get neu
Server response: OK: sj
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get cherry
Server response: OK: red
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get grape
Server response: OK: purple
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get nyu
Server response: OK: nyc
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete neu
Server response: OK: Key deleted successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete penn
Server response: OK: Key deleted successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete grape
Server response: OK: Key deleted successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete cherry
Server response: OK: Key deleted successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete bu
Server response: OK: Key deleted successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): show
Server response: banana : yellow
apple : red
lemon : yellow
ucsd : sd
nyu : nyc


## Running the Server and Client Application with Docker
### 1. Building Docker Image
	```bash
	cd /path/to/your/project2

	```bash
	docker-compose build

### 2. Running Docker Containers
	```bash
	docker-compose up

-Expected output:
➜  Project 2 docker-compose up
Attaching to client-1, server-1
server-1  | Server is ready on port 1099.
client-1  | Connected to the server at server:1099.
client-1  | Pre-populating the key-value store with initial data...
client-1  | Executed: PUT apple red | Response: OK: Key added successfully.
client-1  | Executed: PUT banana yellow | Response: OK: Key added successfully.
client-1  | Executed: PUT grape purple | Response: OK: Key added successfully.
client-1  | Executed: PUT lemon yellow | Response: OK: Key added successfully.
client-1  | Executed: PUT cherry red | Response: OK: Key added successfully.
client-1  | Pre-population completed.


v View in Docker Desktop   o View Config   w Enable Watch


### 3. Interact with the Client
- Open new terminal and navigate to the project directory, cd /path/to/your/project2, e.g. /Users/weiwenwu/Desktop/CS 6650/Project2/src: 

	```bash
	docker attach project2-client-1

- Perform Operations: Test PUT, GET, DELETE, and SHOW commands to ensure they work as expected. 
➜  Project 2 docker attach project2-client-1
put neu bos
Server response: OK: Key added successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put vivian wu
Server response: OK: Key added successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put school graduate
Server response: OK: Key added successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put county china
Server response: OK: Key added successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put age under30
Server response: OK: Key added successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get vivian
Server response: OK: wu
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get school
Server response: OK: graduate
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get neu   
Server response: OK: bos
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get age
Server response: OK: under30
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get country
Server response: ERROR: Key not found.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete vivian
Server response: OK: Key deleted successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete age
Server response: OK: Key deleted successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete country
Server response: ERROR: Key does not exist.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete neu
Server response: OK: Key deleted successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete school
Server response: OK: Key deleted successfully.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): show
Server response: banana : yellow
apple : red
cherry : red
lemon : yellow
county : china
grape : purple


### 4. I have attached a few docker output screenshots as reference. 

![Screenshot 2024-10-24 at 8 50 18 PM](https://github.com/user-attachments/assets/fe125309-5c2c-4f0f-86ce-3b1c6754d8e6)
![Screenshot 2024-10-24 at 8 56 40 PM](https://github.com/user-attachments/assets/0ac6f900-3afc-4a98-9971-7e6a316a2dc7)

