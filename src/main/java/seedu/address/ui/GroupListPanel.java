package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class GroupListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GroupListPanel.class);

    @FXML
    private ListView<Group> groupListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public GroupListPanel(ObservableList<Group> groupList) {
        super(FXML);
        groupListView.setItems(groupList);
        groupListView.setCellFactory(listView -> new GroupListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class GroupListViewCell extends ListCell<Group> {
        @Override
        protected void updateItem(Group group, boolean empty) {
            super.updateItem(group, empty);

            if (empty || group == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new GroupCard(group, getIndex() + 1).getRoot());
            }
        }
    }

}
