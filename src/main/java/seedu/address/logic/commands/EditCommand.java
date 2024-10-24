package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of the person specified by name. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: NAME"
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " John Doe "
            + PREFIX_PHONE + "91234567 ";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    /**
     * Holds edit information to edit a person.
     */
    public record EditPersonDescriptor(Optional<Name> name, Optional<Phone> phone,
                                       Optional<Address> address, Optional<Set<Tag>> tags) { }

    private final Name targetName;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param targetName of the person to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Name targetName, EditPersonDescriptor editPersonDescriptor) {
        this.targetName = targetName;
        this.editPersonDescriptor = editPersonDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToEdit = model
                .findPerson(p -> p.getName().equals(targetName))
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND));

        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        Name updatedName = editPersonDescriptor.name.orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.phone.orElse(personToEdit.getPhone());
        Address updatedAddress = editPersonDescriptor.address.orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.tags.orElse(personToEdit.getTags());
        return new Person(updatedName, updatedPhone, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return targetName.equals(otherEditCommand.targetName)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetName", targetName)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }
}
