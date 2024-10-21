package tuteez.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import tuteez.logic.commands.CommandResult;

@ExtendWith(ApplicationExtension.class)
public class CommandBoxTest {
    private CommandBox commandBox;
    private TextField commandTextField;

    @Start
    public void start(Stage stage) {
        // Set up your GUI for testing
        commandBox = new CommandBox(commandText -> new CommandResult("Command executed"));
        stage.setScene(new Scene(commandBox.getRoot()));
        stage.show();
    }

    @Test
    public void handleCommandEntered_validCommand(FxRobot robot) {
        // Simulate typing in the text field
        commandTextField = robot.lookup("#commandTextField").queryAs(TextField.class);
        robot.clickOn(commandTextField).write("list");

        // Press Enter to simulate command submission
        robot.type(KeyCode.ENTER);

        // Assert that the text field is cleared
        assertEquals("", commandTextField.getText());
    }

    @Test
    public void handleKeyPressed_upArrowRetrievesPreviousCommand(FxRobot robot) {
        // Add commands to history manually for this test
        commandTextField = robot.lookup("#commandTextField").queryAs(TextField.class);
        robot.clickOn(commandTextField).write("list");
        robot.type(KeyCode.ENTER);

        commandTextField = robot.lookup("#commandTextField").queryAs(TextField.class);
        robot.clickOn(commandTextField).write("help");
        robot.type(KeyCode.ENTER);
        // Simulate UP key press
        robot.type(KeyCode.UP);

        // Assert that the text field shows the previous command
        assertEquals("help", commandTextField.getText());
    }

    @Test
    public void handleKeyPressed_downArrowClearsCommand(FxRobot robot) {
        // Add a command to history
        commandTextField = robot.lookup("#commandTextField").queryAs(TextField.class);
        robot.clickOn(commandTextField).write("list");
        robot.type(KeyCode.ENTER);

        // Navigate back to the first command
        robot.type(KeyCode.UP);
        assertEquals("list", commandTextField.getText());

        // Press DOWN to clear the command
        robot.type(KeyCode.DOWN);

        // Assert that the text field is cleared
        assertEquals("", commandTextField.getText());
    }
}
