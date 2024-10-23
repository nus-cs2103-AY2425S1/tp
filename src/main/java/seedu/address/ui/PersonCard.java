package seedu.address.ui;

import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.exam.Exam;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

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
    private Label registerNumber;
    @FXML
    private Label sex;
    @FXML
    private Label studentClass;
    @FXML
    private Label ecName;
    @FXML
    private Label ecNumber;
    @FXML
    private FlowPane tags;
    @FXML
    private TableView<Exam> tableView;
    @FXML
    private TableColumn<Exam, String> examNameColumn;
    @FXML
    private TableColumn<Exam, String> examScoreColumn;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        registerNumber.setText(person.getRegisterNumber().value);
        sex.setText(person.getSex().value);
        studentClass.setText(person.getStudentClass().value);
        ecName.setText(person.getEcName().value);
        ecNumber.setText(person.getEcNumber().value);

        examNameColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        examScoreColumn.setCellValueFactory(new PropertyValueFactory<>("examScore"));
        tableView.setItems(FXCollections.observableArrayList(person.getExams()));

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
