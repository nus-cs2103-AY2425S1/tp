package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.person.Major;
import seedu.address.model.person.Person;

/**
 * A Detail Panel that displays individual person details in the UI.
 * This class is responsible for handling the display of personal information such as name, email, phone number, etc.
 *
 * <p>The FXML file 'DetailPanel.fxml' is used to define the layout of the panel, including labels for displaying the
 * person's name and email.
 *
 * <p>Usage example:
 * <pre>
 *     DetailPanel detailPanel = new DetailPanel();
 *     detailPanel.setPerson(new Person("John Doe", "john.doe@example.com"));
 * </pre>
 */
public class DetailPanel extends UiPart<Region> implements SelectionListener {
    /**
     * The FXML file that represents the layout of the DetailPanel.
     */
    private static final String FXML = "DetailPanel.fxml";

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
    private Label role;
    @FXML
    private Label major;
    @FXML
    private Label tagStart;
    @FXML
    private FlowPane tagDetails;
    @FXML
    private Label meetings;

    private Person person;
    private int displayedIndex;

    /**
     * Constructs a new DetailPanel.
     * This constructor loads the layout from the specified FXML file and initializes
     * the UI components configured in the FXML.
     */
    public DetailPanel() {
        super(FXML);
    }

    /**
     * Sets the person details on the DetailPanel.
     * This method updates the text of the labels with the provided person's details.
     *
     */
    public void updateDetails() {
        name.setText(person.getName().fullName);
        id.setText("ID\t\t: " + displayedIndex);
        phone.setText("Phone\t: " + person.getPhone().value);
        address.setText("Address\t: " + person.getAddress().value);
        email.setText("Email\t: " + person.getEmail().value);
        role.setText("Role\t\t: " + person.getRole());
        major.setText("Major\t: " + getMajorFullName(person.getMajor()));
        tagStart.setText("Tags\t\t: ");
        tagDetails.getChildren().clear(); // necessary to clear existing tags, cus flowpane keeps memory
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tagDetails.getChildren().add(new Label(tag.tagName)));

        meetings.setText("Meetings\t:\n" + (person.getMeetings().toDetailPanelString()));
    }


    /**
     * Event that triggers when a (new) person is selected by user.
     * @param person that user selected from the {@code PersonListPanel}
     * @param index index of the person selected to be displayed in the DetailPanel
     */
    @Override
    public void onPersonSelected(Person person, int index) {
        setPerson(person, index);
    }

    /**
     * Sets the person to be displayed in the {@code DetailPanel}
     * @param person that user selected from the {@code PersonListPanel}
     * @param index index of the person selected to be displayed in the DetailPanel
     */
    public void setPerson(Person person, int index) {
        this.person = person;
        this.displayedIndex = index + 1;
        updateDetails();
    }

    /**
     * Returns the full name of a person's major e.g. cs = Computer Science.
     *
     * @param major the major object to be translated into its full name
     * @return full name of major String
     */
    private String getMajorFullName(Major major) {
        switch (major.toString()) {
        case "cs":
            return "Computer Science";
        case "bza":
            return "Business Analytics";
        case "isys":
            return "Information System";
        case "isec":
            return "Information Security";
        case "ceg":
            return "Computer Engineering";
        default:
            return "Others"; // Default case if the major code doesn't match known ones
        }
    }
}
