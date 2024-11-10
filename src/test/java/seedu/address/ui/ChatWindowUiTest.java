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
                            + "• Adding a buyer/seller client profile - seller/buyer\n"
                            + "• Adding an appointment - apt\n"
                            + "• Adding a listing - listing\n"
                            + "• Adding a buyer to a listing - addlistingbuyers",
                    chatWindow.getResponse("add"));
        });
    }

    @Test
    @Order(7)
    public void getResponse_deleteKeyword_success() {
        interact(() -> {
            assertEquals("I assume you are having trouble with the delete command.\n"
                            + "Can you help specify which you are referring to?\n"
                            + "• Deleting a buyer/seller client profile - deleteclient\n"
                            + "• Deleting an appointment - deleteapt\n"
                            + "• Deleting a listing - deletelisting\n"
                            + "• Deleting a buyer from a listing - removelistingbuyers",
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
    public void getResponse_deleteBuyer_success() {
        assertEquals("This is how to delete a buyer!\n"
                        + "deleteclient {index}",
                chatWindow.getResponse("delete buyer"));
    }

    @Test
    @Order(12)
    public void getResponse_deleteSeller_success() {
        assertEquals("This is how to delete a seller!\n"
                        + "deleteclient {index}",
                chatWindow.getResponse("delete seller"));
    }

    @Test
    @Order(13)
    public void getResponse_deleteAppointment_success() {
        assertEquals("This is how to delete an appointment!\n"
                        + "deleteapt {index}",
                chatWindow.getResponse("delete appointment"));
    }

    @Test
    @Order(15)
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
    @Order(16)
    public void getResponse_invalidMessage_failure() {
        String expected = "I'm sorry, I didn't understand that. Can you please \n"
                + "rephrase?";
        interact(() -> {
            assertEquals(expected, chatWindow.getResponse("random input"));
        });
    }

    @Test
    @Order(17)
    public void getResponse_emptyMessage_failure() {
        String expected = "I'm sorry, I didn't understand that. Can you please \n"
                + "rephrase?";
        interact(() -> {
            assertEquals(expected, chatWindow.getResponse(""));
        });
    }

    @Test
    @Order(18)
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
    @Order(19)
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
    @Order(20)
    public void getResponse_addBuyersToListing_success() {
        assertEquals("This is how to add buyers to a listing!\n"
                        + "addlistingbuyers {listing index} buy/{buyer index} [buy/{additional buyer indexes}...]\n"
                        + "Example: addlistingbuyers 1 buy/ 2 buy/ 3\n"
                        + "Adds the specified buyers to the listing identified by its index.",
                chatWindow.getResponse("add listing buyers"));
    }

    @Test
    @Order(21)
    public void getResponse_addListing_success() {
        assertEquals("This is how to add a listing!\n"
                        + "listing n/{name} pr/{price} ar/{area} add/{address} reg/{region} sel/{seller} "
                        + "(Optional: buy/{buyer1} buy/{buyer2} ...)",
                chatWindow.getResponse("add listing"));
    }

    @Test
    @Order(22)
    public void getResponse_editListing_success() {
        assertEquals("This is how to edit a listing!\n"
                        + "editlisting {listing index} [n/{listing name} pr/{price}] [ar/{area}]"
                        + " [add/{address}] [reg/{region}]\n"
                        + "Note: At least one field must be specified to edit a listing.",
                chatWindow.getResponse("edit listing"));
    }

    @Test
    @Order(23)
    public void getResponse_deleteListing_success() {
        assertEquals("This is how to delete a listing!\n"
                        + "deletelisting {index}",
                chatWindow.getResponse("delete listing"));
    }

    @Test
    @Order(24)
    public void getResponse_editClient_success() {
        assertEquals("This is how to edit a client!\n"
                        + "editclient {client index} [n/{name}] [p/{phone number}] [e/{email}] [t/{tag}...]\n"
                        + "Note: At least one field must be specified to edit a client.",
                chatWindow.getResponse("edit client"));
    }

    @Test
    @Order(25)
    public void getResponse_showClient_success() {
        assertEquals("This is how to show your clients!\n"
                        + "showclients\n"
                        + "Displays all clients in your list. If there are no clients, it will inform you accordingly.",
                chatWindow.getResponse("show client"));
    }

    @Test
    @Order(26)
    public void getResponse_showListing_success() {
        assertEquals("This is how to show your listings!\n"
                        + "showlistings\n"
                        + "Displays all listings in your system. If there are no listings, it will notify you "
                        + "accordingly.",
                chatWindow.getResponse("show listing"));
    }

    @Test
    @Order(27)
    public void getResponse_todaysAppointments_success() {
        assertEquals("This is how to check today's appointments!\n"
                        + "Command: today\n"
                        + "Usage: Shows all clients with appointments scheduled for today.\n"
                        + "For general listings, you may consider:\n"
                        + "• showlistings - Displays all listings\n"
                        + "• showclients - Displays all clients.",
                chatWindow.getResponse("today's appointments"));
    }

    @Test
    @Order(28)
    public void getResponse_showAmbiguous_success() {
        assertEquals("It seems you want to show something.\n"
                        + "Can you specify which you are referring to?\n"
                        + "• Show clients - showclients\n"
                        + "• Show listings - showlistings\n"
                        + "• Show today's appointments - today\n",
                chatWindow.getResponse("show"));
    }

    @Test
    @Order(29)
    public void getResponse_editAmbiguous_success() {
        assertEquals("It seems you want to edit something.\n"
                        + "Can you specify which you are referring to?\n"
                        + "• Editing a client profile (buyer/seller) - editclient\n"
                        + "• Editing a listing - editlisting",
                chatWindow.getResponse("edit"));
    }

    @Test
    @Order(30)
    public void getResponse_deleteListingBuyers_success() {
        assertEquals("This is how to remove buyers from a listing!\n"
                        + "removelistingbuyers {listing index} buy/{buyer index} [buy/{additional buyer indexes}...]\n"
                        + "Example: removelistingbuyers 1 buy/ 2 buy/ 3\n"
                        + "Removes the specified buyers from the listing identified by their index.",
                chatWindow.getResponse("remove listing buyers"));
    }

    @Test
    @Order(31)
    public void getResponse_moreInfo_success() {
        assertEquals("This is how to view more information about a client!\n"
                        + "Command: moreinfo {index}\n"
                        + "Example: moreinfo 1\n"
                        + "Opens a window displaying detailed information about the specified client.",
                chatWindow.getResponse("more info about client"));
    }

    @Test
    @Order(32)
    public void getResponse_clearCommandSuggestions_success() {
        assertEquals("It seems you want to clear some data.\n"
                        + "Can you specify which you are referring to?\n"
                        + "• Clear all client data and listings - clear\n"
                        + "• Clear only listings - clearlistings",
                chatWindow.getResponse("clear"));

        assertEquals("It seems you want to clear some data.\n"
                        + "Can you specify which you are referring to?\n"
                        + "• Clear all client data and listings - clear\n"
                        + "• Clear only listings - clearlistings",
                chatWindow.getResponse("reset all"));

        assertEquals("It seems you want to clear some data.\n"
                        + "Can you specify which you are referring to?\n"
                        + "• Clear all client data and listings - clear\n"
                        + "• Clear only listings - clearlistings",
                chatWindow.getResponse("wipe listings"));
    }

    @Test
    @Order(33)
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

