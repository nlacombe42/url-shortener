# url-shortener

## Build
Run the following to test and build the application.
    ./gradlew build
 
## Run
**Note**: If running on Java 9 you will have to add `--add-modules java.xml.bind` to the JVM options since the hibernate version as part of spring boot still has not updated to java9.

### Run with spring boot from command line
    ./gradlew bootRun

### Run with docker
- Update src/main/resources/application.properties:2 to point to an external MySQL DB.
- Make sure you have docker installed locally.
- Then execute the following:
    ./gradlew buildDockerImage
    docker run -dp "8001:8001" url-shortener:1.0.0
