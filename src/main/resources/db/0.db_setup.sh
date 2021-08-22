## Use to run psql db docker image
docker volume create pgdata
docker run --name localpostgres -e POSTGRES_PASSWORD=1123581321 -d -p 54320:5432 -v pgdata:/var/lib/postgresql/data postgres
docker exec -i localpostgres psql -U postgres -c "CREATE DATABASE poi WITH ENCODING='UTF8' OWNER=postgres;"
docker exec -it localpostgres bash

#Inside container
psql -U postgres poi

#Starting docker image
docker start localpostgres