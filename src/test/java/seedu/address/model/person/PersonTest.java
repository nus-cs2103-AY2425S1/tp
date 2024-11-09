package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_TODO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import seedu.address.model.task.Task;
import seedu.address.model.task.Todo;
import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void personName_invalidNames_throwsIllegalArgumentException() {
        // EP: blank name
        assertThrows(IllegalArgumentException.class, () -> new PersonBuilder().withName(""));
        assertThrows(IllegalArgumentException.class, () -> new PersonBuilder().withName(" "));

        // EP: valid characters for rest of name as first character in name
        assertThrows(IllegalArgumentException.class, () -> new PersonBuilder().withName("[Name"));

        // EP: invalid character in name with other valid characters
        assertThrows(IllegalArgumentException.class, () -> new PersonBuilder().withName("Anne, Marie"));
    }

    @Test
    public void taskNotAssigned_returnsFalse() {
        Person person = ALICE;
        Task task = new Task(VALID_TASK_TODO);
        assertFalse(person.hasTask(task));
    }

    @Test
    public void getTask_taskNotAssigned_throwsNoSuchElementException() {
        // Create a person without any tasks
        Person person = new PersonBuilder().build();
        Task nonExistentTask = new Todo("Non-existent task");

        // Attempt to get a task that isn't assigned to the person
        assertThrows(NoSuchElementException.class, () -> {
            person.getTask(nonExistentTask);
        });
    }


    @Test
    public void getTaskAssigned_returnsSuccessfully() {
        // Create a person with a specific task
        String taskStr = "todo: buy groceries";
        Person person = new PersonBuilder().withTasks(taskStr).build();
        Task newTask = new Todo("buy groceries");

        // Ensure getTask returns the task when it exists
        Task retrievedTask = person.getTask(newTask);
        assertEquals(newTask, retrievedTask, "Expected to retrieve the assigned task successfully.");
    }

    @Test
    public void hasTask_taskAssigned_returnsTrue() {
        Task assignedTask = new Todo("Buy groceries");
        Person person = new PersonBuilder().withTasks("todo: Buy groceries").build();

        // Check that hasTask returns true for the assigned task
        assertTrue(person.hasTask(assignedTask), "Expected to return true for a task that is assigned.");
    }
    @Test
    public void removeTask_taskAssigned_taskRemovedSuccessfully() {
        Person person = new PersonBuilder().withName("assigned").withTasks(VALID_TASK_TODO).build();
        Task task = new Task(VALID_TASK_TODO);
        person.removeTask(task);
        assertFalse(person.hasTask(task));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name is same but differs in case -> returns true
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
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

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different weddings -> returns false
        editedAlice = new PersonBuilder(ALICE).withWeddings(VALID_WEDDING_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tasks -> returns false
        editedAlice = new PersonBuilder(ALICE).withTasks(VALID_TASK_TODO).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void hashCodeTest() {
        Person person1 = new PersonBuilder(ALICE).build();
        Person person2 = new PersonBuilder(ALICE).build();

        // same person -> returns same hashCode
        assertEquals(person1.hashCode(), person2.hashCode());

        // different name -> returns different hashCode
        Person personWithDifferentName = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(person1.hashCode() == personWithDifferentName.hashCode());

        // different weddings -> returns different hashCode
        Person personWithDifferentWeddings = new PersonBuilder(ALICE).withWeddings(VALID_WEDDING_BOB).build();
        assertFalse(person1.hashCode() == personWithDifferentWeddings.hashCode());

        // different tasks -> returns different hashCode
        Person personWithDifferentTasks = new PersonBuilder(ALICE).withTasks(VALID_TASK_TODO).build();
        assertFalse(person1.hashCode() == personWithDifferentTasks.hashCode());
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags()
                + ", weddings=" + ALICE.getWeddings() + ", tasks=" + ALICE.getTasks() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
