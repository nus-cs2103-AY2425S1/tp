package seedu.address.model.job;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_DESCRIPTION_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_REQUIREMENTS_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_SALARY_BARISTA;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalJobs.BARISTA;
import static seedu.address.testutil.TypicalJobs.SWE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.testutil.JobBuilder;

class UniqueJobListTest {
    private final UniqueJobList uniqueJobList = new UniqueJobList();

    @Test
    public void contains_nullJob_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueJobList.contains(null));
    }

    @Test
    public void contains_jobNotInList_returnsFalse() {
        assertFalse(uniqueJobList.contains(SWE));
    }

    @Test
    public void contains_jobInList_returnsTrue() {
        uniqueJobList.add(SWE);
        assertTrue(uniqueJobList.contains(SWE));
    }

    @Test
    public void contains_jobWithSameIdentityFieldsInList_returnsTrue() {
        uniqueJobList.add(SWE);
        Job editedSwe = new JobBuilder(SWE)
                .withSalary(VALID_JOB_SALARY_BARISTA)
                .withRequirements(VALID_JOB_REQUIREMENTS_BARISTA)
                .withDescription(VALID_JOB_DESCRIPTION_BARISTA)
                .build();
        assertTrue(uniqueJobList.contains(editedSwe));
    }

    @Test
    public void add_nullJob_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueJobList.add(null));
    }

    @Test
    public void add_duplicateJob_throwsDuplicateJobException() {
        uniqueJobList.add(SWE);
        assertThrows(DuplicateJobException.class, () -> uniqueJobList.add(SWE));
    }

    @Test
    public void setJobs_nullUniqueJobList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueJobList.setJobs((UniqueJobList) null));
    }

    @Test
    public void setJobs_uniqueJobList_replacesOwnListWithProvidedUniqueJobList() {
        uniqueJobList.add(SWE);
        UniqueJobList expecteduniqueJobList = new UniqueJobList();
        expecteduniqueJobList.add(BARISTA);
        uniqueJobList.setJobs(expecteduniqueJobList);
        assertEquals(expecteduniqueJobList, uniqueJobList);
    }

    @Test
    public void setJobs_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueJobList.setJobs((List<Job>) null));
    }

    @Test
    public void setJobs_list_replacesOwnListWithProvidedList() {
        uniqueJobList.add(SWE);
        List<Job> jobList = Collections.singletonList(BARISTA);
        uniqueJobList.setJobs(jobList);
        UniqueJobList expecteduniqueJobList = new UniqueJobList();
        expecteduniqueJobList.add(BARISTA);
        assertEquals(expecteduniqueJobList, uniqueJobList);
    }

    @Test
    public void setJobs_listWithDuplicateJobs_throwsDuplicateJobException() {
        List<Job> listWithDuplicateJobs = Arrays.asList(SWE, SWE);
        assertThrows(DuplicateJobException.class, () -> uniqueJobList.setJobs(listWithDuplicateJobs));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniqueJobList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueJobList.asUnmodifiableObservableList().toString(), uniqueJobList.toString());
    }

    @Test
    public void equals() {

        // same object -> returns true
        assertTrue(uniqueJobList.equals(uniqueJobList));

        // null -> returns false
        assertFalse(uniqueJobList.equals(null));

        // different type -> returns false
        assertFalse(uniqueJobList.equals(5));
    }
}
