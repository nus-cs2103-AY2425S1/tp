package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class LoadCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        assertCommandSuccess(new LoadCommand(Paths.get("my.json")), model,
                String.format(LoadCommand.MESSAGE_SUCCESS, Paths.get("my.json")), expectedModel);
    }
    @Test
    public void otherUtilityTest() {
        Path path = Paths.get("Test.json");
        LoadCommand command = new LoadCommand(path);
        assertEquals(command.getLoadPath(), path);
    }

    @Test
    public void equal() {
        Path path1 = Paths.get("mybook.json");
        Path path2 = Paths.get("yourbook.json");
        LoadCommand command1 = new LoadCommand(path1);
        LoadCommand command2 = new LoadCommand(path1);
        LoadCommand command3 = new LoadCommand(path2);
        assertTrue(command1.equals(command1));
        assertTrue(command1.equals(command2));
        assertFalse(command1.equals(command3));
        assertFalse(command1.equals(1));
    }


}
