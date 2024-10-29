package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.history.HistoryCommand;

/**
 * Panel containing the list of persons.
 */
public class HistoryCommandListPanel extends UiPart<Region> {
    private static final String FXML = "HistoryCommandListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(HistoryCommandListPanel.class);

    @javafx.fxml.FXML
    private ListView<HistoryCommand> historyCommandListView;

    /**
     * Creates a {@code HistoryCommandListPanel} with the given {@code ObservableList}.
     */
    public HistoryCommandListPanel(ObservableList<HistoryCommand> commandList) {
        super(FXML);
        commandList.sort(Comparator.comparingInt(HistoryCommand::getIndex).reversed());
        historyCommandListView.setItems(commandList);
        historyCommandListView.setCellFactory(listView -> new HistoryCommandListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Command} using a {@code CommandCard}.
     */
    class HistoryCommandListViewCell extends ListCell<HistoryCommand> {
        @Override
        protected void updateItem(HistoryCommand command, boolean empty) {
            super.updateItem(command, empty);

            if (empty || command == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new HistoryCommandCard(command, command.getIndex() + 1).getRoot());
            }
        }
    }
}
