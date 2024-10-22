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
 * Converts a potential hire identified using it's displayed index from the address book to an employee
 */
public class PromoteCommand extends Command {

    public static final String COMMAND_WORD = "promote";

    public static final String MESSAGE_PURPOSE = "Converts a potential hire to an employee.";

    public static final String MESSAGE_FORMAT = COMMAND_WORD + " INDEX CONTRACT_END_DATE";

    public static final String MESSAGE_EXAMPLE = COMMAND_WORD + " 1 2024-01-29";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + MESSAGE_PURPOSE
            + "\nFormat: " + MESSAGE_FORMAT
            + "\nExample: " + MESSAGE_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "Potential hire promoted: %1$s";
    public static final String MESSAGE_NOT_A_POTENTIAL_HIRE =
            "This person is not a potential hire and cannot be promoted";
    private final Index index;
    private final ContractEndDate contractEndDate;

    /**
     * Creates a PromoteCommand to promote the potential hire at the specified {@code index} in the list to an
     * employee with {@code contractEndDate}
     */
    public PromoteCommand(Index index, ContractEndDate contractEndDate) {
        requireNonNull(index);
        requireNonNull(contractEndDate);

        this.index = index;
        this.contractEndDate = contractEndDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> currentList = model.getFilteredPersonList();

        if (index.getZeroBased() >= currentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToPromote = currentList.get(index.getZeroBased());

        if (personToPromote.isEmployee()) {
            throw new CommandException(MESSAGE_NOT_A_POTENTIAL_HIRE);
        }

        Person promotedPerson = createPromotedPerson(personToPromote, contractEndDate);

        model.setPerson(personToPromote, promotedPerson);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(promotedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToPromote}
     * with isEmployee equal to true and {@code contractEndDate}.
     */
    private static Person createPromotedPerson(Person personToPromote, ContractEndDate contractEndDate) {

        assert personToPromote != null : "Person does not exist, cannot be promoted";

        Name name = personToPromote.getName();
        Phone phoneNumber = personToPromote.getPhone();
        Email email = personToPromote.getEmail();
        Address address = personToPromote.getAddress();
        Department department = personToPromote.getDepartment();
        Role role = personToPromote.getRole();

        return new Person(name, phoneNumber, email, address, department,
                role, contractEndDate, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PromoteCommand)) {
            return false;
        }

        PromoteCommand otherPromoteCommand = (PromoteCommand) other;
        return index.equals(otherPromoteCommand.index)
                && contractEndDate.equals(otherPromoteCommand.contractEndDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("contractEndDate", contractEndDate)
                .toString();
    }
}

