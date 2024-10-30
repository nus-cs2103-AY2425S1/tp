package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Clickable card for HelpWindow to display information about a Command
 */
public class CommandHelpCard extends UiPart<Region> {
    private static final String FXML = "CommandHelpCard.fxml";

    private static CommandBox commandBox;
    private static HelpWindow helpWindow;

    @FXML
    private VBox clickableBox;
    @FXML
    private Label titleLabel;
    @FXML
    private Label explanationLabel;

    private String example;

    /**
     * Creates new CommandHelpCard for HelpWindow
     */
    public CommandHelpCard(String title, String explanation, String example) {
        super(FXML);
        titleLabel.setText(title.toUpperCase());
        explanationLabel.setText(explanation);
        this.example = example;

        clickableBox.setOnMouseClicked(this::handleClick);
        clickableBox.setOnKeyPressed(this::handleKeyPress);
    }

    public static void setCommandBox(CommandBox commandBox) {
        CommandHelpCard.commandBox = commandBox;
    }

    public static void setHelpWindow(HelpWindow helpWindow) {
        CommandHelpCard.helpWindow = helpWindow;
    }

    private void inputCommand() {
        CommandHelpCard.commandBox.setInputText(this.example);
    }

    private void handleClick(MouseEvent mouseEvent) {
        inputCommand();
    }

    private void handleKeyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            inputCommand();
        } else if (keyEvent.getCode() == KeyCode.UP) {
            helpWindow.focusPreviousCard(this);
        } else if (keyEvent.getCode() == KeyCode.DOWN) {
            helpWindow.focusNextCard(this);
        }
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setExplanation(String explanation) {
        explanationLabel.setText(explanation);
    }
}
