package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGISTER_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_CLASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonPredicate;

/**
 * Filters and lists all persons in the address book that match the given attributes.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all matching persons with"
            + "the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: [" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_REGISTER_NUMBER + "REGISTER NUMBER] "
            + "[" + PREFIX_SEX + "SEX] "
            + "[" + PREFIX_STUDENT_CLASS + "CLASS] "
            + "[" + PREFIX_ECNAME + "EMERGENCY CONTACT NAME] "
            + "[" + PREFIX_ECNUMBER + "EMERGENCY CONTACT NUMBER] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "Bob "
            + PREFIX_PHONE + "90123445";

    public static final String MESSAGE_INCOMPLETE_COMMAND = "Filter predicate cannot be empty.";

    private static final Logger logger = LogsCenter.getLogger(FilterCommand.class);
    private final PersonPredicate predicate;

    /**
     * Constructs a {@code FilterCommand} with the specified {@code predicate}.
     * This command will filter and display all persons that match the given criteria.
     * @param predicate The filtering criteria to apply for person objects in the address book.
     */
    public FilterCommand(PersonPredicate predicate) {
        assert predicate != null;
        this.predicate = predicate;
        logger.log(Level.FINE, "FilterCommand initialized with predicate");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        List<Person> filteredPersonsList = model.getFilteredPersonList();
        assert filteredPersonsList != null;

        int filterCount = filteredPersonsList.size();
        logger.log(Level.INFO, "FilterCommand execution completed. Number of persons listed: {0}", filterCount);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
