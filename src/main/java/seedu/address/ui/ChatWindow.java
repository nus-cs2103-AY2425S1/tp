package seedu.address.ui;

import java.util.regex.Pattern;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
                    new KeyFrame(Duration.seconds(0.5), e -> {
                        chatArea.appendText(".");
                    }),
                    new KeyFrame(Duration.seconds(0.5), e -> {
                        String chatText = chatArea.getText();
                        chatText = chatText.replace(typingMessage, "");
                        chatText = chatText.replace("..", "");
                        chatArea.setText(chatText);

                        String response = getResponse(message);
                        chatArea.appendText("Assistant: " + response + "\n");
                        userInput.setDisable(false);
                    })
            );

            timeline.setCycleCount(1);
            timeline.play();
        }
    }

    /**
     * Returns a simple response based on the user input message.
     *
     * @param message The user's input message.
     * @return The assistant's response as a String.
     */
    public String getResponse(String message) {
        message = message.toLowerCase().trim();

        if (Pattern.compile("\\bh+e+l+o+\\b").matcher(message).find()
                || Pattern.compile("\\bh+i+\\b").matcher(message).find()
                || Pattern.compile("\\bh+e+y+\\b").matcher(message).find()) {
            return "Hi there! How can I assist you today?";
        } else if (Pattern.compile("\\b(g+o+o+d+b+y+|b+y+e+|b+y+|bye|goodbye)\\b")
                .matcher(message).find()) {
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
        } else if (Pattern.compile("\\b(a+d+d+|adding|adds)\\b.*a+p+o+i+n+t+m+e+n+t+\\b")
                .matcher(message).find()) {
            return "This is how to add an appointment!\n"
                    + "apt {index} d/{date} fr/{start time} to/{end time}";
        } else if (Pattern.compile("\\b(a+d+d+|adding|adds)\\b.*p+r+o+p+e+r+t+y+\\b")
                .matcher(message).find()) {
            return "This is how to add a property!\n"
                    + "prop {index} prop/{date} fr/{address}";
        } else if (Pattern.compile("\\b(d+e+l+e+t+e+|deleted|deleting|deletes)\\b.*c+l+i+e+n+t+\\b")
                .matcher(message).find()) {
            return "We categorise clients into buyers and sellers for clarity of our users!\n"
                    + "Maybe consider:\n"
                    + "• Deleting a buyer\n"
                    + "• Deleting a seller";
        } else if (Pattern.compile("\\b(d+e+l+e+t+e+|deleted|deleting|deletes)\\b.*b+u+y+e+r+\\b")
                .matcher(message).find()) {
            return "This is how to delete a buyer!\n"
                    + "delete n/{name}";
        } else if (Pattern.compile("\\b(d+e+l+e+t+e+|deleted|deleting|deletes)\\b.*s+e+l+e+r+\\b")
                .matcher(message).find()) {
            return "This is how to delete a seller!\n"
                    + "delete n/{name}";
        } else if (Pattern.compile("\\b(d+e+l+e+t+e+|deleted|deleting|deletes)\\b.*a+p+o+i+n+t+m+e+n+t+\\b")
                .matcher(message).find()) {
            return "This is how to delete an appointment!\n"
                    + "delapt n/{name}";
        } else if (Pattern.compile("\\b(d+e+l+e+t+e+|deleted|deleting|deletes)\\b.*p+r+o+p+e+r+t+y+\\b")
                .matcher(message).find()) {
            return "This is how to delete a property!\n"
                    + "delprop n/{name}";
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
                    + "• Deleting a buyer/seller client profile\n"
                    + "• Deleting an appointment\n"
                    + "• Deleting a property\n"
                    + "• Deleting a listing";
        } else if (Pattern.compile("\\b(a+d+d+|adding|adds)\\b").matcher(message).find()) {
            return "I assume you are having trouble with the add command.\n"
                    + "Can you help specify which you are referring to?\n"
                    + "• Adding a buyer/seller client profile\n"
                    + "• Adding an appointment\n"
                    + "• Adding a property\n"
                    + "• Adding a listing";
        } else if (Pattern.compile("\\bh+e+l+p+\\b").matcher(message).find()) {
            return "Sure! What do you need help with?";
        } else {
            return "I'm sorry, I didn't understand that. Can you please \n"
                    + "rephrase?";
        }
    }
}
