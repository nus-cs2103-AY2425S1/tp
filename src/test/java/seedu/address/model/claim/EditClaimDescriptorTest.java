package seedu.address.model.claim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EditClaimDescriptorTest {

    @Test
    public void constructor_emptyDescriptor_success() {
        EditClaimDescriptor editClaimDescriptor = new EditClaimDescriptor();
        assertFalse(editClaimDescriptor.isAnyFieldEdited());
    }

    @Test
    public void constructor_copyConstructor_success() {
        ClaimStatus status = ClaimStatus.APPROVED;
        String description = "Claim approved for payment";

        EditClaimDescriptor originalDescriptor = new EditClaimDescriptor();
        originalDescriptor.setStatus(status);
        originalDescriptor.setDescription(description);

        EditClaimDescriptor copyDescriptor = new EditClaimDescriptor(originalDescriptor);
        assertEquals(originalDescriptor, copyDescriptor);
    }

    @Test
    public void isStatusEdited_statusSet_success() {
        ClaimStatus status = ClaimStatus.PENDING;
        EditClaimDescriptor editClaimDescriptor = new EditClaimDescriptor();
        editClaimDescriptor.setStatus(status);
        assertTrue(editClaimDescriptor.isStatusEdited());
    }

    @Test
    public void isDescriptionEdited_descriptionSet_success() {
        String description = "Claim under review";
        EditClaimDescriptor editClaimDescriptor = new EditClaimDescriptor();
        editClaimDescriptor.setDescription(description);
        assertTrue(editClaimDescriptor.isDescriptionEdited());
    }

    @Test
    public void isAnyFieldEdited_noFieldsEdited_success() {
        EditClaimDescriptor editClaimDescriptor = new EditClaimDescriptor();
        assertFalse(editClaimDescriptor.isAnyFieldEdited());
    }

    @Test
    public void isAnyFieldEdited_statusEdited_success() {
        ClaimStatus status = ClaimStatus.REJECTED;
        EditClaimDescriptor editClaimDescriptor = new EditClaimDescriptor();
        editClaimDescriptor.setStatus(status);
        assertTrue(editClaimDescriptor.isAnyFieldEdited());
    }

    @Test
    public void isAnyFieldEdited_descriptionEdited_success() {
        String description = "Claim denied due to missing documents";
        EditClaimDescriptor editClaimDescriptor = new EditClaimDescriptor();
        editClaimDescriptor.setDescription(description);
        assertTrue(editClaimDescriptor.isAnyFieldEdited());
    }

    @Test
    public void equals_sameObject_success() {
        ClaimStatus status = ClaimStatus.APPROVED;
        String description = "Claim approved for payment";

        EditClaimDescriptor editClaimDescriptor = new EditClaimDescriptor();
        editClaimDescriptor.setStatus(status);
        editClaimDescriptor.setDescription(description);

        assertTrue(editClaimDescriptor.equals(editClaimDescriptor));
    }

    @Test
    public void equals_differentType_failure() {
        ClaimStatus status = ClaimStatus.APPROVED;
        String description = "Claim approved for payment";

        EditClaimDescriptor editClaimDescriptor = new EditClaimDescriptor();
        editClaimDescriptor.setStatus(status);
        editClaimDescriptor.setDescription(description);

        assertFalse(editClaimDescriptor.equals("Not an EditClaimDescriptor"));
    }

    @Test
    public void equals_differentDescriptor_failure() {
        EditClaimDescriptor editClaimDescriptor1 = new EditClaimDescriptor();
        editClaimDescriptor1.setStatus(ClaimStatus.APPROVED);
        editClaimDescriptor1.setDescription("Claim approved for payment");

        EditClaimDescriptor editClaimDescriptor2 = new EditClaimDescriptor();
        editClaimDescriptor2.setStatus(ClaimStatus.REJECTED);
        editClaimDescriptor2.setDescription("Claim denied");

        assertFalse(editClaimDescriptor1.equals(editClaimDescriptor2));
    }

    @Test
    public void getStatus_noStatusSet_success() {
        EditClaimDescriptor editClaimDescriptor = new EditClaimDescriptor();
        assertFalse(editClaimDescriptor.getStatus().isPresent());
    }

    @Test
    public void getDescription_noDescriptionSet_success() {
        EditClaimDescriptor editClaimDescriptor = new EditClaimDescriptor();
        assertFalse(editClaimDescriptor.getDescription().isPresent());
    }
}
