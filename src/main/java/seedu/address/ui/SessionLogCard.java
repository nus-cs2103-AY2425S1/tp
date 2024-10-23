package seedu.address.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.fxml.FXML;
import seedu.address.model.log.Log;

public class SessionLogCard extends UiPart<Region> {

    private static final String FXML = "SessionLogCard.fxml";

    @FXML
    private Label appointmentDate;
    @FXML
    private Label sessionEntry;

    public SessionLogCard(Log log) {
        super(FXML);
        this.appointmentDate.setText(log.getAppointmentDate());
        this.sessionEntry.setText(log.getEntry());
    }
}
