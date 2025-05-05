# Configuration

Configuration uses the regular [Spring Boot configuration process](https://docs.spring.io/spring-boot/reference/features/external-config.html).
This means you can provide configuration properties in multiple ways, e.g., via property files, environment variables, or command-line arguments.
The following table lists some of the most important config properties to be used with FA³ST Registry.

:::{note}
When using environment variable make sure to follow the [naming scheme of Spring Boot](https://docs.spring.io/spring-boot/reference/features/external-config.html#features.external-config.typesafe-configuration-properties.relaxed-binding.environment-variables) which requires upper case and replacing `.` with `_`, e.g., to set the port use environment variable `SERVER_PORT`.
:::

:::{table} Some useful configuration properties.
| Name                                    | Example Value                                                             | Description                                                  |
|:----------------------------------------|---------------------------------------------------------------------------|--------------------------------------------------------------|
| spring.profiles.active                  | default / jpa                                                             | in-memory or jpa database connection                         |
| spring.jpa.properties.hibernate.dialect | org.hibernate.dialect.H2Dialect / org.hibernate.dialect.PostgreSQLDialect | the hibernate dialect to be used for the database connection |
| spring.datasource.driver                | org.h2.Driver / org.postgresql.Driver                                     | the JDBC driver to be used for the database connection       |
| spring.datasource.url                   | jdbc:postgresql://db:5432/postgres                                        | url of the internal or external database                     |
| spring.datasource.username              | postgres                                                                  | username for the database                                    |
| spring.datasource.password              | admin                                                                     | password for the database                                    |
| server.port                             | 8090                                                                      | port of the Registry                                         |
:::
