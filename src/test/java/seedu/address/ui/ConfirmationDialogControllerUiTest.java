package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfirmationDialogControllerUiTest extends ApplicationTest {
    private ConfirmationDialogController controller;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ConfirmationDialog.fxml"));
        VBox vbox = loader.load();
        controller = loader.getController();

        primaryStage.setScene(new Scene(vbox));
        primaryStage.setTitle("Confirmation Dialog Test");
        primaryStage.show();
        stage = primaryStage;
    }

    @BeforeEach
    public void setup() {
        Platform.runLater(() -> stage.show());
    }

    @AfterEach
    public void teardown() {
        Platform.runLater(() -> stage.hide());
    }

    @Test
    public void testSetHeaderLabelText() {
        String headerText = "Delete Confirmation";
        Platform.runLater(() -> controller.setHeaderLabelText(headerText));

        sleep(100);

        Label headerLabel = (Label) stage.getScene().lookup("#headerLabel");
        assertEquals(headerText, headerLabel.getText());
    }

    @Test
    public void testSetClientName() {
        String clientName = "John Doe";
        Platform.runLater(() -> controller.setClientName(clientName));

        sleep(100);

        Label clientNameLabel = (Label) stage.getScene().lookup("#clientNameLabel");
        assertEquals(clientName, clientNameLabel.getText());
    }

    @Test
    public void testYesButtonAction() {
        String headerText = "Are you sure?";
        String clientName = "John Doe";

        Platform.runLater(() -> {
            controller.setHeaderLabelText(headerText);
            controller.setClientName(clientName);
        });

        Button yesButton = (Button) stage.getScene().lookup("#yesButton");
        clickOn(yesButton);

        assertEquals(true, controller.isConfirmed());
    }

    @Test
    public void testNoButtonAction() {
        String headerText = "Are you sure?";
        String clientName = "John Doe";

        Platform.runLater(() -> {
            controller.setHeaderLabelText(headerText);
            controller.setClientName(clientName);
            Button noButton = (Button) stage.getScene().lookup("#noButton");
            noButton.requestFocus();
        });
        push(KeyCode.ENTER);

        assertEquals(false, controller.isConfirmed());
    }

    @Test
    public void testKeyboardEnterOnYes() {
        String headerText = "Are you sure?";
        String clientName = "John Doe";

        Platform.runLater(() -> {
            controller.setHeaderLabelText(headerText);
            controller.setClientName(clientName);
            Button yesButton = (Button) stage.getScene().lookup("#yesButton");
            yesButton.requestFocus();
        });
        push(KeyCode.ENTER);

        assertEquals(true, controller.isConfirmed());
    }

    @Test
    public void testKeyboardEscapeOnNo() {
        String headerText = "Are you sure?";
        String clientName = "John Doe";

        Platform.runLater(() -> {
            controller.setHeaderLabelText(headerText);
            controller.setClientName(clientName);
        });

        push(KeyCode.ESCAPE);

        assertEquals(false, controller.isConfirmed());
    }
}
