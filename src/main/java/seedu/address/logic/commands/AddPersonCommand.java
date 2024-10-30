package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.person.PersonDescriptor;

/**
 * Adds a person to the address book.
 */
public class AddPersonCommand extends AddCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + ParserUtil.PERSON_ENTITY_STRING
        + ": Adds a person to the address book. \n"
        + "Parameters: "
        + PREFIX_NAME + "NAME "
        + PREFIX_PHONE + "PHONE "
        + PREFIX_EMAIL + "EMAIL "
        + PREFIX_ADDRESS + "ADDRESS "
        + PREFIX_STATUS + "STATUS "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " " + ParserUtil.PERSON_ENTITY_STRING + " "
        + PREFIX_NAME + "John Doe "
        + PREFIX_PHONE + "98765432 "
        + PREFIX_EMAIL + "johnd@example.com "
        + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
        + PREFIX_STATUS + "recovering "
        + PREFIX_TAG + "friends "
        + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final PersonDescriptor toAdd;
    private int personId;

    /**
     * Creates an AddPersonCommand to add the specified {@code Person}
     */
    public AddPersonCommand(PersonDescriptor personDescriptor) {
        requireNonNull(personDescriptor);
        toAdd = personDescriptor;
    }

    /**
     * Checks if the person already exists in the model.
     *
     * @param model The model containing the list of persons.
     * @return true if the person already exists in the model, false otherwise.
     */
    @Override
    protected boolean alreadyExists(Model model) {
        return model.hasPerson(toAdd);
    }

    /**
     * Adds the person entity to the model.
     *
     * @param model The model to add the person to.
     */
    @Override
    protected void addEntity(Model model) {
        personId = model.addPerson(toAdd).getPersonId();
    }

    /**
     * Returns the message to be displayed upon successfully adding the person.
     *
     * @return A success message indicating that the person was added.
     */
    @Override
    protected String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }

    /**
     * Returns the message to be displayed if the person already exists.
     *
     * @return A message indicating that the person is a duplicate.
     */
    @Override
    protected String getDuplicateEntityMessage() {
        return MESSAGE_DUPLICATE_PERSON;
    }

    /**
     * Formats the entity details into a string representation.
     *
     * @return A formatted string representing the person.
     */
    @Override
    protected String formatEntity() {
        return Messages.formatPerson(toAdd);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPersonCommand otherAddPersonCommand)) {
            return false;
        }

        return toAdd.equals(otherAddPersonCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("toAdd", toAdd)
            .toString();
    }
}
