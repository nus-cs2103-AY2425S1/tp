package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.ContainsKeywordsPredicate;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "findapp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all appointments based on the specified criteria "
            + "and displays them as a list.\n"
            + "Parameters: "
            + "[" + PREFIX_START_DATE + "START_DATE] "
            + "[" + PREFIX_START_TIME + "START_TIME] "
            + "[" + PREFIX_END_DATE + "END_DATE]"
            + "[" + PREFIX_END_TIME + "END_TIME] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_START_DATE + "01/01/2025 "
            + PREFIX_START_TIME  + "12:00 "
            + PREFIX_END_DATE + "02/01/2025 "
            + PREFIX_END_TIME + "13:00";

    private final ContainsKeywordsPredicate predicate;

    public FindAppointmentCommand(ContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                false, false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindAppointmentCommand)) {
            return false;
        }

        FindAppointmentCommand otherFindAppointmentCommand = (FindAppointmentCommand) other;
        return this.predicate.equals(otherFindAppointmentCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
