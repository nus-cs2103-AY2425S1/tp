package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Tutor;

/**
 * Adds a person to VolunTier.
 */
public class AddTutorCommand extends Command {

    public static final String COMMAND_WORD = "addTutor";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tutor to VolunTier. \n"
            + "Parameters: "

            + PREFIX_NAME + " NAME "
            + PREFIX_PHONE + " PHONE_NUMBER "
            + PREFIX_EMAIL + " EMAIL "
            + PREFIX_ADDRESS + " ADDRESS "
            + "[" + PREFIX_HOURS + " HOURS] "
            + "[" + PREFIX_SUBJECT + " SUBJECT]… \n"
            + "Example: " + COMMAND_WORD + " "

            + PREFIX_NAME + " John Doe "
            + PREFIX_PHONE + " 98765432 "
            + PREFIX_EMAIL + " johndoe@example.com "
            + PREFIX_ADDRESS + " Clementi, 123456 "
            + PREFIX_HOURS + " 6 "
            + PREFIX_SUBJECT + " Math ";

    public static final String MESSAGE_SUCCESS = "New tutor added: \n %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This tutor already exists in VolunTier";

    private final Tutor toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddTutorCommand(Tutor tutor) {
        requireNonNull(tutor);
        toAdd = tutor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTutorCommand)) {
            return false;
        }

        AddTutorCommand otherAddCommand = (AddTutorCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAddTutor", toAdd)
                .toString();
    }
}
