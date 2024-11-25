package seedu.hireme.commons.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Level;

import seedu.hireme.commons.util.ToStringBuilder;

/**
 * Config values used by the application.
 * This class stores configurations such as logging levels and user preferences file path.
 * Guarantees: mutable.
 */
public class Config {

    public static final Path DEFAULT_CONFIG_FILE = Paths.get("config.json");

    // Config values customizable through the config file
    private Level logLevel = Level.INFO;
    private Path userPrefsFilePath = Paths.get("preferences.json");

    /**
     * Returns the log level used by the application.
     *
     * @return The logging level.
     */
    public Level getLogLevel() {
        return logLevel;
    }

    /**
     * Sets the logging level to the specified value.
     *
     * @param logLevel The logging level to set.
     */
    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * Returns the file path to the user preferences file.
     *
     * @return The path to the user preferences file.
     */
    public Path getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    /**
     * Sets the user preferences file path to the specified path.
     *
     * @param userPrefsFilePath The path to the user preferences file.
     */
    public void setUserPrefsFilePath(Path userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    /**
     * Compares this object to the specified object for equality.
     * Returns true if the specified object is also a {@code Config} instance
     * with the same log level and user preferences file path.
     *
     * @param other The object to compare to.
     * @return {@code true} if the objects are equal, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Config)) {
            return false;
        }

        Config otherConfig = (Config) other;
        return Objects.equals(logLevel, otherConfig.logLevel)
                && Objects.equals(userPrefsFilePath, otherConfig.userPrefsFilePath);
    }

    /**
     * Returns the hash code value for this object, based on the log level and user preferences file path.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(logLevel, userPrefsFilePath);
    }

    /**
     * Returns the string representation of the {@code Config} object.
     * The string will include the logging level and user preferences file path.
     *
     * @return A string describing the {@code Config}.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("logLevel", logLevel)
                .add("userPrefsFilePath", userPrefsFilePath)
                .toString();
    }

}
