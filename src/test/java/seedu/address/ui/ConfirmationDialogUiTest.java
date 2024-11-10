package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.clientcommands.DeleteClientProfileCommand.MESSAGE_DELETE_PERSON_SUCCESS;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import seedu.address.MainApp;

public class ConfirmationDialogUiTest extends ApplicationTest {
    private static MainApp app;
    private static FxRobot robot;

    @BeforeAll
    public static void setUp() throws Exception {
        FxToolkit.registerPrimaryStage();
        app = new MainApp();
        FxToolkit.setupApplication(() -> app);
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents(20);

        robot = new FxRobot();
        robot.clickOn();
        robot.clickOn("#commandTextField");
        robot.write("seller n/Tester p/98765432 e/test@testing.com");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#commandTextField");
        robot.write("listing n/Testing Site pr/123456 ar/123 add/123 AVENUE "
                + "reg/east sel/7");
        robot.type(KeyCode.ENTER);
    }

    @Test
    void testShowDeleteConfirmation_acceptsAndRejectsConfirmationCorrectly() {
        robot.clickOn("#commandTextField");
        robot.write("deleteclient 7");
        robot.type(KeyCode.ENTER);

        WaitForAsyncUtils.waitForFxEvents(20);

        robot.clickOn("No");

        String cancellationMessage = "Deletion canceled by user.";
        TextArea resultDisplay = robot.lookup("#resultDisplay").query();
        assertTrue(resultDisplay.getText().contains(cancellationMessage));

        TextField commandTextField = robot.lookup("#commandTextField").query();
        commandTextField.setText("");

        robot.clickOn("#commandTextField");
        robot.write("deleteclient 7");
        robot.type(KeyCode.ENTER);

        WaitForAsyncUtils.waitForFxEvents(20);

        robot.clickOn("Yes");

        String deletionMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                "Tester.\nPhone number: 98765432 and Email: test@testing.com");

        resultDisplay = robot.lookup("#resultDisplay").query();
        assertTrue(resultDisplay.getText().contains(deletionMessage));
    }
}
