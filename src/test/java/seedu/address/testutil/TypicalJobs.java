package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOBNAME_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REQUIREMENTS_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_BARISTA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.job.Job;

/**
 * A utility class containing a list of {@code Job} objects to be used in tests.
 */
public class TypicalJobs {
    public static final Job TA = new JobBuilder()
            .withName("CS2103 Teaching Assistant")
            .withCompany("National University of Singapore")
            .withSalary("2000")
            .withDescription("Conduct weekly tutorial")
            .withRequirements("GradeA", "Year3")
            .build();

    public static final Job SWE = new JobBuilder()
            .withName("Software Engineer, Google Pay, Core")
            .withCompany("Google, Singapore")
            .withSalary("3000")
            .withDescription(
                    "As a software engineer, you will work on a specific project critical to Google’s needs with "
                            + "opportunities to switch teams and projects as you and our fast-paced business grow and"
                            + " evolve. ")
            .withRequirements("Go", "Kubernetes", "Docker", "5YOE")
            .build();

    // Manually added - Job's details found in {@code CommandTestUtil}
    public static final Job BARISTA = new JobBuilder()
            .withName(VALID_JOBNAME_BARISTA)
            .withCompany(VALID_COMPANY_BARISTA)
            .withSalary(VALID_SALARY_BARISTA)
            .withDescription(VALID_DESCRIPTION_BARISTA)
            .withRequirements(VALID_REQUIREMENTS_BARISTA)
            .build();

    // prevents instantiation
    private TypicalJobs() {
    }

    /**
     * Returns an {@code AddressBook} with all the typical jobs.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Job job : getTypicalJobs()) {
            ab.addJob(job);
        }
        return ab;
    }

    public static List<Job> getTypicalJobs() {
        return new ArrayList<>(Arrays.asList(TA, SWE, BARISTA));
    }
}
