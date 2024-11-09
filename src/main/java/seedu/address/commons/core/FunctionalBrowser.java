package seedu.address.commons.core;

import static java.util.Objects.requireNonNull;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Functional browser used for launching local browser of the user.
 */
public class FunctionalBrowser implements seedu.address.commons.core.Browser {
    private static final String MESSAGE_EXTERNAL_LINK_FAILURE = "The current OS does not support opening "
        + "external links.";
    private static final String MESSAGE_INVALID_URI = "The URI specified is invalid.";

    private static final String MESSAGE_OS_ACCESS_FAILURE = "Access to open external links is denied by the security "
        + "manager.";

    private static final String MESSAGE_NO_DEFAULT_BROWSER = "Default browser not found or failed to launch. "
        + "Please try again.";
    private static FunctionalBrowser browser = null;
    private Desktop desktop;

    /**
     * Constructs a {@code FunctionalBrowser}.
     */
    private FunctionalBrowser() {
        try {
            if (!isLinux()) {
                this.desktop = Desktop.getDesktop();
            }
        } catch (HeadlessException e) {
            this.desktop = null;
        }
    }

    /**
     * Checks if the user is using a Linux OS.
     */
    private boolean isLinux() {
        //@@author incogdino-reused
        //Reused from https://mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf("nix") >= 0
                || os.indexOf("nux") >= 0
                || os.indexOf("aix") > 0);
        //@@author
    }

    /**
     * Returns the instance of the functional browser created.
     * If the browser is not instantiated, the method instantiates an instance and returns it.
     *
     * @return Instance of the functional browser.
     */
    public static FunctionalBrowser getDesktop() {
        if (browser != null) {
            return browser;
        }

        //ensures only one instance of the class is created on a single thread
        synchronized (FunctionalBrowser.class) {
            if (browser == null) {
                browser = new FunctionalBrowser();
            }
            return browser;
        }
    }

    /**
     * Launches the URI of the functional browser with the specified {@code url}.
     *
     * @param url url of the website to launch.
     * @throws IOException if the user default browser is not found, or it fails to be launched,
     *      or the default handler application failed to be launched
     * @throws CommandException Generated URI is invalid.
     */
    public void launchUri(String url) throws CommandException {
        requireNonNull(url);
        try {
            URI uri = new URI(url);
            if (isLinux()) {
                //For linux OS
                if (Runtime.getRuntime().exec(new String[] { "which", "xdg-open" }).getInputStream().read() != -1) {
                    Runtime.getRuntime().exec(new String[] { "xdg-open", url });
                } else {
                    throw new CommandException(MESSAGE_EXTERNAL_LINK_FAILURE);
                }
            } else {
                //For non linux OS
                if (this.desktop != null && Desktop.isDesktopSupported()
                        && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    this.desktop.browse(uri);
                } else {
                    throw new CommandException(MESSAGE_EXTERNAL_LINK_FAILURE);
                }
            }
        } catch (URISyntaxException uriException) {
            throw new CommandException(MESSAGE_INVALID_URI);
        } catch (SecurityException securityException) {
            throw new CommandException(MESSAGE_OS_ACCESS_FAILURE);
        } catch (IOException ioException) {
            throw new CommandException(MESSAGE_NO_DEFAULT_BROWSER);
        }
    }
}
