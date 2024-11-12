package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_TUTOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    /*@Test
    public void modifyRole_throwsUnsupportedOperationException() {
        // Build a person with a single, immutable role
        Person person = new PersonBuilder().withRole(VALID_ROLE_STUDENT).build();

        // Assuming role's role cannot be modified, attempt to change it and expect an exception
        Role role = person.getRole();
        assertThrows(UnsupportedOperationException.class, () -> {
            role.setRole("Tutor");
        });
    }*/

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(AMY.isSamePerson(AMY));

        // null -> returns false
        assertFalse(AMY.isSamePerson(null));

        // same studentID, all other attributes different -> returns true
        Person editedAmy = new PersonBuilder(AMY).withStudentId(VALID_STUDENTID_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withRole(VALID_ROLE_TUTOR).build();
        assertTrue(AMY.isSamePerson(editedAmy));

        // different studentID, all other attributes same -> returns false
        editedAmy = new PersonBuilder(ALICE).withStudentId(VALID_STUDENTID_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAmy));

        /* name differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));*/
    }

    @Test
    public void updateModules_validInputs_success() {
        // adding module
        Person person = new PersonBuilder().build();
        Module validModule = new Module(CommandTestUtil.VALID_MODULE_AMY);
        ArrayList<Module> updatedModules = new ArrayList<>();
        updatedModules.add(validModule);
        Person personWithModule = person.addModule(validModule);
        assertEquals(personWithModule,
                new Person(person.getStudentId(),
                        person.getName(),
                        person.getPhone(),
                        person.getEmail(),
                        person.getAddress(),
                        person.getCourse(),
                        person.getRole(),
                        updatedModules));

        // adding grade
        Grade validGrade = new Grade(CommandTestUtil.VALID_GRADE_AMY);
        updatedModules.get(0).setGrade(validGrade);
        Person personWithModuleAndGrade = personWithModule.setModuleGrade(validModule, validGrade);
        assertEquals(personWithModuleAndGrade,
                new Person(person.getStudentId(),
                        person.getName(),
                        person.getPhone(),
                        person.getEmail(),
                        person.getAddress(),
                        person.getCourse(),
                        person.getRole(),
                        updatedModules));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different roles -> returns false
        editedAlice = new PersonBuilder(ALICE).withRole(VALID_ROLE_TUTOR).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void deleteModule_moduleExists_moduleRemoved() {
        Person person = new PersonBuilder().build();
        Module module = new Module(CommandTestUtil.VALID_MODULE_AMY);
        person = person.addModule(module);

        Person personAfterDeletion = person.deleteModule(module);

        assertFalse(personAfterDeletion.hasModule(module));
        assertEquals(0, personAfterDeletion.getModules().size());
    }

    @Test
    public void setModules_updatesModulesCorrectly() {
        Person person = new PersonBuilder().build();
        Module module1 = new Module(CommandTestUtil.VALID_MODULE_AMY);
        Module module2 = new Module(CommandTestUtil.VALID_MODULE_BOB);
        ArrayList<Module> newModules = new ArrayList<>();
        newModules.add(module1);
        newModules.add(module2);

        person.setModules(newModules);

        assertEquals(newModules.size(), person.getModules().size());
        assertTrue(person.hasModule(module1));
        assertTrue(person.hasModule(module2));
    }
    @Test
    public void hasModule_moduleExists_returnsTrue() {
        Person person = new PersonBuilder().build();
        Module module = new Module(CommandTestUtil.VALID_MODULE_AMY);
        person = person.addModule(module);

        assertTrue(person.hasModule(module));
    }

    @Test
    public void hasModule_moduleDoesNotExist_returnsFalse() {
        Person person = new PersonBuilder().build();
        Module module = new Module(CommandTestUtil.VALID_MODULE_AMY);

        assertFalse(person.hasModule(module));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{studentId=" + ALICE.getStudentId() + ", name="
                + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", course=" + ALICE.getCourse()
                + ", role=" + ALICE.getRole() + ", modules=" + ALICE.getModules() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
