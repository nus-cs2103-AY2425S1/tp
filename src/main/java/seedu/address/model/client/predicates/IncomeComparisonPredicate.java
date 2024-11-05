package seedu.address.model.client.predicates;

import static java.util.Objects.requireNonNull;

import java.math.BigInteger;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.client.Client;
import seedu.address.model.util.IncomeComparisonOperator;

/**
 * Predicate that compares a {@code Client}'s income against a threshold using a specified comparison operator.
 */
public class IncomeComparisonPredicate implements Predicate<Client> {
    private final BigInteger incomeThreshold;
    private final IncomeComparisonOperator incomeComparisonOperator;

    /**
     * Constructs an {@code IncomeComparisonPredicate}.
     *
     * @param incomeComparisonOperator The operator used to compare the client's income with the threshold.
     * @param incomeThreshold          The threshold income to compare against.
     */
    public IncomeComparisonPredicate(IncomeComparisonOperator incomeComparisonOperator, BigInteger incomeThreshold) {
        requireNonNull(incomeComparisonOperator);
        checkPositiveIncomeThreshold(incomeThreshold);
        this.incomeThreshold = incomeThreshold;
        this.incomeComparisonOperator = incomeComparisonOperator;
    }

    @Override
    public boolean test(Client client) {
        BigInteger clientIncome = client.getIncome().value;
        int comparisonInt = clientIncome.compareTo(incomeThreshold);

        switch (incomeComparisonOperator.comparisonOperator) {
        case "=":
            return comparisonInt == 0;
        case ">":
            return comparisonInt > 0;
        case "<":
            return comparisonInt < 0;
        default:
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IncomeComparisonPredicate)) {
            return false;
        }

        IncomeComparisonPredicate otherIncomeComparisonPredicate =
                (IncomeComparisonPredicate) other;
        return incomeThreshold.equals(otherIncomeComparisonPredicate.incomeThreshold)
                && incomeComparisonOperator.equals(otherIncomeComparisonPredicate.incomeComparisonOperator);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("incomeThreshold", incomeThreshold)
                .add("incomeComparisonOperator", incomeComparisonOperator)
                .toString();
    }

    /**
     * Ensures that the income threshold is positive.
     *
     * @param incomeThreshold The threshold to check.
     * @throws IllegalArgumentException if {@code incomeThreshold} is not greater than 1.
     */
    private void checkPositiveIncomeThreshold(BigInteger incomeThreshold) {
        if (incomeThreshold.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Income threshold cannot be less than 0.");
        }
    }
}

