package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    public void getResponse_greetingKeyword_success() {
        interact(() -> {
            assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("hello"));
            assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("hi"));
            assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("hey"));
            System.out.println("test");
        });
    }

    @Test
    public void getResponse_helpKeyword_success() {
        interact(() -> {
            assertEquals("Sure! What do you need help with?", chatWindow.getResponse("help"));
        });
    }

    @Test
    public void getResponse_goodbyeKeyword_success() {
        interact(() -> {
            assertEquals("Goodbye! Have a great day!", chatWindow.getResponse("goodbye"));
            assertEquals("Goodbye! Have a great day!", chatWindow.getResponse("bye"));
        });
    }

    @Test
    public void getResponse_thanksKeyword_success() {
        interact(() -> {
            assertEquals("You're welcome! Always happy to help.", chatWindow.getResponse("thank"));
            assertEquals("You're welcome! Always happy to help.", chatWindow.getResponse("thanks"));
            assertEquals("You're welcome! Always happy to help.", chatWindow.getResponse("thank you"));
            assertEquals("You're welcome! Always happy to help.", chatWindow.getResponse("thank u"));
        });
    }

    @Test
    public void getResponse_loveKeyword_success() {
        String expected = "Love is not about possession; it's about appreciation of \n"
                + "the journey we share together, hand in hand through \n"
                + "the beautiful chaos of life.";
        interact(() -> {
            assertEquals(expected, chatWindow.getResponse("love"));
        });
    }

    @Test
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
    public void getResponse_addBuyer_success() {
        assertEquals("This is how to add a buyer!\n"
                        + "buyer n/{name} p/{phone number} e/{email}",
                chatWindow.getResponse("add buyer"));
        assertEquals("This is how to add a buyer!\n"
                        + "buyer n/{name} p/{phone number} e/{email}",
                chatWindow.getResponse("adding a buyer"));
    }

    @Test
    public void getResponse_addSeller_success() {
        assertEquals("This is how to add a seller!\n"
                        + "seller n/{name} p/{phone number} e/{email}",
                chatWindow.getResponse("add seller"));
    }

    @Test
    public void getResponse_addAppointment_success() {
        assertEquals("This is how to add an appointment!\n"
                        + "apt {index} d/{date} fr/{start time} to/{end time}",
                chatWindow.getResponse("add appointment"));
        assertEquals("This is how to add an appointment!\n"
                        + "apt {index} d/{date} fr/{start time} to/{end time}",
                chatWindow.getResponse("adding an appointment"));
    }

    @Test
    public void getResponse_addProperty_success() {
        assertEquals("This is how to add a property!\n"
                        + "prop {index} prop/{date} fr/{address}",
                chatWindow.getResponse("add property"));
    }

    @Test
    public void getResponse_deleteBuyer_success() {
        assertEquals("This is how to delete a buyer!\n"
                        + "delete n/{name}",
                chatWindow.getResponse("delete buyer"));
    }

    @Test
    public void getResponse_deleteSeller_success() {
        assertEquals("This is how to delete a seller!\n"
                        + "delete n/{name}",
                chatWindow.getResponse("delete seller"));
    }

    @Test
    public void getResponse_deleteAppointment_success() {
        assertEquals("This is how to delete an appointment!\n"
                        + "delapt n/{name}",
                chatWindow.getResponse("delete appointment"));
    }

    @Test
    public void getResponse_deleteProperty_success() {
        assertEquals("This is how to delete a property!\n"
                        + "delprop n/{name}",
                chatWindow.getResponse("delete property"));
    }

    @Test
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
    public void getResponse_invalidMessage_failure() {
        String expected = "I'm sorry, I didn't understand that. Can you please \n"
                + "rephrase?";
        interact(() -> {
            assertEquals(expected, chatWindow.getResponse("random input"));
        });
    }

    @Test
    public void getResponse_emptyMessage_failure() {
        String expected = "I'm sorry, I didn't understand that. Can you please \n"
                + "rephrase?";
        interact(() -> {
            assertEquals(expected, chatWindow.getResponse(""));
        });
    }
    @Test
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

        waitFor(Duration.seconds(3));
        interact(() -> {
            String finalResponse = "Assistant: Hi there! How can I assist you today?\n";
            assertEquals(expectedUserMessage + finalResponse, chatArea.getText());
        });
    }

    @Test
    public void handleSendButtonAction_emptyInput_noAction() {
        FxRobot robot = new FxRobot();
        robot.clickOn(userInput);
        robot.write("");
        robot.type(KeyCode.ENTER);

        assertEquals("", chatArea.getText());
    }

    @Test
    public void getResponse_keywordCaseVariations_success() {
        interact(() -> {
            assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("Hello"));
            assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("HELLO"));
            assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("hElLo"));
        });
    }

    @Test
    public void getResponse_punctuationHandling_success() {
        interact(() -> {
            assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("hello?"));
            assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("hi!"));
            assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("hey..."));
        });
    }

    @Test
    public void getResponse_multipleWordsWithKeywords_success() {
        interact(() -> {
            assertEquals("Sure! What do you need help with?",
                    chatWindow.getResponse("Can you help me with my homework?"));
            assertEquals("Love is not about possession; it's about appreciation of \n"
                    + "the journey we share together, hand in hand through \n"
                    + "the beautiful chaos of life.", chatWindow.getResponse("I love coding!"));
        });
    }

    @Test
    public void getResponse_repeatedKeywords_success() {
        interact(() -> {
            assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("hello hello"));
            assertEquals("You're welcome! Always happy to help.", chatWindow.getResponse("thank you thank you"));
        });
    }

    @Test
    public void getResponse_longInput_failure() {
        String expected = "I'm sorry, I didn't understand that. Can you please \n"
                + "rephrase?";
        interact(() -> {
            String longInput = "This is a very long input that doesn't "
                    + "really mean anything and is just a string of words.";
            assertEquals(expected, chatWindow.getResponse(longInput));
        });
    }

    @Test
    public void getResponse_boundaryTests_success() {
        interact(() -> {
            String input = "a";
            String expected = "I'm sorry, I didn't understand that. Can you please \n"
                    + "rephrase?";
            assertEquals(expected, chatWindow.getResponse(input));

            String longInput = "a".repeat(1000);
            assertEquals(expected, chatWindow.getResponse(longInput));
        });
    }

    @Test
    public void getResponse_similarUserMessages_success() {
        interact(() -> {
            assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("say hello"));
            assertEquals("You're welcome! Always happy to help.", chatWindow.getResponse("thanks for your help"));
            assertEquals("Goodbye! Have a great day!", chatWindow.getResponse("I am leaving now, bye!"));
        });
    }

    @Test
    public void handleRapidInput_success() {
        FxRobot robot = new FxRobot();

        String[] messages = {"hello", "help", "thanks", "love", "goodbye"};
        for (String message : messages) {
            robot.clickOn(userInput);
            robot.write(message);
            robot.type(KeyCode.ENTER);
            waitFor(Duration.seconds(1));
        }

        String expectedResponses =
                "You: hello\n"
                + "Assistant: Hi there! How can I assist you today?\n"
                + "You: help\n"
                + "Assistant: Sure! What do you need help with?\n"
                + "You: thanks\n"
                + "Assistant: You're welcome! Always happy to help.\n"
                + "You: love\n"
                + "Assistant: Love is not about possession; it's about appreciation of \n"
                + "the journey we share together, hand in hand through \n"
                + "the beautiful chaos of life.\n"
                + "You: goodbye\n"
                + "Assistant: Goodbye! Have a great day!\n";
        assertEquals(expectedResponses, chatArea.getText());
    }

    private void waitFor(Duration duration) {
        try {
            Thread.sleep((long) duration.toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
