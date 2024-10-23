package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seedu.address.authentication.Authentication;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Login Window for HR Helper.
 */
public class LoginWindow extends UiPart<Stage> {
    private static final String FXML = "LoginWindow.fxml";
    private static final Logger logger = LogsCenter.getLogger(LoginWindow.class);

    private Stage primaryStage;
    private String userName;
    private String passWord;

    @FXML
    private Button loginButton;
    @FXML
    private PasswordField password;
    @FXML
    private TextField username;
    @FXML
    private Label prompt;


    /**
     * Creates a {@code LoginWindow} with the given {@code Stage}.
     */
    public LoginWindow(Stage primaryStage) {
        super(FXML, new Stage());
        this.getRoot().setResizable(false);
        logger.info("=============================[ Initializing Authentication ]===========================");
        // Dependencies
        this.primaryStage = primaryStage;
    }

    @FXML
    void handleButtonPress(ActionEvent event) {
        logger.info("Button pressed");
        try {
            parseDetails();
        } catch (ParseException e) {
            logger.info(e.toString());
        }
    }

    /**
     * Navigates to password field upon Enter key pressed event.
     */
    @FXML
    private void handleEnterKey() {
        logger.info("Enter button pressed");
        password.requestFocus();
    }

    /**
     * Handles the Enter key pressed event.
     */
    @FXML
    private void handleAuthentication() {
        logger.info("Enter button pressed");
        try {
            parseDetails();
        } catch (ParseException e) {
            logger.info(e.toString());
        }
    }

    /**
     * Parse the username and password inputted.
     */
    private void parseDetails() throws ParseException {
        if (username.getText().trim().equals("")) {
            prompt.setText("Enter Username");
            throw new ParseException("Username is empty");
        } else if (password.getText().trim().equals("")) {
            prompt.setText("Enter Password");
            throw new ParseException("Password is empty");
        }
        if (username.getText().contains(" ") || password.getText().contains(" ")) {
            prompt.setText("Username/password has whitespace");
            throw new ParseException("Username/Password contains whitespace");
        }
        userName = username.getText();
        passWord = password.getText();
        username.clear();
        password.clear();
        username.requestFocus();
        authenticate();
    }

    /**
     * Authenticate the username and password inputted
     */
    private void authenticate() {
        boolean success = Authentication.authenticate(userName, passWord);
        if (!success) {
            prompt.setText("Incorrect Username/Password!");
        } else {
            logger.info("Authentication successful!");
            hide();
        }
    }

    /**
     * Shows the login window
     */
    public void show() {
        logger.info("Showing authentication page.");
        getRoot().showAndWait();
    }

    /**
     * Hides the login window
     */
    public void hide() {
        getRoot().close();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        logger.info("Forcibly exiting HR Helper");
        System.exit(0);
    }
}
