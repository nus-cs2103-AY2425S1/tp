package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.PERSON_ENTITY_STRING;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.PersonDescriptor;

/**
 * Adds a person to the address book.
 */
public class AddPersonCommand extends AddCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PERSON_ENTITY_STRING
        + ": Adds a person to the address book. \n"
        + "Parameters: "
        + PREFIX_NAME + "NAME "
        + PREFIX_PHONE + "PHONE "
        + PREFIX_EMAIL + "EMAIL "
        + PREFIX_ADDRESS + "ADDRESS "
        + PREFIX_STATUS + "STATUS "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " " + PERSON_ENTITY_STRING + " "
        + PREFIX_NAME + "John Doe "
        + PREFIX_PHONE + "98765432 "
        + PREFIX_EMAIL + "johnd@example.com "
        + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
        + PREFIX_STATUS + "recovering "
        + PREFIX_TAG + "friends "
        + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final PersonDescriptor personDescriptor;
    private int personId;

    /**
     * Creates an AddPersonCommand to add the specified {@code Person}
     *
     * @param personDescriptorToAdd Person to add.
     */
    public AddPersonCommand(PersonDescriptor personDescriptorToAdd) {
        requireNonNull(personDescriptorToAdd);
        personDescriptor = personDescriptorToAdd;
    }

    /**
     * Checks if the entity being added to model already exists.
     *
     * @param model Model to check if entity exists in.
     * @return True if entity already exists in model, false otherwise.
     */
    @Override
    protected boolean alreadyExists(Model model) {
        return model.hasPerson(personDescriptor);
    }

    /**
     * Adds the entity to the model.
     *
     * @param model Model to add entity to.
     */
    @Override
    protected void addEntity(Model model) {
        personId = model.addPerson(personDescriptor).getPersonId();
    }

    /**
     * Returns the success message of the command.
     *
     * @return Success message of the command.
     */
    @Override
    protected String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }

    /**
     * Returns the message to display when the person ID does not exist.
     *
     * @return Message to display when the person ID does not exist.
     */
    @Override
    protected String getDuplicateEntityMessage() {
        return MESSAGE_DUPLICATE_PERSON;
    }

    /**
     * Formats the entity for displaying in the success message.
     *
     * @return Formatted entity.
     */
    @Override
    protected String formatEntity() {
        return Messages.formatPerson(personDescriptor);
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

        return personDescriptor.equals(otherAddPersonCommand.personDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("personDescriptor", personDescriptor)
            .toString();
    }
}
