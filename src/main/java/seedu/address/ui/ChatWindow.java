package seedu.address.ui;

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

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), e -> {
                        chatArea.appendText(".");
                    }),
                    new KeyFrame(Duration.seconds(2), e -> {
                        chatArea.appendText(".");
                    }),
                    new KeyFrame(Duration.seconds(3), e -> {
                        String chatText = chatArea.getText();
                        chatText = chatText.replace(typingMessage, "");
                        chatText = chatText.replace("..", "");
                        chatArea.setText(chatText);

                        String response = getResponse(message);
                        chatArea.appendText("Assistant: " + response + "\n");
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

        switch (message) {
        case "hello":
        case "hi":
        case "hey":
            return "Hi there! How can I assist you today?";
        case "help":
            return "Sure! What do you need help with?";
        case "goodbye":
        case "bye":
            return "Goodbye! Have a great day!";
        case "thank":
        case "thanks":
        case "thank you":
        case "thank u":
            return "You're welcome! Always happy to help.";
        case "love":
            return "Love is not about possession; it's about appreciation of \n"
                    + "the journey we share together, hand in hand through \n"
                    + "the beautiful chaos of life.";
        default:
            return "I'm sorry, I didn't understand that. Can you please \n"
                    + "rephrase?";
        }
    }
}
