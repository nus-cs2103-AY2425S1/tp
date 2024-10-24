package seedu.edulog.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edulog.testutil.TypicalStudents.ALICE;
import static seedu.edulog.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import javafx.application.Platform;
import javafx.scene.image.Image;
import seedu.edulog.model.student.Address;
import seedu.edulog.model.student.Email;
import seedu.edulog.model.student.Name;
import seedu.edulog.model.student.Phone;
@ExtendWith(ApplicationExtension.class)
public class StudentCardTest {

    @Test
    public void constructor_normalStudent_displaysCorrectDetails() {
        Platform.runLater(() -> {
            StudentCard studentCard = new StudentCard(ALICE, 1);
            assertEquals(ALICE.getName(), new Name(studentCard.getNameText()));
            assertEquals("1. ", studentCard.getIdText());
            assertEquals(ALICE.getPhone(), new Phone(studentCard.getPhoneText()));
            assertEquals(ALICE.getAddress(), new Address(studentCard.getAddressText()));
            assertEquals(ALICE.getEmail(), new Email(studentCard.getEmailText()));
        });
    }

    @Test
    public void equals_sameStudentCard_returnsTrue() {
        StudentCard studentCard1 = new StudentCard(ALICE, 1);
        StudentCard studentCard2 = new StudentCard(ALICE, 1);

        // Both should be equal as they contain the same data
        assertTrue(studentCard1.equals(studentCard2));
    }

    @Test
    public void equals_differentStudentCards_returnsFalse() {
        StudentCard studentCard1 = new StudentCard(ALICE, 1);
        StudentCard studentCard2 = new StudentCard(BOB, 2);

        // They should not be equal as they contain different student details
        assertFalse(studentCard1.equals(studentCard2));
    }

    /**
     * TODO: Clean this and add comments to all tests in this class
     */
    @Test
    public void uiComponentsTest_defaultInitialisation_unpaidStatusIconIsSetCorrectly() {
        Image unpaidIcon = new Image(getClass().getResource("/images/unpaid.png").toExternalForm());
        StudentCard unpaidStudentCard = new StudentCard(ALICE, 1);
        // by default, initial icon should be unpaid
        assertEquals(unpaidIcon.getUrl(), unpaidStudentCard.getPaidStatusIcon().getUrl());
    }



}
