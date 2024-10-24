package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Command to remove tags from a person.
 */
public class UntagCommand extends Command {

    public static final String COMMAND_WORD = "untag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": untags specific contact \n"
            + "Parameters: 'index t/[TAGS TO REMOVE]' OR 'index t/all' (this removes everything)\n"
            + "Examples: " + COMMAND_WORD + " 1 t/friends colleagues," + " " + COMMAND_WORD + " 1 t/all";

    public static final String MESSAGE_UNTAG_SUCCESS = "Untagged the person: %1$s";
    private final Index index;

    private final Set<Tag> tagsToRemove;

    /**
     * Constructs an UntagCommand to remove tags from the person at the specified index.
     * @param index the index of the person in the filtered person list
     * @param tagsToRemove the set of tags to be removed from the person
     */
    public UntagCommand(Index index, Set<Tag> tagsToRemove) {
        requireNonNull(index);
        //requireNonNull(tagsToRemove);

        this.index = index;
        this.tagsToRemove = tagsToRemove;
    }

    /**
     * Executes the untagging command on the given model.
     * @param model the model on which the command is executed
     * @return the result of the untagging command
     * @throws CommandException if the index is invalid or other issues arise
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size() || index.getZeroBased() < 0) {
            throw new CommandException("Error: " + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX + ".");
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        Set<Tag> tagsToRemoveFromPerson;
        if (this.tagsToRemove == null) {
            tagsToRemoveFromPerson = new HashSet<>(personToEdit.getTags());
        } else {
            tagsToRemoveFromPerson = this.tagsToRemove;
        }

        Person editedPerson = new Person(personToEdit.getId(), personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), this.removeTags(personToEdit.getTags(), tagsToRemoveFromPerson));

        model.setPerson(personToEdit, editedPerson);
        model.getActiveTags().decrementTag(tagsToRemoveFromPerson);

        return new CommandResult(generateSuccessMessage(editedPerson, tagsToRemoveFromPerson));
    }

    /**
     * Removes the specified tags from the original tags of the person.
     * @param originalTags the original set of tags
     * @param tagsToRemove the set of tags to remove
     * @return the modified set of tags
     * @throws CommandException if any tag in tagsToRemove does not exist in originalTags
     */
    public Set<Tag> removeTags(Set<Tag> originalTags, Set<Tag> tagsToRemove) throws CommandException {
        Set<Tag> modifiedTags = new HashSet<>(originalTags);

        Set<Tag> nonExistentTags = tagsToRemove.stream()
                .filter(tag -> !originalTags.contains(tag))
                .collect(Collectors.toSet());

        if (modifiedTags.isEmpty()) {
            throw new CommandException("Error: No tags to remove from this person.");
        }

        if (!nonExistentTags.isEmpty()) {
            String nonExistentTagsString = nonExistentTags.stream()
                    .map(tag -> tag.tagName)
                    .collect(Collectors.joining(", "));
            throw new CommandException("Error: The following tags do not exist: " + nonExistentTagsString);
        }

        modifiedTags.removeAll(tagsToRemove);
        return modifiedTags;
    }

    /**
     * Generates a success message after tags are removed.
     * @param personToEdit the person being edited
     * @param removedTags the tags that were removed
     * @return the success message
     */
    private String generateSuccessMessage(Person personToEdit, Set<Tag> removedTags) {
        String removedTagsString = removedTags.isEmpty()
                ? "no tags"
                : String.join(", ", removedTags.stream()
                .map(tag -> "[" + tag.tagName + "]")
                .collect(Collectors.joining(", ")));

        return String.format(MESSAGE_UNTAG_SUCCESS + ". Tags removed: %2$s",
                personToEdit.getName().toString(), removedTagsString);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UntagCommand)) {
            return false;
        }

        // state check
        UntagCommand e = (UntagCommand) other;
        return index.equals(e.index)
                && (tagsToRemove == null ? e.tagsToRemove == null : tagsToRemove.equals(e.tagsToRemove));
    }

}
