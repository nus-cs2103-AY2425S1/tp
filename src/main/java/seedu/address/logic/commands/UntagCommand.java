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
 * Untags a guest identified using it's displayed index from the address book with a tag already associated with guest.
 */
public class UntagCommand extends Command {

    public static final String COMMAND_WORD = "untag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Untags the guest(s) identified by the index number(s) used in the displayed guest list "
                + "with the predefined tag(s). \n"
            + "Parameters: INDEX... (must be positive integer(s))\n"
            + "[" + PREFIX_TAG + "TAG]... (must be a tag on the guest)\n"
            + "Example: " + COMMAND_WORD + " 1 2 "
            + PREFIX_TAG + "bride's side" + " "
            + PREFIX_TAG + "groom's side";

    public static final String MESSAGE_UNTAG_PERSON_SUCCESS = "Untagged guest(s):\n";
    public static final String MESSAGE_TAG_NOT_FOUND = "Some guest(s) do not have the tag(s): ";

    private final List<Index> targetIndexes;
    private final Set<Tag> tags;

    /**
     * @param targetIndexes of the guest in the filtered person list to untag
     * @param tags set of tags to remove from the guest
     */
    public UntagCommand(List<Index> targetIndexes, Set<Tag> tags) {
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
        StringBuilder successMessage = new StringBuilder();
        StringBuilder finalMessage = new StringBuilder();

        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            Person personToUntag = lastShownList.get(targetIndex.getZeroBased());
            Set<Tag> newTags = new HashSet<>(personToUntag.getTags());
            boolean isUpdated = false;

            for (Tag tag : tags) {
                if (!newTags.contains(tag)) {
                    missingTags.add(tag);
                } else {
                    newTags.remove(tag);
                    isUpdated = true;
                }
            }

            if (isUpdated) {
                Person updatedPerson = setPerson(model, personToUntag, newTags);
                updateSuccessMessage(successMessage, updatedPerson);
            }
        }
        if (!successMessage.isEmpty()) {
            finalMessage.append(MESSAGE_UNTAG_PERSON_SUCCESS).append(successMessage);
        }
        if (!missingTags.isEmpty()) {
            updateFinalMessage(finalMessage, missingTags);
        }
        return new CommandResult(finalMessage.toString());
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
        return targetIndexes.equals(otherTagCommand.targetIndexes) && tags.equals(otherTagCommand.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndexes", targetIndexes)
                .add("tag", tags)
                .toString();
    }

    private Person setPerson(Model model, Person personToUntag, Set<Tag> newTags) {
        Person updatedPerson = new Person(personToUntag.getName(), personToUntag.getPhone(),
                personToUntag.getEmail(), personToUntag.getRsvpStatus(), newTags);
        model.setPerson(personToUntag, updatedPerson);
        return updatedPerson;
    }

    private void updateSuccessMessage(StringBuilder successMessage, Person updatedPerson) {
        if (!successMessage.isEmpty()) {
            successMessage.append("\n");
        }
        successMessage.append(Messages.format(updatedPerson));
    }

    private void updateFinalMessage(StringBuilder finalMessage, Set<Tag> missingTags) {
        if (!finalMessage.isEmpty()) {
            finalMessage.append("\n");
        }
        finalMessage.append(MESSAGE_TAG_NOT_FOUND)
                .append(missingTags.stream()
                        .map(Tag::toString)
                        .collect(Collectors.joining(", ")));
    }
}
