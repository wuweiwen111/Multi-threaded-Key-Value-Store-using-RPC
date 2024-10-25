package utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.FileHandler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * The Logger class provides a thread-safe logging mechanism.
 * It implements the ILogger interface and utilizes Java's built-in logging facilities.
 */
public class Logger implements ILogger {
    private java.util.logging.Logger logger;
    private FileHandler fileHandler;
    private static final String FORMAT = "MM-dd-yyyy HH:mm:ss.SSS";

    /**
     * Constructs a new Logger instance with the specified logger name and log file name.
     *
     * @param loggerName  The name of the logger.
     * @param logFileName The name of the log file where messages will be recorded.
     */
    public Logger(String loggerName, String logFileName) {
        createLog(loggerName, logFileName);
    }

    /**
     * Initializes the logger by setting up the FileHandler and Formatter.
     *
     * @param loggerName  The name of the logger.
     * @param logFileName The name of the log file.
     */
    private void createLog(String loggerName, String logFileName) {
        this.logger = java.util.logging.Logger.getLogger(loggerName);
        try {
            this.fileHandler = new FileHandler(logFileName, true); // Append mode
            this.fileHandler.setFormatter(new SimpleFormatter() {
                private static final String FORMAT_PATTERN = "MM-dd-yyyy HH:mm:ss.SSS";

                @Override
                public String format(LogRecord record) {
                    SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_PATTERN);
                    return sdf.format(record.getMillis()) + " - " + record.getMessage() + "\n";
                }
            });
            this.logger.addHandler(this.fileHandler);
            this.logger.setUseParentHandlers(false); // Disable console logging
        } catch (IOException e) {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
            System.err.println(sdf.format(System.currentTimeMillis()) + " - " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Logs an informational message.
     *
     * @param msg The message to be logged.
     */
    @Override
    public void log(String msg) {
        this.logger.info(msg);
    }

    /**
     * Closes the logger, ensuring all resources are properly released.
     */
    @Override
    public void close() {
        if (this.fileHandler != null) {
            this.fileHandler.close();
        }
    }
}
