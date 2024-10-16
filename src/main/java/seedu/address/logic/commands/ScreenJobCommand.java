package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.Job;
import seedu.address.model.person.Person;

/**
 *  Screen all contacts and return a list of candidate that fit a job listing
 *  requirement to save time, filter via contact role for now
 */
public class ScreenJobCommand extends ScreenCommand {

    public static final String ENTITY_WORD = "job";

    public static final String MESSAGE_SUCCESS = "Screen all candidates";
    public static final String MESSAGE_FAILURE = "Invalid index in the job list."
            + "Please enter an appropriate index from the contact list.";

    private final Index targetIndex;
    public ScreenJobCommand(Index jobIndex) {
        this.targetIndex = jobIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Job> jobList = model.getFilteredJobList();

        // Convert jobIndex to zero-based and check if it's valid
        if (targetIndex.getZeroBased() >= jobList.size()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        Job jobToScreen = jobList.get(targetIndex.getZeroBased());
        String jobName = jobToScreen.getName().value;

        // Filter persons by checking if their role is present in the job requirements (case-insensitive)
        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(person -> containsRoleIgnoreCase(jobName, person.getRole().value))
                .collect(Collectors.toList());


        if (matchingPersons.isEmpty()) {
            return new CommandResult("0 contacts found");
        }

        // Show the matching persons and a summary message
        model.updateFilteredPersonList(person -> containsRoleIgnoreCase(jobName, person.getRole().value));
        return new CommandResult(matchingPersons.size() + " contacts found");
    }

    /**
     * Helper method to check if the job name contain the role (case-insensitive).
     */
    private boolean containsRoleIgnoreCase(String jobName, String role) {
        return jobName.toLowerCase().contains(role.toLowerCase());
    }
}
