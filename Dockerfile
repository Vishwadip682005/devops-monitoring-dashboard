FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /app

COPY . .

RUN mvn clean package

EXPOSE 9090

CMD ["mvn", "exec:java", "-Dexec.mainClass=com.devops.MainServer"]