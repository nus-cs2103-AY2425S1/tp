package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.ParserUtil.APPOINTMENT_ENTITY_STRING;
import static seedu.address.logic.parser.ParserUtil.PERSON_ENTITY_STRING;

import seedu.address.model.Model;

/**
 * Clears the model - person, or appointment.
 */
public abstract class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all entities of a certain type. \n"
            + "Parameters: ENTITY_TYPE (person/appt)\n"
            + "Example: " + COMMAND_WORD + " " + PERSON_ENTITY_STRING + "\n"
            + "Example: " + COMMAND_WORD + " " + APPOINTMENT_ENTITY_STRING;

    /**
     * Executes the command to clear the model.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that describes the success of the command.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        clearEntity(model);
        return new CommandResult(getSuccessMessage());
    }

    /**
     * Clears a specified model.
     *
     * @param model {@code Model} which the command should operate on.
     */
    protected abstract void clearEntity(Model model);

    /**
     * Returns success message to display upon adding entity.
     *
     * @return success message
     */
    protected abstract String getSuccessMessage();
}
