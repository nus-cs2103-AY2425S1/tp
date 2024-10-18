package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.commons.core.dateformatter.DateFormatter.MM_DD_YYYY_FORMATTER;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.dateformatter.DateFormatter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.policy.EducationPolicy;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.LifePolicy;
import seedu.address.model.policy.Policy;

class JsonAdaptedPolicyTest {
    final double premiumAmount = 400.0;
    final double coverageAmount = 4000.0;
    final String expiryDate = "12/23/2024";
    final LocalDate parsedExpiryDate = LocalDate.parse(expiryDate, MM_DD_YYYY_FORMATTER);

    @Test
    public void testToModelType_validLifePolicy() throws Exception {
        final JsonAdaptedPolicy policy = new JsonAdaptedPolicy("life", premiumAmount, coverageAmount, expiryDate);

        Policy modelPolicy = policy.toModelType();
        assertEquals(LifePolicy.class, modelPolicy.getClass());
        assertEquals(premiumAmount, modelPolicy.getPremiumAmount());
        assertEquals(coverageAmount, modelPolicy.getCoverageAmount());
        assertEquals(parsedExpiryDate, modelPolicy.getExpiryDate());
    }
    @Test
    public void testToModelType_validHealthPolicy() throws Exception {
        final JsonAdaptedPolicy policy = new JsonAdaptedPolicy("health", premiumAmount, coverageAmount, expiryDate);

        Policy modelPolicy = policy.toModelType();
        assertEquals(HealthPolicy.class, modelPolicy.getClass());
        assertEquals(premiumAmount, modelPolicy.getPremiumAmount());
        assertEquals(coverageAmount, modelPolicy.getCoverageAmount());
        assertEquals(parsedExpiryDate, modelPolicy.getExpiryDate());
    }

    @Test
    public void testToModelType_validEducationPolicy() throws Exception {
        final JsonAdaptedPolicy policy = new JsonAdaptedPolicy("education", premiumAmount, coverageAmount, expiryDate);

        Policy modelPolicy = policy.toModelType();
        assertEquals(EducationPolicy.class, modelPolicy.getClass());
        assertEquals(premiumAmount, modelPolicy.getPremiumAmount());
        assertEquals(coverageAmount, modelPolicy.getCoverageAmount());
        assertEquals(parsedExpiryDate, modelPolicy.getExpiryDate());
    }

    @Test
    public void testToModelType_invalidPolicy_throwsIllegalValueException() {
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy("foo", premiumAmount, coverageAmount, expiryDate);
        assertThrows(IllegalValueException.class, policy::toModelType, Policy.POLICY_TYPE_MESSAGE_CONSTRAINTS);
    }

    @Test
    public void testToModelType_invalidAmounts_throwsIllegalValueException() {
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy("health", -1, coverageAmount, expiryDate);
        assertThrows(IllegalValueException.class, policy::toModelType, Policy.AMOUNT_MESSAGE_CONSTRAINTS);

        policy = new JsonAdaptedPolicy("health", premiumAmount, -1, expiryDate);
        assertThrows(IllegalValueException.class, policy::toModelType, Policy.AMOUNT_MESSAGE_CONSTRAINTS);
    }

    @Test
    public void testToModelType_invalidExpiryDate_throwsIllegalValueException() {
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy("health", premiumAmount, coverageAmount, "12-23-2024");
        assertThrows(IllegalValueException.class, policy::toModelType, DateFormatter.MM_DD_YYYY_MESSAGE_CONSTRAINTS);
    }
}
