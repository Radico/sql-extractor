# sql-extractor

## Project and Rationale

Most Java SQL projects are designed around Object Relational Mappings. While
this is powerful it doesn't allow the flexibility for dynamic queries.

We need a flexible way to connect to various JDBC data sources and run arbitrary queries, return line delimited JSON.

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

```$sh
java -jar build/libs/sql-extractor/sql-extractor-1.0-SNAPSHOT-all.jar \
    -u <USERNAME> \
    -h <HOST> \
    -p <PORT> \
    -t <TYPE> (sqlserver, mysql, redshift...) \
    -s /path/to/query.sql \
    -f /path/to/outputfile.jsonl
```

### Tests

## Additional Information

### Security

Passwords can be passed via an environment variable: `EXTRACT_DB_PASSWORD`, 
however, the preferred method is to use the password console prompt.