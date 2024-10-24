package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.model.person.Person;

public class DetailedPersonCardWindow extends UiPart<Stage> {
    private static final String FXML = "DetailedPersonCardWindow.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public static Person personToShow;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label fees;
    @FXML
    private Label classId;
    @FXML
    private Label monthsPaid;

    @FXML
    private FlowPane tags;

    @FXML
    private void initialize() {
        assert personToShow != null;
        name.setText("Name:  " + personToShow.getName().fullName);
        phone.setText("Phone:  " + personToShow.getPhone().value);
        address.setText("Address:  " + personToShow.getAddress().value);
        email.setText("Email:  " + personToShow.getEmail().value);
        fees.setText("Fees:  " + personToShow.getFees().value);
        classId.setText("Class ID:  " + personToShow.getClassId().value);
        monthsPaid.setText("Months Paid:  " + personToShow.getMonthsPaid().stream()
                .map(monthPaid -> monthPaid.monthPaidValue)
                .reduce((curr, next) -> curr + " " + next)
                .orElse("(empty)"));
    }

    public DetailedPersonCardWindow(Stage root) {
        super(FXML, root);
    }

    public DetailedPersonCardWindow() {
        this(new Stage());
    }

    public static void setPerson(Person person) {
        personToShow = person;
    }

    public void show() {
        getRoot().show();
        getRoot().centerOnScreen();
    }

    public boolean isShowing() {
        return getRoot().isShowing();
    }

    public void close() {
        getRoot().close();
    }

    public void focus() {
        getRoot().requestFocus();
    }



}
