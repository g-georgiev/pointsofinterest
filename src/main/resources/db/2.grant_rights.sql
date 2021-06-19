
--Create database service accounts
CREATE ROLE poi_dev WITH CREATEDB LOGIN PASSWORD 'dev_pass';

--Database grants
GRANT SELECT ON ALL TABLES IN SCHEMA public to poi_dev;
GRANT INSERT ON ALL TABLES IN SCHEMA public to poi_dev;
GRANT UPDATE ON ALL TABLES IN SCHEMA public to poi_dev;
GRANT DELETE ON ALL TABLES IN SCHEMA public to poi_dev;
grant ALL on ALL sequences in schema public to poi_dev;