package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Module;
import seedu.address.model.person.Person;

/**
 * Grades a student in the address book.
 */
public class GradeCommand extends Command {
    public static final String COMMAND_WORD = "grade";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Grades a student identified by the index number "
            + "used in the displayed person list with a numerical score.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_GRADE + "GRADE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MODULE + "MA2103 "
            + PREFIX_GRADE + "80";
    public static final String MESSAGE_GRADE_SUCCESS = "Graded Module: %1$s for %2$s";
    public static final String MESSAGE_INVALID_GRADE = "The grade provided is invalid.";
    public static final String MESSAGE_INVALID_MODULE = "The module specified is not found.";
    public static final String MESSAGE_INVALID_PERSON = "The person index provided is invalid.";
    public static final String MESSAGE_MISMATCH_MODULE_GRADE = "The number of modules and grades"
            + "provided do not match.";

    private final Index targetIndex;
    private final Map<String, Integer> moduleGrades;

    /**
     * Creates a GradeCommand to grade the specified {@code Person}
     */
    public GradeCommand(Index targetIndex, Map<String, Integer> moduleGrades) {
        requireNonNull(targetIndex, "Target index cannot be null");
        requireNonNull(moduleGrades, "Module cannot be null");
        this.targetIndex = targetIndex;
        this.moduleGrades = new LinkedHashMap<>(moduleGrades);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToGrade = lastShownList.get(targetIndex.getZeroBased());
        Set<Module> personModules = new HashSet<>(personToGrade.getModules());
        List<String> gradeResults = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : moduleGrades.entrySet()) {
            String moduleName = entry.getKey();
            int grade = entry.getValue();

            if (grade < 0 || grade > 100) {
                throw new CommandException(MESSAGE_INVALID_GRADE);
            }

            Optional<Module> moduleToGrade = personModules.stream()
                    .filter(m -> m.module.equals(moduleName))
                    .findFirst();

            if (moduleToGrade.isEmpty()) {
                throw new CommandException(MESSAGE_INVALID_MODULE + " (" + moduleName + ")");
            }

            Module module = moduleToGrade.get();
            module.assignGrade(grade);
            gradeResults.add(String.format("%s: %d", moduleName, grade));
        }

        Person updatedPerson = new Person(personToGrade.getName(), personToGrade.getPhone(),
                personToGrade.getGender(), personModules, personToGrade.getTags());
        model.setPerson(personToGrade, updatedPerson);

        String gradeResultsString = String.join("\n", gradeResults);
        return new CommandResult(String.format(MESSAGE_GRADE_SUCCESS, personToGrade.getName(), gradeResultsString));
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof GradeCommand
                && targetIndex.equals(((GradeCommand) other).targetIndex)
                && moduleGrades.equals(((GradeCommand) other).moduleGrades));
    }
}
