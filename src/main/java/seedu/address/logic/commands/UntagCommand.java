package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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
            + "Parameters: 'index t/ [TAG TO REMOVE]' OR 'index t/ all' (this removes everything)\n"
            + "Examples: " + COMMAND_WORD + "1 t/ friends," + " " + COMMAND_WORD + " 1 t/ all";

    public static final String MESSAGE_UNTAG_SUCCESS = "Untagged the person: %1$s";
    private final Index index;

    private final Set<Tag> tagsToRemove;

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

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        Set<Tag> tagsToRemoveFromPerson;
        if (this.tagsToRemove == null) {
            tagsToRemoveFromPerson = new HashSet<>(personToEdit.getTags());
        } else {
            tagsToRemoveFromPerson = this.tagsToRemove;
        }

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), this.removeTags(personToEdit.getTags(), tagsToRemoveFromPerson));

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson, tagsToRemoveFromPerson));
    }

    /**
     * Removes the specified tags from the original tags of the person.
     * @param originalTags the original set of tags
     * @param tagsToRemove the set of tags to remove
     * @return the modified set of tags
     */
    public Set<Tag> removeTags(Set<Tag> originalTags, Set<Tag> tagsToRemove) {
        Set<Tag> modifiedTags = new HashSet<>(originalTags);
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
                .map(tag -> tag.tagName)
                .collect(Collectors.joining(", ")));

        return String.format(MESSAGE_UNTAG_SUCCESS + ". Tags removed: [%2$s]",
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
                && tagsToRemove.equals(e.tagsToRemove);
    }

}
