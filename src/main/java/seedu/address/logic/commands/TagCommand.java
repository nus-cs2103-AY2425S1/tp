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
 * Tags a guest identified using it's displayed index from the address book with a tag already created.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Tags the person identified by the index number used in the displayed person list "
                + "with a predefined tag. \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "[" + PREFIX_TAG + "TAG] (must be defined using 'newtag' command first\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "bride's side";

    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Tagged Person: %1$s";
    public static final String MESSAGE_TAG_NOT_CREATED = "Tag must be created first using (newtag) command. \n";
    public static final String MESSAGE_DUPLICATE_TAG = "This person already has this tag.";

    private final Index targetIndex;
    private final Tag tag;

    /**
     * @param targetIndex of the person in the filtered person list to tag
     * @param tag to tag the person with
     */
    public TagCommand(Index targetIndex, Tag tag) {
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

        Person personToTag = lastShownList.get(targetIndex.getZeroBased());

        if (!model.hasTag(tag)) {
            throw new CommandException(tag + " " + MESSAGE_TAG_NOT_CREATED);
        }

        if (personToTag.getTags().contains(tag)) {
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
        }

        Set<Tag> newTags = new HashSet<>(personToTag.getTags());
        newTags.add(tag);

        Person updatedPerson = new Person(personToTag.getName(), personToTag.getPhone(),
                personToTag.getEmail(), personToTag.getRsvp(), newTags);
        model.setPerson(personToTag, updatedPerson);

        return new CommandResult(String.format(MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedPerson)));
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
