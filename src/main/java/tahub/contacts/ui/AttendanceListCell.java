package tahub.contacts.ui;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import tahub.contacts.model.course.AttendanceSession;
/**
 * A custom JavaFX ListCell implementation that displays attendance session information
 * with visual indicators for present/absent status.
 * This class extends to provide a specialized cell representation for attendance sessions
 * in a ListView. Each cell displays the attendance
 * status with a colored label and an appropriate symbol (✓ for present, ✗ for absent).
 */
public class AttendanceListCell extends ListCell<AttendanceSession> {

    /**
     * Updates the content of the list cell based on the provided AttendanceSession.
     * When an AttendanceSession is present, this method creates a colored label indicating
     * the attendance status:
     * Present: Shows "Present ✓" in green
     * Absent: Shows "Absent ✗" in red
     * If the cell is empty or the session is null, all content is cleared.
     *
     * @param session The AttendanceSession object to be displayed in the cell.
     *               Can be null if the cell is empty.
     * @param empty A boolean flag indicating whether the cell is empty.
     */
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
