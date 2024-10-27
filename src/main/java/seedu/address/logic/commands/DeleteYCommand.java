// src/main/java/seedu/address/logic/commands/DeleteYCommand.java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import seedu.address.logic.StaticContext;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * Confirms the deletion of a person from the address book or a wedding from the wedding book.
 */
public class DeleteYCommand extends Command {

    public static final String COMMAND_WORD = "delete-y";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person:\n%1$s";
    public static final String MESSAGE_DELETE_WEDDING_SUCCESS = "Deleted Wedding:\n%1$s";

    private final Person personToDelete;
    private final Wedding weddingToDelete;

    /**
     * Creates a DeleteYCommand to delete the specified {@code Person} once the confirmation is given.
     * @param personToDelete
     */
    public DeleteYCommand(Person personToDelete) {
        this.personToDelete = personToDelete;
        this.weddingToDelete = null;
    }

    /**
     * Creates a DeleteYCommand to delete the specified {@code Wedding} once the confirmation is given.
     * @param weddingToDelete
     */
    public DeleteYCommand(Wedding weddingToDelete) {
        this.weddingToDelete = weddingToDelete;
        this.personToDelete = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!(weddingToDelete == null)) {
            deleteTagsWithWedding(model);
            model.deleteWedding(weddingToDelete);
            // Clear history of weddingToDelete from StaticContext once delete operation is done
            StaticContext.setWeddingToDelete(null);
            return new CommandResult(String.format(MESSAGE_DELETE_WEDDING_SUCCESS, weddingToDelete.getWeddingName()));
        }

        model.deletePerson(personToDelete);
        // Clear history of personToDelete from StaticContext once delete operation is done
        StaticContext.setPersonToDelete(null);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    /**
     * Deletes the tag of the wedding from the participants associated in the wedding to be deleted.
     */
    private void deleteTagsWithWedding(Model model) {
        Set<Person> weddingParticipants = weddingToDelete.getParticipants();
        for (Person participant : weddingParticipants) {
            participant.getTags().removeIf(tag -> tag.getTagName().equals(weddingToDelete.getWeddingName().toString()));
            Person newPerson = new Person(
                    participant.getName(), participant.getPhone(), participant.getEmail(),
                    participant.getAddress(), participant.getJob(),
                    participant.getTags());
            model.setPerson(participant, newPerson);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteYCommand)) {
            return false;
        }

        DeleteYCommand otherDeleteYCommand = (DeleteYCommand) other;

        if (weddingToDelete != null) {
            return weddingToDelete.equals(otherDeleteYCommand.weddingToDelete);
        }
        return personToDelete.equals(otherDeleteYCommand.personToDelete);
    }
}
