FROM maven:3.8.3-openjdk-17
WORKDIR /app
COPY . .
CMD ["sh", "-c", "sleep 10 && mvn -Dtest=CucumberTestRunner test"]
