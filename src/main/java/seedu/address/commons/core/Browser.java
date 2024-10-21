package seedu.address.commons.core;

import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * API of the Browser component
 */
public interface Browser {

    public void launchUri(String url) throws IOException, CommandException;
}
