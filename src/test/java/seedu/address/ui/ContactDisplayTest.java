package seedu.address.ui;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNull;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.testfx.framework.junit5.ApplicationTest;
// import org.testfx.util.WaitForAsyncUtils;

// import javafx.scene.Scene;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.layout.FlowPane;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.VBox;
// import javafx.stage.Stage;
// import seedu.address.model.person.Person;
// import seedu.address.model.person.company.Company;
// import seedu.address.model.person.student.Student;
// import seedu.address.testutil.CompanyBuilder;
// import seedu.address.testutil.PersonBuilder;
// import seedu.address.testutil.StudentBuilder;

public class ContactDisplayTest {

    private ContactDisplay contactDisplay;

    // @Test
    // public void testShowHelpDisplay() {
    //     // Call the method without interaction
    //     contactDisplay.showHelpDisplay();
    // }

    // @Test
    // public void testClear() {
    //     // Call the method without interaction
    //     ContactDisplay contactDisplay = new ContactDisplay();
    //     contactDisplay.clear();
    // }

    // @BeforeEach
    // public void setUp() {
    //     WaitForAsyncUtils.asyncFx(() -> {
    //         contactDisplay = new ContactDisplay();
    //     });
    //     WaitForAsyncUtils.waitForFxEvents();
    // }

    // @Override
    // public void start(Stage stage) {
    //     VBox root = (VBox) contactDisplay.getRoot();
    //     stage.setScene(new Scene(root));
    //     stage.show();
    // }

    // @Test
    // public void initialize_defaultHelpMessageDisplayed() {
    //     WaitForAsyncUtils.asyncFx(() -> {
    //         contactDisplay.initialize();
    //         assertEquals(ContactDisplay.CONDENSED_HELP_MESSAGE, contactDisplay.getHelpLabel().getText());
    //     });
    //     WaitForAsyncUtils.waitForFxEvents();
    // }

    // @Test
    // public void updateContactDetails_personDetailsDisplayed() {
    //     WaitForAsyncUtils.asyncFx(() -> {
    //         Person person = new PersonBuilder().withName("John Doe").withPhone("12345678")
    //                 .withEmail("john@example.com").withAddress("123, Jurong West Ave 6").withTags("friend").build();

    //         contactDisplay.updateContactDetails(person);

    //         assertEquals("Name: John Doe", contactDisplay.getNameLabel().getText());
    //         assertEquals("Phone: 12345678", contactDisplay.getPhoneLabel().getText());
    //         assertEquals("Email: john@example.com", contactDisplay.getEmailLabel().getText());
    //         assertEquals("Address: 123, Jurong West Ave 6", contactDisplay.getAddressLabel().getText());
    //         assertEquals("friend", ((Label) contactDisplay.getTags().getChildren().get(0)).getText());
    //     });
    //     WaitForAsyncUtils.waitForFxEvents();
    // }

    // @Test
    // public void updateContactDetails_companyDetailsDisplayed() {
    //     Company company = new CompanyBuilder().withName("Tech Corp").withPhone("98765432")
    //             .withEmail("tech@example.com").withAddress("1 Tech Street").withIndustry("Software").build();

    //     contactDisplay.updateContactDetails(company);

    //     assertEquals("Name: Tech Corp", contactDisplay.getNameLabel().getText());
    //     assertEquals("Phone: 98765432", contactDisplay.getPhoneLabel().getText());
    //     assertEquals("Email: tech@example.com", contactDisplay.getEmailLabel().getText());
    //     assertEquals("Address: 1 Tech Street", contactDisplay.getAddressLabel().getText());
    //     assertEquals("Industry: Software", contactDisplay.getIndustryStudentIdLabel().getText());
    // }

    // @Test
    // public void updateContactDetails_studentDetailsDisplayed() {
    //     Student student = new StudentBuilder().withName("Jane Smith").withPhone("12345678")
    //             .withEmail("jane@example.com").withAddress("456, Clementi Ave 3").withStudentID("A1234567X").build();

    //     contactDisplay.updateContactDetails(student);

    //     assertEquals("Name: Jane Smith", contactDisplay.getNameLabel().getText());
    //     assertEquals("Phone: 12345678", contactDisplay.getPhoneLabel().getText());
    //     assertEquals("Email: jane@example.com", contactDisplay.getEmailLabel().getText());
    //     assertEquals("Address: 456, Clementi Ave 3", contactDisplay.getAddressLabel().getText());
    //     assertEquals("Student ID: A1234567X", contactDisplay.getIndustryStudentIdLabel().getText());
    // }

    // @Test
    // public void clear_labelsCleared() {
    //     contactDisplay.clear();
    //     assertEquals("Name:", contactDisplay.getNameLabel().getText());
    //     assertEquals("Phone:", contactDisplay.getPhoneLabel().getText());
    //     assertEquals("Email:", contactDisplay.getEmailLabel().getText());
    //     assertEquals("Address:", contactDisplay.getAddressLabel().getText());
    //     assertEquals("", contactDisplay.getIndustryStudentIdLabel().getText());
    // }

    // @Test
    // public void showHelpDisplay_helpMessageDisplayed() {
    //     contactDisplay.showHelpDisplay();

    //     assertEquals(ContactDisplay.CONDENSED_HELP_MESSAGE, contactDisplay.getHelpLabel().getText());
    //     assertNull(contactDisplay.getNameLabel().getText());
    //     assertNull(contactDisplay.getPhoneLabel().getText());
    //     assertNull(contactDisplay.getEmailLabel().getText());
    //     assertNull(contactDisplay.getAddressLabel().getText());
    //     assertNull(contactDisplay.getIndustryStudentIdLabel().getText());
    // }

}
