package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Removes a tag associated with an existing person in the address book.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid.";
    public static final String MESSAGE_TAG_NOT_FOUND = "One or more specified tags do not exist in the model.";
    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag(s) %1$s to %2$s.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds one or multiple tags to the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "t/[TAG]... (can specify multiple tags)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "t/florist t/photographer.";

    private final Index index;
    private final List<Tag> tagsToAdd;


    /**
     * Constructs a TagCommand to add tags to a person.
     *
     * @param index The index of the person in the person list.
     * @param tagsToAdd The list of tags to be added.
     */
    public TagCommand(Index index, List<Tag> tagsToAdd) {
        this.index = index;
        this.tagsToAdd = tagsToAdd;
    }

    /**
     * Generates a command execution success message showing the added tags and the person.
     *
     * @param personToEdit The person to whom the tags were added.
     * @return A success message indicating the tags that were added and the name of the person.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String addedTags = tagsToAdd.stream()
                .map(tag -> tag.toString().replaceAll("[\\[\\]]", ""))
                .collect(Collectors.joining(", "));
        return String.format(MESSAGE_ADD_TAG_SUCCESS, addedTags, personToEdit.getName().toString());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        for (Tag tag : tagsToAdd) {
            if (!model.hasTag(tag)) {
                throw new CommandException(MESSAGE_TAG_NOT_FOUND);
            }
        }

        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        updatedTags.addAll(tagsToAdd);

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                updatedTags);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand otherCommand = (TagCommand) other;
        return index.equals(otherCommand.index)
                && tagsToAdd.equals(otherCommand.tagsToAdd);
    }


}
