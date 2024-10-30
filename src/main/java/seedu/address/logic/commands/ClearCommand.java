package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;

/**
 * Clears the model - person, or appointment.
 */
public abstract class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all entities of a certain type. \n"
            + "Parameters: ENTITY_TYPE (person/appt)\n "
            + "Example: " + COMMAND_WORD + " " + ParserUtil.PERSON_ENTITY_STRING + "\n"
            + "Example: " + COMMAND_WORD + " " + ParserUtil.APPOINTMENT_ENTITY_STRING;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        clearEntity(model);
        return new CommandResult(getSuccessMessage());
    }

    /*
     * Clears a specified model.
     */
    protected abstract void clearEntity(Model model);

    /*
     * Returns success message to display upon adding entity.
     */
    protected abstract String getSuccessMessage();
}
