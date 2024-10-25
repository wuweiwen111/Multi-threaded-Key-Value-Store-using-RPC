package utils;

/**
 * The ILogger interface defines the contract for logging mechanisms.
 * It ensures that all logger implementations provide methods to log messages and close the logger.
 */
public interface ILogger {

    /**
     * Logs a general informational message.
     *
     * @param msg The message to be logged.
     */
    void log(String msg);

    /**
     * Closes the logger, releasing any held resources.
     */
    void close();
}
