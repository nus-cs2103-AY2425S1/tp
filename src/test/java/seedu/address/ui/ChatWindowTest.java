package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Tests for the ChatWindow class.
 */
public class ChatWindowTest extends ApplicationTest {
    private ChatWindow chatWindow;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChatWindow.fxml"));
        Parent root = loader.load();
        chatWindow = loader.getController();
        stage.setScene(new Scene(root, 450, 400));
        stage.show();
    }

    @BeforeEach
    public void setup() throws Exception {
        FxToolkit.registerStage(() -> new Stage());
    }

    @Test
    public void testGetResponseGreetings() {
        assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("hello"));
        assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("hi"));
        assertEquals("Hi there! How can I assist you today?", chatWindow.getResponse("hey"));
    }

    @Test
    public void testGetResponseHelp() {
        assertEquals("Sure! What do you need help with?", chatWindow.getResponse("help"));
    }

    @Test
    public void testGetResponseGoodbye() {
        assertEquals("Goodbye! Have a great day!", chatWindow.getResponse("goodbye"));
        assertEquals("Goodbye! Have a great day!", chatWindow.getResponse("bye"));
    }

    @Test
    public void testGetResponseLove() {
        String expected = "Love is not about possession; it's about appreciation of \n"
                + "the journey we share together, hand in hand through \n"
                + "the beautiful chaos of life.";
        assertEquals(expected, chatWindow.getResponse("love"));
    }

    @Test
    public void testGetResponseInvalidMessage() {
        String expected = "I'm sorry, I didn't understand that. Can you please \n"
                + "rephrase?";
        assertEquals(expected, chatWindow.getResponse("random input"));
    }
}
