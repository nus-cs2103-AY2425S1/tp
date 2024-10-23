package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Untags a guest identified using it's displayed index from the address book with a tag already associated with guest.
 */
public class UntagCommand extends Command {

    public static final String COMMAND_WORD = "untag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Untags a tag from the person identified by the index number used in the displayed person list. \n "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "[" + PREFIX_TAG + "TAG]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "bride's side";

    public static final String MESSAGE_UNTAG_PERSON_SUCCESS = "Untagged Person: %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "This person does not have this tag.";

    private final Index targetIndex;
    private final Tag tag;

    /**
     * @param targetIndex of the person in the filtered person list to untag
     * @param tag to remove from the person
     */
    public UntagCommand(Index targetIndex, Tag tag) {
        requireNonNull(targetIndex);
        requireNonNull(tag);

        this.targetIndex = targetIndex;
        this.tag = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUntag = lastShownList.get(targetIndex.getZeroBased());

        if (!personToUntag.getTags().contains(tag)) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }

        Set<Tag> newTags = new HashSet<>(personToUntag.getTags());
        newTags.remove(tag);

        Person updatedPerson = new Person(personToUntag.getName(), personToUntag.getPhone(),
                personToUntag.getEmail(), personToUntag.getRsvpStatus(), newTags);
        model.setPerson(personToUntag, updatedPerson);

        return new CommandResult(String.format(MESSAGE_UNTAG_PERSON_SUCCESS, Messages.format(updatedPerson)));
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

        UntagCommand otherTagCommand = (UntagCommand) other;
        return targetIndex.equals(otherTagCommand.targetIndex) && tag.equals(otherTagCommand.tag);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("tag", tag)
                .toString();
    }
}
