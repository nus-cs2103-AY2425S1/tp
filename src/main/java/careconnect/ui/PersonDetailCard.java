package careconnect.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;

import careconnect.model.person.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * An UI component that displays detailed information of a {@code Person} in the right pane of
 * the window.
 */
public class PersonDetailCard extends UiPart<Region> {

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
    private Label address;
    @FXML
    private Label addressLink;
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
    public PersonDetailCard(Person person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        appointmentDate.setText(person.getAppointmentDate().toString());
        addressLink.setOnMouseClicked(e -> {
            try {
                String q = person.getAddress().value.replaceAll(" ", "+");
                URI link = new URI("https://www.google.com/maps/place/" + q);
                Desktop.getDesktop().browse(link);
            } catch (URISyntaxException | IOException ex) {
                // TODO: handle this properly. we should show a ui to inform the user that
                //  something has gone wrong
                throw new RuntimeException(ex);
            }
        });
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        logsListPanel = new LogListPanel(person.getLogs());
        logsListPanelPlaceHolder.getChildren().setAll(logsListPanel.getRoot());
    }
}
