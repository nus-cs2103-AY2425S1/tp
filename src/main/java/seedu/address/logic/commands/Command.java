package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.EventManager;

public abstract class Command {
    public abstract CommandResult execute(Model model, EventManager eventManager) throws CommandException;
}
