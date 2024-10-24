package seedu.edulog.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.edulog.model.student.Phone;
import seedu.edulog.model.student.Student;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class StudentCard extends UiPart<Region> {

    private static final String FXML = "StudentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on EduLog level 4</a>
     */

    public final Student student;

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
    private FlowPane tags;
    @FXML
    private ImageView paidStatusIcon;

    public final Image unpaidIcon = new Image(getClass().getResource("/images/unpaid.png").toExternalForm());
    public final Image paidIcon = new Image(getClass().getResource("/images/paid.png").toExternalForm());


    /**
     * Creates a {@code StudentCode} with the given {@code Student} and index to display.
     */
    public StudentCard(Student student, int displayedIndex) {
        super(FXML);
        this.student = student;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        phone.setText(student.getPhone().value);
        address.setText(student.getAddress().value);
        email.setText(student.getEmail().value);
        student.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        paidStatusIcon.setImage(student.getHasPaid() ? paidIcon : unpaidIcon);
    }

    /**
     * Creates a copy of this {@code StudentCard} instance.
     *
     * <p>This method is used primarily for testing purposes to obtain an
     * independent copy of the current instance. The copy includes the same
     * {@code Student} instance and the displayed index as in the original
     * {@code StudentCard}. Note that the copy will reflect the current state
     * of the {@code StudentCard}, but modifications to the copy will not
     * affect the original object.</p>
     *
     * @return a new {@code StudentCard} instance that is a copy of this
     *         {@code StudentCard}.
     */
    public StudentCard getCopy() {
        return new StudentCard(this.student, Integer.parseInt(this.id.getText()));
    }

    /**
     * Returns the text displayed for the student's name.
     *
     * @return the student's name as a {@code String}.
     */
    public String getNameText() {
        return name.getText();
    }

    /**
     * Returns the text displayed for the student's ID.
     *
     * @return the student's ID as a {@code String}.
     */
    public String getIdText() {
        return id.getText();
    }

    /**
     * Returns the text displayed for the student's phone number.
     *
     * @return the student's phone number as a {@code String}.
     */
    public String getPhoneText() {
        return phone.getText();
    }

    /**
     * Returns the text displayed for the student's address.
     *
     * @return the student's address as a {@code String}.
     */
    public String getAddressText() {
        return address.getText();
    }

    /**
     * Returns the text displayed for the student's email.
     *
     * @return the student's email as a {@code String}.
     */
    public String getEmailText() {
        return email.getText();
    }

    /**
     * Returns the image representing the student's payment status.
     *
     * <p>The image can either be the paid icon or the unpaid icon, depending on the student's status.</p>
     *
     * @return the student's payment status image as an {@code Image}. Since {@code Image} is immutable,
     *         this method safely returns the same instance of the image.
     */
    public Image getPaidStatusIcon() {
        return paidStatusIcon.getImage();
    }

    /**
     * Checks if this {@code StudentCard} is equal to another object.
     *
     * <p>This method compares the current {@code StudentCard} instance
     * with the specified object. It returns {@code true} if the other
     * object is also a {@code StudentCard} and all relevant fields
     * (name, ID, phone, address, email, and payment status) are equal.
     * If the object is the same instance or if the other object is
     * {@code null}, it returns {@code true}.</p>
     *
     * @param other the object to be compared for equality with this
     *              {@code StudentCard}.
     * @return {@code true} if the specified object is equal to this
     *         {@code StudentCard}; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentCard)) {
            return false;
        }

        StudentCard otherStudentCard = (StudentCard) other;
        boolean isEqualName = this.name.getText().equals(otherStudentCard.name.getText());
        boolean isEqualId = this.id.getText().equals(otherStudentCard.id.getText());
        boolean isEqualPhone = this.phone.getText().equals(otherStudentCard.phone.getText());
        boolean isEqualAddress = this.address.getText().equals(otherStudentCard.address.getText());
        boolean isEqualEmail = this.email.getText().equals(otherStudentCard.email.getText());
        boolean isEqualPaidStatus = this.paidStatusIcon.getImage().getUrl().equals(otherStudentCard.paidStatusIcon.getImage().getUrl());

        return isEqualName && isEqualId && isEqualPhone && isEqualAddress && isEqualEmail && isEqualPaidStatus;
    }

}