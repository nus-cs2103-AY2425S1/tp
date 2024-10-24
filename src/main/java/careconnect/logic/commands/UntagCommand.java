package careconnect.logic.commands;

import static careconnect.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import careconnect.commons.core.index.Index;
import careconnect.commons.util.ToStringBuilder;
import careconnect.logic.Messages;
import careconnect.logic.commands.exceptions.CommandException;
import careconnect.logic.parser.CliSyntax;
import careconnect.model.Model;
import careconnect.model.log.Log;
import careconnect.model.person.Address;
import careconnect.model.person.AppointmentDate;
import careconnect.model.person.Email;
import careconnect.model.person.Name;
import careconnect.model.person.Person;
import careconnect.model.person.Phone;
import careconnect.model.tag.Tag;

/**
 * Deletes a tag from a person in the address book.
 */
public class UntagCommand extends Command {

    public static final String COMMAND_WORD = "untag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a tag from the person "
            + "identified "
            + "by the index number used in the displayed person list. "
            + "If tag is not found in person, a warning will be displayed\n"
            + "Parameters: INDEX (must be a positive integer) "
            + CliSyntax.PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + CliSyntax.PREFIX_TAG + "high priority";

    public static final String MESSAGE_SUCCESS = "Deleted tag: %1$s from person %2$s";

    private final Index targetIndex;
    private final Tag tag;

    /**
     * @param index Index of the person in the filtered person list to delete tag from
     * @param tag Tag to be deleted from the person
     */
    public UntagCommand(Index index, Tag tag) {
        requireAllNonNull(index, tag);
        this.targetIndex = index;
        this.tag = tag;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToDeleteTag} and
     * deleted tag
     */
    private static Person createPersonWithoutTag(
            Person personToDeleteTag, Tag tagToDelete) throws CommandException {
        requireAllNonNull(personToDeleteTag, tagToDelete);

        Name name = personToDeleteTag.getName();
        Phone phone = personToDeleteTag.getPhone();
        Email email = personToDeleteTag.getEmail();
        Address address = personToDeleteTag.getAddress();
        ArrayList<Log> logs = new ArrayList<>(personToDeleteTag.getLogs());
        Set<Tag> updatedTags = new HashSet<Tag>(personToDeleteTag.getTags());
        AppointmentDate appointmentDate = personToDeleteTag.getAppointmentDate();
        if (updatedTags.contains(tagToDelete)) {
            updatedTags.remove(tagToDelete);
        } else {
            throw new CommandException(String.format(Messages.MESSAGE_TAG_NOT_FOUND_IN_PERSON,
                    tagToDelete, personToDeleteTag.getName()));
        }

        return new Person(name, phone, email, address, updatedTags, logs, appointmentDate);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDeleteTag = lastShownList.get(targetIndex.getZeroBased());
        Person personWithoutTag = createPersonWithoutTag(personToDeleteTag, this.tag);

        model.setPerson(personToDeleteTag, personWithoutTag);
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.tag,
                personToDeleteTag.getName()),
                false, false, targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UntagCommand)) {
            return false;
        }
        UntagCommand otherUntagCommand = (UntagCommand) other;

        return targetIndex.equals(otherUntagCommand.targetIndex)
                && tag.equals(otherUntagCommand.tag);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("tag", tag)
                .toString();
    }

}
