package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonTaggedWithPredicate;
import seedu.address.model.tag.Tag;

public class UntagCommand extends Command{

    public static final String COMMAND_WORD = "untag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": untags specific contact \n"
            + "Parameters: 'index t/ [TAG TO REMOVE]' OR 'index t/ all' (this removes everything)\n"
            + "Examples: " + COMMAND_WORD + "1 t/ friends" + " " + COMMAND_WORD + " 1 t/ all";

    public static final String MESSAGE_UNTAG_SUCCESS = "Untagged the person: %1$s";
    private final Index index;

    private final Set<Tag> tagsToRemove;

    public UntagCommand(Index index, Set<Tag> tagsToRemove) {
        requireNonNull(index);
        //requireNonNull(tagsToRemove);

        this.index = index;
        this.tagsToRemove = tagsToRemove;
    }

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

    public Set<Tag> removeTags(Set<Tag> originalTags, Set<Tag> tagsToRemove) {
        Set<Tag> modifiedTags = new HashSet<>(originalTags);
        modifiedTags.removeAll(tagsToRemove);
        return modifiedTags;
    }

    /**
     * Generates a command execution success message when the person has been untagged
     * {@code personToEdit}.
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
