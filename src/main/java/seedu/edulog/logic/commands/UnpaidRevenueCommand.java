package seedu.edulog.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edulog.model.Model.PREDICATE_SHOW_UNPAID_STUDENTS;

import seedu.edulog.commons.util.ToStringBuilder;
import seedu.edulog.model.Model;

/**
 * Calculate total revenue earned
 */
public class UnpaidRevenueCommand extends RevenueCommand {

    private final String option = "unpaid";

    public UnpaidRevenueCommand() { }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        int result = model.getUnpaid();
        model.updateFilteredStudentList(PREDICATE_SHOW_UNPAID_STUDENTS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, option, result));
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof UnpaidRevenueCommand);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
