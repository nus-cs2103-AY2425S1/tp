package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.history.HistoryCommand;

/**
 * A UI component that displays information of a {@code Command}.
 */
public class HistoryCommandCard extends UiPart<Region> {

    private static final String FXML = "HistoryCommandListCard.fxml";

    public final HistoryCommand command;

    @FXML
    private HBox cardPane;
    @FXML
    private VBox vBox;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label commandDetails;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public HistoryCommandCard(HistoryCommand command, int displayedIndex) {
        super(FXML);
        this.command = command;
        assert command != null : "command being added to history should not be null";
        id.setText(displayedIndex + ". ");
        commandDetails.setText(command.getOriginalCommandText());
    }
}
