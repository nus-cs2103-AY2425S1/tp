//package seedu.address.model.person;
//
//import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.util.AppUtil.checkArgument;
//
//import java.util.List;
//import java.util.Objects;
//
//import seedu.address.logic.parser.SortOrder;
//import seedu.address.model.person.predicates.CompanyContainsKeywordPredicate;
//import seedu.address.model.person.predicates.NameContainsPredicate;
//import seedu.address.model.person.predicates.ProductContainsKeywordPredicate;
//import seedu.address.model.person.predicates.SupplierPredicate;
//
///**
// * Represents the field to match the given keyword in a find supplier command.
// */
//public class SupplierFindBy {
//
//    /**
//     * Represents the fields to find by
//     * 'N' represents name, 'COM' represents company, and 'PRO' represents product
//     */
//    public enum FindBy {
//        N, COM, PRO;
//    }
//
//    public static final String MESSAGE_CONSTRAINTS = "Find parameter should be 'n' for name, 'com' for company or "
//            + "'pro' for product, and it should not be blank";
//
//    /**
//     * Field to find by must be 'n', 'pro' or 'com'.
//     */
//    public static final String VALIDATION_REGEX = "^(n|com|pro)$";
//
//    private final FindBy findBy;
//
//    /**
//     * Creates a SupplierFindBy object with the corresponding field to match keywords against.
//     * @param findBy Represents name with 'n', company with 'c', and product with 'pro'.
//     */
//    public SupplierFindBy(String findBy) {
//        requireNonNull(findBy);
//        checkArgument(isValidSortBy(findBy), MESSAGE_CONSTRAINTS);
//        switch (FindBy.valueOf(findBy.toUpperCase())) {
//        case N:
//            this.findBy = FindBy.N;
//            break;
//        case COM:
//            this.findBy = FindBy.COM;
//            break;
//        case PRO:
//            this.findBy = FindBy.PRO;
//            break;
//        default:
//            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
//        }
//    }
//
//    /**
//     * Returns the corresponding predicate of {@code findBy} which finds by {@code FindBy}
//     */
//    public static SupplierPredicate getSupplierFindByPredicate(SupplierFindBy supplierFindBy, List<String> keywords) {
//        switch (supplierFindBy.getFindBy()) {
//        case N:
//            return new NameContainsPredicate(keywords);
//        case COM:
//            return new CompanyContainsKeywordPredicate(keywords);
//        case PRO:
//            return new ProductContainsKeywordPredicate(keywords);
//        default:
//            return null;
//        }
//    }
//
//    public FindBy getFindBy() {
//        return this.findBy;
//    }
//
//    /**
//     * Returns true if a given string is a valid field to sort by.
//     */
//    public static boolean isValidSortBy(String test) {
//        return test.matches(VALIDATION_REGEX);
//    }
//
//    /**
//     * Returns the corresponding field that is being sorted by.
//     */
//    @Override
//    public String toString() {
//        switch (findBy) {
//        case N:
//            return "name";
//        case PRO:
//            return "date time";
//        case COM:
//            return "company";
//        default:
//            return "ERROR";
//        }
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(findBy);
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//
//        // instanceof handles nulls
//        if (!(other instanceof SupplierFindBy)) {
//            return false;
//        }
//
//        SupplierFindBy otherSupplierFindBy = (SupplierFindBy) other;
//        return findBy.equals(otherSupplierFindBy.getFindBy());
//    }
//}
