package seedu.address.ui;

import java.util.regex.Pattern;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The {@code ChatWindow} class represents the chat interface where users can
 * interact with an assistant. This class handles user input and displays
 * messages in a chat area.
 */
public class ChatWindow {
    @FXML
    private TextArea chatArea;

    @FXML
    private TextField userInput;

    /**
     * Initializes the chat window, setting up key event handling.
     */
    @FXML
    public void initialize() {
        userInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleSendButtonAction();
            }
        });
    }

    /**
     * Handles the action when the send button is clicked in the chat interface.
     * This method retrieves the user's input message from the input field,
     * checks if the message is not empty, and appends it to the chat area.
     * It then clears the input field for the next message. Additionally,
     * it simulates a response from the assistant based on the user's input
     * with a typing effect.
     */
    @FXML
    public void handleSendButtonAction() {
        String message = userInput.getText();
        if (!message.trim().isEmpty()) {
            chatArea.appendText("You: " + message + "\n");
            userInput.clear();

            String typingMessage = "Assistant is typing";
            chatArea.appendText(typingMessage);

            userInput.setDisable(true);

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.5), e -> {
                        chatArea.appendText(".");
                    }),
                    new KeyFrame(Duration.seconds(0.75), e -> {
                        chatArea.appendText(".");
                    }),
                    new KeyFrame(Duration.seconds(1), e -> {
                        String chatText = chatArea.getText();
                        chatText = chatText.replace(typingMessage, "");
                        chatText = chatText.replace("..", "");
                        chatArea.setText(chatText);

                        String response = getResponse(message);
                        chatArea.appendText("Assistant: " + response + "\n");
                        if (isGoodbyeMessage(message)) {
                            Timeline closeTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e2 -> {
                                closeChatWindow();
                            }));
                            closeTimeline.setCycleCount(1);
                            closeTimeline.play();
                        }
                        userInput.setDisable(false);
                        userInput.requestFocus();
                    })
            );

            timeline.setCycleCount(1);
            timeline.play();
        }
    }

    /**
     * Checks if the user's message is a goodbye message.
     *
     * @param message The user's input message.
     * @return True if the message indicates a goodbye; otherwise, false.
     */
    private boolean isGoodbyeMessage(String message) {
        String lowerMessage = message.toLowerCase().trim();
        return Pattern.compile("\\b(g+o+o+d+b+y+e+|b+y+e+|bai|bubye|see ya|see you"
                        + "|take care|later|cya|catch you later|peace|adieu|ta"
                        + "|farewell|good night|so long|cheerio|toodle-oo"
                        + "|until next time|ttyl|g2g|gotta go|im off|exit"
                        + "|im leaving|im out|im off now|im outta here|i'm out)\\b")
                .matcher(lowerMessage).find();
    }

    /**
     * Checks if the user's message is a hello message.
     *
     * @param message The user's input message.
     * @return True if the message indicates a hello; otherwise, false.
     */
    private boolean isHelloMessage(String message) {
        String lowerMessage = message.toLowerCase().trim();
        return Pattern.compile("\\b(h+e+l+o+|h+i+|h+e+y+|howdy|greetings|salutations"
                        + "|what's up|what's good|yo|sup|how's it going|how are you|howdy doo"
                        + "aloha|bonjour|hola|holla|howdy+|hiya|wazzup|welcome)\\b")
                .matcher(lowerMessage).find();
    }

    /**
     * Requests focus for the user input field in the chat window.
     * <p>
     * This method checks if the user input field is initialized and, if so,
     * requests the focus to ensure that the user can start typing immediately
     * when the chat window is opened.
     * </p>
     */
    public void focusInputField() {
        if (userInput != null) {
            userInput.requestFocus();
        }
    }

    /**
     * Closes the chat window.
     */
    private void closeChatWindow() {
        Stage stage = (Stage) userInput.getScene().getWindow();
        stage.close();
    }

    /**
     * Returns a simple response based on the user input message.
     *
     * @param message The user's
     *                input message.
     * @return The assistant's response as a String.
     */
    public String getResponse(String message) {
        message = message.toLowerCase().trim();

        if (isHelloMessage(message)) {
            return "Hi there! How can I assist you today?";
        } else if (Pattern.compile("(?=.*\\b(a+d+d+|adding)\\b)(?=.*\\bl+i+s+t+i+n+g+s*\\b)"
                        + "(?" + "=.*\\bb+u+y+e+r+s*\\b)")
                .matcher(message).find()) {
            return "This is how to add buyers to a listing!\n"
                    + "addlistingbuyers {listing index} buy/{buyer index} [buy/{additional buyer indexes}...]\n"
                    + "Example: addlistingbuyers 1 buy/ 2 buy/ 3\n"
                    + "Adds the specified buyers to the listing identified by its index.";
        } else if (Pattern.compile("(?=.*\\b(r+e+m+o+v+e+|removing)\\b)(?=.*\\bl+i+s+t+i+n+g+s*\\b)"
                        + "(?=.*\\bb+u+y+e+r+s*\\b)")
                .matcher(message).find()) {
            return "This is how to remove buyers from a listing!\n"
                    + "removelistingbuyers {listing index} buy/{buyer index} [buy/{additional buyer indexes}...]\n"
                    + "Example: removelistingbuyers 1 buy/ 2 buy/ 3\n"
                    + "Removes the specified buyers from the listing identified by their index.";
        } else if (isGoodbyeMessage(message)) {
            return "Goodbye! Have a great day!";
        } else if (Pattern.compile("\\b(a+d+d+|adding|adds)\\b.*c+l+i+e+n+t+\\b")
                .matcher(message).find()) {
            return "We categorise clients into buyers and sellers for clarity of our users!\n"
                    + "Maybe consider:\n"
                    + "• Adding a buyer\n"
                    + "• Adding a seller";
        } else if (Pattern.compile("\\b(a+d+d+|adding|adds)\\b.*b+u+y+e+r+\\b")
                .matcher(message).find()) {
            return "This is how to add a buyer!\n"
                    + "buyer n/{name} p/{phone number} e/{email}";
        } else if (Pattern.compile("\\b(a+d+d+|adding|adds)\\b.*s+e+l+e+r+\\b")
                .matcher(message).find()) {
            return "This is how to add a seller!\n"
                    + "seller n/{name} p/{phone number} e/{email}";
        } else if (Pattern.compile("\\b(a+d+d+|adding|adds)\\b.*l+i+s+t+i+n+g+\\b")
                .matcher(message).find()) {
            return "This is how to add a listing!\n"
                    + "listing n/{name} pr/{price} ar/{area} add/{address} reg/{region} sel/{seller} "
                    + "(Optional: buy/{buyer1} buy/{buyer2} ...)";
        } else if (Pattern.compile("\\b(a+d+d+|adding|adds)\\b.*a+p+o+i+n+t+m+e+n+t+\\b")
                .matcher(message).find()) {
            return "This is how to add an appointment!\n"
                    + "apt {index} d/{date} fr/{start time} to/{end time}";
        } else if (Pattern.compile("\\b(d+e+l+e+t+e+|deleted|deleting|deletes)\\b.*c+l+i+e+n+t+\\b")
                .matcher(message).find()) {
            return "We categorise clients into buyers and sellers for clarity of our users!\n"
                    + "Maybe consider:\n"
                    + "• Deleting a buyer\n"
                    + "• Deleting a seller";
        } else if (Pattern.compile("\\b(d+e+l+e+t+e+|deleted|deleting|deletes)\\b.*b+u+y+e+r+\\b")
                .matcher(message).find()) {
            return "This is how to delete a buyer!\n"
                    + "deleteclient {index}";
        } else if (Pattern.compile("\\b(d+e+l+e+t+e+|deleted|deleting|deletes)\\b.*s+e+l+e+r+\\b")
                .matcher(message).find()) {
            return "This is how to delete a seller!\n"
                    + "deleteclient {index}";
        } else if (Pattern.compile("\\b(d+e+l+e+t+e+|deleted|deleting|deletes)\\b.*a+p+o+i+n+t+m+e+n+t+\\b")
                .matcher(message).find()) {
            return "This is how to delete an appointment!\n"
                    + "deleteapt {index}";
        } else if (Pattern.compile("\\b(d+e+l+e+t+e+|deleted|deleting|deletes)\\b.*l+i+s+t+i+n+g+\\b")
                .matcher(message).find()) {
            return "This is how to delete a listing!\n"
                    + "deletelisting {index}";
        } else if (Pattern.compile("\\b(e+d+i+t+|editing|edits)\\b.*c+l+i+e+n+t+\\b").matcher(message).find()) {
            return "This is how to edit a client!\n"
                    + "editclient {client index} [n/{name}] [p/{phone number}] [e/{email}] [t/{tag}...]\n"
                    + "Note: At least one field must be specified to edit a client.";
        } else if (Pattern.compile("\\b(e+d+i+t+|editing|edits)\\b.*l+i+s+t+i+n+g+\\b").matcher(message).find()) {
            return "This is how to edit a listing!\n"
                    + "editlisting {listing index} [n/{listing name} pr/{price}] [ar/{area}]"
                    + " [add/{address}] [reg/{region}]\n"
                    + "Note: At least one field must be specified to edit a listing.";
        } else if (Pattern.compile("\\b(s+h+o+w+|display|view)\\b.*c+l+i+e+n+t+s*\\b").matcher(message).find()) {
            return "This is how to show your clients!\n"
                    + "showclients\n"
                    + "Displays all clients in your list. If there are no clients, it will inform you accordingly.";
        } else if (Pattern.compile("\\b(s+h+o+w+|display|view)\\b.*l+i+s+t+i+n+g+s*\\b").matcher(message).find()) {
            return "This is how to show your listings!\n"
                    + "showlistings\n"
                    + "Displays all listings in your system. If there are no listings, it will notify you accordingly.";
        } else if (Pattern.compile("\\bt+h+a+n+k+|t+h+a+n+k+\\s+y+o+u+|t+h+a+n+k+\\s+u+\\b")
                .matcher(message).find()) {
            return "You're welcome! Always happy to help.";
        } else if (Pattern.compile("\\bl+o+v+e+\\b").matcher(message).find()) {
            return "Love is not about possession; it's about appreciation of \n"
                    + "the journey we share together, hand in hand through \n"
                    + "the beautiful chaos of life.";
        } else if (Pattern.compile("\\b(d+e+l+e+t+e+|deleted|deleting|deletes)\\b").matcher(message).find()) {
            return "I assume you are having trouble with the delete command.\n"
                    + "Can you help specify which you are referring to?\n"
                    + "• Deleting a buyer/seller client profile - deleteclient\n"
                    + "• Deleting an appointment - deleteapt\n"
                    + "• Deleting a listing - deletelisting\n"
                    + "• Deleting a buyer from a listing - removelistingbuyers";
        } else if (Pattern.compile("\\b(a+d+d+|adding|adds)\\b").matcher(message).find()) {
            return "I assume you are having trouble with the add command.\n"
                    + "Can you help specify which you are referring to?\n"
                    + "• Adding a buyer/seller client profile - seller/buyer\n"
                    + "• Adding an appointment - apt\n"
                    + "• Adding a listing - listing\n"
                    + "• Adding a buyer to a listing - addlistingbuyers";
        } else if (Pattern.compile("\\b(e+d+i+t+|editing|edits)\\b").matcher(message).find()) {
            return "It seems you want to edit something.\n"
                    + "Can you specify which you are referring to?\n"
                    + "• Editing a client profile (buyer/seller) - editclient\n"
                    + "• Editing a listing - editlisting";
        } else if (Pattern.compile("\\b(list|show|view|see|check|today's)\\b.*appointments?\\b").matcher(message)
                .find()) {
            return "This is how to check today's appointments!\n"
                    + "Command: today\n"
                    + "Usage: Shows all clients with appointments scheduled for today.\n"
                    + "For general listings, you may consider:\n"
                    + "• showlistings - Displays all listings\n"
                    + "• showclients - Displays all clients.";
        } else if (Pattern.compile("\\b(s+h+o+w+|display|view)\\b").matcher(message).find()) {
            return "It seems you want to show something.\n"
                    + "Can you specify which you are referring to?\n"
                    + "• Show clients - showclients\n"
                    + "• Show listings - showlistings\n"
                    + "• Show today's appointments - today\n";
        } else if (Pattern.compile("\\b(m+o+r+e+\\s*i+n+f+o+|i+n+f+o+|v+i+e+w+\\s*d+e+t+a+i+l+s*)\\b")
                .matcher(message).find()) {
            return "This is how to view more information about a client!\n"
                    + "Command: moreinfo {index}\n"
                    + "Example: moreinfo 1\n"
                    + "Opens a window displaying detailed information about the specified client.";
        } else if (Pattern.compile("\\b(c+l+e+a+r+|reset|wipe)\\b").matcher(message).find()) {
            return "It seems you want to clear some data.\n"
                    + "Can you specify which you are referring to?\n"
                    + "• Clear all client data and listings - clear\n"
                    + "• Clear only listings - clearlistings";
        } else if (Pattern.compile("\\bh+e+l+p+\\b").matcher(message).find()) {
            return "Sure! What do you need help with?";
        } else {
            return "I'm sorry, I didn't understand that. Can you please \n"
                    + "rephrase?";
        }
    }
}
