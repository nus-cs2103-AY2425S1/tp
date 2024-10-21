package seedu.address.commons.core;

import seedu.address.logic.commands.exceptions.CommandException;

import java.io.IOException;

public interface Browser {

    public void launchUri(String url) throws IOException, CommandException;
}
