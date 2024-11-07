package seedu.address.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_TAG_NOT_FOUND_IN_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Removes a tag associated with an existing person in the Wedlinker.
 */
public class UntagCommand extends Command {

    public static final String COMMAND_WORD = "untag";
    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed tag(s) %1$s from %2$s.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes one or multiple tags from the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "TAG... (can specify multiple tags)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "florist " + PREFIX_TAG + "photographer.";

    private final Index index;
    private final HashSet<Tag> tagsToRemove;

    /**
     * Constructs an UntagCommand to remove tags from a person.
     *
     * @param index The index of the person in the person list.
     * @param tagsToRemove The list of tags to be removed.
     */
    public UntagCommand(Index index, HashSet<Tag> tagsToRemove) {
        requireNonNull(index);
        requireNonNull(tagsToRemove);
        this.index = index;
        this.tagsToRemove = tagsToRemove;
    }

    /**
     * Generates a command execution success message showing the removed tags and the person.
     *
     * @param personToEdit The person from whom the tags were removed.
     * @return A success message indicating the tags that were removed and the name of the person.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String removedTags = tagsToRemove.stream()
                .map(tag -> tag.toString().replaceAll("[\\[\\]]", ""))
                .collect(Collectors.joining(", "));
        return String.format(MESSAGE_REMOVE_TAG_SUCCESS, removedTags, personToEdit.getName().toString());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());

        if (personToEdit.getTags().isEmpty()) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND_IN_CONTACT);
        }

        if (tagsToRemove.isEmpty()) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND_IN_CONTACT);
        }

        if (!updatedTags.containsAll(tagsToRemove)) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND_IN_CONTACT);
        }

        for (Tag tag : updatedTags) {
            if (tagsToRemove.contains(tag)) {
                tag.decreaseTaggedCount();
            }
        }

        updatedTags.removeAll(tagsToRemove);

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                updatedTags,
                personToEdit.getWeddings(),
                personToEdit.getTasks());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredTagList(Model.PREDICATE_SHOW_ALL_TAGS);

        return new CommandResult(generateSuccessMessage(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UntagCommand otherCommand)) {
            return false;
        }

        return index.equals(otherCommand.index)
                && tagsToRemove.equals(otherCommand.tagsToRemove);
    }


}
