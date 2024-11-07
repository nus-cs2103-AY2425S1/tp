package seedu.address.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
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
    private TextArea detailedPerson;

    @FXML
    private FlowPane tags;

    public DetailedPersonCardWindow(Stage root) {
        super(FXML, root);
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                root.close();
            }
        });
    }

    public DetailedPersonCardWindow() {
        this(new Stage());
    }

    @FXML
    private void initialize() {
        assert personToShow != null;
        StringBuilder detailedPersonText = new StringBuilder();
        detailedPersonText.append("Name: ").append(personToShow.getName().fullName).append("\n\n");
        detailedPersonText.append("Phone: ").append(personToShow.getPhone().value).append("\n\n");
        detailedPersonText.append("Email: ").append(personToShow.getEmail().value).append("\n\n");
        detailedPersonText.append("Address: ").append(personToShow.getAddress().value).append("\n\n");
        detailedPersonText.append("Class Id: ").append(personToShow.getClassId().value).append("\n\n");
        detailedPersonText.append("Fees: ").append(personToShow.getFees().value).append("\n\n");
        String monthsPaid = personToShow.getMonthsPaidToString();
        detailedPersonText.append("Months Paid: ").append(monthsPaid).append("\n\n");
        String tags = personToShow.getTagsToString();
        detailedPersonText.append("Tags: ").append(tags).append("\n");
        detailedPerson.setText(detailedPersonText.toString());


    }

    public static void setPerson(Person person) {
        personToShow = person;
    }

    /**
     * Shows the Detailed Person Card window.
     */
    public void show() {
        getRoot().setTitle(personToShow.getName().fullName);
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
