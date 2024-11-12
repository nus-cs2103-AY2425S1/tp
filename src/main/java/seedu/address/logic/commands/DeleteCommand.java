package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.logic.StaticContext;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using their name from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "del";
    public static final String COMMAND_FUNCTION = COMMAND_WORD
            + ": Deletes the person identified by the name used in the address book.";

    public static final String MESSAGE_USAGE = COMMAND_FUNCTION
            + "\nParameters: " + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "Li Sirui";

    public static final String MESSAGE_NO_MATCH_FOUND = "No contact with the name '%1$s' found."
            + " Please enter the full name (case-insensitive).";
    public static final String MESSAGE_MISSING_NAME = "Contact name is required.";
    public static final String MESSAGE_CONFIRMATION_PROMPT = """
            Are you sure you want to delete the following contact?
            Enter 'y' to confirm, or 'n' to cancel.
            Name: %1$s
            Phone: %2$s
            Email: %3$s
            Address: %4$s
            Job: %5$s
            """;

    private final String name;

    /**
     * Creates a DeleteCommand to delete the Person with the specified {@code Name}.
     */
    public DeleteCommand(String name) {
        this.name = name.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);


        if (name.isEmpty()) {
            throw new CommandException(MESSAGE_MISSING_NAME);
        }

        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(name))
                .toList();

        if (matchingPersons.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_MATCH_FOUND, name));
        }

        Person personToDelete = matchingPersons.get(0);

        String confirmationMessage = String.format(MESSAGE_CONFIRMATION_PROMPT,
                personToDelete.getName().fullName,
                personToDelete.getPhone().value,
                personToDelete.getEmail().value,
                personToDelete.getAddress().value,
                personToDelete.getJob().value);

        // Store the personToDelete in a static context
        StaticContext.setPersonToDelete(personToDelete);

        return new CommandResult(confirmationMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteCommand otherDeleteCommand)) {
            return false;
        }

        return name.equals(otherDeleteCommand.name);
    }
}
