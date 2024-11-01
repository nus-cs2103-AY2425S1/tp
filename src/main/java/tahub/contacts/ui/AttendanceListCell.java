package tahub.contacts.ui;

import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import tahub.contacts.model.course.AttendanceSession;

public class AttendanceListCell extends ListCell<AttendanceSession> {
    @Override
    protected void updateItem(AttendanceSession session, boolean empty) {
        super.updateItem(session, empty);

        if (empty || session == null) {
            setText(null);
            setGraphic(null);
            return;
        }

        Label statusLabel = new Label(session.getIsSessionAttended() ? "Present ✓" : "Absent ✗");
        statusLabel.setTextFill(session.getIsSessionAttended() ? Color.GREEN : Color.RED);
        setGraphic(statusLabel);
    }
}
