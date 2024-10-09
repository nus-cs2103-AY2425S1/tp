package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.supplier.Supplier;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Supplier> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Supplier> supplierList) {
        super(FXML);
        personListView.setItems(supplierList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Supplier} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Supplier> {
        @Override
        protected void updateItem(Supplier supplier, boolean empty) {
            super.updateItem(supplier, empty);

            if (empty || supplier == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(supplier, getIndex() + 1).getRoot());
            }
        }
    }

}
