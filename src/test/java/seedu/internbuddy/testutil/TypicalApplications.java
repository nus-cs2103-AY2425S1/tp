package seedu.internbuddy.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.internbuddy.model.application.Application;

/**
 * A utility class containing a list of {@code Application} objects to be used in tests.
 */
public class TypicalApplications {
    public static final Application EMPTY_APPLICATION = new ApplicationBuilder().build();
    public static final Application SWE_APPLICATION = new ApplicationBuilder()
        .withDescription("Software Engineering Intern")
        .withStatus("APPLIED").build();
    public static final Application DS_APPLICATION = new ApplicationBuilder()
        .withDescription("Data Science Intern")
        .withStatus("REJECTED").build();
    public static final Application PM_APPLICATION = new ApplicationBuilder()
        .withDescription("Product Manager Intern")
        .withStatus("INTERVIEWED").build();

    private TypicalApplications() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical applications.
     */
    //TODO: Update this method to return an AddressBook object

    public static List<Application> getTypicalApplications() {
        return new ArrayList<>(Arrays.asList(SWE_APPLICATION, DS_APPLICATION, PM_APPLICATION));
    }
}
