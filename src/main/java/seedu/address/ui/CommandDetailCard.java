package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * A UI component that displays information of a command and its use case in the {@code HelpWindow}.
 */
public class CommandDetailCard extends UiPart<Node> {
    private static final String FXML = "CommandDetailCard.fxml";

    @FXML
    private Label command;
    @FXML
    private Label commandDetails;

    /**
     * Creates a {@code CommandDetailCard} with the given command word and command details of the {@code Command}.
     *
     * @param commandWord command word used to call the command.
     * @param commandDetails details of how to use the command.
     */
    public CommandDetailCard(String commandWord, String commandDetails) {
        super(FXML);
        this.command.setText(commandWord);
        this.commandDetails.setText(commandDetails);
    }

}
