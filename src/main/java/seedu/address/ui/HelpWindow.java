package seedu.address.ui;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {
    private static final int URL_WRONG_SYNTAX = -1;
    private static final int URL_CONNECTION_FAIL = 0;
    private static final int URL_CONNECTION_SUCCESS = 1;

    public static final String WRONG_URL = "htt\\ps://google.com/";
    //public static final String WRONG_URL = "https://goegagggle.com/";
    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103-f09-1.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL + "\nNote" +
            ": Internet Connection is required to access the user guide URL (duh)";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Button goToButton;

    @FXML
    private Label helpMessage;

    @FXML
    private Label linkMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        configureDisplay();
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");

        if (!isShowing()) {
            getRoot().hide(); // https://stackoverflow.com/questions/8341305/how-to-remove-javafx-stage-buttons-minimize-maximize-close
        }
        //getRoot().initStyle(StageStyle.UTILITY);
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    private void createHelpMessage(int urlConnectionStatus) {
        assert urlConnectionStatus == URL_CONNECTION_FAIL || urlConnectionStatus == URL_WRONG_SYNTAX
                || urlConnectionStatus == URL_CONNECTION_SUCCESS;
        switch (urlConnectionStatus) {
        case URL_WRONG_SYNTAX-> helpMessage.setText("!!Programming error - coder's problem, sorry");
        case URL_CONNECTION_FAIL -> helpMessage.setText(HELP_MESSAGE + "\n!!Unable to connect to the URL. " +
                "There" +
                " may be no " +
                "internet connection" + "\nWhat about copying the user guide URL to your clipboard first?");
        case URL_CONNECTION_SUCCESS -> helpMessage.setText(HELP_MESSAGE);
        } // no default
    }

    private int checkUrlConnectionStatus() {
        int urlStatus = URL_CONNECTION_SUCCESS;
        try {
            URL url = new URL(USERGUIDE_URL);
            URLConnection connection = url.openConnection();
            connection.connect();
        } catch (MalformedURLException exp) { // should catch all Exception?
            System.out.println(exp.getMessage() + "URL SYNTAX");
            urlStatus = URL_WRONG_SYNTAX;
        }
        catch (IOException expn) {
            System.out.println(expn.getMessage() + "No internet connection");
            urlStatus = URL_CONNECTION_FAIL;
        }
        finally {
            return urlStatus;
        }
    }

    /**
     * Goes to the URL to the user guide
     */
    @FXML
    private void goToUrl() {
        try {
            URI uri = new URI(USERGUIDE_URL);
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException ex) {
            System.out.println(ex.getMessage() + ". URI SYNTAX Programming error");
            helpMessage.setText("Programming error - coder's problem, sorry");
        } catch (IOException expn) {
            System.out.println(expn.getMessage() + ". Desktop is unable to browse");
            helpMessage.setText("There may be an issue with your desktop device. Save the url into your " +
                    "clipboard instead");
        }
    }

    private void configureDisplay() {
        int urlConnectionStatus = checkUrlConnectionStatus();
        createHelpMessage(urlConnectionStatus);
        if (urlConnectionStatus != URL_CONNECTION_SUCCESS) {
            assert urlConnectionStatus == URL_CONNECTION_FAIL || urlConnectionStatus == URL_WRONG_SYNTAX;
            goToButton.setDisable(true);
            return;
        }
        goToButton.setDisable(false);
    }
    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
