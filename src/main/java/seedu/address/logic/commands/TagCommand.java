package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            + ": Tags the guest identified by the index number used in the displayed guest list "
                + "with predefined tag(s). \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "[" + PREFIX_TAG + "TAG]... (must be created using 'newtag' command first)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "bride's side";

    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Tagged guest: %1$s";
    public static final String MESSAGE_TAG_NOT_CREATED = "Tag must be created first using 'newtag' command: ";
    public static final String MESSAGE_DUPLICATE_TAG = "This guest already has tag(s): ";

    private final Index targetIndex;
    private final Set<Tag> tags;

    /**
     * @param targetIndex of the person in the filtered person list to tag
     * @param tags set of tags to tag the person with
     */
    public TagCommand(Index targetIndex, Set<Tag> tags) {
        requireNonNull(targetIndex);
        requireNonNull(tags);

        this.targetIndex = targetIndex;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToTag = lastShownList.get(targetIndex.getZeroBased());
        Set<Tag> newTags = new HashSet<>(personToTag.getTags());
        Set<Tag> missingTags = new HashSet<>();
        Set<Tag> duplicateTags = new HashSet<>();
        Set<Tag> toAddTags = new HashSet<>();

        for (Tag tag : tags) {
            if (!model.hasTag(tag)) {
                missingTags.add(tag);
            } else if (newTags.contains(tag)) {
                duplicateTags.add(tag);
            } else {
                newTags.add(tag);
                toAddTags.add(tag);
            }
        }

        StringBuilder errorMessages = new StringBuilder();
        if (!missingTags.isEmpty()) {
            String missingTagsString = missingTags.stream().map(Tag::toString).collect(Collectors.joining(", "));
            errorMessages.append(MESSAGE_TAG_NOT_CREATED).append(missingTagsString);
        }
        if (!duplicateTags.isEmpty()) {
            if (errorMessages.length() > 0) {
                errorMessages.append("\n");
            }
            String duplicateTagsString = duplicateTags.stream().map(Tag::toString).collect(Collectors.joining(", "));
            System.out.println(duplicateTagsString);
            errorMessages.append(MESSAGE_DUPLICATE_TAG).append(duplicateTagsString);
            System.out.println(errorMessages);
        }

        if (!toAddTags.isEmpty()) {
            Person updatedPerson = new Person(personToTag.getName(), personToTag.getPhone(),
                    personToTag.getEmail(), personToTag.getRsvpStatus(), newTags);
            model.setPerson(personToTag, updatedPerson);
            String successMessage = String.format(MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedPerson));
            if (errorMessages.length() > 0) {
                successMessage += "\n" + errorMessages.toString();
            }
            return new CommandResult(successMessage);
        } else {
            throw new CommandException(errorMessages.toString());
        }
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
        return targetIndex.equals(otherTagCommand.targetIndex) && tags.equals(otherTagCommand.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("tags", tags)
                .toString();
    }
}
