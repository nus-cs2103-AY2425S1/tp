package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.model.person.Person;

/**
 * A UI component that displays detailed information of a {@code Person}.
 */
public class DetailedPersonCardWindow extends UiPart<Stage> {

    private static Person personToShow;
    private static final String FXML = "DetailedPersonCardWindow.fxml";

    @FXML
    private VBox cardPane;
    @FXML
    private TextField name;
    @FXML
    private TextField id;
    @FXML
    private TextField phone;
    @FXML
    private TextField address;
    @FXML
    private TextField email;
    @FXML
    private TextField fees;
    @FXML
    private TextField classId;
    @FXML
    private TextField monthsPaid;

    @FXML
    private FlowPane tags;

    public DetailedPersonCardWindow(Stage root) {
        super(FXML, root);
    }

    public DetailedPersonCardWindow() {
        this(new Stage());
    }

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

    public static void setPerson(Person person) {
        personToShow = person;
    }

    /**
     * Shows the Detailed Person Card window.
     */
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
