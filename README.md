# InfraScope Backend

InfraScope is a **full-stack Infrastructure as Code (IaC) Visualizer & Simulator**.  
This repository contains the **backend Spring Boot application** responsible for:

- Parsing IaC files (Terraform, Ansible, Docker Compose)  
- Creating an internal infrastructure model  
- Providing REST APIs for graph visualization, simulation, and report export  

## Features (MVP)

- Upload and parse `.tf`, `.yml`, `.yaml`, `Dockerfile`, and `docker-compose.yml` files  
- Generate a unified internal model of resources and dependencies  
- Serve JSON endpoints for frontend visualization and simulation  
- Export infrastructure and simulation reports (JSON, Markdown, PDF)  

## Tech Stack

- Java 21 + Spring Boot v3.5.5
- REST API endpoints for frontend consumption
- Swagger

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone <backend-repo-url>
   cd infrascope-backend
   ```

2. Build and run (Maven):

   ```bash
   ./mvnw clean package
   java -jar target/infrascope-backend.jar
   ```

3. Backend will start on `http://localhost:8080`

4. The Swagger UI page will then be available at `http://localhost:8080/swagger-ui.html`

5. The OpenAPI description will be available at the following url for json format: `http://localhost:8080/v3/api-docs`

## Future Work

- Step-through simulation UI
- Conflict detection (ports, duplicate resources, etc)
- Multi-file combined parsing