package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.log.Log;

public class SessionLogDetail extends UiPart<Region> {

    private static final String FXML = "SessionLogDetail.fxml";

    @FXML
    private Label detailDate;

    @FXML
    private Label detailSessionEntry;

    public SessionLogDetail(Log log) {
        super(FXML);
        setLogDetails(log);
    }

    public void setLogDetails(Log log) {
        detailDate.setText(log.getAppointmentDate().toString());
        detailSessionEntry.setText(log.toDetailedString());
    }
}