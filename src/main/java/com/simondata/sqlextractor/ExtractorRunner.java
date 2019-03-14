package com.simondata.sqlextractor;

import com.simondata.sqlextractor.clients.*;
import com.simondata.sqlextractor.writers.*;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static com.simondata.sqlextractor.util.TextFormat.parseInteger;

public class ExtractorRunner {

    private final static Logger logger = LoggerFactory.getLogger(ExtractorRunner.class);

    private static final String ENV_VAR_PASSWORD_KEY = "EXTRACT_DB_PASSWORD";
    private static final String DEFAULT_OUTPUT_FILENAME = "query_result.json";

    private static Options getOptions() {
        Options options = new Options();
        options.addRequiredOption("u", "user", true, "user");
        options.addOption("h", "host", true, "host");
        options.addOption(
                "p",
                "port",
                true,
                "Port, defaults to engine's default port."
        );
        options.addOption("d", "database", true, "database");
        options.addOption("t", "type", true, "Driver type (SQLServer | MySQL | Postgres )");
        options.addOption("s", "sql", true,
                "SQL file to read.");
        options.addOption("print", "print", false, "Print to stdout");
        options.addOption("dry", "dry", false, "Dry run");
        options.addOption("format", "format", true, "The output format, defaults to json (JSON | CSV)");
        options.addOption(
                "f",
                "file",
                true,
                "File to write to. Defaults to " + DEFAULT_OUTPUT_FILENAME + '.'
        );
        options.addOption("c", "case", true, "Key case format (DEFAULT | Snake | Camel)");
        options.addOption("fetchsize", "fetchsize", true, "Fetch size");
        options.addOption("timeout", "timeout", true, "Query Timeout in seconds");
        options.addOption("maxrows", "maxrows", true, "Maximum rows");

        Option customParams = Option.builder("custom")
                .longOpt("custom")
                .hasArgs()
                .valueSeparator()
                .argName("property=value")
                .desc("Custom params")
                .numberOfArgs(2)
                .optionalArg(true)
                .build();

        options.addOption(customParams);
        return options;
    }

    private static String getPassword() {
        /*
          Try to get password from environment var.
          If that doesn't exist prompt on console.
         */
        String value = System.getenv(ENV_VAR_PASSWORD_KEY);
        if (value != null) {
            return value;
        } else {
            Console console = System.console();
            return new String(console.readPassword("Password: "));
        }
    }

    private static void configureLogging() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "trace");
    }

    private static String readSql(String filename) throws IOException {
        logger.debug("Reading " + filename);
        return new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
    }

    private static SQLParams getSqlParams(CommandLine commandLine) {
        String user = commandLine.getOptionValue("user");
        String host = commandLine.getOptionValue("host", "localhost");
        Integer port = parseInteger(commandLine.getOptionValue("port"));
        String database = commandLine.getOptionValue("database");
        String password = getPassword();
        Properties props = commandLine.getOptionProperties("custom");
        return new SQLParams(host, port, user, password, database, props);
    }

    private static QueryParams getQueryParams(CommandLine commandLine) {
        Integer fetchSize = parseInteger(commandLine.getOptionValue("fetchsize"));
        Integer maxRows = parseInteger(commandLine.getOptionValue("maxRows"));
        Integer timeout = parseInteger(commandLine.getOptionValue("timeout"));
        return new QueryParams(fetchSize, maxRows, timeout);
    }

    private static FormattingParams getFormattingParams(CommandLine commandLine) {
        String caseFormat = commandLine.getOptionValue("case");
        FormattingParams params = new FormattingParams();
        params.setKeyCaseFormat(caseFormat);
        return params;
    }

    public static void main(String[] args) {
        configureLogging();

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(getOptions(), args);
            SQLParams sqlParams = getSqlParams(line);
            FormattingParams formattingParams = getFormattingParams(line);
            QueryParams queryParams = getQueryParams(line);
            String type = line.getOptionValue("type", "SQLSERVER").toUpperCase();
            FileOutputFormat outputFormat = FileOutputFormat.valueOf(
                    line.getOptionValue("format", "json").toUpperCase());

            if (line.hasOption("dry")) {
                sqlParams.logValues();
                queryParams.logValues();
                formattingParams.logValues();
                System.exit(0);
            }
            SqlEngine engine = SqlEngine.byName(type);
            SQLExtractor sqlExtractor = new SQLExtractor(engine, sqlParams, formattingParams);

            try {
                String inputSql = readSql(line.getOptionValue("sql"));
                String outputFile = line.getOptionValue("file", DEFAULT_OUTPUT_FILENAME);
                sqlExtractor.queryToFile(inputSql, new File(outputFile), outputFormat, queryParams, formattingParams);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ParseException exp) {
            logger.error("Parsing failed.  Reason: " + exp.getMessage());
        }
    }
}
