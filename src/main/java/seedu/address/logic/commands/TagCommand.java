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

    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Tagged guest(s):\n";
    public static final String MESSAGE_TAG_NOT_CREATED = "Tag(s) must be created first using 'newtag' command: ";
    public static final String MESSAGE_DUPLICATE_TAG = "Some guest(s) already have tag(s): ";

    private final List<Index> targetIndexes;
    private final Set<Tag> tags;

    /**
     * @param targetIndexes of the person in the filtered person list to tag
     * @param tags set of tags to tag the person with
     */
    public TagCommand(List<Index> targetIndexes, Set<Tag> tags) {
        requireNonNull(targetIndexes);
        requireNonNull(tags);

        this.targetIndexes = targetIndexes;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Set<Tag> missingTags = new HashSet<>();
        Set<Tag> duplicateTags = new HashSet<>();
        StringBuilder successMessage = new StringBuilder();
        StringBuilder finalMessage = new StringBuilder();

        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            Person personToTag = lastShownList.get(targetIndex.getZeroBased());
            Set<Tag> newTags = new HashSet<>(personToTag.getTags());
            boolean updated = false;

            for (Tag tag : tags) {
                if (!model.hasTag(tag)) {
                    missingTags.add(tag);
                } else if (newTags.contains(tag)) {
                    duplicateTags.add(tag);
                } else {
                    newTags.add(tag);
                    updated = true;
                }
            }

            if (updated) {
                Person updatedPerson = new Person(personToTag.getName(), personToTag.getPhone(),
                        personToTag.getEmail(), personToTag.getRsvpStatus(), newTags);
                model.setPerson(personToTag, updatedPerson);
                if (successMessage.length() > 0) {
                    successMessage.append("\n");
                }
                successMessage.append(Messages.format(updatedPerson));
            }
        }
        if (successMessage.length() > 0) {
            finalMessage.append(MESSAGE_TAG_PERSON_SUCCESS).append(successMessage);
        }
        if (!missingTags.isEmpty() || !duplicateTags.isEmpty()) {
            if (finalMessage.length() > 0) {
                finalMessage.append("\n");
            }
            if (!missingTags.isEmpty()) {
                finalMessage.append(MESSAGE_TAG_NOT_CREATED)
                        .append(missingTags.stream()
                                .map(Tag::toString)
                                .collect(Collectors.joining(", ")));
            }
            if (!duplicateTags.isEmpty()) {
                if (!missingTags.isEmpty()) {
                    finalMessage.append("\n");
                }
                finalMessage.append(MESSAGE_DUPLICATE_TAG)
                        .append(duplicateTags.stream()
                                .map(Tag::toString)
                                .collect(Collectors.joining(", ")));
            }
        }
        return new CommandResult(finalMessage.toString());
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
        return targetIndexes.equals(otherTagCommand.targetIndexes) && tags.equals(otherTagCommand.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndexes", targetIndexes)
                .add("tags", tags)
                .toString();
    }
}
