package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDY_GROUP_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 * ResearchRoster uses an address book
 * to keep track of persons
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME\n"
            + " " + PREFIX_EMAIL + "EMAIL\n"
            + " " + PREFIX_GENDER + "GENDER\n"
            + " " + PREFIX_AGE + "AGE\n"
            + " " + "[" + PREFIX_DETAIL + "DETAIL]\n"
            + " " + "[" + PREFIX_STUDY_GROUP_TAG + "STUDY-GROUP-TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe\n"
            + " " + PREFIX_EMAIL + "johnd@example.com\n"
            + " " + PREFIX_GENDER + "M\n"
            + " " + PREFIX_AGE + "30\n"
            + " " + PREFIX_DETAIL + "To be assigned\n"
            + " " + PREFIX_STUDY_GROUP_TAG + "Control "
            + " " + PREFIX_STUDY_GROUP_TAG + "1A";

    public static final String MESSAGE_SUCCESS = "Added successfully!\n"
            + "Added participant: %1$s";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book!";

    private final Person toAdd;

    /**
     * Creates an AddCommand to
     * add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
