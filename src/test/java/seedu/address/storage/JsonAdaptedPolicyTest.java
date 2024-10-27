package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.policy.CoverageAmount;
import seedu.address.model.policy.EducationPolicy;
import seedu.address.model.policy.ExpiryDate;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.LifePolicy;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyType;
import seedu.address.model.policy.PremiumAmount;


class JsonAdaptedPolicyTest {
    final double premiumAmount = 400.0;
    final double coverageAmount = 4000.0;
    final String expiryDate = "12/23/2024";
    final List<JsonAdaptedClaim> claims = new ArrayList<>();

    final PremiumAmount expectedPremiumAmount = new PremiumAmount(premiumAmount);
    final CoverageAmount expectedCoverageAmount = new CoverageAmount(coverageAmount);
    final ExpiryDate expectedExpiryDate = new ExpiryDate(expiryDate);

    @Test
    public void testToModelType_validLifePolicy() throws Exception {
        final JsonAdaptedPolicy policy = new JsonAdaptedPolicy("life", premiumAmount, coverageAmount,
                expiryDate, claims);

        Policy modelPolicy = policy.toModelType();
        assertEquals(LifePolicy.class, modelPolicy.getClass());
        assertEquals(expectedPremiumAmount, modelPolicy.getPremiumAmount());
        assertEquals(expectedCoverageAmount, modelPolicy.getCoverageAmount());
        assertEquals(expectedExpiryDate, modelPolicy.getExpiryDate());
    }

    @Test
    public void testToModelType_validHealthPolicy() throws Exception {
        final JsonAdaptedPolicy policy = new JsonAdaptedPolicy("health", premiumAmount, coverageAmount,
                expiryDate, claims);

        Policy modelPolicy = policy.toModelType();
        assertEquals(HealthPolicy.class, modelPolicy.getClass());
        assertEquals(expectedPremiumAmount, modelPolicy.getPremiumAmount());
        assertEquals(expectedCoverageAmount, modelPolicy.getCoverageAmount());
        assertEquals(expectedExpiryDate, modelPolicy.getExpiryDate());
    }

    @Test
    public void testToModelType_validEducationPolicy() throws Exception {
        final JsonAdaptedPolicy policy = new JsonAdaptedPolicy("education", premiumAmount, coverageAmount,
                expiryDate, claims);

        Policy modelPolicy = policy.toModelType();
        assertEquals(EducationPolicy.class, modelPolicy.getClass());
        assertEquals(expectedPremiumAmount, modelPolicy.getPremiumAmount());
        assertEquals(expectedCoverageAmount, modelPolicy.getCoverageAmount());
        assertEquals(expectedExpiryDate, modelPolicy.getExpiryDate());
    }

    @Test
    public void testToModelType_invalidPolicy_throwsIllegalValueException() {
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy("foo", premiumAmount, coverageAmount,
                expiryDate, claims);
        assertThrows(IllegalValueException.class, policy::toModelType, PolicyType.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void testToModelType_invalidPremiumAmount_throwsIllegalValueException() {
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy("health", -1, coverageAmount,
                expiryDate, claims);
        assertThrows(IllegalValueException.class, policy::toModelType, PremiumAmount.MESSAGE_CONSTRAINTS);

        policy = new JsonAdaptedPolicy("health", 1.555, coverageAmount, expiryDate, claims);
        assertThrows(IllegalValueException.class, policy::toModelType, PremiumAmount.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void testToModelType_invalidCoverageAmount_throwsIllegalValueException() {
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy("health", premiumAmount, -1,
                expiryDate, claims);
        assertThrows(IllegalValueException.class, policy::toModelType, CoverageAmount.MESSAGE_CONSTRAINTS);

        policy = new JsonAdaptedPolicy("health", premiumAmount, 1.555, expiryDate, claims);
        assertThrows(IllegalValueException.class, policy::toModelType, CoverageAmount.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void testToModelType_invalidExpiryDate_throwsIllegalValueException() {
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy("health", premiumAmount, coverageAmount,
                "12-23-2024", claims);
        assertThrows(IllegalValueException.class, policy::toModelType, ExpiryDate.MESSAGE_CONSTRAINTS);
    }
    @Test
    void toModelType_nullPolicyType_throwsIllegalValueException() {
        JsonAdaptedPolicy adaptedPolicy = new JsonAdaptedPolicy(
                null, premiumAmount, coverageAmount, expiryDate, claims);

        IllegalValueException exception = assertThrows(IllegalValueException.class, adaptedPolicy::toModelType);
        assertEquals(String.format(JsonAdaptedPolicy.MISSING_FIELD_MESSAGE_FORMAT,
                PolicyType.class.getSimpleName()), exception.getMessage());
    }
    @Test
    void toModelType_nullExpiryDate_throwsIllegalValueException() {
        JsonAdaptedPolicy adaptedPolicy = new JsonAdaptedPolicy(
                "health", premiumAmount, coverageAmount, null, claims);

        IllegalValueException exception = assertThrows(IllegalValueException.class, adaptedPolicy::toModelType);
        assertEquals(String.format(JsonAdaptedPolicy.MISSING_FIELD_MESSAGE_FORMAT, ExpiryDate.class.getSimpleName()),
                exception.getMessage());
    }
}
