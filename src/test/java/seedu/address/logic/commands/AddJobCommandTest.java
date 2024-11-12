package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.job.Job;
import seedu.address.testutil.JobBuilder;
import seedu.address.testutil.ModelStub;

public class AddJobCommandTest {
    private Job validJob = new JobBuilder().withRequirements().build();

    @Test
    public void constructor_nullJob_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddJobCommand(null));
    }

    @Test
    public void execute_jobAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingJobAdded modelStub = new ModelStubAcceptingJobAdded();

        CommandResult commandResult = new AddJobCommand(validJob).execute(modelStub);

        assertEquals(
                String.format(AddJobCommand.MESSAGE_SUCCESS, validJob.getName(),
                        validJob.getCompany(),
                        validJob.getSalary(),
                        validJob.getDescription(),
                        "NIL"),
                commandResult.getFeedbackToUser()
        );
        assertEquals(Arrays.asList(validJob), modelStub.jobsAdded);
    }

    @Test
    public void execute_duplicateJob_throwsCommandException() {
        AddCommand addCommand = new AddJobCommand(validJob);
        ModelStub modelStub = new ModelStubWithJob(validJob);

        assertThrows(CommandException.class, AddJobCommand.MESSAGE_DUPLICATE_JOB, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Job waiter = new JobBuilder().withName("Waiter").build();
        Job engineer = new JobBuilder().withName("Engineer").build();
        AddJobCommand addWaiterCommand = new AddJobCommand(waiter);
        AddJobCommand addEngineerCommand = new AddJobCommand(engineer);

        // same object -> returns true
        assertTrue(addWaiterCommand.equals(addWaiterCommand));

        // same values -> returns true
        AddJobCommand addWaiterCommandCopy = new AddJobCommand(waiter);
        assertTrue(addWaiterCommand.equals(addWaiterCommandCopy));

        // different types -> returns false
        assertFalse(addWaiterCommand.equals(1));

        // null -> returns false
        assertFalse(addWaiterCommand.equals(null));

        // different jobs -> returns false
        assertFalse(addWaiterCommand.equals(addEngineerCommand));
    }

    /**
     * A Model stub that contains a single job.
     */
    private class ModelStubWithJob extends ModelStub {
        private final Job job;

        ModelStubWithJob(Job job) {
            requireNonNull(job);
            this.job = job;
        }

        @Override
        public boolean hasJob(Job job) {
            requireNonNull(job);
            return this.job.isSameJob(job);
        }
    }

    /**
     * A Model stub that always accept the job being added.
     */
    private class ModelStubAcceptingJobAdded extends ModelStub {
        final ArrayList<Job> jobsAdded = new ArrayList<>();

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public boolean hasJob(Job job) {
            requireNonNull(job);
            return jobsAdded.stream()
                    .anyMatch(job::isSameJob);
        }

        @Override
        public void addJob(Job job) {
            requireNonNull(job);
            jobsAdded.add(job);
        }
    }

}
