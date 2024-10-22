package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.ContractEndDate;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;

/**
 * Converts an employee identified using it's displayed index from the address book to a potential hire
 */
public class DemoteCommand extends Command {

    public static final String COMMAND_WORD = "demote";

    public static final String MESSAGE_PURPOSE = "Converts an employee to a potential hire.";

    public static final String MESSAGE_PARAMETER = "INDEX (must be a positive integer)";

    public static final String MESSAGE_EXAMPLE = COMMAND_WORD + "1";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + MESSAGE_PURPOSE
            + "\nParameters: " + MESSAGE_PARAMETER
            + "\nExample: " + MESSAGE_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "Employee demoted: %1$s";
    public static final String MESSAGE_NOT_AN_EMPLOYEE = "This person is not an employee and cannot be demoted";
    private final Index index;

    /**
     * Creates a DemoteCommand to demote the employee at the specified {@code index} in the list to a potential hire
     */
    public DemoteCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> currentList = model.getFilteredPersonList();

        if (index.getZeroBased() >= currentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDemote = currentList.get(index.getZeroBased());

        if (!personToDemote.isEmployee()) {
            throw new CommandException(MESSAGE_NOT_AN_EMPLOYEE);
        }

        Person demotedPerson = createDemotedPerson(personToDemote);

        model.setPerson(personToDemote, demotedPerson);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(demotedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with isEmployee equal to false and an empty contract end date
     */
    private static Person createDemotedPerson(Person personToDemote) {
        assert personToDemote != null : "Person does not exist, cannot be demoted";

        Name name = personToDemote.getName();
        Phone phoneNumber = personToDemote.getPhone();
        Email email = personToDemote.getEmail();
        Address address = personToDemote.getAddress();
        Department department = personToDemote.getDepartment();
        Role role = personToDemote.getRole();
        ContractEndDate contractEndDate = ContractEndDate.empty();

        return new Person(name, phoneNumber, email, address, department,
                role, contractEndDate, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DemoteCommand)) {
            return false;
        }

        DemoteCommand otherDemoteCommand = (DemoteCommand) other;
        return index.equals(otherDemoteCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
