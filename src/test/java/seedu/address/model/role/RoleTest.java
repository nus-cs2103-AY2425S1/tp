package seedu.address.model.role;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;



import seedu.address.model.person.Person;







public class RoleTest {


    @Test
    public void equals_equalRole() {
        Role role1 = new Attendee();
        Role role2 = new Attendee();
        assertEquals(role1, role2);
    }

    @Test
    public void equals_differentRole() {
        Role role1 = new Attendee();
        Role role2 = new Vendor();
        assert (!role1.equals(role2));
    }

    @Test
    public void equals_nullRole() {
        Role role1 = new Attendee();
        assert (!role1.equals(null));
    }

    @Test
    public void equals_notRole() {
        Role role1 = new Attendee();
        assert (!role1.equals(new ArrayList<Person>()));
    }

    @Test
    public void hashCode_sameRole() {
        Role role1 = new Vendor();
        Role role2 = new Vendor();
        assertEquals(role1.hashCode(), role2.hashCode());
    }

    @Test
    public void hashCode_differentRole() {
        Role role1 = new Vendor();
        Role role2 = new Sponsor();
        assert (role1.hashCode() != role2.hashCode());
    }

    @Test
    public void addPerson_personIsTagged() {
        Role role = new Attendee();
        role.addPerson(ALICE);
        assert role.isTagged(ALICE);
    }

    @Test
    public void isTagged_personInRole() {
        Role role = new Attendee();
        role.addPerson(ALICE);
        assert role.isTagged(ALICE);
    }

    @Test
    public void isTagged_validPerson_failure() {
        Role role = new Attendee();
        assert !role.isTagged(ALICE);
    }

    @Test
    public void removePerson_existingPerson() {
        Role role = new Attendee();
        role.addPerson(ALICE);
        role.removePerson(ALICE);
        assert !role.isTagged(ALICE);
    }

    @Test
    public void removePerson_personNotInRole() {
        Role role = new Attendee();
        assertThrows(IllegalArgumentException.class, () -> role.removePerson(ALICE));
    }



}
