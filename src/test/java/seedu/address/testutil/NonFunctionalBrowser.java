package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

import seedu.address.commons.core.Browser;
import seedu.address.commons.core.FunctionalBrowser;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Non-functional browser used for testing.
 */
public class NonFunctionalBrowser implements Browser {
    private static NonFunctionalBrowser browser = null;
    private Desktop desktop;

    /**
     * Constructs a {@code NonFunctionalBrowser}.
     */
    private NonFunctionalBrowser() {
        this.desktop = null;
    }

    /**
     * Returns the instance of the non-functional browser created.
     * If the browser is not instantiated, the method instantiates an instance and returns it.
     *
     * @return Instance of the non-functional browser.
     */
    public static NonFunctionalBrowser getDesktop() {
        if (browser != null) {
            return browser;
        }

        //ensures only one instance of the class is created on a single thread
        synchronized (FunctionalBrowser.class) {
            if (browser == null) {
                browser = new NonFunctionalBrowser();
            }
            return browser;
        }
    }

    /**
     * Performs the necessary {@code url} and {@code uri} checks and does nothing.
     *
     * @param url url of the website to launch.
     * @throws CommandException Generated URI is invalid.
     */
    public void launchUri(String url) throws CommandException {
        requireNonNull(url);
        try {
            URI uri = new URI(url);
            return; //do nothing
        } catch (URISyntaxException e) {
            throw new CommandException("The URI specified is invalid.");
        }
    }

}
