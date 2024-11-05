package spleetwaise.transaction.model.filterpredicate;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import spleetwaise.commons.util.AppUtil;
import spleetwaise.commons.util.ToStringBuilder;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * Tests if a {@code Transaction}'s {@code Amount} has a positive or negative sign.
 */
public class AmountSignFilterPredicate implements Predicate<Transaction> {

    public static final String POSITIVE_SIGN = "Pos";
    public static final String NEGATIVE_SIGN = "Neg";

    public static final String MESSAGE_CONSTRAINTS = "Amount sign should be '" + POSITIVE_SIGN + "' or '"
            + NEGATIVE_SIGN + "'";

    private final boolean sign;

    /**
     * Creates a {@code AmountSignFilterPredicate} that tests if a {@code Transaction}'s {@code Amount} has a
     * positive or negative sign depending on the signString.
     *
     * @param signString The sign of the {@code Amount} to filter by.
     */
    public AmountSignFilterPredicate(String signString) {
        requireNonNull(signString);
        signString = signString.trim();
        AppUtil.checkArgument(isValidSign(signString), MESSAGE_CONSTRAINTS);
        sign = POSITIVE_SIGN.equals(signString);
    }

    /**
     * Returns true if a given string is a valid amount sign.
     */
    public static boolean isValidSign(String sign) {
        sign = sign.trim();
        return POSITIVE_SIGN.equals(sign) || NEGATIVE_SIGN.equals(sign);
    }

    @Override
    public boolean test(Transaction transaction) {
        return transaction.getAmount().isNegative() != sign;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AmountSignFilterPredicate otherAmountSignFilterPredicate)) {
            return false;
        }

        return sign == otherAmountSignFilterPredicate.sign;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("sign", sign)
                .toString();
    }

}
