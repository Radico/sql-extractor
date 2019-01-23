# sql-extractor

## Project and Rationale

Most Java SQL projects are designed around Object Relational Mappings. While
this is powerful it doesn't allow the flexibility for dynamic queries.

We need a flexible way to connect to various JDBC data sources, run arbitrary queries, return line delimited JSON.

More info: http://jsonlines.org/

## Running

### System Dependencies
* Gradle
* Java 8

### Build
```$sh
gradle shadowJar
```

### Run
Required Parameters:
* `-u` `--user`: The username to connect with (required)
* `-t` `--type`: The engine type to use (See full list below)
* `-d` `--database`: The database name
* `-s` `--sql`: The path to the input SQL

Optional Parameters:
* `-h` `--host`: The host to connect to (defaults to `localhost`)
* `-c` `--case`: The case to apply to the keys (defaults to query values, `default`|`snake`|`camel`)
* `-p` `--port`: The port to connect to (defaults to the standard port for the given engine e.g. `3306` for MySQL)
* `-f` `--file`: The output file to write to (defaults to a basic filename)
* `-o` `--print`: Boolean. Write results to `stdout`.

Passwords are passed by TTY input or passed through a `EXTRACT_DB_PASSWORD` environment variable.

```$sh
java -jar build/libs/sql-extractor/sql-extractor-1.0-SNAPSHOT-all.jar \
    -u <USERNAME> \
    -h <HOST> \
    -p <PORT> \
    -d <DATABASE NAME> \
    -t <TYPE> (sqlserver, mysql...see full list below) \
    -c <CASE> (default, snake, camel)
    -s /path/to/query.sql \
    -f /path/to/outputfile.jsonl
```

### Tests
```$sh
gradle test
```

### Deploy

Currently we run this via Python (see `jdbc.py`) in Jenkins. Deploying has two steps.
First, we copy the fat jar up to S3, and then we copy that file to each Jenkins box.

```$sh
aws s3 cp build/libs/sql-extractor-1.0-SNAPSHOT-all.jar s3://prod.radi.co/build/libs/sql-extractor-1.0-SNAPSHOT-all.jar

djprod fab jenkins.deploy_jdbc_extractor
```

## Additional Information

### Security

Passwords can be passed via an environment variable: `EXTRACT_DB_PASSWORD`, 
however, the preferred method is to use the password console prompt.

### Supported Databases

* Microsoft SQLServer: `sqlserver`
* MySQL / MariaDB: `mysql`
* PostgreSQL: `postgresql`
* Amazon Redshift: `redshift`
* Athena: `athena`
* Informix `informix`

In future we may add drivers support for the following:
* Sybase
* Microsoft Access
* Firebird/ Interbase
* IBM DB2
* Google BigQuery

The following will be straightforward to implement by a fork
* Oracle: `oracle`

## Future Enhancements
* Add Jar versioning
* Provide different types of output
* Improve JSON serialization configuration options
* Read query SQL from stdin or console.
