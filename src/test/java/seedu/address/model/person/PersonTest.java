package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.group.Group;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void addGroups_addsGroupSuccessfully() {
        Person actualPerson = new PersonBuilder().build();
        Group group = new Group("StudyGroup", List.of(actualPerson));
        actualPerson.addGroups(group);
        assertTrue(actualPerson.getGroups().contains(group));
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE)
                .withClass(VALID_CLASS_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));

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

        // different class -> returns false
        editedAlice = new PersonBuilder(ALICE).withClass(VALID_CLASS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));


        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void addTags() {
        Person expectedPerson = new PersonBuilder().withTags("tag").build();
        Person actualPerson = new PersonBuilder().build();
        Tag tag = new Tag("tag");
        Set<Tag> tags = Set.of(tag);
        actualPerson.addTags(tags);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void deleteTags() {
        Person actualPerson = new PersonBuilder().withTags("tag").build();
        Person expectedPerson = new PersonBuilder().build();
        Tag tag = new Tag("tag");
        Set<Tag> tags = Set.of(tag);
        actualPerson.deleteTags(tags);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void getGroups_returnsImmutableSet() {
        Person person = new PersonBuilder().build();
        Group group = new Group("StudyGroup", List.of(person));
        person.addGroups(group);

        // Verify that modification attempt throws exception
        Set<Group> groups = person.getGroups();
        assertThrows(UnsupportedOperationException.class, () -> groups.add(group));
    }

    @Test
    public void addGroups_groupAlreadyPresent_doesNotDuplicate() {
        Person actualPerson = new PersonBuilder().build();
        Group group = new Group("StudyGroup", List.of(actualPerson));
        actualPerson.addGroups(group); // Add the first time
        actualPerson.addGroups(group); // Add again

        // Ensure the group is not duplicated
        assertEquals(1, actualPerson.getGroups().size());
        assertTrue(actualPerson.getGroups().contains(group));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName()
                + ", studentClass=" + ALICE.getStudentClass()
                + ", phone=" + ALICE.getPhone()
                + ", tags=" + ALICE.getTags()
                + ", groups=" + ALICE.getGroups()
                + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void hashCode_sameAttributes_sameHashCode() {
        // Create two person objects with the same attributes
        Person person1 = new PersonBuilder(ALICE).build();
        Person person2 = new PersonBuilder(ALICE).build();

        // Ensure that two objects with the same attributes have the same hashCode
        assertEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    public void hashCode_differentAttributes_differentHashCode() {
        // Create two person objects with different attributes
        Person person1 = new PersonBuilder(ALICE).build();
        Person person2 = new PersonBuilder(BOB).build();

        // Ensure that two objects with different attributes have different hashCodes
        assertFalse(person1.hashCode() == person2.hashCode());
    }

}
