FROM mysql:8.2.0

ENV MYSQL_ROOT_PASSWORD=password

# add init script to initialize database
ADD init.sql /docker-entrypoint-initdb.d

EXPOSE 3306