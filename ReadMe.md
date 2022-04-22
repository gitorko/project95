# traefik demo


## Setup

Add the entry '127.0.0.1 localhost.com' to /etc/hosts

### Build

Build the project and docker image

```bash
cd project95
./gradlew bootRun
./gradlew clean build
docker build -f docker/Dockerfile --force-rm -t project95:1.0.0 .
```

### K8 Deployment

Deploy traefik and deploy the service

```bash

helm install traefik traefik/traefik
kubectl apply -f dashboard.yaml
kubectl port-forward $(kubectl get pods --selector "app.kubernetes.io/name=traefik" --output=name) 9000:9000

kubectl apply -f docker/deployment-traefik.yaml

kubectl apply -f docker/deployment-traefik-ratelimit.yaml

kubectl describe ingressroute 
```

http://127.0.0.1:9000/dashboard/

```bash
curl --request GET 'http://localhost.com/api/time'
```

### Postgres DB

To run postgres on docker

```
docker run -p 5432:5432 --name pg-container -e POSTGRES_PASSWORD=password -d postgres:9.6.10
docker ps
docker run -it --rm --link pg-container:postgres postgres psql -h postgres -U postgres
CREATE USER test WITH PASSWORD 'test@123';
CREATE DATABASE "test-db" WITH OWNER "test" ENCODING UTF8 TEMPLATE template0;
grant all PRIVILEGES ON DATABASE "test-db" to test;
```

Tag the image

```bash
docker tag project95:1.0.0 gitorko/project95:1.0.0
docker push gitorko/project95:1.0.0
docker-compose -f docker/docker-compose.yml up 

```
