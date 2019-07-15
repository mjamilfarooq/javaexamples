# Running 

### using maven
    
1- mvn clean install

2- java -jar target/json_parser-1.0-SNAPSHOT-jar-with-dependencies.jar smartward.json

### using docker

Dockerfile is provided to run in docker container:

1- Building image: `docker build -t smartwardparser .`

2- Running container: `docker run smartwardparser` 

# Design Consideration

1- Person is a generalization for each type of individual

2- also Person implements Comparable interface for sorting  Person by Full name `first_name family_name`.

3- Person further specify the property in JSON  `class` , considered it case sensitive "Patient" and "patient" is not same, against which sub type will be identified in JSON and parsed.

4- Staff class further specialize for staff members in hospitals and specify json sub type which will be parsed against class. 

5- mainly problem is solved using java.stream API.

6- Parser is written to parse file at runtime and produce next Person object for stream API.

7- Optional is used in Parser to represent end of stream.

### Method 1: FamousQueries.ExecuteQueryMethod1

8- Person stream further isolate each type of person and collect in separate streams to process in parallel.

9- patient, and doctor streams are further sorted and collected in their respected lists.

10- nurses streams are further grouped by ward number and converted to parallel streams and each of the ward is separately sorted in parallel. 

11- result set is collected in separated static variables.

### Method 2: FamousQueries.ExecuteQueryAndDisplay

12- Only used sequential stream and display the result set at the same time


# Alternate Approach

1- alternate approach can be to populate JSON object in a property map.

2- write Factory to generate appropriate subtype of Person based on class property in property map instead of using jackson specific annotation.

3- Inject Factory in the Parser.

4- For ordering for first name and family name appropriate lambdas can be written inline instead of driving Person from Comparable interface. 