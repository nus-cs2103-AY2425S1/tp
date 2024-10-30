package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Displays note of a specified person.
 */
public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_VIEW_SUCCESS = "Viewing Note: %1$s";
    public static final String MESSAGE_FIELD_MISSING = "Both index and name must be provided.";
    public static final String MESSAGE_INDEX_NOT_FOUND = "Invalid index provided..";
    public static final String MESSAGE_NAME_NOT_FOUND = "Invalid person name provided.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the details of the person identified "
            + "by index number or full name of the person.\n"
            + "Parameters: NAME or INDEX \n"
            + "Example: " + COMMAND_WORD + " John Doe ";

    private final Name fullName;
    private final Index index;

    /**
     * Displays note of a person by full name.
     */
    public ViewCommand(Name fullName) {
        this.fullName = fullName;
        this.index = null;
    }

    /**
     * Displays note of a person by index.
     */
    public ViewCommand(Index index) {
        this.fullName = null;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> personList = model.getFilteredPersonList();
        Person personToView = getPersonToView(personList);
        model.viewNote(personToView);
        return new CommandResult(String.format(MESSAGE_VIEW_SUCCESS, personToView.getName()));
    }

    /**
     * Finds a person from a list by index or by full name.
     *
     * @param personList The list of persons currently shown.
     * @return The person found.
     * @throws CommandException if the person is not found.
     */
    private Person getPersonToView(List<Person> personList) throws CommandException {
        if (index != null) {
            return findPersonByIndex(personList);
        } else if (fullName != null) {
            return findPersonByName(personList);
        } else {
            throw new CommandException(MESSAGE_FIELD_MISSING);
        }
    }

    /**
     * Finds a person by full name.
     *
     * @param personList The list of persons currently shown.
     * @return The person found.
     * @throws CommandException if the person is not found.
     */
    private Person findPersonByName(List<Person> personList) throws CommandException {
        Optional<Person> personOptional = personList.stream()
                .filter(person -> person.getName().equals(fullName))
                .findFirst();

        if (personOptional.isEmpty()) {
            throw new CommandException(MESSAGE_NAME_NOT_FOUND);
        }
        return personOptional.get();
    }

    /**
     * Finds a person by index.
     *
     * @param personList The list of persons currently shown.
     * @return The person found.
     * @throws CommandException if the person is not found.
     */
    private Person findPersonByIndex(List<Person> personList) throws CommandException {
        try {
            return personList.get(index.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_INDEX_NOT_FOUND);
        }
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

        ViewCommand otherFindCommand = (ViewCommand) other;
        if (fullName != null) {
            return fullName.equals(otherFindCommand.fullName);
        } else {
            return index.equals(otherFindCommand.index);
        }
    }

    @Override
    public String toString() {
        if (fullName != null) {
            return new ToStringBuilder(this)
                    .add("person to view", fullName)
                    .toString();
        } else {
            return new ToStringBuilder(this)
                    .add("index to view", index)
                    .toString();
        }
    }

}
