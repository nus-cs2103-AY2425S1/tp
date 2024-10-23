package seedu.address.commons.core;

import java.util.logging.Logger;

import javafx.application.HostServices;

/**
 * Manages the HostServices of the main application.
 *
 * Enables MainWindow to access the {@link HostServices} of MainApp to allow it to open the User Guide's webpage in
 * a browser from within the application.
 */
public class HostServiceManager {
    private static HostServices hostServices;
    private static boolean hasSetHostServices = false;
    private static final Logger logger = LogsCenter.getLogger(HostServiceManager.class); // logger for this class

    /**
     * Sets hostServices as the HostServices of the main application
     * @param services
     */
    public static void setHostServices(HostServices services) {
        if (!hasSetHostServices) {
            hostServices = services;
            hasSetHostServices = true;
        } else {
            logger.warning("Cannot modify HostService after it has been set.");
        }
    }

    public static HostServices getHostServices() {
        return hostServices;
    }

}
