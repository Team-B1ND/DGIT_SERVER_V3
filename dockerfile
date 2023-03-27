FROM openjdk:11
COPY build/libs/digt-0.0.1-SNAPSHOT.jar app.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java","-jar","/app.jar","-Duser.timezone=Asia/Seoul"]