package careconnect.ui;

import java.util.Comparator;

import careconnect.model.person.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * An UI component that displays detailed information of a {@code Person} in the right pane of
 * the window.
 */
public class PersonDetailCard extends UiPart<Region> implements ShiftTabFocusable {

    private static final String FXML = "PersonDetailCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see
     * <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;
    @FXML
    private final LogListPanel logsListPanel;
    @FXML
    private HBox personDetailCardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Text address;
    @FXML
    private Label email;
    @FXML
    private Label appointmentDate;
    @FXML
    private FlowPane tags;
    @FXML
    private StackPane logsListPanelPlaceHolder;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonDetailCard(Person person, MainWindow.FocusItemUpdater focusItemUpdater) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        appointmentDate.setText(person.getAppointmentDate().toString());
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        logsListPanel = new LogListPanel(person.getLogs(), focusItemUpdater);
        logsListPanelPlaceHolder.getChildren().setAll(logsListPanel.getRoot());
    }

    /**
     * Sets focus on the logs within the PersonDetailCard.
     * Currently, logs are the only component in the PersonDetailCard
     * that should be focusable with Shift + Tab navigation.
     * This method directs the focus to logsListPanel by calling logsListPanel.focus().
     */
    @Override
    public void focus() {
        this.logsListPanel.focus();
    }

    protected boolean isLogListEmpty() {
        return this.logsListPanel.isLogListEmpty();
    }
}
