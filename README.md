# sql-extractor

## Project and Rationale

SQL has become one the most important ways to describe and query data in large data systems and the need for simple SQL interfaces is more important than ever. 
However, most Java SQL projects are designed around Object Relational Mappings. 
While this is powerful it doesn't allow the flexibility for dynamic queries.

While there are various packages on the market, generally querying a database as a Java developer is much more complex than writing basic queries in languages like Python or PHP.

The original rationale for this project was to design a flexible way to connect to various JDBC data sources, run arbitrary queries, return line delimited JSON for later storage and processing.

The project has evolved and now offers a variety of features that offer power, scalability, and flexibility when querying SQL from a JVM project or directly from the command line.

More info: http://jsonlines.org/

### Key Features
* Built in support for major databases (see below for full list)
* Can be used as a library in your application or directly on command line with a provided runner.
* Queries can be retrieved as a data structure, offered row-by-row to a callback, or serialized to a file.
* Files can be CSV or JSONL

### Author and Contributors
Written by @chetmancini and owned by Simon Data, Inc. Offered without warranty.

## Installation

### Dependencies

#### System
* Gradle
* Java 8

#### Build and Run

**Athena JDBC Libraries** - These are downloaded manually and pushed directly to Artifactory from [AWS Athena JDBC Download](https://docs.aws.amazon.com/athena/latest/ug/connect-with-jdbc.html).  We use the non-AWS SDK library and push to the path of `maven-local:com/syncron/amazonaws/AthenaJDBC42` or the current Athena Driver version to match.  This should match the `jdbc` major/minor version.

### Build
Build 'uberjar' to run as a standalone app.
```$sh
gradle shadowJar
```

### Publish

First publish a new release to [JFrog Artifactory](https://simondata.jfrog.io/ui/repos/tree/General/gradle-virtual/gradle-int/sql-extractor)
```sh
gradle clean shadowJar artifactoryPublish
```

You will see a version pushed like `sql-extractor-0.0.x-all.jar` in the output of the publish.

Next, update Jenkins build argument for the version to pull into the container.
[Jenkins Build Base Container](https://misc.automation.simondata.net/job/build-base-web-container/configure)

## Usage
### Use as a library

```$java
SQLParams params = new SQLParams(host, port, user, password, database)
SQLExtractor extract = new SQLExtractor(engine, sqlParams);

String query = "select * from table";
List<Map<String, Object>> records = extract.queryAsList(query);
```

### Use as a command line application
Required Parameters:
* `-u` `--user`: The username to connect with (required)
* `-t` `--type`: The engine type to use (See full list below)
* `-d` `--database`: The database name
* `-s` `--sql`: The path to the input SQL

Optional Parameters:
* `-h` `--host`: The host to connect to (defaults to `localhost`)
* `--key-case`: The case to apply to the keys (defaults to query values, `default`|`snake`|`camel`)
* `-p` `--port`: The port to connect to (defaults to the standard port for the given engine e.g. `3306` for MySQL)
* `-f` `--file`: The output file to write to (defaults to a basic filename)
* `--dry`: Flag to show whether to just print out the input or whether to actually run the query.

Custom Parameters
* `-custom <key>=<value>`: For specific engines custom parameters may be provided

Passwords are passed by TTY input or passed through a `EXTRACT_DB_PASSWORD` environment variable.

```$sh
java -jar build/libs/sql-extractor/sql-extractor-1.0-SNAPSHOT.jar \
    --user <USERNAME> \
    --host <HOST> \
    --port <PORT> (optional, defaults to default port)\
    --database <DATABASE NAME> \
    --type <TYPE> (sqlserver, mysql...see full list below) \
    --case <CASE> (default, snake, camel)
    --sql /path/to/query.sql \
    --file /path/to/outputfile.jsonl
```

### Tests
```$sh
gradle test
```

## Additional Information

### Security

Passwords can be passed via an environment variable: `EXTRACT_DB_PASSWORD`, 
however, the preferred method is to use the password console prompt.

### Supported Databases

* Microsoft SQLServer: `sqlserver`
* Microsoft Azure (same driver): `azure`
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

Since Oracle has specific licensing restrictions, it's easiest to implement this yourself by subclassing `AbstractSQLClient`.

## Custom Parameters By Engine
### SQLServer
* `-custom encrypt=true`
* `-custom trustServerCertificate=true`
* `-custom hostNameInCertificate=str`
* `-custom accessToken=str`
* `-custom authentication=str`
* `-custom authenticationScheme=str`
* `-custom columnEncryptionSetting=str`
* `-custom failoverPartner=str`
* `-custom integratedSecurity=true`



## Ideas for Future Enhancements
* Provide additional output formats beyond JSON and CSV
* Allow splitting out to multiple files by row or filesize.
* Enhance JSON serialization configuration options
