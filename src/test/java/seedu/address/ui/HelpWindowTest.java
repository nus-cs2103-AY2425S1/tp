package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.stage.Stage;

public class HelpWindowTest {

    @Test
    public void getAllCommands_returnsCorrectCommands() {
        String expectedCommands = "Here are the list of commands available:\n"
                + "1. add\n"
                + "2. delete\n"
                + "3. addtask\n"
                + "4. deletetask\n"
                + "5. emergency\n"
                + "6. priority\n"
                + "7. list\n"
                + "8. help\n"
                + "9. exit\n"
                + "10. find\n"
                + "11. clear\n";
        assertEquals(expectedCommands, HelpWindow.getAllCommands());
    }

    @Test
    public void constructor_validMainStage_createsHelpWindow() {
        Platform.startup(() -> {
            Stage mainStage = mock(Stage.class);

            HelpWindow helpWindow = new HelpWindow(mainStage);

            assertNotNull(helpWindow);
        });
    }
}
