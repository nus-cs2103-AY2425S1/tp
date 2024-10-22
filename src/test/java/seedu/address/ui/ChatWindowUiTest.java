package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void setup() throws Exception {
        FxToolkit.registerStage(() -> new Stage());
    }

    @Test
    public void getResponse_greetingKeyword_success() {
        interact(() -> {
            assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("hello"));
            assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("hi"));
            assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("hey"));
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

    private void waitFor(Duration duration) {
        try {
            Thread.sleep((long) duration.toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

