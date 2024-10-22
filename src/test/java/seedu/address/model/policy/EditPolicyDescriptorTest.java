package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.model.policy.PremiumAmount;
import seedu.address.model.policy.CoverageAmount;
import seedu.address.model.policy.ExpiryDate;
import seedu.address.model.policy.PolicyType;

import java.util.Optional;

public class EditPolicyDescriptorTest {

    private EditPolicyDescriptor descriptor;
    private PolicyType policyType;
    private PremiumAmount premiumAmount;
    private CoverageAmount coverageAmount;
    private ExpiryDate expiryDate;

    @BeforeEach
    public void setUp() {
        policyType = PolicyType.HEALTH;
        premiumAmount = new PremiumAmount("1000");
        coverageAmount = new CoverageAmount("5000");
        expiryDate = new ExpiryDate("2025-12-31");
        descriptor = new EditPolicyDescriptor(policyType);
    }

    @Test
    public void constructor_validPolicyType_createsDescriptorWithCorrectType() {
        // Test if the descriptor is initialized with the correct policyType.
        assertEquals(policyType, descriptor.getPolicyType());
    }

    @Test
    public void setPremiumAmount_validAmount_setsCorrectly() {
        // Set a premium amount and check if it was set correctly.
        descriptor.setPremiumAmount(premiumAmount);
        assertEquals(Optional.of(premiumAmount), descriptor.getPremiumAmount());
    }

    @Test
    public void setCoverageAmount_validAmount_setsCorrectly() {
        // Set a coverage amount and check if it was set correctly.
        descriptor.setCoverageAmount(coverageAmount);
        assertEquals(Optional.of(coverageAmount), descriptor.getCoverageAmount());
    }

    @Test
    public void setExpiryDate_validDate_setsCorrectly() {
        // Set an expiry date and check if it was set correctly.
        descriptor.setExpiryDate(expiryDate);
        assertEquals(Optional.of(expiryDate), descriptor.getExpiryDate());
    }

    @Test
    public void isAnyFieldEdited_noFieldsEdited_returnsFalse() {
        // No fields edited, should return false.
        assertFalse(descriptor.isAnyFieldEdited());
    }

    @Test
    public void isAnyFieldEdited_anyFieldEdited_returnsTrue() {
        // After editing any field, isAnyFieldEdited should return true.
        descriptor.setPremiumAmount(premiumAmount);
        assertTrue(descriptor.isAnyFieldEdited());

        descriptor = new EditPolicyDescriptor(policyType); // Resetting
        descriptor.setCoverageAmount(coverageAmount);
        assertTrue(descriptor.isAnyFieldEdited());

        descriptor = new EditPolicyDescriptor(policyType); // Resetting
        descriptor.setExpiryDate(expiryDate);
        assertTrue(descriptor.isAnyFieldEdited());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        // Test if the same object is considered equal.
        assertTrue(descriptor.equals(descriptor));
    }

    @Test
    public void equals_differentObjectSameValues_returnsTrue() {
        // Create another descriptor with the same fields, should be equal.
        EditPolicyDescriptor other = new EditPolicyDescriptor(policyType);
        other.setPremiumAmount(premiumAmount);
        other.setCoverageAmount(coverageAmount);
        other.setExpiryDate(expiryDate);

        descriptor.setPremiumAmount(premiumAmount);
        descriptor.setCoverageAmount(coverageAmount);
        descriptor.setExpiryDate(expiryDate);

        assertTrue(descriptor.equals(other));
    }

    @Test
    public void equals_null_returnsFalse() {
        // Descriptor should not equal null.
        assertFalse(descriptor.equals(null));
    }

    @Test
    public void equals_differentPolicyType_returnsFalse() {
        // Descriptors with different policy types should not be equal.
        EditPolicyDescriptor differentType = new EditPolicyDescriptor(PolicyType.LIFE);
        assertFalse(descriptor.equals(differentType));
    }

    @Test
    public void equals_differentFieldValues_returnsFalse() {
        // Descriptors with different fields should not be equal.
        EditPolicyDescriptor other = new EditPolicyDescriptor(policyType);
        other.setPremiumAmount(new PremiumAmount("2000"));
        assertFalse(descriptor.equals(other));
    }

    @Test
    public void testOptionalMethods_returnEmptyWhenNotSet() {
        // Test Optional fields return empty when not set.
        assertEquals(Optional.empty(), descriptor.getPremiumAmount());
        assertEquals(Optional.empty(), descriptor.getCoverageAmount());
        assertEquals(Optional.empty(), descriptor.getExpiryDate());
    }
}

