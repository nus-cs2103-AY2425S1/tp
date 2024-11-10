package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddContactCommand extends AddCommand<Person> {
    public static final String ENTITY_WORD = "contact";
    public static final String FULL_COMMAND = COMMAND_WORD + " " + ENTITY_WORD;
    public static final String MESSAGE_USAGE = FULL_COMMAND + ": Adds a contact to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ROLE + "ROLE "
            + "[" + PREFIX_SKILL + "SKILL]... (Optional)\n"
            + "Example: " + FULL_COMMAND + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ROLE + "copywriter";

    public static final String MESSAGE_SUCCESS = "Candidate added: Name: %1$s; "
            + "Email: %2$s; Phone: %3$s; Applying for: %4$s.";

    public static final String MESSAGE_DUPLICATE_CONTACT = "A candidate with this phone number "
            + "or email already exists.";

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddContactCommand(Person person) {
        super(person);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }

        model.addPerson(toAdd);

        String successMessage = String.format(MESSAGE_SUCCESS,
                toAdd.getName().fullName,
                toAdd.getPhone().value,
                toAdd.getEmail().value,
                toAdd.getRole().value
        );
        return new CommandResult(successMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddContactCommand)) {
            return false;
        }

        AddContactCommand otherAddCommand = (AddContactCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }
}
