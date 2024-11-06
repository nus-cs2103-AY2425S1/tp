package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the name, phone, or email.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL  (atleast one) \n"
            + "Example: " + COMMAND_WORD + " n/John Doe";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_MULTIPLE_PERSONS_FOUND = "Multiple patients with the same details found.";
    public static final String MESSAGE_NO_PERSON_FOUND = "No matching person found. Please check the details.";

    public static final String MESSAGE_NO_ARGUMENTS_FOUND = "Please provide at least one of the following:"
            + " name, phone, or email.";

    private final Optional<String> name;
    private final Optional<String> phone;
    private final Optional<String> email;

    /**
     * Constructs a {@code DeleteCommand} with the specified parameters.
     *
     * @param name  the name of the person to delete, wrapped in an {@code Optional}.
     *              If the name is not provided, this should be {@code Optional.empty()}.
     * @param phone the phone number of the person to delete, wrapped in an {@code Optional}.
     *              If the phone number is not provided, this should be {@code Optional.empty()}.
     * @param email the email address of the person to delete, wrapped in an {@code Optional}.
     *              If the email is not provided, this should be {@code Optional.empty()}.
     */
    public DeleteCommand(Optional<String> name, Optional<String> phone, Optional<String> email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Filter persons based on provided criteria
        List<Person> matchingPersons = lastShownList.stream()
                .filter(person -> matches(person))
                .collect(Collectors.toList());

        // Handle different cases of matches
        if (matchingPersons.isEmpty()) {
            throw new CommandException(MESSAGE_NO_PERSON_FOUND);
        }

        if (matchingPersons.size() > 1) {
            throw new CommandException(MESSAGE_MULTIPLE_PERSONS_FOUND);
        }

        // Delete the single matching person
        Person personToDelete = matchingPersons.get(0);
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    /**
     * Checks if a person matches the criteria provided in the delete command.
     */
    private boolean matches(Person person) {
        boolean matches = true;

        // If name is provided, check if it matches
        if (name.isPresent()) {
            matches &= person.getName().fullName.equalsIgnoreCase(name.get());
        }

        // If phone is provided, check if it matches
        if (phone.isPresent()) {
            matches &= person.getPhone().value.equals(phone.get());
        }

        // If email is provided, check if it matches
        if (email.isPresent()) {
            matches &= person.getEmail().value.equalsIgnoreCase(email.get());
        }

        return matches;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return name.equals(otherDeleteCommand.name)
                && phone.equals(otherDeleteCommand.phone)
                && email.equals(otherDeleteCommand.email);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("name", name)
                .add("phone", phone)
                .add("email", email)
                .toString();
    }
}
