package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
public class InfoCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new CommandHistory());

    @Test
    public void execute_validIndex_success() throws CommandException {
        InfoCommand infoCommand = new InfoCommand(INDEX_FIRST_PERSON);
        CommandResult commandResult = infoCommand.execute(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToShowInfo = lastShownList.get(INDEX_FIRST_PERSON.getZeroBased());
        assertEquals(new CommandResult(String.format(InfoCommand.MESSAGE_INFO_PERSON_SUCCESS
                + personToShowInfo.getName()), false, false, false, true, false), commandResult);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        InfoCommand infoCommand = new InfoCommand(outOfBoundIndex);
        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> infoCommand.execute(model));
    }

    @Test
    public void equals() {
        InfoCommand infoFirstCommand = new InfoCommand(INDEX_FIRST_PERSON);
        InfoCommand infoSecondCommand = new InfoCommand(INDEX_SECOND_PERSON);


        assertTrue(infoFirstCommand.equals(infoFirstCommand));


        InfoCommand infoFirstCommandCopy = new InfoCommand(INDEX_FIRST_PERSON);
        assertTrue(infoFirstCommand.equals(infoFirstCommandCopy));


        assertFalse(infoFirstCommand.equals(1));


        assertFalse(infoFirstCommand.equals(null));


        assertFalse(infoFirstCommand.equals(infoSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        InfoCommand infoCommand = new InfoCommand(targetIndex);
        String expected = InfoCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, infoCommand.toString());
    }
}
