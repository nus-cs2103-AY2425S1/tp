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
            + ": Tags the guest(s) identified by the index number(s) used in the displayed guest list "
                + "with the predefined tag(s). \n"
            + "Parameters: INDEX... (must be a positive integer(s))\n"
            + "[" + PREFIX_TAG + "TAG]... (must be created using 'newtag' command first)\n"
            + "Example: " + COMMAND_WORD + " 1 2 "
            + PREFIX_TAG + "bride's side" + " "
            + PREFIX_TAG + "groom's side";

    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Tagged guest(s):\n";
    public static final String MESSAGE_TAG_NOT_CREATED = "Tag(s) must be created first using 'newtag' command: ";
    public static final String MESSAGE_DUPLICATE_TAG = "Some guest(s) already have tag(s): ";

    private final List<Index> targetIndexes;
    private final Set<Tag> tags;

    /**
     * @param targetIndexes of the guest in the filtered person list to tag
     * @param tags set of tags to tag the guest with
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
            boolean isUpdated = false;

            for (Tag tag : tags) {
                if (!model.hasTag(tag)) {
                    missingTags.add(tag);
                } else if (newTags.contains(tag)) {
                    duplicateTags.add(tag);
                } else {
                    newTags.add(tag);
                    isUpdated = true;
                }
            }

            if (isUpdated) {
                Person updatedPerson = setPerson(model, personToTag, newTags);
                updateSuccessMessage(successMessage, updatedPerson);
            }
        }
        if (!successMessage.isEmpty()) {
            finalMessage.append(MESSAGE_TAG_PERSON_SUCCESS).append(successMessage);
        }
        if (!missingTags.isEmpty() || !duplicateTags.isEmpty()) {
            updateFinalMessage(finalMessage, duplicateTags, missingTags);
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

    private Person setPerson(Model model, Person personToTag, Set<Tag> newTags) {
        Person updatedPerson = new Person(personToTag.getName(), personToTag.getPhone(),
                personToTag.getEmail(), personToTag.getRsvpStatus(), newTags);
        model.setPerson(personToTag, updatedPerson);
        return updatedPerson;
    }

    private void updateSuccessMessage(StringBuilder successMessage, Person updatedPerson) {
        if (!successMessage.isEmpty()) {
            successMessage.append("\n");
        }
        successMessage.append(Messages.format(updatedPerson));
    }

    private void updateFinalMessage(StringBuilder finalMessage, Set<Tag> duplicateTags, Set<Tag> missingTags) {
        if (!finalMessage.isEmpty()) {
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
}
