package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.consultation.Consultation;

/**
 * A utility class containing a list of {@code Consultation} objects to be used in tests.
 */
public class TypicalConsults {

    public static final Consultation CONSULT_1 = new ConsultationBuilder()
            .withDate("2024-10-21")
            .withTime("10:00").build();
    public static final Consultation CONSULT_2 = new ConsultationBuilder()
            .withDate("2024-10-23")
            .withTime("10:30").build();
    public static final Consultation CONSULT_3 = new ConsultationBuilder()
            .withDate("2024-10-25")
            .withTime("11:30").build();
    public static final Consultation CONSULT_4 = new ConsultationBuilder()
            .withDate("2024-10-27")
            .withTime("13:00").build();
    public static final Consultation CONSULT_5 = new ConsultationBuilder()
            .withDate("2024-10-29")
            .withTime("15:30").build();

    private TypicalConsults() {} // prevents instantiation

    public static List<Consultation> getTypicalConsults() {
        return new ArrayList<>(Arrays.asList(CONSULT_1, CONSULT_2, CONSULT_3, CONSULT_4, CONSULT_5));
    }
}
