package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.job.Job;

/**
 * Deletes a job identified using it's displayed index from the address book.
 */
public class DeleteJobCommand extends DeleteCommand<Job> {
    public static final String ENTITY_WORD = "job";
    public static final String MESSAGE_DELETE_JOB_SUCCESS = "Job deleted: Title: %1$s; Location: %2$s.";

    public DeleteJobCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    protected List<Job> getEntityList(Model model) {
        return model.getFilteredJobList();
    }

    @Override
    protected void deleteEntity(Model model, Job jobToDelete) {
        model.deleteJob(jobToDelete);
    }

    @Override
    protected String getSuccessMessage(Job jobToDelete) {
        String name = jobToDelete.getName().toString();
        String company = jobToDelete.getCompany().toString();

        return String.format(MESSAGE_DELETE_JOB_SUCCESS, name, company);
    }
}
