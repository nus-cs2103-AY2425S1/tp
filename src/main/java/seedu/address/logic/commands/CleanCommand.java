package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.Year;
import java.util.List;
import java.util.function.Predicate;

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
    public static final String MESSAGE_UNDO_SUCCESS = "Restored deleted graduated people";

    private final Predicate<Person> predicate;
    private AddressBook initialAddressBook;

    /**
     * Creates a CleanCommand to delete the people whose graduation dates have past
     */
    public CleanCommand() {
        String year = String.valueOf(Year.now().getValue());
        this.predicate = new GradYearPredicate(new GradYear(year));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNotExecuted();
        requireNonNull(model);
        initialAddressBook = new AddressBook(model.getAddressBook());

        model.updateFilteredPersonList(predicate);
        List<Person> lastShownList = model.getFilteredPersonList();
        for (int i = lastShownList.size() - 1; i >= 0; i--) {
            model.deletePerson(lastShownList.get(i));
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        isExecuted = true;
        return new CommandResult(MESSAGE_CLEAN_SUCCESS);
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
