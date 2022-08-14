# Project 95

Traefik Rate Limit

[https://gitorko.github.io/spring-boot-traefik-rate-limit/](https://gitorko.github.io/spring-boot-traefik-rate-limit/)

### Version

Check version

```bash
$java --version
openjdk 17.0.3 2022-04-19 LTS

helm version --short
v3.9.1+ga7c043a

kubectl version --short
Client Version: v1.24.3
Kustomize Version: v4.5.4
```

### Postgres DB

```
docker run -p 5432:5432 --name pg-container -e POSTGRES_PASSWORD=password -d postgres:9.6.10
docker ps
docker exec -it pg-container psql -U postgres -W postgres
CREATE USER test WITH PASSWORD 'test@123';
CREATE DATABASE "test-db" WITH OWNER "test" ENCODING UTF8 TEMPLATE template0;
grant all PRIVILEGES ON DATABASE "test-db" to test;

docker stop pg-container
docker start pg-container
```

### Docker

For docker on laptop we cant use localhost as the hostname, so add this entry to the /etc/hosts file.

```bash
127.0.0.1 localhost.com
```

Build the project and docker image

```bash
cd project95
./gradlew bootRun
./gradlew clean build
docker build -f docker/Dockerfile --force-rm -t project95:1.0.0 .
```

If you want to deploy via docker compose. 

```bash
docker tag project95:1.0.0 gitorko/project95:1.0.0
docker push gitorko/project95:1.0.0
docker-compose -f docker/docker-compose.yml up 
```

### Traefik

Deploy traefik via helm

```bash
helm install traefik traefik/traefik
```

Traefik comes with the dashboard to visualize the config that is not exposed so run port forward command. If you dont need to visualize the config then you can skip this step as it is not mandatory

```bash
kubectl port-forward $(kubectl get pods --selector "app.kubernetes.io/name=traefik" --output=name) 9000:9000
```

Open the dashboard url

[http://127.0.0.1:9000/dashboard/](http://127.0.0.1:9000/dashboard/)

### Kubernetes

Now deploy the application on kubernetes

If you want a plain deployment without traefik, This will deploy the spring boot application along with postgres, run the below command

```bash
kubectl apply -f docker/deployment.yaml
```

To test the api, run the curl command

```bash
curl --request GET 'http://localhost.com:8080/rest/time'
```

Clean up

```bash
kubectl delete -f docker/deployment.yaml
```

### Kubernetes & Traefik Ingress

If you want traefik as the ingress controller, run the below command

```bash
kubectl apply -f docker/deployment-traefik.yaml
```

To test the api, run the curl command

```bash
curl --request GET 'http://localhost.com/rest/time'
```

Clean up

```bash
kubectl delete -f docker/deployment-traefik.yaml
```

### Kubernetes & Traefik IngressRoute with Rate Limit

If you want traefik as the ingress & want to rate limit, run the below command

```bash
kubectl apply -f docker/deployment-traefik-ratelimit.yaml
```

To test the api, run the curl command

```bash
curl --request GET 'http://localhost.com/rest/time'
```

Clean up

```bash
kubectl delete -f docker/deployment-traefik-ratelimit.yaml
```

Few command to look at the services

```bash
kubectl get ingress
kubectl describe ingress

kubectl get ingressroute 
kubectl describe ingressroute 

kubectl get all

k logs -f deployment.apps/project95 --all-containers=true

helm uninstall traefik
```
