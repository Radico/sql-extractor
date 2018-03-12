import org.apache.commons.cli.*;

import java.io.Console;

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

    public static void main(String[] args) {
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

            JsonLOutputWriter writer = new JsonLOutputWriter();

            String queryText = "";
            String outputFile = "";

            writer.writeQuery(client.query(queryText), outputFile);

            System.out.println("Wrote to " + outputFile);
        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }

    }
}
