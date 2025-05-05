# Usage

## Command-line Interface (CLI)

To start FA³ST Registry from command-line you need to run the `fa3st-registry-starter` module by calling

```sh
> java -jar fa3st-registry-starter-{version}.jar
```

:::{table} Supported CLI arguments and environment variables.
| CLI (short)   | CLI (long)            | Description                                                                                                                                              |
| ------------- | --------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `-q`          | `--quite`             | Reduces log output (*ERROR* for FAST packages, *ERROR* for all other packages).<br>Default information about the starting process will still be printed. |
| `-v`          | `--verbose`           | Enables verbose logging (*INFO* for FAST packages, *WARN* for all other packages).                                                                       |
| `-vv`         |                       | Enables very verbose logging (*DEBUG* for FAST packages, *INFO* for all other packages).                                                                 |
| `-vvv`        |                       | Enables very very verbose logging (*TRACE* for FAST packages, *DEBUG* for all other packages).                                                           |
:::

## Docker

### Docker CLI

To start the FA³ST Registry with default values execute this command. This will start the image of FA³ST Registry from Docker Hub.
A FA³ST Registry with in-memory database on port 8090 will be started.

```sh
docker run --rm -P eclipsefa3st/fa3st-registry
```

To start the FA³ST Registry with your own configuration, override the environment variables.

```sh
docker run --rm -P -e "server.port=8091" eclipsefa3st/fa3st-registry
```

To start the FA³ST Registry from your local configuration, e.g. if you have implemented changes, change to the root folder of the repository and run these command inside it:

```sh
docker build -t eclipsefa3st/fa3st-registry .
docker run -p 8090:8090 -t eclipsefa3st/fa3st-registry
```

Similarly to the above examples you can pass more arguments to the FA³ST Registry by using the CLI or an environment file

### Docker-Compose

Clone this repository, navigate to `/misc/docker/` and run this command inside it.

```sh
cd misc/docker
docker-compose up
```
