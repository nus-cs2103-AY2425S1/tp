package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class GroupTest {

    @Test
    public void constructor_nullGroupName_throwsNullPointerException() {
        PersonBuilder personBuilder = new PersonBuilder();
        List<Person> members = Collections.singletonList(personBuilder.build());
        assertThrows(NullPointerException.class, () -> new Group(null, members));
    }

    @Test
    public void constructor_nullMembers_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Group("Group A", null));
    }

    @Test
    public void getGroupName() {
        Group group = new Group("Group A", Collections.emptyList());
        assertEquals(new GroupName("Group A"), group.getGroupName());
    }

    @Test
    public void getMembers() {
        PersonBuilder personBuilder = new PersonBuilder();
        Person person = personBuilder.build();
        List<Person> members = Arrays.asList(person, person);
        Group group = new Group("Group A", members);
        assertEquals(members, group.getMembers());
    }

    @Test
    public void toString_validGroup_correctStringRepresentation() {
        PersonBuilder personBuilder = new PersonBuilder();
        Person person = personBuilder.build();
        List<Person> members = Arrays.asList(person, person);
        Group group = new Group("Group A", members);
        String expectedString = String.format("[Group: Group A, Members: %s, %s]", PersonBuilder.DEFAULT_NAME,
                PersonBuilder.DEFAULT_NAME);
        assertEquals(expectedString, group.toString());
    }
}
