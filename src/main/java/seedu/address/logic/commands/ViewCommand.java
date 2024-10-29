package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

public class ViewCommand extends Command{
    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_VIEW_SUCCESS = "Viewing Note: %1$s";
    public static final String MESSAGE_FIELD_MISSING = "Index and name is not provided.";
    public static final String MESSAGE_INDEX_NOT_FOUND = "Index provided is invalid.";
    public static final String MESSAGE_NAME_NOT_FOUND = "Person's name provided is invalid.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the details of the person identified"
            + "by the index number or full name of the person.\n"
            + "Parameters: NAME or INDEX \n"
            + "Example: " + COMMAND_WORD + " John Doe ";

    private final Name fullName;
    private final Index index;

    public ViewCommand(Name fullName) {
        this.fullName = fullName;
        this.index = null;
    }

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
     * Finds a person by name.
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

    private Person findPersonByIndex(List<Person> personList) throws CommandException {
        try {
            return personList.get(index.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_INDEX_NOT_FOUND);
        }
    }

}
