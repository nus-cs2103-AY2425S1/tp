package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DOE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RoleCommand.PersonWithRoleDescriptor;
import seedu.address.testutil.PersonWithRoleDescriptorBuilder;

public class TagPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        PersonWithRoleDescriptor descriptorWithSameValues = new PersonWithRoleDescriptor(DESC_DOE);
        assertTrue(DESC_DOE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_DOE.equals(DESC_DOE));

        // different values -> returns false
        assertFalse(DESC_DOE.equals(DESC_BOB));

        // different role -> returns false
        PersonWithRoleDescriptor DoeWithRole = new PersonWithRoleDescriptorBuilder(DESC_DOE).withRole("colleague").build();
        assertFalse(DESC_DOE.equals(DoeWithRole));

        // different role (multiple tags) -> returns false
        DoeWithRole = new PersonWithRoleDescriptorBuilder(DESC_DOE).withRole("friend").build();
        assertFalse(DESC_DOE.equals(DoeWithRole));
    }


    @Test
    public void toStringMethod() {
        PersonWithRoleDescriptor personWithRoleDescriptor = new PersonWithRoleDescriptor();
        String expected = PersonWithRoleDescriptor.class.getCanonicalName()
                + "{name=null, phone=null, email=null, address=null, role=null}";
        assertEquals(expected, personWithRoleDescriptor.toString());
    }

}
