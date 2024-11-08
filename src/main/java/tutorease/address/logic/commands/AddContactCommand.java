package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorease.address.logic.Messages.MESSAGE_DUPLICATE_EMAIL;
import static tutorease.address.logic.Messages.MESSAGE_DUPLICATE_PHONE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_TAG;

import tutorease.address.commons.util.ToStringBuilder;
import tutorease.address.logic.Messages;
import tutorease.address.logic.commands.exceptions.CommandException;
import tutorease.address.model.Model;
import tutorease.address.model.person.Person;

/**
 * Adds a contact to the TutorEase.
 */
public class AddContactCommand extends ContactCommand {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = ContactCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds a person to TutorEase.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_ROLE + "ROLE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + ContactCommand.COMMAND_WORD + " " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_ROLE + "Student "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New contact added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This contact already exists in TutorEase. If you wish to "
            + "save an alternative version of a person, "
            + "you may add a unique identifier to his/her name e.g. Ryan Tan Sec 1";
    private final Person toAdd;

    /**
     * Creates an AddContactCommand to add the specified {@code Person}.
     */
    public AddContactCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        checkDuplicateContacts(model, toAdd);

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    private static void checkDuplicateContacts(Model model, Person toAdd) throws CommandException {
        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (model.hasSamePhone(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PHONE);
        }

        if (model.hasSameEmail(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EMAIL);
        }
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

        AddContactCommand otherAddContactCommand = (AddContactCommand) other;
        return toAdd.equals(otherAddContactCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
