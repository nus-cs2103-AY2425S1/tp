package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChatWindowUiTest extends ApplicationTest {
    private ChatWindow chatWindow;
    private TextField userInput;
    private TextArea chatArea;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChatWindow.fxml"));
        Parent root = loader.load();
        chatWindow = loader.getController();
        stage.setScene(new Scene(root, 450, 400));
        stage.show();

        userInput = lookup("#userInput").query();
        chatArea = lookup("#chatArea").query();
    }

    @BeforeEach
    public void setup() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
    }

    @AfterEach
    public void stopApp() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @Test
    @Order(1)
    public void getResponse_greetingKeyword_success() {
        interact(() -> {
            assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("hello"));
            assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("hi"));
            assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("hey"));
        });
    }

    @Test
    @Order(2)
    public void getResponse_helpKeyword_success() {
        interact(() -> {
            assertEquals("Sure! What do you need help with?", chatWindow.getResponse("help"));
        });
    }

    @Test
    @Order(3)
    public void getResponse_goodbyeKeyword_success() {
        interact(() -> {
            assertEquals("Goodbye! Have a great day!", chatWindow.getResponse("goodbye"));
            assertEquals("Goodbye! Have a great day!", chatWindow.getResponse("bye"));
        });
    }

    @Test
    @Order(4)
    public void getResponse_thanksKeyword_success() {
        interact(() -> {
            assertEquals("You're welcome! Always happy to help.", chatWindow.getResponse("thank"));
            assertEquals("You're welcome! Always happy to help.", chatWindow.getResponse("thanks"));
            assertEquals("You're welcome! Always happy to help.", chatWindow.getResponse("thank you"));
            assertEquals("You're welcome! Always happy to help.", chatWindow.getResponse("thank u"));
        });
    }

    @Test
    @Order(5)
    public void getResponse_loveKeyword_success() {
        String expected = "Love is not about possession; it's about appreciation of \n"
                + "the journey we share together, hand in hand through \n"
                + "the beautiful chaos of life.";
        interact(() -> {
            assertEquals(expected, chatWindow.getResponse("love"));
        });
    }

    @Test
    @Order(6)
    public void getResponse_addKeyword_success() {
        interact(() -> {
            assertEquals("I assume you are having trouble with the add command.\n"
                            + "Can you help specify which you are referring to?\n"
                            + "• Adding a buyer/seller client profile\n"
                            + "• Adding an appointment\n"
                            + "• Adding a property\n"
                            + "• Adding a listing",
                    chatWindow.getResponse("add"));
        });
    }

    @Test
    @Order(7)
    public void getResponse_deleteKeyword_success() {
        interact(() -> {
            assertEquals("I assume you are having trouble with the delete command.\n"
                            + "Can you help specify which you are referring to?\n"
                            + "• Deleting a buyer/seller client profile\n"
                            + "• Deleting an appointment\n"
                            + "• Deleting a property\n"
                            + "• Deleting a listing",
                    chatWindow.getResponse("delete"));
        });
    }

    @Test
    @Order(8)
    public void getResponse_addBuyer_success() {
        assertEquals("This is how to add a buyer!\n"
                        + "buyer n/{name} p/{phone number} e/{email}",
                chatWindow.getResponse("add buyer"));
        assertEquals("This is how to add a buyer!\n"
                        + "buyer n/{name} p/{phone number} e/{email}",
                chatWindow.getResponse("adding a buyer"));
    }

    @Test
    @Order(9)
    public void getResponse_addSeller_success() {
        assertEquals("This is how to add a seller!\n"
                        + "seller n/{name} p/{phone number} e/{email}",
                chatWindow.getResponse("add seller"));
    }

    @Test
    @Order(10)
    public void getResponse_addAppointment_success() {
        assertEquals("This is how to add an appointment!\n"
                        + "apt {index} d/{date} fr/{start time} to/{end time}",
                chatWindow.getResponse("add appointment"));
        assertEquals("This is how to add an appointment!\n"
                        + "apt {index} d/{date} fr/{start time} to/{end time}",
                chatWindow.getResponse("adding an appointment"));
    }

    @Test
    @Order(11)
    public void getResponse_addProperty_success() {
        assertEquals("This is how to add a property!\n"
                        + "prop {index} prop/{date} fr/{address}",
                chatWindow.getResponse("add property"));
    }

    @Test
    @Order(12)
    public void getResponse_deleteBuyer_success() {
        assertEquals("This is how to delete a buyer!\n"
                        + "delete n/{name}",
                chatWindow.getResponse("delete buyer"));
    }

    @Test
    @Order(13)
    public void getResponse_deleteSeller_success() {
        assertEquals("This is how to delete a seller!\n"
                        + "delete n/{name}",
                chatWindow.getResponse("delete seller"));
    }

    @Test
    @Order(14)
    public void getResponse_deleteAppointment_success() {
        assertEquals("This is how to delete an appointment!\n"
                        + "delapt n/{name}",
                chatWindow.getResponse("delete appointment"));
    }

    @Test
    @Order(15)
    public void getResponse_deleteProperty_success() {
        assertEquals("This is how to delete a property!\n"
                        + "delprop n/{name}",
                chatWindow.getResponse("delete property"));
    }

    @Test
    @Order(16)
    public void getResponse_clientCategory_success() {
        assertEquals("We categorise clients into buyers and sellers for clarity of our users!\n"
                        + "Maybe consider:\n"
                        + "• Adding a buyer\n"
                        + "• Adding a seller",
                chatWindow.getResponse("add client"));
        assertEquals("We categorise clients into buyers and sellers for clarity of our users!\n"
                        + "Maybe consider:\n"
                        + "• Deleting a buyer\n"
                        + "• Deleting a seller",
                chatWindow.getResponse("delete client"));
    }

    @Test
    @Order(17)
    public void getResponse_invalidMessage_failure() {
        String expected = "I'm sorry, I didn't understand that. Can you please \n"
                + "rephrase?";
        interact(() -> {
            assertEquals(expected, chatWindow.getResponse("random input"));
        });
    }

    @Test
    @Order(18)
    public void getResponse_emptyMessage_failure() {
        String expected = "I'm sorry, I didn't understand that. Can you please \n"
                + "rephrase?";
        interact(() -> {
            assertEquals(expected, chatWindow.getResponse(""));
        });
    }

    @Test
    @Order(19)
    public void handleSendButtonAction_typingResponse_success() {
        FxRobot robot = new FxRobot();
        robot.clickOn(userInput);
        robot.write("hello");
        robot.type(KeyCode.ENTER);

        String expectedUserMessage = "You: hello\n";
        assertEquals(expectedUserMessage, chatArea.getText().substring(0, expectedUserMessage.length()));

        interact(() -> {
            String typingMessage = "Assistant is typing";
            assertEquals(expectedUserMessage + typingMessage, chatArea.getText());
        });

        waitFor(Duration.seconds(1));
        interact(() -> {
            String finalResponse = "Assistant: Hi there! How can I assist you today?\n";
            assertEquals(expectedUserMessage + finalResponse, chatArea.getText());
        });
    }

    @Test
    @Order(20)
    public void handleSendButtonAction_specialCharacters_success() {
        FxRobot robot = new FxRobot();
        robot.clickOn(userInput);
        robot.write("@#$%");
        robot.type(KeyCode.ENTER);

        String expected = "You: @#$%\n"
                + "Assistant: I'm sorry, I didn't understand that. Can you please \n"
                + "rephrase?";
        waitFor(Duration.seconds(1));
        assertEquals(expected, chatArea.getText().trim());
    }

    @Test
    @Order(21)
    public void handleSendButtonAction_exitOnGoodbye_success() {
        FxRobot robot = new FxRobot();
        robot.clickOn(userInput);
        robot.write("goodbye");
        robot.type(KeyCode.ENTER);
        waitFor(Duration.seconds(1));

        String expectedGoodbyeResponse = "You: goodbye\n"
                + "Assistant: Goodbye! Have a great day!";
        assertEquals(expectedGoodbyeResponse, chatArea.getText().trim());

        assertTrue(isChatWindowClosed(), "The application did not exit as expected.");
    }

    private boolean isChatWindowClosed() {
        return FxToolkit.isFXApplicationThreadRunning();
    }

    private void waitFor(Duration duration) {
        try {
            Thread.sleep((long) duration.toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

