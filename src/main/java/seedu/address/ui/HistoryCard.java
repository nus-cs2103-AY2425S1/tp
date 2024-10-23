package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.contactrecord.ContactRecord;

/**
 * An UI component that displays information of a {@code CallHistory}.
 */
public class HistoryCard extends UiPart<HBox> {
    private static final String FXML = "HistoryListCard.fxml";

    public final ContactRecord contactRecord;

    @FXML
    private Label indexLabel;
    @FXML
    private Label contactRecordLabel;

    @FXML
    private Label notesLabel;

    /**
     * Creates a {@code HistoryCard} with the given {@code ContactRecord} and index to display.
     */
    public HistoryCard(ContactRecord contactRecord, int index) {
        super(FXML);
        this.contactRecord = contactRecord;
        indexLabel.setText(index + ". ");
        contactRecordLabel.setText(contactRecord.getDate());
        notesLabel.setText(contactRecord.getNotes());
    }
}

