package bizbook.logic.commands;

import static bizbook.commons.util.CollectionUtil.requireAllNonNull;
import static bizbook.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import bizbook.commons.core.index.Index;
import bizbook.logic.Messages;
import bizbook.logic.commands.exceptions.CommandException;
import bizbook.model.Model;
import bizbook.model.person.Address;
import bizbook.model.person.Email;
import bizbook.model.person.Name;
import bizbook.model.person.Note;
import bizbook.model.person.Person;
import bizbook.model.person.Phone;
import bizbook.model.tag.Tag;

/**
 * Delete a tag from an existing person in the address book.
 */
public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tag from a specfic person.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_TAG + "BusinessMan";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Tag was removed from Person: %1$s";
    public static final String TAG_DOES_NOT_EXIST = "Unable to delete %1$s "
            + "because the tag does not exist for the person.";

    private final Index personIndex;
    private final Tag tagToDelete;

    /**
     * @param personIndex of the person in the filtered person list to remove the tag from.
     * @param tag         is the name of the tag to be removed from the {@code Person}.
     */
    public DeleteTagCommand(Index personIndex, Tag tag) {
        requireAllNonNull(personIndex, tag);

        this.personIndex = personIndex;
        this.tagToDelete = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());
        Set<Tag> tagSet = personToEdit.getTags();

        if (!tagSet.contains(tagToDelete)) {
            throw new CommandException(String.format(TAG_DOES_NOT_EXIST, tagToDelete.tagName));
        }

        Set<Tag> updatedTagSet = tagSet.stream()
                .filter(t -> !t.equals(tagToDelete))
                .collect(Collectors.toSet());

        Name name = personToEdit.getName();
        Email email = personToEdit.getEmail();
        Phone phone = personToEdit.getPhone();
        Address address = personToEdit.getAddress();
        ArrayList<Note> notesList = personToEdit.getNotes();

        Person updatedPerson = new Person(name, phone, email, address, updatedTagSet, notesList);

        model.setPerson(personToEdit, updatedPerson);

        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS, Messages.format(updatedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        DeleteTagCommand otherDeleteTagCommand = (DeleteTagCommand) other;
        return this.personIndex.equals(otherDeleteTagCommand.personIndex)
                && this.tagToDelete.equals(otherDeleteTagCommand.tagToDelete);
    }

}
