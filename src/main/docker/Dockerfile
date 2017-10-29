FROM leodutra/docker-alpine-oraclejre8
RUN apk add --no-cache bash

ADD startApp.sh /startApp.sh
RUN chmod u+x /startApp.sh
ENTRYPOINT ["/startApp.sh"]

ADD *.jar app.jar
