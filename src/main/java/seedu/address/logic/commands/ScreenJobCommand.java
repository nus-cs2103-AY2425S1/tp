package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COMPANIES;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobRequirements;
import seedu.address.model.person.Person;
import seedu.address.commons.core.index.Index;

import java.util.List;
import java.util.stream.Collectors;

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
        String jobRequirements = jobToScreen.getRequirements().value;

        // Filter persons by checking if their role is present in the job requirements (case-insensitive)
        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(person -> containsRoleIgnoreCase(jobRequirements, person.getRole().value))
                .collect(Collectors.toList());


        if (matchingPersons.isEmpty()) {
            return new CommandResult("0 contacts found");
        }

        // Show the matching persons and a summary message
        model.updateFilteredPersonList(person -> containsRoleIgnoreCase(jobRequirements, person.getRole().value));
        return new CommandResult(matchingPersons.size() + " contacts found");
    }

    /**
     * Helper method to extract the role from JobRequirements string.
     * Assumes the role is mentioned as "Role: <role name>".
     */
    private String extractRoleFromRequirements(JobRequirements requirements) {
        String value = requirements.value;
        String rolePrefix = "Role:";
        int roleIndex = value.indexOf(rolePrefix);

        if (roleIndex == -1) {
            return ""; // Return empty or handle as necessary if no role is specified
        }

        // Extract the role after "Role:" and trim any extra spaces
        return value.substring(roleIndex + rolePrefix.length()).trim();
    }

    /**
     * Helper method to check if the job requirements contain the role (case-insensitive).
     */
    private boolean containsRoleIgnoreCase(String jobRequirements, String role) {
        return jobRequirements.toLowerCase().contains(role.toLowerCase());
    }
}
