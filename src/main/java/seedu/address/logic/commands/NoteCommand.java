package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.ui.Ui.UiState;

/**
 * * Changes the notes of an existing person in the address book.
 */
public class NoteCommand extends Command {

    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the note of the person identified "
            + "by their name (must already exist in the address book). "
            + "Existing note will be overwritten by the input.\n"
            + "Parameters: " + PREFIX_NAME + "NAME "
            + PREFIX_NOTE + "NOTE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_NOTE + "Very mischievous.";

    public static final String MESSAGE_ADD_NOTE_SUCCESS = "Added note to Person: %1$s";
    public static final String MESSAGE_DELETE_NOTE_SUCCESS = "Removed note from Person: %1$s";

    private final Name name;
    private final Note note;

    /**
     * @param name of the person to edit the notes
     * @param note of the person to be updated to
     */
    public NoteCommand(Name name, Note note) {
        requireAllNonNull(name, note);

        this.name = name;
        this.note = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> allStudents = model.getAddressBook().getPersonList();
        Person personToEdit = null;

        for (Person person : allStudents) {
            if (person.getName().equals(name)) {
                personToEdit = person;
            }
        }

        if (personToEdit == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME);
        }

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmergencyContact(),
                personToEdit.getAddress(), note, personToEdit.getSubjects(), personToEdit.getLevel(),
                personToEdit.getTaskList(), personToEdit.getLessonTimes());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson), UiState.DETAILS);
    }

    /**
     * Generates a command execution success message based on whether the note is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !note.value.isEmpty() ? MESSAGE_ADD_NOTE_SUCCESS : MESSAGE_DELETE_NOTE_SUCCESS;
        return String.format(message, Messages.format(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteCommand)) {
            return false;
        }

        NoteCommand e = (NoteCommand) other;
        return name.equals(e.name)
                && note.equals(e.note);
    }

}
