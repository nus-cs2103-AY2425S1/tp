package seedu.address.commons.core;

import static java.util.Objects.requireNonNull;

import java.awt.Desktop;
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
        this.desktop = Desktop.getDesktop();
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
    public void launchUri(String url) throws IOException, CommandException {
        requireNonNull(url);
        try {
            URI uri = new URI(url);
            this.desktop.browse(uri);
        } catch (URISyntaxException e) {
            throw new CommandException("The URI specified is invalid.");
        }
    }
}
