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
 * Adds a tag to a person in the address book.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag to the person "
            + "identified "
            + "by the index number used in the displayed person list. "
            + "Tag will be appended to existing tags.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + CliSyntax.PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + CliSyntax.PREFIX_TAG + "high priority";

    public static final String MESSAGE_SUCCESS = "New tag: %1$s added to person %2$s";

    private final Index targetIndex;
    private final Tag tag;

    /**
     * @param index Index of the person in the filtered person list to add tag
     * @param tag Tag to be added to the person
     */
    public TagCommand(Index index, Tag tag) {
        requireAllNonNull(index, tag);
        this.targetIndex = index;
        this.tag = tag;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToAddTag} and
     * added tag
     */
    private static Person createPersonWithNewTag(Person personToAddTag, Tag newTag) {
        requireAllNonNull(personToAddTag, newTag);

        Name name = personToAddTag.getName();
        Phone phone = personToAddTag.getPhone();
        Email email = personToAddTag.getEmail();
        Address address = personToAddTag.getAddress();
        ArrayList<Log> logs = new ArrayList<>(personToAddTag.getLogs());
        Set<Tag> updatedTags = new HashSet<Tag>(personToAddTag.getTags());
        AppointmentDate appointmentDate = personToAddTag.getAppointmentDate();
        updatedTags.add(newTag);

        return new Person(name, phone, email, address, updatedTags, logs, appointmentDate);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddTag = lastShownList.get(targetIndex.getZeroBased());
        Person personWithTagAdded = createPersonWithNewTag(personToAddTag, this.tag);

        model.setPerson(personToAddTag, personWithTagAdded);
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.tag,
                personToAddTag.getName()),
                false, false, targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCommand)) {
            return false;
        }
        TagCommand otherTagCommand = (TagCommand) other;

        return targetIndex.equals(otherTagCommand.targetIndex)
                && tag.equals(otherTagCommand.tag);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("tag", tag)
                .toString();
    }

}
