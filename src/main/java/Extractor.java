import org.apache.commons.cli.*;

public class Extractor {

    private static Options getOptions() {
        Options options = new Options();
        options.addOption( "u", "user", false, "user" );
        options.addOption( "w", "password", false, "password" );
        options.addOption( "h", "host", false, "host" );
        options.addOption( "p", "port", false, "port" );
        options.addOption( "d", "database", false, "database" );
        return options;
    }

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(getOptions(), args);

            SQLServer client = new SQLServer(null);
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
