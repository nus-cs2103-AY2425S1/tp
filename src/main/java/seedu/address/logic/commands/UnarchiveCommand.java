package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ARCHIVED_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ArchiveCommand.ArchivePersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.OrderTracker;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class UnarchiveCommand extends Command {

    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": unrchive the person identified "
            + "by the index number used in the displayed person list. "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_ARCHIVE_PERSON_SUCCESS = "Unarchived Person: %1$s";
    public static final String MESSAGE_UNARCHIVE_PERSON_SUCCESS = "Unarchived Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_NOT_ARCHIVED = "At least one field to archive must be provided.";

    private final Index index;
    private final ArchivePersonDescriptor archivePersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param archivePersonDescriptor details to edit the person with
     */
    public UnarchiveCommand(Index index, ArchivePersonDescriptor archivePersonDescriptor) {
        requireNonNull(index);
        requireNonNull(archivePersonDescriptor);

        this.index = index;
        this.archivePersonDescriptor = new ArchivePersonDescriptor(archivePersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToArchive = lastShownList.get(index.getZeroBased());
        Person archivedPerson = createArchivedPerson(personToArchive, archivePersonDescriptor);

        if (!personToArchive.isSamePerson(archivedPerson) && model.hasPerson(archivedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.setPerson(personToArchive, archivedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_ARCHIVED_PERSONS);
        return new CommandResult(String.format(MESSAGE_ARCHIVE_PERSON_SUCCESS, Messages.format(archivedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createArchivedPerson(Person personToEdit, ArchivePersonDescriptor archivePersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = archivePersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = archivePersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = archivePersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = archivePersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        PostalCode updatedPostalCode = archivePersonDescriptor.getPostalCode().orElse(personToEdit.getPostalCode());
        Set<Tag> updatedTags = archivePersonDescriptor.getTags().orElse(personToEdit.getTags());
        Boolean updatedIsArchive = archivePersonDescriptor.getIsArchived().orElse(personToEdit.isArchived());
        OrderTracker updatedTracker = personToEdit.getOrderTracker();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedPostalCode, updatedTags,
                updatedTracker, updatedIsArchive);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnarchiveCommand)) {
            return false;
        }

        UnarchiveCommand otherArchiveCommand = (UnarchiveCommand) other;
        return index.equals(otherArchiveCommand.index)
                && archivePersonDescriptor.equals(otherArchiveCommand.archivePersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("archivePersonDescriptor", archivePersonDescriptor)
                .toString();
    }

}

