package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DISPLAYED_INDEX;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.Job;
import seedu.address.model.person.Person;

/**
 *  Screens all contacts and return a list of candidate that fit a job listing
 *  requirement
 */
public class ScreenJobCommand extends ScreenCommand {

    public static final String ENTITY_WORD = "job";

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
            throw new CommandException(MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        Job jobToScreen = jobList.get(targetIndex.getZeroBased());

        Predicate<Person> filterCriteria = roleMatchesJobPredicate(jobToScreen)
                .and(person -> !person.isMatchPresent());

        // Filter persons by checking if their role is present in the job requirements (case-insensitive)
        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(filterCriteria)
                .collect(Collectors.toList());


        if (matchingPersons.isEmpty()) {
            model.updateFilteredPersonList(unused -> false);
            return new CommandResult("0 candidates found");
        }

        // Show the matching persons and a summary message
        model.updateFilteredPersonList(filterCriteria);
        String candidateWord = matchingPersons.size() == 1 ? "candidate" : "candidates";
        return new CommandResult(matchingPersons.size() + " " + candidateWord + " found");
    }

    /**
     * Returns a predicate that checks whether a person's role matches the job's requirements (case-insensitive).
     *
     * This is used to determine if the contact is applying for roles that match the job listing.
     *
     * @param job The job to be matched.
     * @return A predicate that can be used to filter persons based on their role matching the job.
     */
    private Predicate<Person> roleMatchesJobPredicate(Job job) {
        String jobName = job.getName().fullName.toLowerCase();

        // Return a predicate that checks if the person's role matches the job's name
        return person -> jobName.contains(person.getRole().value.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ScreenJobCommand)) {
            return false;
        }

        // Compare by the index of both objects
        ScreenJobCommand otherCommand = (ScreenJobCommand) other;
        return targetIndex.equals(otherCommand.targetIndex);
    }

    @Override
    public int hashCode() {
        return targetIndex.hashCode();
    }
}
