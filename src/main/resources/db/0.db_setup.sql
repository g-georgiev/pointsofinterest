## Use to run psql db docker image
# docker volume create pgdata
# docker run --name localpostgres -e POSTGRES_PASSWORD=1123581321 -d -p 54320:5432 -v pgdata:/var/lib/postgresql/data postgres
# docker exec -i localpostgres psql -U postgres -c "CREATE DATABASE poi WITH ENCODING='UTF8' OWNER=postgres;"
# docker exec -it localpostgres bash


##Use to enter db
# docker exec -it postgres-img bash

# connect to mysql and run as root user
#Create Databases
CREATE DATABASE poi  WITH ENCODING='UTF8';

#Create database service accounts
CREATE USER poi_user PASSWORD 'poiPASS';