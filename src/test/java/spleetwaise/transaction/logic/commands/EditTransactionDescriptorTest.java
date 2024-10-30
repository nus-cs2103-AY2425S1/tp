package spleetwaise.transaction.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.transaction.logic.commands.EditCommand.EditTransactionDescriptor;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;

public class EditTransactionDescriptorTest {

    @Test
    public void equals() {
        EditTransactionDescriptor someDescriptor = new EditTransactionDescriptor();
        someDescriptor.setPerson(TypicalPersons.ALICE);
        someDescriptor.setAmount(new Amount("100"));
        someDescriptor.setDescription(new Description("foo"));
        someDescriptor.setDate(new Date("01012020"));
        someDescriptor.setCategories(new HashSet<>(Arrays.asList(new Category("foo"))));

        // same object -> true
        assertEquals(someDescriptor, someDescriptor);

        // same values -> true
        EditTransactionDescriptor clonedDescriptor = new EditTransactionDescriptor(someDescriptor);
        assertEquals(clonedDescriptor, someDescriptor);

        assertNotEquals(someDescriptor, null);
        assertNotEquals(someDescriptor, 3);

        clonedDescriptor.setPerson(TypicalPersons.CARL);
        assertNotEquals(someDescriptor, clonedDescriptor);
        clonedDescriptor.setPerson(TypicalPersons.ALICE);

        clonedDescriptor.setAmount(new Amount("101"));
        assertNotEquals(someDescriptor, clonedDescriptor);
        clonedDescriptor.setAmount(new Amount("100"));

        clonedDescriptor.setDescription(new Description("foo2"));
        assertNotEquals(someDescriptor, clonedDescriptor);
        clonedDescriptor.setDescription(new Description("foo"));

        clonedDescriptor.setDate(new Date("02012020"));
        assertNotEquals(someDescriptor, clonedDescriptor);
        clonedDescriptor.setDate(new Date("01012020"));

        clonedDescriptor.setCategories(new HashSet<>(Arrays.asList(new Category("foo2"))));
        assertNotEquals(someDescriptor, clonedDescriptor);
        clonedDescriptor.setCategories(new HashSet<>(Arrays.asList(new Category("foo"))));
    }

    @Test
    public void isAnyFieldEditedMethod() {
        EditTransactionDescriptor editTransactionDescriptor = new EditTransactionDescriptor();
        assertFalse(editTransactionDescriptor.isAnyFieldEdited());
        editTransactionDescriptor.setDescription(new Description("foo"));
        assertTrue(editTransactionDescriptor.isAnyFieldEdited());
    }

    @Test
    public void toStringMethod() {
        EditTransactionDescriptor editTransactionDescriptor = new EditTransactionDescriptor();
        String expected =
                EditTransactionDescriptor.class.getCanonicalName() + "{id=" + editTransactionDescriptor.getId()
                        .orElse(null) + ", person=" + editTransactionDescriptor.getPerson().orElse(null) + ", amount"
                        + "=" + editTransactionDescriptor.getAmount().orElse(null) + ", description="
                        + editTransactionDescriptor.getDescription().orElse(null) + ", date="
                        + editTransactionDescriptor.getDate().orElse(null) + ", status="
                        + editTransactionDescriptor.getStatus().orElse(null) + ", categories="
                        + editTransactionDescriptor.getCategories().orElse(null) + "}";
        assertEquals(expected, editTransactionDescriptor.toString());
    }
}
