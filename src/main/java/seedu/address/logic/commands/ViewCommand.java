package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String SHORT_COMMAND_WORD = "v";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a view of the specified person whose name"
            + "contain any of "
            + "the specified keywords (case-insensitive) and display them as a popup modal.\n"
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " " + "alice";

    public static final String SHOWING_VIEW_MESSAGE = "Opened view window.";

    private final NameContainsKeywordsPredicate predicate;

    public ViewCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // In the list, it will show the person that is viewed
        model.updateFilteredPersonList(predicate);

        // Check if there is anyone in the filtered list
        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(
                    String.format(Messages.MESSAGE_PERSON_LISTED_OVERVIEW_FOR_VIEW,
                            model.getFilteredPersonList().size())
                            + "\nPlease specify the name further to view."
            );
        }

        // Check if there are duplicates
        if (model.getFilteredPersonList().size() > 1) {
            return new CommandResult(
                    String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                            model.getFilteredPersonList().size())
                            + "\nDuplicates found. Please specify the name further."
            );
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSON_LISTED_OVERVIEW_FOR_VIEW, model.getFilteredPersonList().size()),
                false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;
        return predicate.equals(otherViewCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
