# Smart-Campus

## **Overview**
The SmartCampus API is a restful web service that was built using JAX-RS and an embedded jetty server. It provides endpoints to manage rooms, sensors, and sensor readings for the smart campus environment. The API uses data structures like hashmap and arraylist to store data and follows restful principles including proper HTTP status codes, JSON responses, exception handling and request logging.

## **Building and Running Instructions**

### **requirements**
- Java 21
- Maven 3.9+

### **Steps to build and run**

**Clone the repository**

git clone https://github.com/w1995934/SmartCampus.git
cd SmartCampus

**Build the project**
mvn clean package

**Run the server**
java -jar target/SmartCampus-1.0-SNAPSHOT.jar

**The API will be available at**
http://localhost:8080/api/v1


## **Sample curl Commands**

**Get API discovery info**
curl GET http://localhost:8080/api/v1/discovery

**Get all rooms**
curl GET http://localhost:8080/api/v1/rooms

**Create a new room**
curl POST http://localhost:8080/api/v1/rooms
{"roomID": "room4", "name": "Computer Lab", "building": "Tech Block", "floor": 3}

**Delete a room with no sensors**
curl DELETE http://localhost:8080/api/v1/rooms/room3

**Delete a room with sensors (409 conflict)**
curl DELETE http://localhost:8080/api/v1/rooms/room1

**Get all sensors**
curl GET http://localhost:8080/api/v1/sensors

**Get a sensors filtered by type**
curl GET http://localhost:8080/api/v1/sensors?type=CO2

**Create a new sensor**
curl POST http://localhost:8080/api/v1/sensors
{"sensorID": "sensor4", "type": "CO2", "status": "ACTIVE", "roomID": "room1", "currentValue": 0.0}

**Create a sensor with invalid roomID (422 error)**
curl POST http://localhost:8080/api/v1/sensors
{"sensorID": "sensor5", "type": "CO2", "status": "ACTIVE", "roomID": "room99", "currentValue": 0.0}

**Get readings for sensor1**
curl GET http://localhost:8080/api/v1/sensors/sensor1/readings

**Post a new reading**
curl POST http://localhost:8080/api/v1/sensors/sensor1/readings
{"value": 420.0, "unit": "ppm"}

**Post a reading to maintenance sensor (403 error)**
curl POST http://localhost:8080/api/v1/sensors/sensor3/readings
{"value": 55.0, "unit": "%"}

## **Questions and Answers**

**Question: In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures.**

JAX-RS creates a new instance of a resource class for every incoming HTTP request. This is known as the per request lifecycle which means each request gets its own fresh instance of the resource class, which avoids thread-safety issues within the resource class itself. But because a new instance is created per request any instance variables are reset to maintain shared state across requests. Data needs to be stored in a static and shared data structure such as a hashmap. In this project the DataStore class uses static hashmap to store rooms, sensors, and readings which makes sure the data persists across multiple requests. Since multiple requests could access and modify the hashmap also care must be taken to prevent race conditions. In a production system concurrenthashmap would be used to prevent data corruption.

**Question: Why is the provision of Hypermedia considered a hallmark of advanced RESTful design? How does this approach benefit client developers compared to static documentation?**

Hypermedia is considered a hallmark of advanced restful design because it allows clients to navigate an API dynamically through links provided in responses, rather than relying on hardcoded URLs or external documentation. This benefits client developers because they only need to know the entry point of the API and can discover all available actions and resources from the responses themselves. Which reduces coupling between the client and server and makes the API self documenting and allows the server to change URLs without breaking clients as long as the links remain the same.

**Question: When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client side processing.**

Returning only IDs in a list response reduces the size of the payload which saves network bandwidth especially when there are many rooms. But the client must then make additional requests to retrieve the all the details of each room which increases the number of HTTP calls. Returning full room objects increases the payload size but reduces the number of requests needed which in terms improves the performance for clients that need the full data. The best approach depends on the use case for example a large collections such as summary list with IDs is preferred while for smaller collections returning full objects is more convenient for the client.

**Question: Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times.**

The DELETE operation is idempotent in this implementation because if a client sends the same DELETE request for a room multiple times, the first request will successfully delete the room and return a 200 ok response. Any repeat DELETE requests for the same room will return a 404 not found response because the room no longer exists in the DataStore. Although the response is different the state of the server remains the same after the first deletion so the room is gone regardless of how many times the request is sent.

**Question: We explicitly use the @Consumes(MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml.**

The consumes annotation tells JAX-RS that the POST method only accepts requests with a specific content type that is json. If a client sends data in a different format such as plain text JAX-RS will automatically reject the request and return an HTTP 415 Unsupported Media Type response before the method is even uaed. This makes sure that only valid json data is processed by the API and prevents unexpected errors from malformed or incorrectly formatted request bodies.

**Question: You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path. Why is the query parameter approach generally considered superior for filtering and searching collections?**

Using Queryparam for filtering is considered better for embedding the filter value in the URL path because query parameters are already optional. Using Queryparam with the same endpoint can return all sensors when no type is provided or even a filtered list when a type is specified. If the type was part of the path such as /sensors/type/CO2 it would be a mandatory part of the URL structure making it impossible to retrieve all sensors without a separate endpoint. Query parameters are also generally better for filtering and searching as they dont identify a specific resource but instead modify the result of a collection request.

**Question: Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs?**

The subresource pattern is a way of splitting up a large API into smaller and more manageable classes. Instead of putting all the code for every endpoint into one big class, each part of the API gets its own dedicated class. In this project instead of handling sensor readings inside the SensorResource class there is a separate SensorReadingResource class that was created just for readings. This keeps each class small and focused on one task and making the code easier to understand and maintain. It also means that if the readings logic needs to change, only the SensorReadingResource class needs to be updated without touching anything else.

**Question: Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?**

HTTP 422 is a better fit than 404 in this situation because 404 means the URL was not found but because here the URL exists and works fine. The real problem is that the roomID inside the request body does not exist in the system. So the request arrived correctly but the data inside it was invalid. HTTP 422 is designed exactly for this situation where it tells the client that the request was received and understood but could not be completed because of a problem with the data that was sent.

**Question: From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?**

Showing a Java stack trace to users is a security risk because it reveals too much information about how the application works. It shows things like class names, method names, and line numbers which an attacker could use to find weaknesses in the code. With this information an attacker could craft a targeted attack against the application. By returning a simple 500 Internal Server Error message with no technical details, the API keeps its internal workings hidden and makes it much harder for attackers to exploit it.

**Question: Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method?**

Using a filter for logging means the logging code only needs to be written once instead of copying it into every single resource method. If logging was added manually to each method then any small change would need to be updated everywhere which is ,pre time consuming and easy to get wrong. With a filter the JAX-RS automatically adds the logging to every request and response without touching any of the resource classes. This keeps the code clean and easy to maintain.