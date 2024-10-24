package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.JobCode;
import seedu.address.model.person.JobCodePredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Tag;

/**
 * MassRejectCommand changes the tags of persons in bulk based on
 * the specified job code and tag criteria.
 */
public class MassRejectCommand extends Command {

    public static final String COMMAND_WORD = "massReject";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Rejects persons with specified job code and/or tag.\n"
            + "Parameters:\n"
            + "JobCode + Tag: " + COMMAND_WORD + " j/SWE2024 t/bp\n"
            + "Tag only: " + COMMAND_WORD + " t/bp\n"
            + "JobCode only (excluding accepted): " + COMMAND_WORD + " j/SWE2024\n";

    public static final String MESSAGE_SUCCESS = "Mass rejection complete. %d persons updated.";

    private final Predicate<Person> predicate;

    public MassRejectCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Step 1: Use the predicate to filter the relevant persons
        List<Person> personsToReject = model.getFilteredPersonList().stream()
                .filter(person -> shouldReject(person))
                .collect(Collectors.toList());

        if (personsToReject.isEmpty()) {
            throw new CommandException("No matching persons found.");
        }

        // Step 2: Iterate over the filtered persons and edit their tags to "r" (rejected)
        for (Person person : personsToReject) {
            Person updatedPerson = createRejectedPerson(person);
            model.setPerson(person, updatedPerson);
        }

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, personsToReject.size()));
    }

    /**
     * Determines whether a person should be rejected based on the predicate.
     * If the predicate is a JobCodePredicate, persons with the 'a' (accepted) tag are excluded.
     */
    private boolean shouldReject(Person person) {
        if (predicate instanceof JobCodePredicate) {
            // Exclude persons with the 'a' (accepted) tag if JobCodePredicate
            return (predicate.test(person) && !person.getTag().tagCode.equals("A"));
        }
        // Use the predicate for other cases without exclusions
        return predicate.test(person);
    }

    /**
     * Creates and returns a {@code Person} with the same details as {@code personToEdit}
     * but with the tag changed to "r" (rejected).
     */
    private Person createRejectedPerson(Person personToEdit) {
        assert personToEdit != null;

        // Reuse existing details of the person
        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        JobCode updatedJobCode = personToEdit.getJobCode();
        Remark updatedRemark = personToEdit.getRemark();

        // Set the tag to "r" (rejected)
        Tag rejectedTag = new Tag("r");

        return new Person(updatedName, updatedPhone, updatedEmail, updatedJobCode, rejectedTag, updatedRemark);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MassRejectCommand)) {
            return false;
        }

        MassRejectCommand otherMassRejectCommand = (MassRejectCommand) other;
        return predicate.equals(otherMassRejectCommand.predicate);
    }
}
