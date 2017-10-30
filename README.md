# url-shortener

## Prerequisites
- Make sure you have docker installed locally.
- Run a mysql server.
    - You can run a local mysql server inside docker like this: `docker run -dp "3306:3306" -e MYSQL_USER=urlshortener -e MYSQL_PASSWORD=urlshortener -e MYSQL_ROOT_HOST=% -e MYSQL_DATABASE=urlshortener mysql/mysql-server:5.7`
    - If you prefer an external mysql server you can setup one and then update src/main/resources/application.properties:2 to point to an external MySQL DB.

**Note**: If running on Java 9 you will have to add `--add-modules java.xml.bind` to the JVM options since the hibernate version as part of spring boot still has not updated to java9.

## Build
Run the following to test and build the application.

    ./gradlew build

## Run
### Run with spring boot from command line
    ./gradlew bootRun

### Run with docker
- Update src/main/resources/application.properties:2 to point to an external MySQL DB.
- Execute `./gradlew buildDockerImage` to build the docker image.
- Execute `docker run -dp "8001:8001" url-shortener:1.0.0` to run the application.
