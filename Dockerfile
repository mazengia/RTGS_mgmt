FROM openjdk:17-alpine
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
ARG JAR_FILE=target/*.jar
RUN  mkdir /var/storage
#WORKDIR /home/spring
COPY ${JAR_FILE} app.jar
ENV SPRING_PROFILES_ACTIVE develop
RUN apk add --no-cache fontconfig ttf-dejavu
RUN apk --no-cache add msttcorefonts-installer fontconfig && \
    update-ms-fonts && \
    fc-cache -f
RUN cp -r /usr/share/fonts/truetype/msttcorefonts $JAVA_HOME/lib/fonts

ENV PORT 8080
EXPOSE $PORT
CMD [  "java","-jar","app.jar","-Dserver.port=${PORT}","-Duser.timezone=UTC+03:00" , "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}" ]
