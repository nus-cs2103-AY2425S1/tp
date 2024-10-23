package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.log.Log;

/**
 * Panel containing the list of persons.
 */
public class SessionLogPanel extends UiPart<Region> {
    private static final String FXML = "SessionLogPanel.fxml";

    @FXML
    private ListView<Log> logListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public SessionLogPanel(ObservableList<Log> logList) {
        super(FXML);
        logListView.setItems(logList);
        logListView.setCellFactory(listView -> new SessionLogViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a SessionLog
     */
    class SessionLogViewCell extends ListCell<Log> {
        @Override
        protected void updateItem(Log log, boolean empty) {
            super.updateItem(log, empty);

            if (empty || log == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SessionLogCard(log).getRoot());
            }
        }
    }
}
