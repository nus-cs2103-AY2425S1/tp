package seedu.address.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.exam.Exam;
import seedu.address.model.person.AbsentDate;
import seedu.address.model.person.AbsentReason;
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
    private TableView<AttendanceEntry> tableView1;
    @FXML
    private TableColumn<AttendanceEntry, String> absentDateColumn;
    @FXML
    private TableColumn<AttendanceEntry, String> absentReasonColumn;
    @FXML
    private TableView<Exam> tableView2;
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
        name.setText(person.getDisplayedName());
        phone.setText(person.getDisplayedPhone());
        address.setText(person.getDisplayedAddress());
        email.setText(person.getDisplayedEmail());
        registerNumber.setText(person.getDisplayedRegisterNumber());
        sex.setText(person.getDisplayedSex());
        studentClass.setText(person.getDisplayedStudentClass());
        ecName.setText(person.getDisplayedEcName());
        ecNumber.setText(person.getDisplayedEcNumber());

        examNameColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        examScoreColumn.setCellValueFactory(new PropertyValueFactory<>("examScore"));
        tableView2.setItems(FXCollections.observableArrayList(person.getExams()));

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        List<AttendanceEntry> attendanceEntries = new ArrayList<>();
        for (Map.Entry<AbsentDate, AbsentReason> entry : person.getAttendances().entrySet()) {
            attendanceEntries.add(new AttendanceEntry(entry.getKey().toString(), entry.getValue().toString()));
        }

        ObservableList<AttendanceEntry> observableAttendanceList = FXCollections.observableArrayList(attendanceEntries);
        tableView1.setItems(observableAttendanceList);

        absentDateColumn.setCellValueFactory(new PropertyValueFactory<>("absentDate"));
        absentReasonColumn.setCellValueFactory(new PropertyValueFactory<>("absentReason"));
    }

    /**
     * Represents an entry of attendance containing the absent date and reason for a student's absence.
     */
    public static class AttendanceEntry {

        private final String absentDate;
        private final String absentReason;

        /**
         * Constructs an {@code AttendanceEntry} with the given absent date and reason.
         *
         * @param absentDate The date the student was absent.
         * @param absentReason The reason for the student's absence.
         */
        public AttendanceEntry(String absentDate, String absentReason) {
            this.absentDate = absentDate;
            this.absentReason = absentReason;
        }

        /**
         * Returns the absent date for this attendance entry.
         *
         * @return The absent date as a string.
         */
        public String getAbsentDate() {
            return absentDate;
        }

        /**
         * Returns the absent reason for this attendance entry.
         *
         * @return The reason for absence as a string.
         */
        public String getAbsentReason() {
            return absentReason;
        }
    }

}
