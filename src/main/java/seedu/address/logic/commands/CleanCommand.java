package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.Year;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.GradYear;
import seedu.address.model.person.GradYearPredicate;
import seedu.address.model.person.Person;



/**
 * Deletes all people whose graduation date has passed.
 */
public class CleanCommand extends ConcreteCommand {

    public static final String COMMAND_WORD = "clean";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the people past their graduation date in the displayed person list.\n"
            + "No parameters\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_CLEAN_SUCCESS = "Deleted graduated people";
    public static final String MESSAGE_ALREADY_CLEANED = "All graduated people have already been deleted";
    public static final String MESSAGE_UNDO_SUCCESS = "Restored deleted graduated people";
    private static final Logger logger = LogsCenter.getLogger(CleanCommand.class);
    private final Predicate<Person> predicate;
    private final String gradYear;
    private AddressBook initialAddressBook;

    /**
     * Creates a CleanCommand to delete the people whose graduation dates have past
     */
    public CleanCommand() {
        this.gradYear = String.valueOf(Year.now().getValue());
        this.predicate = new GradYearPredicate(new GradYear(gradYear));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNotExecuted();
        requireNonNull(model);
        initialAddressBook = new AddressBook(model.getAddressBook());
        if (model.hasGraduatedBefore(gradYear)) {
            model.deletePersonByPredicate(predicate);
            isExecuted = true;
            logger.info("clean successful");
            return new CommandResult(MESSAGE_CLEAN_SUCCESS);
        } else {
            throw new CommandException(MESSAGE_ALREADY_CLEANED);
        }
    }

    @Override
    public CommandResult undo(Model model) {
        requireExecuted();
        requireNonNull(model);
        requireNonNull(initialAddressBook);
        model.setAddressBook(initialAddressBook);
        isExecuted = false;
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CleanCommand)) {
            return false;
        }

        CleanCommand otherCleanCommand = (CleanCommand) other;
        return this.predicate.equals(otherCleanCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("graduation date predicate", predicate)
                .toString();
    }
}
