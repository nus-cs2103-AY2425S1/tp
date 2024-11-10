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
    public static final String LINE_BREAK = "_____________________________________________________________";
    @FXML
    public static final String CONDENSED_HELP_MESSAGE = "Link to the user guide: helpwindow\n"
            + "Pops up a link to the user guide for the user to easily access.\n"
            + "\n" + "Format: helpwindow\n"
            + LINE_BREAK + "\n"
            + "Adding a contact : student OR company\n"
            + "Adds either a student or a company to the address book.\n"
            + "\n" + "Format: student n/NAME s/STUDENT_ID p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…\u200B"
            + "\n" + "Format: company n/NAME i/INDUSTRY p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…\u200B"
            + "\n" + LINE_BREAK + "\n"
            + "Listing all persons : list\n"
            + "Shows a list of all persons in the address book.\n"
            + "\n" + "Format: list"
            + "\n" + LINE_BREAK + "\n"
            + "Viewing a contact : view\n"
            + "Shows the details of a contact.\n"
            + "\n" + "Format: view INDEX"
            + "\n" + LINE_BREAK + "\n"
            + "Editing a person : edit\n"
            + "Edits an existing person in the address book.\n" + "\n"
            + "Format: edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…\u200B"
            + "\n" + LINE_BREAK + "\n"
            + "Locating persons by name : find\n"
            + "Finds persons whose names contain any of the given keywords.\n"
            + "\n" + "Format: find KEYWORD [MORE_KEYWORDS]" + "\n" + LINE_BREAK + "\n"
            + "Filtering contacts by tags : filtertag\n"
            + "Finds contacts whose tags are the same as the specified keyword.\n"
            + "\n" + "Format: filtertag KEYWORD" + "\n" + LINE_BREAK + "\n"
            + "Track contacts by category : track\n"
            + "Tracks and lists all contacts who are in the category of the \nspecified keywords.\n"
            + "\n" + "Format: track CATEGORY" + "\n" + LINE_BREAK + "\n"
            + "Deleting a contact : delete\n"
            + "Deletes the specified person from the address book.\n"
            + "\n" + "Format: delete INDEX" + "\n" + LINE_BREAK + "\n"
            + "Adding tag(s) to a contact: tag\n"
            + "Adds additional tags to the specified contact.\n"
            + "\n" + "Format: tag INDEX t/TAG [t/MORE_TAGS]" + "\n" + LINE_BREAK + "\n"
            + "Deleting tag(s) from contact: deletetag\n"
            + "Deletes the specified tag(s) from the specified contact.\n"
            + "\n" + "Format: deletetag INDEX t/TAG [t/MORE_TAGS]\n"
            + "OR\n"
            + "Format: deletetag all t/TAG [t/MORE_TAGS]" + "\n" + LINE_BREAK + "\n"
            + "Importing CSV files: import\n"
            + "Imports data from a CSV file, based on the path to the CSV file.\n"
            + "\n" + "Format: import /path/to/data/File.csv" + "\n" + LINE_BREAK + "\n"
            + "Exporting CSV files: export\n"
            + "Exports the current data to a CSV file, based on the desired \npath and filename for the CSV.\n"
            + "\n" + "Format: export /path/to/data/File.csv" + "\n" + LINE_BREAK + "\n"
            + "Clearing all entries : clear\n"
            + "Clears all entries from the address book.\n"
            + "\n" + "Format: clear" + "\n" + LINE_BREAK + "\n"
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
        checkIsNullCategory();
        addTagsColor();
    }

    /**
     * Updates the contact display with the details of the specified person.
     *
     * @param person The person whose details are to be displayed.
     */
    public void updateContactDetails(Person person) {
        helpLabel.setText(null);
        updateBasicFields(person);
        updateTags(person);
        updateExtraFields(person);
    }

    /**
     * Updates the extra fields of the student or company.
     *
     * @param person The contact whose details are to be displayed.
     */
    public void updateExtraFields(Person person) {
        if (person instanceof Company) {
            Company company = (Company) person;
            industryStudentIdLabel.setText(String.format("Industry: %s", company.getIndustry().value));
        }
        if (person instanceof Student) {
            Student student = (Student) person;
            industryStudentIdLabel.setText(String.format("Student ID: %s", student.getStudentId().value));
        }
    }

    /**
     * Updates the basic fields of the contact.
     *
     * @param person The contact whose details are to be displayed.
     */
    public void updateBasicFields(Person person) {
        nameLabel.setText(person.getName().fullName);
        categoryLabel.setText(person.getCategoryDisplayName());
        phoneLabel.setText(String.format("Phone: %s", person.getPhone().value));
        emailLabel.setText(String.format("Email: %s", person.getEmail().value));
        addressLabel.setText(String.format("Address: %s", person.getAddress().value));
        tags.getChildren().clear();
    }

    /**
     * Updates the tags of the contact.
     *
     * @param person The contact whose details are to be displayed.
     */
    public void updateTags(Person person) {
        person.getTags().stream()
        .sorted(Comparator.comparing(tag -> tag.tagName))
        .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        addTagsColor();
    }

    /**
     * Clears the contact details, resetting the labels to their default placeholders.
     */
    public void clear() {
        nameLabel.setText(null);
        categoryLabel.setText(null);
        industryStudentIdLabel.setText(null);
        phoneLabel.setText(null);
        emailLabel.setText(null);
        addressLabel.setText(null);
        tags.getChildren().clear();
        helpLabel.setText(null);
        checkIsNullCategory();
        addTagsColor();
    }

    /**
     * Shows the condensed help message over the contact display.
     */
    public void showHelpDisplay() {
        helpLabel.setText(CONDENSED_HELP_MESSAGE);
        nameLabel.setText(null);
        industryStudentIdLabel.setText(null);
        phoneLabel.setText("You can also click on the contacts to view their details!");
        emailLabel.setText("User Guide: https://ay2425s1-cs2103t-t14-2.github.io/tp/UserGuide.html");
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

    /**
     * Adds background color of the category label.
     * Removes the background color of the category label if it is null.
     */
    public void checkIsNullCategory() {
        categoryLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            categoryLabel.getStyleClass().removeAll("student-background", "company-background");
            if (newValue != null) {
                addCategoryColor(newValue);
            }
        });
    }

    /**
     * Adds background color for category.
     * @param value The value of the category.
     */
    public void addCategoryColor(String value) {
        if ("Student".equalsIgnoreCase(value.trim())) {
            categoryLabel.getStyleClass().add("student-background");
        } else if ("Company".equalsIgnoreCase(value.trim())) {
            categoryLabel.getStyleClass().add("company-background");
        }
    }

    /**
     * Adds background color for tags.
     * @tagLabel The tag.
     * @param text The text of the tag.
     * @param i The order of the tag.
     */
    public void addTagColor(Label tagLabel, String text, int i) {
        String message = String.format(("Adding color to tag: %s, index: %d"), text, i);
        System.out.println(message);
        if (text.equalsIgnoreCase("paid")) {
            tagLabel.getStyleClass().add("paid");
        } else {
            tagLabel.getStyleClass().add(i % 2 == 0
                    ? "with-background-1"
                    : "with-background-2");
        }

    }

    /**
     * Adds background color of all tags.
     * Removes the background color of the tags flowpane if it is null.
     */
    public void addTagsColor() {
        System.out.println("Adding tags color.");
        for (int i = 0; i < tags.getChildren().size(); i++) {
            javafx.scene.Node node = tags.getChildren().get(i);
            if (node instanceof Label tagLabel) {
                String text = tagLabel.getText();
                tagLabel.getStyleClass().remove("with-background-1");
                tagLabel.getStyleClass().remove("with-background-2");
                if (!text.isEmpty()) {
                    addTagColor(tagLabel, text, i);
                }
            }
        }
    }
}

