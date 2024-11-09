package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
public class TagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Tags the guest(s) identified by the index number(s) used in the displayed guest list "
                + "with the predefined tag(s). \n"
            + "Parameters: INDEX... (must be within the minimum and maximum list indexes shown)\n"
            + "[" + PREFIX_TAG + "TAG]... Maximum of 50 alphanumeric characters, spaces, parenthesis"
            + " and apostrophes per tag. (must be created using 'newtag' command first)\n"
            + "Example: " + COMMAND_WORD + " 1 2 "
            + PREFIX_TAG + "bride's side" + " "
            + PREFIX_TAG + "groom's side";

    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Tagged guest(s):\n";
    public static final String MESSAGE_TAG_NOT_CREATED = "Tag(s) must be created first using 'newtag' command: ";
    public static final String MESSAGE_DUPLICATE_TAG = "Some guest(s) already have tag(s): ";
    public static final String MESSAGE_INVALID_INDEX = "ERROR: Please enter a valid index (from 1 to ";

    private final List<Index> targetIndexes;
    private final Set<Tag> tags;
    private final Map<Index, Set<Tag>> addedTagsMap = new HashMap<>();
    private Set<Tag> missingTags = new HashSet<>();
    private Set<Tag> duplicateTags = new HashSet<>();
    private StringBuilder successMessage = new StringBuilder();
    private StringBuilder finalMessage = new StringBuilder();

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

        tagPersons(model, targetIndexes, lastShownList);
        if (!successMessage.isEmpty()) {
            finalMessage.append(MESSAGE_TAG_PERSON_SUCCESS).append(successMessage);
        }
        if (!missingTags.isEmpty() || !duplicateTags.isEmpty()) {
            updateFinalMessage();
        }
        return new CommandResult(finalMessage.toString());
    }

    @Override
    public void undo(Model model) {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        for (Map.Entry<Index, Set<Tag>> entry: addedTagsMap.entrySet()) {
            Index target = entry.getKey();
            Person personToUntag = lastShownList.get(target.getZeroBased());
            Set<Tag> restoredTags = new HashSet<>(personToUntag.getTags());
            restoredTags.removeAll(entry.getValue());

            Person restoredPerson = new Person(personToUntag.getName(), personToUntag.getPhone(),
                    personToUntag.getEmail(), personToUntag.getRsvpStatus(), restoredTags);
            model.setPerson(personToUntag, restoredPerson);
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

    private void updateFinalMessage() {
        if (!finalMessage.isEmpty()) {
            finalMessage.append("\n");
        }
        if (!missingTags.isEmpty()) {
            finalMessage.append(MESSAGE_TAG_NOT_CREATED)
                    .append(missingTags.stream().map(Tag::toString).collect(Collectors.joining(", ")));
        }
        if (duplicateTags.isEmpty()) {
            return;
        }

        if (!missingTags.isEmpty()) {
            finalMessage.append("\n");
        }
        finalMessage.append(MESSAGE_DUPLICATE_TAG)
                .append(duplicateTags.stream().map(Tag::toString).collect(Collectors.joining(", ")));
    }

    private void tagPersons(Model model, List<Index> targetIndexes, List<Person> lastShownList)
            throws CommandException {
        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(String.format(TagCommand.MESSAGE_INVALID_INDEX
                        + model.getFilteredPersonList().size() + ")"));
            }
            Person personToTag = lastShownList.get(targetIndex.getZeroBased());
            Set<Tag> newTags = new HashSet<>(personToTag.getTags());
            Set<Tag> addedTags = new HashSet<>();

            boolean isUpdated = updateTags(model, newTags, addedTags);
            if (isUpdated) {
                updatePerson(model, personToTag, newTags, targetIndex, addedTags);
            }
        }
    }

    private boolean updateTags(Model model, Set<Tag> newTags, Set<Tag> addedTags) {
        boolean isUpdated = false;
        for (Tag tag : tags) {
            if (!model.hasTag(tag)) {
                missingTags.add(tag);
            } else if (newTags.contains(tag)) {
                duplicateTags.add(tag);
            } else {
                newTags.add(tag);
                addedTags.add(tag);
                isUpdated = true;
            }
        }
        return isUpdated;
    }

    private void updatePerson(Model model, Person personToTag, Set<Tag> newTags, Index targetIndex,
                              Set<Tag> addedTags) {
        Person updatedPerson = setPerson(model, personToTag, newTags);
        addedTagsMap.put(targetIndex, addedTags);
        updateSuccessMessage(successMessage, updatedPerson);
    }
}
