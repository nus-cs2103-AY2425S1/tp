package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.RoleIsMemberPredicate;

/**
 * Lists all members of the cca (people with member role) in address book
 */
public class ListAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "attendance";
    public static final String COMMAND_ALIAS = "atd";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all members of the cca in the system\n"
            + "Parameters: None\n"
            + "Example: " + COMMAND_WORD;

    private static final RoleIsMemberPredicate memberFilter = new RoleIsMemberPredicate();

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredPersonList(memberFilter);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
}
