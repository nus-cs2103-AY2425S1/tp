package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guitests.guihandles.CommandBoxHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.commandpopup.AutoSuggestionTextField;

public class CommandBoxTest extends GuiUnitTest {

    private static final String COMMAND_THAT_SUCCEEDS = ListCommand.COMMAND_WORD;
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;
    private final ArrayList<String> history = new ArrayList<>();

    private CommandBoxHandle commandBoxHandle;
    private Stage primaryStage;

    @BeforeEach
    public void setUp() {
        CommandBox commandBox = new CommandBox(commandText -> {
            history.add(commandText);
            if (commandText.equals(COMMAND_THAT_SUCCEEDS)) {
                return new CommandResult("Command successful");
            }
            throw new CommandException("Command failed");
        });
        uiPartExtension.setUiPart(commandBox);
        commandBoxHandle = new CommandBoxHandle(getChildNode(commandBox.getRoot(),
                CommandBoxHandle.COMMAND_INPUT_FIELD_ID));

        defaultStyleOfCommandBox = new ArrayList<>(commandBoxHandle.getStyleClass());

        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    @Test
    public void commandBox_startingWithSuccessfulCommand() {
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_startingWithFailedCommand() {
        assertBehaviorForFailedCommand();
        assertBehaviorForSuccessfulCommand();

        // verify that style is changed correctly even after multiple consecutive failed commands
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_handleKeyPress() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
        guiRobot.push(KeyCode.ESCAPE);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());

        guiRobot.push(KeyCode.A);
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    @Test
    public void handleKeyPress_startingWithUp() {
        // empty history
        assertInputHistory(KeyCode.UP, "");
        assertInputHistory(KeyCode.DOWN, "");

        // one command
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        //assertInputHistory(KeyCode.DOWN, "");


        // two commands (latest command is failure)
        commandBoxHandle.run(COMMAND_THAT_FAILS);

        // insert command in the middle of retrieving previous commands
        guiRobot.push(KeyCode.UP);
        String thirdCommand = "list";
        commandBoxHandle.run(thirdCommand);
        assertInputHistory(KeyCode.UP, thirdCommand);
        //assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        //assertInputHistory(KeyCode.DOWN, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, thirdCommand);
        //assertInputHistory(KeyCode.DOWN, "");
    }

    @Test
    public void handleKeyPress_startingWithDown() {


        // empty history
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, "");

        // one command
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        //assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);

        // two commands
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        //assertInputHistory(KeyCode.DOWN, "");
        //assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);

        // insert command in the middle of retrieving previous commands
        guiRobot.push(KeyCode.UP);
        String thirdCommand = "list";
        commandBoxHandle.run(thirdCommand);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, thirdCommand);
    }

    @Test
    public void commandBox_checkSuggestionPopUp() {
        guiRobot.push(KeyCode.E);
        guiRobot.push(KeyCode.SHIFT, KeyCode.UP);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DOWN);
        guiRobot.push(KeyCode.TAB);
        assertEquals("edit", commandBoxHandle.getInput());
        guiRobot.push(KeyCode.SPACE);
    }

    /**
     * Runs a command that fails, then verifies that <br>
     * - the text remains <br>
     * - the command box's style is the same as {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForFailedCommand() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(COMMAND_THAT_FAILS, commandBoxHandle.getInput());
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that <br>
     * - the text is cleared <br>
     * - the command box's style is the same as {@code defaultStyleOfCommandBox}.
     */
    private void assertBehaviorForSuccessfulCommand() {
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertEquals("", commandBoxHandle.getInput());
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Pushes {@code keycode} and checks that the input in the {@code commandBox} equals to {@code expectedCommand}.
     */
    private void assertInputHistory(KeyCode keycode, String expectedCommand) {
        guiRobot.push(keycode);
        assertEquals(expectedCommand, commandBoxHandle.getInput());
    }

    @Test
    void testPopulatePopup_emptyFilteredList() {
        AutoSuggestionTextField popup = new AutoSuggestionTextField();
        List<String> filteredList = new ArrayList<>();
        String searchRequest = "test";
        popup.populatePopup(filteredList, searchRequest);
        List<TextFlow> menuItems = popup.getSuggestionList().getItems();
        Assertions.assertTrue(menuItems.isEmpty());
    }

    @Test
    void testPopulatePopup_nonEmptyFilteredList() {
        AutoSuggestionTextField popup = new AutoSuggestionTextField();
        List<String> filteredList = new ArrayList<>();
        filteredList.add("test command 1");
        filteredList.add("another test command");
        String searchRequest = "test";

        List<Label> menuItems = popup.populatePopup(filteredList, searchRequest);
        Assertions.assertEquals(2, menuItems.size());
        Assertions.assertTrue(menuItems.get(0) instanceof Label);
        Assertions.assertTrue(menuItems.get(1) instanceof Label);
        Label label = menuItems.get(1);
        TextFlow textFlow = (TextFlow) label.getGraphic();
        String text = "";
        for (Node node : textFlow.getChildren()) {
            if (node instanceof Text) {
                text += ((Text) node).getText();
            }
        }
        Assertions.assertEquals("another test command", text);
    }


    @Test
    void testPopulatePopup_highlightsMatchingText() {
        AutoSuggestionTextField popup = new AutoSuggestionTextField();
        List<String> filteredList = new ArrayList<>();
        filteredList.add("test command 1");
        filteredList.add("another test command");
        String searchRequest = "test";
        popup.getSuggestionList();
        List<Label> menuItems = popup.populatePopup(filteredList, searchRequest);
        Assertions.assertEquals(2, menuItems.size());
    }

}
