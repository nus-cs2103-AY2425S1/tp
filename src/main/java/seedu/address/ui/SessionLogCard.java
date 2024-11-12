package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.index.Index;
import seedu.address.model.log.Log;

/**
 * A UI component that displays information of a {@code Log}.
 */
public class SessionLogCard extends UiPart<Region> {

    private static final String FXML = "SessionLogCard.fxml";
    @FXML
    private Label id;
    @FXML
    private Label appointmentDate;
    @FXML
    private Label sessionEntry;

    /**
     * Creates a {@code SessionLogCard} with the given {@code log}.
     */
    public SessionLogCard(Log log, Index index) {
        super(FXML);
        this.id.setText("Session log " + index.getOneBased());
        this.appointmentDate.setText(log.getAppointmentDateString());
        this.sessionEntry.setText(log.getTruncatedEntry());
    }
}
