package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EditPolicyDescriptorBuilder;

public class EditPolicyDescriptorTest {

    private static final String VALID_PREMIUM_A = "1000";
    private static final String VALID_PREMIUM_B = "2000";
    private static final String VALID_COVERAGE_A = "5000";
    private static final String VALID_COVERAGE_B = "10000";
    private static final String VALID_EXPIRY_A = "11/31/2024";
    private static final String VALID_EXPIRY_B = "11/30/2025";
    private static final PolicyType VALID_POLICY_TYPE_A = PolicyType.HEALTH;
    private static final PolicyType VALID_POLICY_TYPE_B = PolicyType.LIFE;

    @Test
    public void equals() {
        // same values -> returns true
        EditPolicyDescriptor descriptorWithSameValues = new EditPolicyDescriptorBuilder(VALID_POLICY_TYPE_A)
                .withPremiumAmount(VALID_PREMIUM_A)
                .withCoverageAmount(VALID_COVERAGE_A)
                .withExpiryDate(VALID_EXPIRY_A)
                .build();
        EditPolicyDescriptor descriptor = new EditPolicyDescriptorBuilder(VALID_POLICY_TYPE_A)
                .withPremiumAmount(VALID_PREMIUM_A)
                .withCoverageAmount(VALID_COVERAGE_A)
                .withExpiryDate(VALID_EXPIRY_A)
                .build();
        assertTrue(descriptor.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(descriptor.equals(descriptor));

        // null -> returns false
        assertFalse(descriptor.equals(null));

        // different types -> returns false
        assertFalse(descriptor.equals(5));

        // different values -> returns false
        EditPolicyDescriptor descriptorWithDifferentValues = new EditPolicyDescriptorBuilder(VALID_POLICY_TYPE_B)
                .withPremiumAmount(VALID_PREMIUM_B)
                .withCoverageAmount(VALID_COVERAGE_B)
                .withExpiryDate(VALID_EXPIRY_B)
                .build();
        assertFalse(descriptor.equals(descriptorWithDifferentValues));

        // different premium -> returns false
        EditPolicyDescriptor editedDescriptor = new EditPolicyDescriptorBuilder(descriptor)
                .withPremiumAmount(VALID_PREMIUM_B).build();
        assertFalse(descriptor.equals(editedDescriptor));

        // different coverage -> returns false
        editedDescriptor = new EditPolicyDescriptorBuilder(descriptor)
                .withCoverageAmount(VALID_COVERAGE_B).build();
        assertFalse(descriptor.equals(editedDescriptor));

        // different expiry date -> returns false
        editedDescriptor = new EditPolicyDescriptorBuilder(descriptor)
                .withExpiryDate(VALID_EXPIRY_B).build();
        assertFalse(descriptor.equals(editedDescriptor));
    }
}
