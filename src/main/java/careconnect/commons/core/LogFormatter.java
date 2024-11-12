package careconnect.commons.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * LogFormatter adapted from Manuel Moser's implementation. See below:
 * https://stackoverflow.com/questions/53211694/change-color-and-format-of-java-util-logging-logger-output-in-eclipse
 */
public class LogFormatter extends Formatter {
    // ANSI escape code
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    /**
     * Creates a formatted string containing the datetime,
     * log level, class, followed by the message of the log.
     * The string will be colored according to the log level.
     *
     * @param record the log record to be formatted.
     * @return the formatted log string
     */
    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();

        // Set log color based on logging level
        Level logLevel = record.getLevel();
        if (logLevel.equals(Level.WARNING)) {
            builder.append(ANSI_YELLOW);
        } else if (logLevel.equals(Level.SEVERE)) {
            builder.append(ANSI_RED);
        } else {
            builder.append(ANSI_WHITE);
        }

        // Datetime
        builder.append(calcDate(record.getMillis()));

        // Log level
        builder.append(" [");
        builder.append(record.getLevel().getName());
        builder.append("]");

        // Source class
        builder.append(" [");
        builder.append(record.getSourceClassName());
        builder.append("]");

        // Log message
        builder.append(" - ");
        builder.append(record.getMessage());

        Object[] params = record.getParameters();

        if (params != null) {
            builder.append("\t");
            for (int i = 0; i < params.length; i++) {
                builder.append(params[i]);
                if (i < params.length - 1) {
                    builder.append(", ");
                }
            }
        }

        builder.append(ANSI_RESET);
        builder.append("\n");
        return builder.toString();
    }

    private String calcDate(long millisecond) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date resultdate = new Date(millisecond);
        return dateFormat.format(resultdate);
    }
}
