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
        assertEquals("add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [g/GAME]... [t/TAG]..."
                + " [pt/TIME-TIME]...", commandTextFlow.getDetailText());
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
        assertEquals("add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [g/GAME]... [t/TAG]..."
                + " [pt/TIME-TIME]...", detailText.getText());
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
                + "[t/TAG]... [pt/TIME-TIME]...", editCommandTextFlow.getDetailText());

        // Check highlight for the "edit" command
        Text highlightedText = (Text) editCommandTextFlow.getChildren().get(0);
        assertEquals("edit", highlightedText.getText());
        assertEquals("0xffa500ff", highlightedText.getFill().toString());
    }
    @Test
    public void testHelpCommand() {
        CommandTextFlow helpCommandTextFlow = new CommandTextFlow("help", "help");
        assertEquals("help", helpCommandTextFlow.getCommandText());
        assertEquals("help - Shows program help instructions and command summary",
                helpCommandTextFlow.getDetailText());

        Text highlightedText = (Text) helpCommandTextFlow.getChildren().get(0);
        assertEquals("help", highlightedText.getText());
        assertEquals("0xffa500ff", highlightedText.getFill().toString());
    }

    @Test
    public void testAddGameCommand() {
        CommandTextFlow addGameCommandTextFlow = new CommandTextFlow("addgame", "addgame");
        assertEquals("addgame", addGameCommandTextFlow.getCommandText());
        assertEquals("addgame INDEX g/GAME [u/USERNAME] [s/SKILLLEVEL] [r/ROLE]",
                addGameCommandTextFlow.getDetailText());

        Text highlightedText = (Text) addGameCommandTextFlow.getChildren().get(0);
        assertEquals("addgame", highlightedText.getText());
        assertEquals("0xffa500ff", highlightedText.getFill().toString());
    }

    @Test
    public void testEditGameCommand() {
        CommandTextFlow editGameCommandTextFlow = new CommandTextFlow("editgame", "editgame");
        assertEquals("editgame", editGameCommandTextFlow.getCommandText());
        assertEquals("editgame INDEX g/GAME [u/USERNAME] [s/SKILL_LEVEL] [r/ROLE]",
                editGameCommandTextFlow.getDetailText());

        Text highlightedText = (Text) editGameCommandTextFlow.getChildren().get(0);
        assertEquals("editgame", highlightedText.getText());
        assertEquals("0xffa500ff", highlightedText.getFill().toString());
    }

    @Test
    public void testDeleteGameCommand() {
        CommandTextFlow deleteGameCommandTextFlow = new CommandTextFlow("deletegame", "deletegame");
        assertEquals("deletegame", deleteGameCommandTextFlow.getCommandText());
        assertEquals("deletegame INDEX g/GAME",
                deleteGameCommandTextFlow.getDetailText());

        Text highlightedText = (Text) deleteGameCommandTextFlow.getChildren().get(0);
        assertEquals("deletegame", highlightedText.getText());
        assertEquals("0xffa500ff", highlightedText.getFill().toString());
    }

    @Test
    public void testFavGameCommand() {
        CommandTextFlow favGameCommandTextFlow = new CommandTextFlow("favgame", "favgame");
        assertEquals("favgame", favGameCommandTextFlow.getCommandText());
        assertEquals("favgame INDEX g/GAME",
                favGameCommandTextFlow.getDetailText());

        Text highlightedText = (Text) favGameCommandTextFlow.getChildren().get(0);
        assertEquals("favgame", highlightedText.getText());
        assertEquals("0xffa500ff", highlightedText.getFill().toString());
    }

    @Test
    public void testUnfavGameCommand() {
        CommandTextFlow unfavGameCommandTextFlow = new CommandTextFlow("unfavgame", "unfavgame");
        assertEquals("unfavgame", unfavGameCommandTextFlow.getCommandText());
        assertEquals("unfavgame INDEX g/GAME",
                unfavGameCommandTextFlow.getDetailText());

        Text highlightedText = (Text) unfavGameCommandTextFlow.getChildren().get(0);
        assertEquals("unfavgame", highlightedText.getText());
        assertEquals("0xffa500ff", highlightedText.getFill().toString());
    }

    @Test
    public void testFindCommand() {
        CommandTextFlow findCommandTextFlow = new CommandTextFlow("find", "find");
        assertEquals("find", findCommandTextFlow.getCommandText());
        assertEquals("find KEYWORD [MORE_KEYWORDS]... - Finds persons whose names contain any of the keywords",
                findCommandTextFlow.getDetailText());

        Text highlightedText = (Text) findCommandTextFlow.getChildren().get(0);
        assertEquals("find", highlightedText.getText());
        assertEquals("0xffa500ff", highlightedText.getFill().toString());
    }

    @Test
    public void testFindTimeCommand() {
        CommandTextFlow findTimeCommandTextFlow = new CommandTextFlow("findtime", "findtime");
        assertEquals("findtime", findTimeCommandTextFlow.getCommandText());
        assertEquals("find TIME-TIME [TIME-TIME]... - Finds persons whose preferred time contains any TIMES",
                findTimeCommandTextFlow.getDetailText());

        Text highlightedText = (Text) findTimeCommandTextFlow.getChildren().get(0);
        assertEquals("findtime", highlightedText.getText());
        assertEquals("0xffa500ff", highlightedText.getFill().toString());
    }

    @Test
    public void testFindGameCommand() {
        CommandTextFlow findGameCommandTextFlow = new CommandTextFlow("findgame", "findgame");
        assertEquals("findgame", findGameCommandTextFlow.getCommandText());
        assertEquals("findgame KEYWORD [MORE_KEYWORDS]... - Finds persons whose games contain any of the keywords",
                findGameCommandTextFlow.getDetailText());

        Text highlightedText = (Text) findGameCommandTextFlow.getChildren().get(0);
        assertEquals("findgame", highlightedText.getText());
        assertEquals("0xffa500ff", highlightedText.getFill().toString());
    }

    @Test
    public void testUndoCommand() {
        CommandTextFlow undoCommandTextFlow = new CommandTextFlow("undo", "undo");
        assertEquals("undo", undoCommandTextFlow.getCommandText());
        assertEquals("undo - Undoes the previous command (aside from save and load)",
                undoCommandTextFlow.getDetailText());

        Text highlightedText = (Text) undoCommandTextFlow.getChildren().get(0);
        assertEquals("undo", highlightedText.getText());
        assertEquals("0xffa500ff", highlightedText.getFill().toString());
    }

    @Test
    public void testSaveCommand() {
        CommandTextFlow saveCommandTextFlow = new CommandTextFlow("save", "save");
        assertEquals("save", saveCommandTextFlow.getCommandText());
        assertEquals("save - Saves the gamer address book data",
                saveCommandTextFlow.getDetailText());

        Text highlightedText = (Text) saveCommandTextFlow.getChildren().get(0);
        assertEquals("save", highlightedText.getText());
        assertEquals("0xffa500ff", highlightedText.getFill().toString());
    }

    @Test
    public void testLoadCommand() {
        CommandTextFlow loadCommandTextFlow = new CommandTextFlow("load", "load");
        assertEquals("load", loadCommandTextFlow.getCommandText());
        assertEquals("load - Loads the gamer address book data",
                loadCommandTextFlow.getDetailText());

        Text highlightedText = (Text) loadCommandTextFlow.getChildren().get(0);
        assertEquals("load", highlightedText.getText());
        assertEquals("0xffa500ff", highlightedText.getFill().toString());
    }

    @Test
    public void testExitCommand() {
        CommandTextFlow exitCommandTextFlow = new CommandTextFlow("exit", "exit");
        assertEquals("exit", exitCommandTextFlow.getCommandText());
        assertEquals("exit - Closes the gamer address book",
                exitCommandTextFlow.getDetailText());

        Text highlightedText = (Text) exitCommandTextFlow.getChildren().get(0);
        assertEquals("exit", highlightedText.getText());
        assertEquals("0xffa500ff", highlightedText.getFill().toString());
    }

}
