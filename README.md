# Sharex Spring Boot Custom Uploader

A custom uploader for Sharex using Spring Boot and its internal Tomcat.

## Setup

- `PASSWORD` (Required): A password to restrict who can upload.
- `DOMAIN` (Required): The domain this server is going to run under, e.g. <http://yourdomain.com>. Must also contain the port if the server does not run on port 80.
- `FOLDER` (Optional): The name of the folder all uploads are placed in, uses "uploads" as default.

```sh
docker run -p [PORT]:8080 --name sharex-spring-boot-custom-uploader -e PASSWORD=[PASSWORD] -e DOMAIN=[DOMAIN] -e FOLDER=[FOLDER] -d yustheyokai/sharex-spring-boot-custom-uploader:[VERSION]
```
