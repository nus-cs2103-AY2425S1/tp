package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all persons or appointments in the address book to the user.
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ": Lists all entities of a given type \n"
            + "Parameters: ENTITY_TYPE (person/appt) \n"
            + "Example: list person"
            + "Example: list appt";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        listEntity(model);
        return new CommandResult(getSuccessMessage());
    }

    /*
     * Lists everything in the model (person or appointment).
     */
    protected abstract void listEntity(Model model);

    /*
     * Returns the message to be displayed after listing all persons or appointments.
     */
    protected abstract String getSuccessMessage();
}
