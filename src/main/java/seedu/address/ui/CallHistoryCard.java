package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.contactrecord.ContactRecord;

/**
 * An UI component that displays information of a {@code CallHistory}.
 */
public class CallHistoryCard extends UiPart<HBox> {
    private static final String FXML = "CallHistoryCard.fxml";

    @FXML
    private Label indexLabel;
    @FXML
    private Label contactRecordLabel;

    /**
     * Creates a {@code CallHistoryCard} with the given {@code CallHistory} and index to display.
     */
    public CallHistoryCard(ContactRecord contactRecord, int index) {
        super(FXML);
        indexLabel.setText(index + ". ");
        contactRecordLabel.setText(contactRecord.toString());
    }
}

