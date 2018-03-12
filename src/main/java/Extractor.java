import org.apache.commons.cli.*;

import java.io.Console;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Extractor {
    /**
     * Extractor
     *
     * @return
     */

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
        String value = System.getenv("EXTRACT_DB_PASSWORD");
        if (value != null) {
            return value;
        } else {
            Console console = System.console();
            return new String(console.readPassword("Password: "));
        }
    }

    private static void configure() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");
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

            SQLClient client;
            if (type.equals("SQLSERVER") || type.equals("MSSQL")) {
                client = new SQLServer(params);
            } else {
                System.out.println("Invalid DB type.");
                return;
            }

            try {
                String inputFileName = line.getOptionValue("sql");
                String inputSql = new String(
                        Files.readAllBytes(Paths.get(inputFileName)),
                        StandardCharsets.UTF_8);
                String outputFile = line.getOptionValue("output", "out.json");
                JsonLOutputWriter writer = new JsonLOutputWriter();
                writer.writeQuery(client.query(inputSql), outputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }

    }
}
