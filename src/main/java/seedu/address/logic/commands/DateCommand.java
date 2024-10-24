package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Date;
import seedu.address.model.person.Person;



/**
 * Changes the date of an existing person in the address book.
 */
public class DateCommand extends Command {
    public static final String COMMAND_WORD = "date";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the appointment date of the person identified "
            + "by their name, phone and/or email "
            + "Existing date will be overwritten by the input.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_DATE + "[DATE]\n"
            + "Example: " + COMMAND_WORD + " n/John Doe"
            + PREFIX_DATE + "12 October 2024";

    public static final String MESSAGE_ARGUMENTS = "Person: %1$s, Date: %2$s";
    public static final String MESSAGE_ADD_DATE_SUCCESS = "Added date to Person: %1$s";
    public static final String MESSAGE_DELETE_DATE_SUCCESS = "Removed date from Person: %1$s";
    public static final String MESSAGE_MULTIPLE_PERSONS_FOUND = "Multiple patients with the same details found.";
    public static final String MESSAGE_NO_PERSON_FOUND = "No matching person found. Please check the details.";

    private final Optional<String> name;
    private final Optional<String> phone;
    private final Optional<String> email;
    private final Date date;

    /**
     * @param name of the person to identify person to update
     * @param phone of the person to identify person to update
     * @param email of the person to identify person to update
     * @param date of the person to be updated to
     */
    public DateCommand(Optional<String> name, Optional<String> phone, Optional<String> email, Date date) {
        requireAllNonNull(date);

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
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

        Person personToEdit = matchingPersons.get(0);
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTag(), date);
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage(editedPerson));
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

    /**
     * Generates a command execution success message based on whether the date is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !date.value.isEmpty() ? MESSAGE_ADD_DATE_SUCCESS : MESSAGE_DELETE_DATE_SUCCESS;
        return String.format(message, personToEdit);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateCommand)) {
            return false;
        }

        DateCommand otherDateCommand = (DateCommand) other;
        return name.equals(otherDateCommand.name)
                && phone.equals(otherDateCommand.phone)
                && email.equals(otherDateCommand.email)
                && date.equals(otherDateCommand.date);
    }
}
