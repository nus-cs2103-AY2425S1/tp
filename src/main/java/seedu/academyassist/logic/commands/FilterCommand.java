package seedu.academyassist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.academyassist.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_FILTER_PARAM;

import seedu.academyassist.logic.commands.exceptions.CommandException;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.filter.FilterParam;
import seedu.academyassist.model.person.PersonInYearPredicate;
import seedu.academyassist.model.person.PersonTakeSubjectPredicate;
import seedu.academyassist.model.person.Subject;
import seedu.academyassist.model.person.YearGroup;

/**
 * Filters contacts based on year group or class.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filter contacts based on year group or class. " + "\n"
            + "Parameters: "
            + PREFIX_FILTER_PARAM + "[FILTER TYPE] " + "[FILTER VALUE]" + "\n"
            + "Examples: " + "\n" + COMMAND_WORD + " "
            + PREFIX_FILTER_PARAM + "yearGroup " + "yg/2" + "\n"
            + COMMAND_WORD + " "
            + PREFIX_FILTER_PARAM + "subject " + "s/Science" + "\n";

    public static final String MESSAGE_SUCCESS = "Filtered by: %1$s, %2$s";

    private FilterParam filterParam;
    private Object filterValue;

    /**
     * Creates a FilterCommand
     * @param filterParam FilterParam.YEARGROUP or FilterParam.CLASS
     */
    public FilterCommand(FilterParam filterParam, Object filterValue) {
        // Ensure that the filterParam and filterValue are not null using assertions
        assert filterParam != null : "FilterParam should not be null";
        assert filterValue != null : "FilterValue should not be null";

        // Null-check validation
        requireAllNonNull(filterParam);
        requireAllNonNull(filterValue);

        // Assign values to the fields
        this.filterParam = filterParam;
        this.filterValue = filterValue;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);
        if (filterParam.isSubject()) {
            model.updateFilteredPersonList(new PersonTakeSubjectPredicate((Subject) filterValue));
        } else if (filterParam.isYearGroup()) {
            model.updateFilteredPersonList(new PersonInYearPredicate((YearGroup) filterValue));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, filterParam.toString(), filterValue.toString()));

    }
}
