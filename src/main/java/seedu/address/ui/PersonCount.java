package seedu.address.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

/**
 * A ui for the counter that keeps track of number of persons displayed.
 */
public class PersonCount extends UiPart<Region> {

    private static final String FXML = "PersonCount.fxml";
    private static final String DISPLAY_FORMAT = "Currently displaying %d of %d Students.";
    private ReadOnlyAddressBook addressBook;

    @FXML
    private Label personCountDisplay;

    /**
     * Creates a {@code PersonCount} with the given {@code AddressBook} and {@code filteredPersonList}.
     */
    public PersonCount(ObservableList<Person> filteredPersonList, ReadOnlyAddressBook addressBook) {
        super(FXML);

        assert addressBook != null;

        this.addressBook = addressBook;

        personCountDisplay.setText(String.format(DISPLAY_FORMAT, filteredPersonList.size(),
                addressBook.getPersonList().size()));

        filteredPersonList.addListener((ListChangeListener<Person>) c -> {
            if (c.next()) {
                int displayNum = c.getList().size();
                int totalNum = addressBook.getPersonList().size();

                personCountDisplay.setText(String.format(DISPLAY_FORMAT, displayNum, totalNum));
            }
        });

    }

}
