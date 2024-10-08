package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.authentication.Authentication;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;

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
    public Button loginButton;
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

        // Dependencies
        this.primaryStage = primaryStage;
    }

    @FXML
    void handleButtonPress(ActionEvent event) throws Exception {
        logger.info("Button pressed");
        parseDetails();
    }

    /**
     * Parse the username and password inputted.
     */
    private void parseDetails() {
        if (username.getText().trim().equals("")) {
            logger.info("=============================[ Initializing Authentication ]===========================");
            prompt.setText("Enter Username");
        } else if (password.getText().trim().equals("")) {
            prompt.setText("Enter Password");
        } else {
            userName = username.getText();
            passWord = password.getText();
            username.clear();
            password.clear();
            authenticate();
        }
    }

    /**
     * Authenticate the username and password inputted
     */
    private boolean authenticate() {
        boolean success = Authentication.authenticate(userName, passWord);
        if (!success) {
            prompt.setText("Incorrect Username/Password!");
            return false;
        } else {
            logger.info("Authentication successful!");
            hide();
            return true;
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
        getRoot().close();
        primaryStage.close();
        Platform.exit();
        System.exit(0);
    }
}
