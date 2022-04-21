# traefik


## Setup

### Postgres DB

```
docker run -p 5432:5432 --name pg-container -e POSTGRES_PASSWORD=password -d postgres:9.6.10
docker ps
docker run -it --rm --link pg-container:postgres postgres psql -h postgres -U postgres
CREATE USER test WITH PASSWORD 'test@123';
CREATE DATABASE "test-db" WITH OWNER "test" ENCODING UTF8 TEMPLATE template0;
grant all PRIVILEGES ON DATABASE "test-db" to test;
```

```bash
cd project95
./gradlew bootRun
./gradlew clean build
```

```bash
docker build -f docker/Dockerfile --force-rm -t project95:1.0.0 .
docker images
docker tag project95:1.0.0 gitorko/project95:1.0.0
docker push gitorko/project95:1.0.0
docker-compose -f docker/docker-compose.yml up 


helm install traefik traefik/traefik
kubectl apply -f dashboard.yaml
kubectl port-forward $(kubectl get pods --selector "app.kubernetes.io/name=traefik" --output=name) 9000:9000
```

