# TimeBridge



## Information
More information about our project including the **Requirements Analysis Document (RAD)** and **System Design Document (SDD)**, can be found in the `docs` folder.

## Running the application
### Step 1 – Install Docker and Docker Compose
Make sure Docker **and Docker Compose** are installed on your machine.

- On **(Windows/Mac**), Docker Compose is often included by default.

- On **Linux**, Docker Compose might not be installed by default

You can download Docker Desktop here: [https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop)

To verify installation, run:
```bash
docker --version
docker compose version
```

---

### Step 2 - Clone the repository
Clone the repository into a folder of your choice:
```bash
git clone https://github.com/kevinpettersson/scheduling-application.git
```

---

### Step 3 - Build and start the Docker container
Navigate to the project directory:
```bash
cd /path/to/scheduling-application
```
Then run Docker Compose:
```bash
docker compose up --build
```
The first build may take a few minutes as Docker install all dependencies.

---

### Step 4 - Access the Web Application
Once the containers are up and running, open your browser and go to:
```arduino
http://localhost:3000/
```
