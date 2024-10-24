package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String SHORT_COMMAND_WORD = ":a";
    public static final String LONG_COMMAND_WORD = ":add";

    public static final String MESSAGE_USAGE = SHORT_COMMAND_WORD + " or " + LONG_COMMAND_WORD
            + ": Adds a person to the address book. "
            + "\nCompulsory parameters: "
            + PREFIX_NAME + "NAME "
            + "\nOptional parameters: "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_LOCATION + "LOCATION "
            + "[" + PREFIX_TAG + "TAG]... "
            + PREFIX_REMARK + "REMARK\n"
            + "Example 1: " + SHORT_COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + "\nExample 2: " + LONG_COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_LOCATION + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney"
            + PREFIX_REMARK + "Likes to swim";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String COMMAND_SUMMARY_ACTION = "Add";
    public static final String COMMAND_SUMMARY_FORMAT =
            ":add -n NAME -p PHONE_NUMBER -e EMAIL -l LOCATION -t TAG -r REMARK…\n"
            + ":a -n NAME -p PHONE_NUMBER -e EMAIL -l LOCATION -t TAG -r REMARK…";
    public static final String COMMAND_SUMMARY_EXAMPLES =
            ":add -n James Ho -p 98765432 -e jamesho@example.com -l 123, "
                   + "Clementi Rd, 1234665 -t friend -r My favourite colleague";

    public static final List<String> INVALID_VARIANTS = Arrays.asList("add", "a");
    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
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
