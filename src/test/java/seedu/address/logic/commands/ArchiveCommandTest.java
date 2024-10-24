package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ArchiveCommandTest {

    private static final Path archivePath = Paths.get("archiveTest", "archiveTest.json");
    private static final UserPrefs prefs = new UserPrefs();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager(new AddressBook(), prefs);
        Model expectedModel = new ModelManager();
        assertCommandSuccess(new ArchiveCommand(archivePath), model,
                String.format(ArchiveCommand.MESSAGE_SUCCESS, archivePath), expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), prefs);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), prefs);
        expectedModel.setAddressBook(new AddressBook());
        assertCommandSuccess(new ArchiveCommand(archivePath), model,
                String.format(ArchiveCommand.MESSAGE_SUCCESS, archivePath), expectedModel);
    }

    @Test
    public void equal() {
        Path path1 = Paths.get("mybook.json");
        Path path2 = Paths.get("yourbook.json");
        ArchiveCommand command1 = new ArchiveCommand(path1);
        ArchiveCommand command2 = new ArchiveCommand(path1);
        ArchiveCommand command3 = new ArchiveCommand(path2);
        assertTrue(command1.equals(command1));
        assertTrue(command1.equals(command2));
        assertFalse(command1.equals(command3));
        assertFalse(command1.equals(1));
    }

}
