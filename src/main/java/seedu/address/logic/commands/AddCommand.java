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
import static seedu.address.logic.parser.ParserUtil.APPOINTMENT_ENTITY_STRING;
import static seedu.address.logic.parser.ParserUtil.PERSON_ENTITY_STRING;

import seedu.address.logic.commands.exceptions.CommandException;
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
            + "Example: " + COMMAND_WORD + " " + PERSON_ENTITY_STRING + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_STATUS + "recovering "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney \n"
            + "Example: " + COMMAND_WORD + " " + APPOINTMENT_ENTITY_STRING + " "
            + PREFIX_PERSON_ID + "1 "
            + PREFIX_APPOINTMENT_TYPE + "Check up "
            + PREFIX_DATETIME + "2024-10-16 12:30 "
            + PREFIX_SICKNESS + "Common Cold "
            + PREFIX_MEDICINE + "Paracetamol";

    /**
     * Executes the command to add an entity to the address book.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that describes the success of the command.
     * @throws CommandException if there are duplicates
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (alreadyExists(model)) {
            throw new CommandException(getDuplicateEntityMessage());
        }

        addEntity(model);
        return new CommandResult(String.format(getSuccessMessage(), formatEntity()));
    }

    /**
     * Checks if the entity being added to model already exists.
     *
     * @param model Model to check if entity already exists.
     * @return True if entity already exists in model, false otherwise.
     */
    protected abstract boolean alreadyExists(Model model);

    /**
     * Adds the entity to the model.
     *
     * @param model Model to add entity to.
     * @throws CommandException If entity cannot be added.
     */
    protected abstract void addEntity(Model model) throws CommandException;

    /**
     * Returns success message to display upon adding entity.
     *
     * @return Success message.
     */
    protected abstract String getSuccessMessage();

    /**
     * Returns the message to display when there is a duplicate.
     *
     * @return Duplicate entity message.
     */
    protected abstract String getDuplicateEntityMessage();

    /**
     * Formats the entity for displaying in the success message.
     *
     * @return Formatted entity.
     */
    protected abstract String formatEntity();
}
