package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of rental information.
 */
public class RentalInformationListPanel extends UiPart<Region> {
    private static final String FXML = "RentalInformationListPanel.fxml";

    @FXML
    private ListView<Person> rentalInformationListView;

    /**
     * Creates a {@code RentalInformationListPanel} with the given {@code ObservableList}.
     */
    public RentalInformationListPanel(ObservableList<Person> personList) {
        super(FXML);
        rentalInformationListView.setItems(personList);
        rentalInformationListView.setCellFactory(listView -> new RentalInformationListViewCell());
    }

    class RentalInformationListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RentalInformationCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
