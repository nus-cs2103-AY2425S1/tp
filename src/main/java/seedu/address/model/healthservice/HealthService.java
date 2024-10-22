package seedu.address.model.healthservice;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a Health Service in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidHealthserviceName(String)}
 */
public class HealthService {

    public static final String MESSAGE_CONSTRAINTS = "Health Service should be one of the following "
            + "provided by the clinic" + '\n'
            + "Blood Test, Cancer Screening, Vaccination, Consult";

    private enum PossibleServices {
        BLOOD_TEST("BLOOD TEST"),
        CANCER_SCREENING("CANCER SCREENING"),
        VACCINATION("VACCINATION"),
        CONSULT("CONSULT");

        private final String service;

        PossibleServices(String service) {
            this.service = service;
        }

        @Override
        public String toString() {
            return service;
        }

    };

    public final String healthServiceName;

    /**
     * Constructs a {@code HealthService}.
     *
     * @param healthServiceName A valid Health Service name.
     */
    public HealthService(String healthServiceName) {
        requireNonNull(healthServiceName);
        healthServiceName = healthServiceName.trim().toUpperCase();
        checkArgument(isValidHealthserviceName(healthServiceName.toUpperCase()), MESSAGE_CONSTRAINTS);
        this.healthServiceName = healthServiceName;
    }

    /**
     * Returns true if a given string is a valid Health Service name.
     */
    public static boolean isValidHealthserviceName(String test) {
        requireNonNull(test);
        test = test.strip().toUpperCase();
        return test.equals(PossibleServices.VACCINATION.toString())
                || test.equals(PossibleServices.BLOOD_TEST.toString())
                || test.equals(PossibleServices.CONSULT.toString())
                || test.equals(PossibleServices.CANCER_SCREENING.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HealthService)) {
            return false;
        }

        HealthService otherhealthService = (HealthService) other;
        return healthServiceName.equals(otherhealthService.healthServiceName);
    }

    @Override
    public int hashCode() {
        return healthServiceName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + healthServiceName + ']';
    }

}
