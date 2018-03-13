import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Console;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Extractor {

    final static Logger logger = LoggerFactory.getLogger(Extractor.class);

    private static Options getOptions() {
        Options options = new Options();
        options.addRequiredOption("u", "user", true, "user");
        options.addOption("h", "host", true, "host");
        options.addOption("p", "port", true, "port");
        options.addOption("d", "database", true, "database");
        options.addOption("t", "type", true, "Driver type (SQLServer | MySQL | Postgres )");
        options.addOption("s", "sql", true, "SQL file to read");
        options.addOption("o", "output", true, "File to write to");
        return options;
    }

    private static String getPassword() {
        /*
          Try to get password from environment var.
          If that doesn't exist prompt on console.
         */
        String value = System.getenv("EXTRACT_DB_PASSWORD");
        if (value != null) {
            return value;
        } else {
            Console console = System.console();
            return new String(console.readPassword("Password: "));
        }
    }

    private static void configure() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "trace");
    }

    private static SQLClient sqlClientFactory(String sqlType, SQLParams params) {
        SQLClient client = null;
        switch (sqlType) {
            case "SQLSERVER":
            case "MSSQL":
                client = new SQLServer(params);
                break;
            case "MYSQL":
                logger.error("Not supported yet.");
                break;
            case "POSTGRES":
            case "POSTGRESQL":
                logger.error("Not supported yet.");
                break;
            case "ORACLE":
                logger.error("Not supported yet.");
                break;
            default:
                logger.error("Invalid DB type.");
                break;
        }
        return client;
    }

    private static String readSql(String filename) throws IOException {
        logger.debug("Reading " + filename);
        return new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        configure();

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(getOptions(), args);

            String user = line.getOptionValue("user");
            String host = line.getOptionValue("host", "localhost");
            String type = line.getOptionValue("type", "SQLSERVER").toUpperCase();
            String strPort = line.getOptionValue("port");
            Integer port = null;
            if (strPort != null) {
                port = Integer.parseInt(strPort);
            }
            String database = line.getOptionValue("database");
            String password = getPassword();

            SQLParams params = new SQLParams(host, port, user, password, database);

            logger.info(params.getSqlServerConnectionUrl());
            SQLClient client = sqlClientFactory(type, params);
            try {
                String inputSql = readSql(line.getOptionValue("sql"));
                String outputFile = line.getOptionValue("output", "out.json");
                logger.debug("Output File: " + outputFile);
                JsonLOutputWriter writer = new JsonLOutputWriter();
                int rows = writer.writeQuery(client.query(inputSql), outputFile);
                logger.info("finished " + rows + " rows");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (ParseException exp) {
            logger.error("Parsing failed.  Reason: " + exp.getMessage());
        }

    }
}
