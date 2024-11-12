package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

public class LogsCenterTest {
    private static final String TEST_LOG_FILE = "addressbook.log";

    @Test
    public void getLogger_nullClass_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> LogsCenter.getLogger((Class<?>) null));
    }

    @Test
    public void getLogger_validClass_returnsLoggerWithCorrectName() {
        Logger logger = LogsCenter.getLogger(LogsCenterTest.class);
        assertEquals("ab3.LogsCenterTest", logger.getName());
        assertTrue(logger.getUseParentHandlers());
    }

    @Test
    public void getLogger_validString_returnsLoggerWithCorrectName() {
        Logger logger = LogsCenter.getLogger("TestLogger");
        assertEquals("ab3.TestLogger", logger.getName());
        assertTrue(logger.getUseParentHandlers());
    }

    @Test
    public void init_validConfig_setsCorrectLogLevel() {
        Config config = new Config();
        config.setLogLevel(Level.FINE);
        LogsCenter.init(config);

        // Get the base logger directly since it's the one that has the level set
        Logger baseLogger = Logger.getLogger("ab3");
        assertEquals(Level.FINE, baseLogger.getLevel());
    }

    @Test
    public void baseLogger_hasCorrectHandlers() {
        Logger baseLogger = Logger.getLogger("ab3");
        Handler[] handlers = baseLogger.getHandlers();

        boolean hasConsoleHandler = false;
        boolean hasFileHandler = false;

        for (Handler handler : handlers) {
            if (handler instanceof ConsoleHandler) {
                hasConsoleHandler = true;
            } else if (handler instanceof FileHandler) {
                hasFileHandler = true;
            }
        }

        assertTrue(hasConsoleHandler, "Base logger should have a ConsoleHandler");
        assertTrue(hasFileHandler, "Base logger should have a FileHandler");
    }

    @Test
    public void multipleLoggers_shareBaseLoggerHandlers() {
        Logger logger1 = LogsCenter.getLogger("TestLogger1");
        Logger logger2 = LogsCenter.getLogger("TestLogger2");

        assertTrue(logger1.getUseParentHandlers());
        assertTrue(logger2.getUseParentHandlers());
    }

    @Test
    public void loggerLevel_inheritsFromBaseLogger() {
        Config config = new Config();
        config.setLogLevel(Level.FINEST);
        LogsCenter.init(config);

        // Get the base logger to verify its level
        Logger baseLogger = Logger.getLogger("ab3");
        assertEquals(Level.FINEST, baseLogger.getLevel());

        // Create a child logger and verify it inherits the level through useParentHandlers
        Logger logger = LogsCenter.getLogger("TestLogger");
        assertTrue(logger.getUseParentHandlers());
    }
}
