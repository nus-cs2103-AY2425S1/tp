package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
/**
 * A UI component that displays detailed information of a {@code Person}.
 */
public class PersonDetails extends UiPart<Region> {
    private static final String FXML = "PersonDetails.fxml";


    public final Person person;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label studentId;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label tag;
    @FXML
    private Label course;
    @FXML
    private Label module;
    @FXML
    private ImageView profileImage;
    @FXML
    private Label studentIdHeader;
    @FXML
    private Label contactHeader;
    @FXML
    private Label addressHeader;
    @FXML
    private Label emailHeader;
    @FXML
    private Label moduleHeader;
    /**
     * Creates a {@code PersonDetails} with the given {@code Person}.
     *
     * @param person The person whose details are to be displayed.
     */
    public PersonDetails(Person person) {
        super(FXML);
        this.person = person;
        updateDetails(person);
        Image image = new Image(getClass().getResourceAsStream("/images/user.png"));
        profileImage.setImage(image);
    }

    /**
     * Updates the labels with the details of the given {@code Person}.
     */
    public void updateDetails(Person person) {
        if (person != null) {
            name.setText(person.getName().fullName);
            studentId.setText(person.getStudentId().value);
            phone.setText(person.getPhone().value);
            tag.setText(person.getTag().role.getRole());
            address.setText(person.getAddress().value);
            email.setText(person.getEmail().value);
            course.setText(person.getCourse().course);
            String modulesAsString = person.getModules().stream()
                    .map(m -> m.toString() + "\n")
                    .reduce("", (x, y) -> x + y);
            module.setText((modulesAsString.isEmpty() ? "No enrolled modules" : modulesAsString));
            profileImage.setVisible(true);
            studentIdHeader.setVisible(true);
            contactHeader.setVisible(true);
            addressHeader.setVisible(true);
            emailHeader.setVisible(true);
            moduleHeader.setVisible(true);

        } else {
            name.setText("");
            studentId.setText("");
            phone.setText("");
            address.setText("");
            email.setText("");
            tag.setText("");
            course.setText("");
            module.setText("");
        }
    }
}
