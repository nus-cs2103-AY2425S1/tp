package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.CommandInfo;

/**
 * Panel containing the list of commands.
 */
public class HelpListPanel extends UiPart<Region> {
    private static final String FXML = "HelpListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<CommandInfo> helpListView;

    /**
     * Creates a {@code HelpListPanel} with the given {@code ObservableList}.
     */
    public HelpListPanel(ObservableList<CommandInfo> helpList) {
        super(FXML);
        helpListView.setItems(helpList);
        helpListView.setCellFactory(listView -> new HelpListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of {@code CommandInfo} using a {@code HelpListCard}.
     */
    class HelpListViewCell extends ListCell<CommandInfo> {
        @Override
        protected void updateItem(CommandInfo commandInfo, boolean empty) {
            super.updateItem(commandInfo, empty);

            if (empty || commandInfo == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new HelpListCard(commandInfo).getRoot());
            }
        }
    }

}
