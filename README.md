# DevOps Monitoring Dashboard

## Project Overview
This is a real-time system monitoring dashboard built using Java, exposing REST APIs and a frontend dashboard. It monitors CPU, memory, system health, and OS details.

---

## Tech Stack
- Java (HttpServer)
- Maven
- Docker
- HTML, CSS, JavaScript
- REST APIs

---

## Features
- Health Check API
- CPU Usage Monitoring
- Memory Usage Monitoring
- System Information API
- Live Auto-refresh Dashboard UI
- Dockerized Application

---

## API Endpoints

- `/api/health` → System health
- `/api/cpu` → CPU usage
- `/api/memory` → Memory usage
- `/api/system` → OS & system info

---

## Docker Setup

```bash
docker build -t monitoring-dashboard .
docker run -p 9090:9090 monitoring-dashboard
