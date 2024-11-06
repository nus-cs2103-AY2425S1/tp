package seedu.sellsavvy.logic.commands.personcommands;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.logic.Messages.MESSAGE_SIMILAR_NAME_WARNING;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.sellsavvy.commons.util.ToStringBuilder;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.logic.commands.Command;
import seedu.sellsavvy.logic.commands.CommandResult;
import seedu.sellsavvy.logic.commands.exceptions.CommandException;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddPersonCommand extends Command {

    public static final String COMMAND_WORD = "addcustomer";
    public static final String COMMAND_ALIAS = "addc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a customer to the address book. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_SIMILAR_TAGS_WARNING = "Note: "
            + "This customer has 2 or more similar tags, "
            + "verify if this is a mistake.\n";

    private final Person toAdd;

    /**
     * Creates an AddPersonCommand to add the specified {@code Person}
     */
    public AddPersonCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        String feedbackToUser = model.hasSimilarPerson(toAdd)
                ? MESSAGE_SIMILAR_NAME_WARNING
                : "";
        feedbackToUser += toAdd.hasSimilarTags()
                ? MESSAGE_SIMILAR_TAGS_WARNING
                : "";

        model.addPerson(toAdd);
        return new CommandResult(feedbackToUser + String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPersonCommand)) {
            return false;
        }

        AddPersonCommand otherAddPersonCommand = (AddPersonCommand) other;
        return toAdd.equals(otherAddPersonCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
