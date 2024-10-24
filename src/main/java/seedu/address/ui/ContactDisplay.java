package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;
import seedu.address.model.person.company.Company;
import seedu.address.model.person.student.Student;

/**
 * A UI component that displays detailed information of a selected contact.
 */
public class ContactDisplay extends UiPart<Region> {
    @FXML
    public static final String CONDENSED_HELP_MESSAGE = "Adding a contact : student OR company\n"
            + "Adds either a student or a company to the address book.\n"
            + "\n" + "Format: student n/NAME s/STUDENT_ID p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…\u200B"
            + "\n" + "Format: company n/NAME i/INDUSTRY p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…\u200B" + "\n---\n"
            + "Listing all persons : list\n"
            + "Shows a list of all persons in the address book.\n"
            + "\n" + "Format: list\n---\n"
            + "Editing a person : edit\n"
            + "Edits an existing person in the address book.\n" + "\n"
            + "Format: edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…\u200B \n---\n"
            + "Locating persons by name : find\n"
            + "Finds persons whose names contain any of the given keywords.\n"
            + "\n" + "Format: find KEYWORD [MORE_KEYWORDS]\n---\n"
            + "Filtering contacts by tags : filtertag\n"
            + "Finds contacts whose tags are the same as the specified keyword.\n"
            + "\n" + "Format: filtertag KEYWORD\n---\n"
            + "Track contacts by category : track\n"
            + "Tracks and lists all contacts who are in the category of the specified keywords.\n"
            + "\n" + "Format: track CATEGORY\n---\n"
            + "Deleting a contact : delete\n"
            + "Deletes the specified person from the address book.\n"
            + "\n" + "Format: delete INDEX\n---\n"
            + "Clearing all entries : clear\n"
            + "Clears all entries from the address book.\n"
            + "\n" + "Format: clear\n---\n"
            + "Exiting the program : exit\n"
            + "Exits the program.\n" + "\n" + "Format: exit\n";
    public static final String FXML = "ContactDisplay.fxml";
    @FXML
    private VBox cardPane;
    @FXML
    private Label helpLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label industryStudentIdLabel;
    @FXML
    private Label tagLabel;
    @FXML
    private FlowPane tags;
    @FXML
    private ScrollPane scrollPane;

    /**
     * Constructs a ContactDisplay with default placeholder labels.
     */
    public ContactDisplay() {
        super(FXML);
    }

    /**
     * initializes the nameLabel with the help message.
     */
    @FXML void initialize() {
        helpLabel.setText(CONDENSED_HELP_MESSAGE);
    }

    /**
     * Updates the contact display with the details of the specified person.
     *
     * @param person The person whose details are to be displayed.
     */
    public void updateContactDetails(Person person) {
        helpLabel.setText(null);
        nameLabel.setText("Name: " + person.getName().fullName);
        categoryLabel.setText(person.getCategoryDisplayName());
        phoneLabel.setText("Phone: " + person.getPhone().value);
        emailLabel.setText("Email: " + person.getEmail().value);
        addressLabel.setText("Address: " + person.getAddress().value);
        tags.getChildren().clear();
        person.getTags().stream()
        .sorted(Comparator.comparing(tag -> tag.tagName))
        .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        if (person instanceof Company) {
            Company company = (Company) person;
            industryStudentIdLabel.setText("Industry: " + company.getIndustry().value);
        }
        if (person instanceof Student) {
            Student student = (Student) person;
            industryStudentIdLabel.setText("Student ID: " + student.getStudentID());
        }
    }

    /**
     * Updates the contact display with the details of the specified person.
     *
     * @param company The company whose details are to be displayed.
     */
    public void updateContactDetails(Company company) {
        nameLabel.setText("Name: " + company.getName().fullName);
        categoryLabel.setText("Category: " + company.getCategoryDisplayName());
        industryStudentIdLabel.setText("Industry: " + company.getIndustry().value);
        phoneLabel.setText("Phone: " + company.getPhone().value);
        emailLabel.setText("Email: " + company.getEmail().value);
        addressLabel.setText("Address: " + company.getAddress().value);
        tags.getChildren().clear();
        company.getTags().stream()
        .sorted(Comparator.comparing(tag -> tag.tagName))
        .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Updates the contact display with the details of the specified person.
     *
     * @param student The person whose details are to be displayed.
     */
    public void updateContactDetails(Student student) {
        nameLabel.setText("Name: " + student.getName().fullName);
        categoryLabel.setText("Category: " + student.getCategoryDisplayName());
        industryStudentIdLabel.setText("Student ID: " + student.getStudentID());
        phoneLabel.setText("Phone: " + student.getPhone().value);
        emailLabel.setText("Email: " + student.getEmail().value);
        addressLabel.setText("Address: " + student.getAddress().value);
        tags.getChildren().clear();
        student.getTags().stream()
        .sorted(Comparator.comparing(tag -> tag.tagName))
        .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Clears the contact details, resetting the labels to their default placeholders.
     */
    public void clear() {
        nameLabel.setText("Name:");
        categoryLabel.setText(null);
        industryStudentIdLabel.setText("");
        phoneLabel.setText("Phone:");
        emailLabel.setText("Email:");
        addressLabel.setText("Address:");
        tags.getChildren().clear();
        helpLabel.setText(null);
    }

    /**
     * Shows the condensed help message over the contact display.
     */
    public void showHelpDisplay() {
        helpLabel.setText(CONDENSED_HELP_MESSAGE);
        nameLabel.setText(null);
        industryStudentIdLabel.setText(null);
        phoneLabel.setText(null);
        emailLabel.setText(null);
        addressLabel.setText(null);
        categoryLabel.setText(null);
        tags.getChildren().clear();
    }

    /**
     * Returns the scrollPane.
     */
    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    /**
     * Returns the nameLabel for testing.
     */
    public Label getNameLabel() {
        return nameLabel;
    }

    /**
     * Returns the phoneLabel for testing.
     */
    public Label getPhoneLabel() {
        return phoneLabel;
    }

    /**
     * Returns the emailLabel for testing.
     */
    public Label getEmailLabel() {
        return emailLabel;
    }

    /**
     * Returns the tags for testing.
     */
    public FlowPane getTags() {
        return tags;
    }

    /**
     * Returns the industryStudentIdLabel for testing.
     */
    public Label getIndustryStudentIdLabel() {
        return industryStudentIdLabel;
    }

    /**
     * Returns the helpLabel for testing.
     */
    public Label getHelpLabel() {
        return helpLabel;
    }

    public Label getAddressLabel() {
        return addressLabel;
    }


}

