## Example create image

remove image
podman rm image 32ed57722819

build
podman  build -t gitecsl/apppostulante:1.0 .

prugne
podman image  prune -f

target
podman tag localhost/gitecsl/apppostulante:1.0 registry.digitalocean.com/gitecsl-registry/apppostulante:1.0

push
podman push registry.digitalocean.com/gitecsl-registry/apppostulante:1.0

docker login
docker login registry.digitalocean.com/gitecsl-registry

token full access
...

pull a la imagen
docker pull registry.digitalocean.com/gitecsl-registry/apppostulante:1.0

launch
docker run -d -p 8081:8081 registry.digitalocean.com/gitecsl-registry/apppostulante:1.0 

