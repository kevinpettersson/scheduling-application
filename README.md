# DIT213-GROUP-15

## TimeBridge

## Information
More information about our project including the **Requirements Analysis Document (RAD)** and **System Design Document (SDD)**, can be found in the `docs` folder.

## Installation
Before running the application, ensure the following are installed on your system:
- **Step 1.** Install Node.js eg. https://nodejs.org/en
- **Step 2.** Install Apache Maven eg. https://maven.apache.org/install.html
- **Step 3.** Java Development Kit (JDK)

## Running the application
- **Step 1.** Open a terminal and navigate to the `timebridge` directory inside the project folder.
- **Step 2.** Run the follwoing command `mvn clean install` (only required on the first run)
- **Step 2.** Run the following command to start the Spring-Boot server: `mvn spring-boot:run`
- **Step 3.** Open a second terminal and navigate to the `timebridge-web` directory.
- **Step 4.** Run the following command to install the frontend dependencies (only required on the first run): `npm install`
- **Step 5.** Run the following command to start the frontend deployment server: `npm run dev`
- **Step 6.** Now copy the adress from the terminal into the browser to access the application (http://localhost:5173)

