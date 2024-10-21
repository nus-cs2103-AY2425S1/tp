package seedu.address.commons.core;

import seedu.address.logic.commands.exceptions.CommandException;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static java.util.Objects.requireNonNull;

public class FunctionalBrowser implements seedu.address.commons.core.Browser {
    private static FunctionalBrowser browser = null;
    private Desktop desktop;

    private FunctionalBrowser() {
        this.desktop = Desktop.getDesktop();
    }

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
