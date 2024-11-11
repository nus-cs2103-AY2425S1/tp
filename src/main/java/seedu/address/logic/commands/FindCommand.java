package seedu.address.logic.commands;


import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;

/**
 * Finds and lists all persons in PawPatrol whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public abstract class FindCommand<T> extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds owners or pets in PawPatrol whose "
            + "relevant fields contain the specified keywords (case-insensitive) and displays them as a list with "
            + "index numbers.\n"
            + "To find owners: find owner KEYWORD [MORE_KEYWORDS]...\n"
            + "To find pets: find pet KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: find owner bobby, find pet golden retriever";


    protected final Predicate<T> predicate;
    /**
     * Constructor for finding any entity.
     * @param predicate Predicate for entity.
     */
    public FindCommand(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public abstract CommandResult execute(Model model);

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand<?> otherFindCommand = (FindCommand<?>) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
