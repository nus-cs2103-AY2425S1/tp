package seedu.sellsavvy.ui;

import static seedu.sellsavvy.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.sellsavvy.commons.core.LogsCenter;
import seedu.sellsavvy.model.customer.Customer;

/**
 * Panel containing the list of persons.
 */
public class CustomerListPanel extends UiPart<Region> {
    private static final String FXML = "CustomerListPanel.fxml";
    private static final String EMPTY_PERSON_LIST_MESSAGE = "You do not have any recorded customers currently.";
    private static final String NO_RELATED_PERSONS_FOUND = "No related customers found.";
    private final Logger logger = LogsCenter.getLogger(CustomerListPanel.class);
    private final ListChangeListener<Customer> orderChangeListener = change -> handleChangeInPersons();
    private ObservableList<Customer> customerList;

    @FXML
    private ListView<Customer> personListView;
    @FXML
    private Label personListEmpty;

    /**
     * Creates a {@code CustomerListPanel} with the given {@code ObservableList}.
     */
    public CustomerListPanel(ObservableList<Customer> customerList) {
        super(FXML);
        this.customerList = customerList;
        customerList.addListener(orderChangeListener);;
        personListView.setItems(customerList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        handleChangeInPersons();
    }

    /**
     * Handles events whether a displayed customer list changes.
     */
    private void handleChangeInPersons() {
        toggleNoPersonsLabel(personListView.getItems().isEmpty());
    }

    /**
     * Toggles whether the {@code personListEmpty} is displayed and determines the
     * message displayed.
     */
    private void toggleNoPersonsLabel(boolean personListIsEmpty) {
        setPersonListEmptyText();
        personListEmpty.setManaged(personListIsEmpty);
        personListEmpty.setVisible(personListIsEmpty);
        personListView.setManaged(!personListIsEmpty);
        personListView.setVisible(!personListIsEmpty);
    }

    /**
     * Sets the text displayed in {@code personListEmpty}.
     */
    private void setPersonListEmptyText() {
        FilteredList<Customer> filteredList = (FilteredList<Customer>) customerList;
        if (filteredList.getPredicate() == PREDICATE_SHOW_ALL_PERSONS) {
            personListEmpty.setText(EMPTY_PERSON_LIST_MESSAGE);
        } else {
            personListEmpty.setText(NO_RELATED_PERSONS_FOUND);
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Customer} using a {@code CustomerCard}.
     */
    class PersonListViewCell extends ListCell<Customer> {
        @Override
        protected void updateItem(Customer customer, boolean empty) {
            super.updateItem(customer, empty);

            if (empty || customer == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CustomerCard(customer, getIndex() + 1).getRoot());
            }
        }
    }

}
