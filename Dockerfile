# Step 1: Use the Gradle base image for building the application
FROM gradle:jdk21 as build

# Step 2: Create app directory in container
WORKDIR /app

# Step 3: Copy the project files to the container
COPY . .

# Step 4: Build the application
RUN gradle build -x test

# Step 5: Command to run the application
RUN cp /app/build/libs/MicroservizioBase-0.0.1-SNAPSHOT.jar /app.jar

# Step 6: Command to run the application
ENTRYPOINT ["java","-jar","/app.jar"]
