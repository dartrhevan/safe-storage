FROM maven:3.6.3-jdk-14 as builder

COPY . /usr/src/app/backend

WORKDIR /usr/src/app/backend

RUN mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V && mvn test -B

#java -Dserver.port=$PORT $JAVA_OPTS -jar target/numericanalysis-backend-0.0.1-SNAPSHOT.jar

FROM openjdk:14-jdk

WORKDIR /usr/src/app/backend

COPY --from=builder /usr/src/app/backend/target/safe-storage-0.0.1-SNAPSHOT.jar .
#TODO: change jar name
ENTRYPOINT [ "java -Dserver.port=$PORT $JAVA_OPTS -jar safe-storage-0.0.1-SNAPSHOT.jar" ]