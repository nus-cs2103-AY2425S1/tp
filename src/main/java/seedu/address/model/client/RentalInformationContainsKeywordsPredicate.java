package seedu.address.model.client;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Client}'s {@code Rental Information} matches any of the keywords given.
 */
public class RentalInformationContainsKeywordsPredicate implements Predicate<Client> {
    private final List<String> keywords;

    public RentalInformationContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Client client) {
        if (client.getRentalInformation().isEmpty()) {
            return false;
        } else {
            boolean allEmpty = keywords.stream()
                    .allMatch(keyword -> keyword.trim().isEmpty());
            if (allEmpty) {
                return false;
            } else {
                return keywords.stream()
                        .anyMatch(keyword -> client.getRentalInformation().stream()
                                .anyMatch(rentalInformation ->
                                        StringUtil.containsIgnoreCase(rentalInformation.getAddress().value, keyword)
                                                || StringUtil.containsIgnoreCase(
                                                        rentalInformation.getDeposit().toString(), keyword)
                                                || StringUtil.containsIgnoreCase(
                                                        rentalInformation.getMonthlyRent().toString(), keyword)
                                                || StringUtil.containsIgnoreCase(
                                                        rentalInformation.getRentDueDate().toString(), keyword)
                                                || StringUtil.containsIgnoreCase(
                                                        rentalInformation.getRentalStartDate().toString(), keyword)
                                                || StringUtil.containsIgnoreCase(
                                                        rentalInformation.getRentalEndDate().toString(), keyword)
                                                || StringUtil.containsIgnoreCase(
                                                        rentalInformation.getCustomerList().toString(), keyword)
                                ));
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RentalInformationContainsKeywordsPredicate)) {
            return false;
        }

        RentalInformationContainsKeywordsPredicate otherRentalInformationContainsKeywordsPredicate =
                (RentalInformationContainsKeywordsPredicate) other;
        return keywords.equals(otherRentalInformationContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
