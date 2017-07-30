docker build -t psbox/workshop -f container.docker .
docker run -d -p 18080:8080 psbox/workshop
