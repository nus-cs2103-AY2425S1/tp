package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.ClientTypeContainsKeywordsPredicate;

import static java.util.Objects.requireNonNull;

public class FindClientTypeCommand extends Command{

    public static final String COMMAND_WORD = "fc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: CLIENT_TYPE [MORE_CLIENT_TYPES]...\n"
            + "Example: " + COMMAND_WORD + "Investment Plan 1";

    private final ClientTypeContainsKeywordsPredicate predicate;

    public FindClientTypeCommand(ClientTypeContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
