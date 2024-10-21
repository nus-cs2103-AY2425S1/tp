package seedu.address.testutil;

import seedu.address.commons.core.Browser;
import seedu.address.commons.core.FunctionalBrowser;
import seedu.address.logic.commands.exceptions.CommandException;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static java.util.Objects.requireNonNull;

public class NonFunctionalBrowser implements Browser {
    private static NonFunctionalBrowser browser = null;
    private Desktop desktop;

    private NonFunctionalBrowser() {
        this.desktop = null;
    }

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

    public void launchUri(String url) throws IOException, CommandException {
        requireNonNull(url);
        try {
            URI uri = new URI(url);
            return; //do nothing
        } catch (URISyntaxException e) {
            throw new CommandException("The URI specified is invalid.");
        }
    }

}
