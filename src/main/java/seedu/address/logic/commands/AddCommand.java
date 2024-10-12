package seedu.address.logic.commands;

import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.Person;

import java.util.logging.Logger;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    private static final Logger logger = LogsCenter.getLogger(AddCommand.class);

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
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
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25, S120300 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_DUPLICATE_PHONE = "\nWarning! There is a person with the same phone number";
    public static final String MESSAGE_DUPLICATE_EMAIL = "\nWarning! There is a person with the same email";

    private final Person toAdd;
    private final Delivery deliveryToAdd;
    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
        deliveryToAdd = null;
    }

    public AddCommand(Delivery delivery) {
        requireNonNull(delivery);
        deliveryToAdd = delivery;
        toAdd = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!AddressBookParser.getInspect()) {
            requireNonNull(model);
            //Checks if name already exist
            if (model.hasPerson(toAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

            boolean hasPhone = model.hasPhone(toAdd);
            boolean hasEmail = model.hasEmail(toAdd);
            String warning = "";
            //Check if phone number duplicate
            if (hasPhone) {
                warning += MESSAGE_DUPLICATE_PHONE;
            }
            //Check if email duplicate
            if (hasEmail) {
                warning += MESSAGE_DUPLICATE_EMAIL;
            }
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS + warning, Messages.format(toAdd)));
        } else {
            requireNonNull(model);
            throw new CommandException("Implement this after merging");
        }
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
