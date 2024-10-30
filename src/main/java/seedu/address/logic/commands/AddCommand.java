package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SICKNESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;

/**
 * Adds an entity (person or appointment) to the address book.
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an entity to address or appointment book. \n"
            + "Parameters: "
            + "ENTITY_TYPE (person/appt) "
            + "DATA_FIELDS [MORE_DATA_FIELDS]... \n"
            + "Example: " + COMMAND_WORD + " " + ParserUtil.PERSON_ENTITY_STRING + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_STATUS + "recovering "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney \n"
            + "Example: " + COMMAND_WORD + " " + ParserUtil.APPOINTMENT_ENTITY_STRING + " "
            + PREFIX_APPOINTMENT_TYPE + "Check up "
            + PREFIX_DATETIME + "2024-10-16 12:30:30 "
            + PREFIX_PERSON_ID + "1 "
            + PREFIX_SICKNESS + "Common Cold "
            + PREFIX_MEDICINE + "Paracetamol";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (alreadyExists(model)) {
            throw new CommandException(getDuplicateEntityMessage());
        }

        addEntity(model);
        return new CommandResult(String.format(getSuccessMessage(), formatEntity()));
    }

    /*
     * Checks if the entity being added to model already exists.
     */
    protected abstract boolean alreadyExists(Model model);

    /*
     * Adds the entity to the model.
     */
    protected abstract void addEntity(Model model) throws CommandException;

    /*
     * Returns success message to display upon adding entity.
     */
    protected abstract String getSuccessMessage();

    /*
     * Returns the message to display when there is a duplicate.
     */
    protected abstract String getDuplicateEntityMessage();

    /**
     * Formats the entity for displaying in the success message.
     */
    protected abstract String formatEntity();
}
