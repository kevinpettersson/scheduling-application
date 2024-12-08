#!/bin/bash

# Navigate to the backend and build (only for the first run)
cd timebridge
mvn clean install && mvn spring-boot:run &

# Navigate to the frontend and start it
cd ../timebridge-web
npm install && npm run dev &
wait