package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RSVP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.EditGuestDescriptor;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Guest;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Relation;
import seedu.address.model.person.Rsvp;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing guest in the address book.
 */
public class EditGuestCommand extends Command {

    public static final String COMMAND_WORD = "edit_guest";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the guest identified "
            + "by the index number used in the displayed guest list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_RSVP + "RSVP] "
            + "[" + PREFIX_RELATION + "RELATION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_GUEST_SUCCESS = "Edited Guest: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_GUEST = "This guest already exists in the address book.";

    private final Index index;
    private final EditGuestDescriptor editGuestDescriptor;

    /**
     * @param index of the guest in the filtered guest list to edit
     * @param editGuestDescriptor details to edit the guest with
     */
    public EditGuestCommand(Index index, EditGuestDescriptor editGuestDescriptor) {
        requireNonNull(index);
        requireNonNull(editGuestDescriptor);

        this.index = index;
        this.editGuestDescriptor = new EditGuestDescriptor(editGuestDescriptor);
    }

    /**
     * Creates and returns a {@code Guest} with the details of {@code guestToEdit}
     * edited with {@code editGuestDescriptor}.
     */
    private static Guest createEditedGuest(Guest guestToEdit, EditGuestDescriptor editGuestDescriptor) {
        assert guestToEdit != null;

        Name updatedName = editGuestDescriptor.getName().orElse(guestToEdit.getName());
        Phone updatedPhone = editGuestDescriptor.getPhone().orElse(guestToEdit.getPhone());
        Email updatedEmail = editGuestDescriptor.getEmail().orElse(guestToEdit.getEmail());
        Address updatedAddress = editGuestDescriptor.getAddress().orElse(guestToEdit.getAddress());
        Set<Tag> updatedTags = editGuestDescriptor.getTags().orElse(guestToEdit.getTags());
        Rsvp updatedRsvp = editGuestDescriptor.getRsvp().orElse(guestToEdit.getRsvp());
        Relation updatedRelation = editGuestDescriptor.getRelation().orElse(guestToEdit.getRelation());

        return new Guest(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedTags, updatedRsvp, updatedRelation);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredGuestList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Guest guestToEdit = (Guest) lastShownList.get(index.getZeroBased());
        Guest editedGuest = createEditedGuest(guestToEdit, editGuestDescriptor);

        if (!guestToEdit.isSamePerson(editedGuest) && model.hasPerson(editedGuest)) {
            throw new CommandException(MESSAGE_DUPLICATE_GUEST);
        }

        model.setPerson(guestToEdit, editedGuest);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_GUEST_SUCCESS, Messages.format(editedGuest)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditGuestCommand)) {
            return false;
        }

        EditGuestCommand otherEditCommand = (EditGuestCommand) other;
        return index.equals(otherEditCommand.index)
                && editGuestDescriptor.equals(otherEditCommand.editGuestDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editGuestDescriptor", editGuestDescriptor)
                .toString();
    }
}
