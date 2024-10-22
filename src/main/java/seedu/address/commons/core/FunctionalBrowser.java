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
                    throw new CommandException("The current OS does not support opening external links.");
                }
            } else {
                //For non linux OS
                if (this.desktop != null && Desktop.isDesktopSupported()
                        && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    this.desktop.browse(uri);
                } else {
                    throw new CommandException("The current OS does not support opening external links.");
                }
            }
        } catch (URISyntaxException uriException) {
            throw new CommandException("The URI specified is invalid.");
        } catch (SecurityException securityException) {
            throw new CommandException("Access to open external links is denied by the security manager.");
        } catch (IOException ioException) {
            throw new CommandException("Default browser not found or failed to laucnh. Please try again.");
        }
    }
}
