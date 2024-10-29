package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


public class ArchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new ArrayList<>(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getAddressBook(), new ArrayList<>(), new UserPrefs());

    @Test
    public void execute_archivePerson_success() throws Exception {
        Person personToArchive = model.getFilteredPersonList().get(0);
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON, true);

        Person archivedPerson = new PersonBuilder(personToArchive).withArchived(true).build();
        expectedModel.setPerson(personToArchive, archivedPerson);

        CommandResult result = archiveCommand.execute(model);

        assertEquals(String.format(ArchiveCommand.MESSAGE_ARCHIVE_PERSON_SUCCESS, Messages.format(archivedPerson)),
                result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_unarchivePerson_success() throws Exception {
        Person personToUnarchive = new PersonBuilder(model.getFilteredPersonList().get(0)).withArchived(true).build();
        model.setPerson(model.getFilteredPersonList().get(0), personToUnarchive);
        ArchiveCommand unarchiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON, false);

        Person unarchivedPerson = new PersonBuilder(personToUnarchive).withArchived(false).build();
        expectedModel.setPerson(personToUnarchive, unarchivedPerson);

        CommandResult result = unarchiveCommand.execute(model);

        assertEquals(String.format(ArchiveCommand.MESSAGE_UNARCHIVE_PERSON_SUCCESS, Messages.format(unarchivedPerson)),
                result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex, true);

        assertThrows(CommandException.class, () -> archiveCommand.execute(model),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personAlreadyArchived_throwsCommandException() {
        Person personToArchive = new PersonBuilder(model.getFilteredPersonList().get(0)).withArchived(true).build();
        model.setPerson(model.getFilteredPersonList().get(0), personToArchive);
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON, true);

        assertThrows(CommandException.class, () -> archiveCommand.execute(model),
                ArchiveCommand.MESSAGE_PERSON_IS_ALREADY_ARCHIVED);
    }

    @Test
    public void execute_personAlreadyUnarchived_throwsCommandException() {
        Person personToUnarchive = new PersonBuilder(model.getFilteredPersonList().get(0)).withArchived(false).build();
        model.setPerson(model.getFilteredPersonList().get(0), personToUnarchive);
        ArchiveCommand unarchiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON, false);

        assertThrows(CommandException.class, () -> unarchiveCommand.execute(model),
                ArchiveCommand.MESSAGE_PERSON_IS_ALREADY_UNARCHIVED);
    }

    @Test
    public void getPersonToModify_executed() throws Exception {
        Person personToModify = model.getFilteredPersonList().get(0);
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON, !personToModify.isArchived());
        archiveCommand.execute(model);
        assertEquals(personToModify, archiveCommand.getPersonToModify());
    }

    @Test
    public void getPersonToModify_notExecuted_null() {
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON, true);
        assertNull(archiveCommand.getPersonToModify());
    }

    @Test
    public void getCommandWord_isArchive_true() {
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON, true);
        assertEquals(ArchiveCommand.COMMAND_WORD_ARCHIVE, archiveCommand.getCommandWord());
    }

    @Test
    public void getCommandWord_isUnarchive_true() {
        ArchiveCommand unarchiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON, false);
        assertEquals(ArchiveCommand.COMMAND_WORD_UNARCHIVE, unarchiveCommand.getCommandWord());
    }
}
