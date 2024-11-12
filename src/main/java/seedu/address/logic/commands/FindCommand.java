package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.ParserUtil.APPOINTMENT_ENTITY_STRING;
import static seedu.address.logic.parser.ParserUtil.PERSON_ENTITY_STRING;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;


/**
 * Finds and lists all persons or appointments in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public abstract class FindCommand<T> extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Finds all entities whose data contain any of "
        + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
        + "Parameters: ENTITY_TYPE (person/appt) KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " " + PERSON_ENTITY_STRING + " n/John\n"
        + "Example: " + COMMAND_WORD + " " + APPOINTMENT_ENTITY_STRING + " n/John d/2024-10-20";

    protected final Predicate<T> predicate;

    /**
     * Creates a {@code FindCommand} with the specified predicate.
     * */
    public FindCommand(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    /**
     * Executes the find command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that describes the result of executing the command.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        findEntity(model);
        return new CommandResult(getSuccessMessage(model));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand<?> otherFindCommand)) {
            return false;
        }

        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }


    /**
     * Finds the person/appointment from the list
     *
     * @param model {@code Model} which the FindCommand should operate on.
     */
    protected abstract void findEntity(Model model);

    /**
     * Returns the message to be displayed after finding all persons or appointments matching the query.
     *
     * @param model {@code Model} which the FindCommand should operate on.
     * @return Success message
     */
    protected abstract String getSuccessMessage(Model model);
}
