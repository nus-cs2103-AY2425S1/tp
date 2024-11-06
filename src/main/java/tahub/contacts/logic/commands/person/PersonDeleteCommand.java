package tahub.contacts.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_MATRICULATION_NUMBER;

import java.util.List;

import tahub.contacts.commons.util.ToStringBuilder;
import tahub.contacts.logic.Messages;
import tahub.contacts.logic.commands.Command;
import tahub.contacts.logic.commands.CommandResult;
import tahub.contacts.logic.commands.exceptions.CommandException;
import tahub.contacts.model.Model;
import tahub.contacts.model.person.MatriculationNumber;
import tahub.contacts.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class PersonDeleteCommand extends Command {

    public static final String COMMAND_WORD = "person-delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the matriculation number.\n"
            + "Parameters: MATRICULATION_NUMBER (must be matriculation number of an existing student)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_MATRICULATION_NUMBER + "A1234567M ";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final MatriculationNumber matriculationNumber;

    public PersonDeleteCommand(MatriculationNumber matriculationNumber) {
        this.matriculationNumber = matriculationNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (!containsStudentInPersonList(lastShownList, matriculationNumber)) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
        }

        Person personToDelete = getStudentFromPersonList(lastShownList, matriculationNumber);
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    private static boolean containsStudentInPersonList(List<Person> personList,
                                                       MatriculationNumber matriculationNumber) {
        return personList.stream().anyMatch(person -> person.getMatricNumber().equals(matriculationNumber));
    }

    private static Person getStudentFromPersonList(List<Person> personList, MatriculationNumber matriculationNumber) {
        return personList.stream().filter(person -> person.getMatricNumber()
                .equals(matriculationNumber)).findFirst().get();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonDeleteCommand)) {
            return false;
        }

        PersonDeleteCommand otherPersonDeleteCommand = (PersonDeleteCommand) other;
        return matriculationNumber.equals(otherPersonDeleteCommand.matriculationNumber);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("matriculationNumber", matriculationNumber)
                .toString();
    }
}
