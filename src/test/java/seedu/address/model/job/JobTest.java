package seedu.address.model.job;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_COMPANY_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_DESCRIPTION_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_NAME_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_REQUIREMENTS_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_SALARY_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalJobs.BARISTA;
import static seedu.address.testutil.TypicalJobs.SWE;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.JobBuilder;
import seedu.address.testutil.PersonBuilder;

public class JobTest {
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getSkills().remove(0));
    }

    @Test
    public void isSameJob() {
        // same object -> returns true
        assertTrue(SWE.isSameJob(SWE));

        // null -> returns false
        assertFalse(SWE.isSameJob(null));

        // same name and company, all other attributes different -> returns true
        Job editedSwe = new JobBuilder(SWE)
                .withSalary(VALID_JOB_SALARY_BARISTA)
                .withRequirements(VALID_JOB_REQUIREMENTS_BARISTA)
                .withDescription(VALID_JOB_DESCRIPTION_BARISTA)
                .build();
        assertTrue(SWE.isSameJob(editedSwe));

        // different name, all other attributes same -> returns false
        editedSwe = new JobBuilder(SWE).withName(VALID_JOB_NAME_BARISTA).build();
        assertFalse(SWE.isSameJob(editedSwe));

        // different company, all other attributes same -> returns false
        editedSwe = new JobBuilder(SWE).withCompany(VALID_JOB_COMPANY_BARISTA).build();
        assertFalse(SWE.isSameJob(editedSwe));

        // name differs in case, all other attributes same -> returns false
        Job editedBarista = new JobBuilder(BARISTA).withName(VALID_JOB_NAME_BARISTA.toLowerCase()).build();
        assertFalse(BARISTA.isSameJob(editedBarista));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBarista = new JobBuilder(BARISTA).withName(nameWithTrailingSpaces).build();
        assertFalse(BARISTA.isSameJob(editedBarista));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Job copySwe = new JobBuilder(SWE).build();
        assertTrue(SWE.equals(copySwe));

        // same object -> returns true
        assertTrue(SWE.equals(SWE));

        // null -> returns false
        assertFalse(SWE.equals(null));

        // different type -> returns false
        assertFalse(SWE.equals(5));

        // different job -> returns false
        assertFalse(SWE.equals(BARISTA));

        // different name -> returns false
        Job editedSwe = new JobBuilder(SWE).withName(VALID_JOB_NAME_BARISTA).build();
        assertFalse(SWE.equals(editedSwe));

        // different company -> returns false
        editedSwe = new JobBuilder(SWE).withCompany(VALID_JOB_COMPANY_BARISTA).build();
        assertFalse(SWE.equals(editedSwe));

        // different salary -> returns false
        editedSwe = new JobBuilder(SWE).withSalary(VALID_JOB_SALARY_BARISTA).build();
        assertFalse(SWE.equals(editedSwe));

        // different requirements -> returns false
        editedSwe = new JobBuilder(SWE).withRequirements(VALID_JOB_REQUIREMENTS_BARISTA).build();
        assertFalse(SWE.equals(editedSwe));

        // different description -> returns false
        editedSwe = new JobBuilder(SWE).withDescription(VALID_JOB_DESCRIPTION_BARISTA).build();
        assertFalse(SWE.equals(editedSwe));
    }

    @Test
    public void toStringMethod() {
        String expected =
                Job.class.getCanonicalName() + "{name=" + SWE.getName() + ", company=" + SWE.getCompany() + ", salary="
                        + SWE.getSalary() + ", description=" + SWE.getDescription() + ", requirements="
                        + SWE.getRequirements() + "}";
        assertEquals(expected, SWE.toString());
    }
}
