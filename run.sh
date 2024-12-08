#!/bin/bash

# Function to handle cleanup
cleanup() {
    echo "Cleaning up..."
    kill $BACKEND_PID
    kill $FRONTEND_PID
    exit 1
}

# Trap script exit and call cleanup on any error
trap cleanup EXIT

# Navigate to the backend and build (only on the first run)
cd timebridge
echo "Building the backend..."
mvn clean install || { echo "Backend build failed. Exiting..."; cleanup; }
echo "Starting the Spring Boot server..."
mvn spring-boot:run &
BACKEND_PID=$!

# Navigate to the frontend and install dependencies (only on the first run)
cd ../timebridge-web
echo "Installing frontend dependencies..."
npm install || { echo "Frontend installation failed. Exiting..."; kill $BACKEND_PID; cleanup; }
echo "Starting the frontend server..."
npm run dev &
FRONTEND_PID=$!

# Wait for processes to complete
wait $BACKEND_PID
wait $FRONTEND_PID

echo "Both processes finished."