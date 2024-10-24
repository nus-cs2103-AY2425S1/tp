package seedu.address.ui.commandpopup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class CommandTextFlowTest extends Application {

    private CommandTextFlow commandTextFlow;

    @Override
    public void start(Stage stage) {
        // Basic setup for the JavaFX test environment
        commandTextFlow = new CommandTextFlow("add", "add");
        Scene scene = new Scene(commandTextFlow, 300, 100);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        // Reset the CommandTextFlow object before each test
        commandTextFlow = new CommandTextFlow("add", "add");
    }

    @Test
    public void testCommandTextFlowConstruction() {
        assertNotNull(commandTextFlow);
        assertEquals("add", commandTextFlow.getCommandText());
        assertEquals("add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [g/GAME]... [t/TAG]... "
                + "[pt/PREFERRED TIME]...", commandTextFlow.getDetailText());
    }

    @Test
    public void testHighlightedCommandText() {
        // Check that the command is properly highlighted
        Text highlightedText = (Text) commandTextFlow.getChildren().get(0);
        assertEquals("add", highlightedText.getText());
        assertEquals("0xffa500ff", highlightedText.getFill().toString());
    }

    @Test
    public void testDetailText() {
        // Check that the detail text is correctly set
        Text detailText = (Text) commandTextFlow.getChildren().get(2);
        assertEquals("add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [g/GAME]... [t/TAG]... "
                + "[pt/PREFERRED TIME]...", detailText.getText());
    }

    @Test
    public void testInvalidCommand() {
        CommandTextFlow invalidCommandTextFlow = new CommandTextFlow("unknownCommand", "");
        assertEquals("No additional information available", invalidCommandTextFlow.getDetailText());
    }

    @Test
    public void testClearCommand() {
        CommandTextFlow clearCommandTextFlow = new CommandTextFlow("clear", "clear");
        assertEquals("clear", clearCommandTextFlow.getCommandText());
        assertEquals("clear - Clears all entries from the gamer address book",
                clearCommandTextFlow.getDetailText());

        // Check highlight for the "clear" command
        Text highlightedText = (Text) clearCommandTextFlow.getChildren().get(0);
        assertEquals("clear", highlightedText.getText());
        assertEquals("0xffa500ff", highlightedText.getFill().toString());
    }

    @Test
    public void testDeleteCommand() {
        CommandTextFlow deleteCommandTextFlow = new CommandTextFlow("delete", "delete");
        assertEquals("delete", deleteCommandTextFlow.getCommandText());
        assertEquals("delete INDEX - Deletes the specified person (e.g., delete 3)",
                deleteCommandTextFlow.getDetailText());

        // Check highlight for the "delete" command
        Text highlightedText = (Text) deleteCommandTextFlow.getChildren().get(0);
        assertEquals("delete", highlightedText.getText());
        assertEquals("0xffa500ff", highlightedText.getFill().toString());
    }

    @Test
    public void testEditCommand() {
        CommandTextFlow editCommandTextFlow = new CommandTextFlow("edit", "edit");
        assertEquals("edit", editCommandTextFlow.getCommandText());
        assertEquals("edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [g/GAME]... "
                + "[t/TAG]... [pt/PREFERRED TIME]...", editCommandTextFlow.getDetailText());

        // Check highlight for the "edit" command
        Text highlightedText = (Text) editCommandTextFlow.getChildren().get(0);
        assertEquals("edit", highlightedText.getText());
        assertEquals("0xffa500ff", highlightedText.getFill().toString());
    }
}
