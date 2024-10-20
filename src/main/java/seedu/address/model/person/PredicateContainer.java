package seedu.address.model.person;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Contains all predicates for FindCommand
 */
public class PredicateContainer {
    private NameContainsKeywordsPredicate nameContainsKeywordsPredicate;
    private PhoneContainsKeywordsPredicate phoneContainsKeywordsPredicate;
    private EmailContainsKeywordsPredicate emailContainsKeywordsPredicate;
    private DepartmentContainsKeywordsPredicate departmentContainsKeywordsPredicate;
    private RoleContainsKeywordsPredicate roleContainsKeywordsPredicate;

    public PredicateContainer() {

    }

    /**
     * Adds a {@code NameContainsKeywordsPredicate} to the {@code PredicateContainer}.
     * This method sets the internal {@code nameContainsKeywordsPredicate} field to the provided predicate and
     * returns the current {@code PredicateContainer} instance. This allows method chaining.
     *
     * @param nameContainsKeywordsPredicate The {@code NameContainsKeywordsPredicate} that will be stored in this
     *                                      container.
     * @return The current {@code PredicateContainer} instance with the updated {@code nameContainsKeywordsPredicate}.
     */
    public PredicateContainer addNameContainsKeywordsPredicate(
            NameContainsKeywordsPredicate nameContainsKeywordsPredicate) {
        this.nameContainsKeywordsPredicate = nameContainsKeywordsPredicate;
        return this;
    }
    /**
     * Adds a {@code PhoneContainsKeywordsPredicate} to the {@code PredicateContainer}.
     * This method sets the internal {@code phoneContainsKeywordsPredicate} field to the provided predicate and
     * returns the current {@code PredicateContainer} instance. This allows method chaining.
     *
     * @param phoneContainsKeywordsPredicate The {@code PhoneContainsKeywordsPredicate} that will be stored in this
     *                                       container.
     * @return The current {@code PredicateContainer} instance with the updated {@code phoneContainsKeywordsPredicate}.
     */
    public PredicateContainer addPhoneContainsKeywordsPredicate(
            PhoneContainsKeywordsPredicate phoneContainsKeywordsPredicate) {
        this.phoneContainsKeywordsPredicate = phoneContainsKeywordsPredicate;
        return this;
    }
    /**
     * Adds a {@code EmailContainsKeywordsPredicate} to the {@code PredicateContainer}.
     * This method sets the internal {@code emailContainsKeywordsPredicate} field to the provided predicate and
     * returns the current {@code PredicateContainer} instance. This allows method chaining.
     *
     * @param emailContainsKeywordsPredicate The {@code EmailContainsKeywordsPredicate} that will be stored in this
     *                                       container.
     * @return The current {@code PredicateContainer} instance with the updated {@code emailContainsKeywordsPredicate}.
     */
    public PredicateContainer addEmailContainsKeywordsPredicate(
            EmailContainsKeywordsPredicate emailContainsKeywordsPredicate) {
        this.emailContainsKeywordsPredicate = emailContainsKeywordsPredicate;
        return this;
    }
    /**
     * Adds a {@code DepartmentContainsKeywordsPredicate} to the {@code PredicateContainer}.
     *
     * This method sets the internal {@code departmentContainsKeywordsPredicate} field to the provided predicate and
     * returns the current {@code PredicateContainer} instance. This allows method chaining.
     *
     * @param departmentContainsKeywordsPredicate The {@code DepartmentContainsKeywordsPredicate} that will be stored
     *                                            in this container.
     * @return The current {@code PredicateContainer} instance with the updated {@code
     * departmentContainsKeywordsPredicate}.
     */
    public PredicateContainer addDepartmentContainsKeywordsPredicate(
            DepartmentContainsKeywordsPredicate departmentContainsKeywordsPredicate) {
        this.departmentContainsKeywordsPredicate = departmentContainsKeywordsPredicate;
        return this;
    }
    /**
     * Adds a {@code RoleContainsKeywordsPredicate} to the {@code PredicateContainer}.
     *
     * This method sets the internal {@code roleContainsKeywordsPredicate} field to the provided predicate and
     * returns the current {@code PredicateContainer} instance. This allows method chaining.
     *
     * @param roleContainsKeywordsPredicate The {@code RoleContainsKeywordsPredicate} that will be stored in this
     *                                      container.
     * @return The current {@code PredicateContainer} instance with the updated {@code roleContainsKeywordsPredicate}.
     */
    public PredicateContainer addRoleContainsKeywordsPredicate(
            RoleContainsKeywordsPredicate roleContainsKeywordsPredicate) {
        this.roleContainsKeywordsPredicate = roleContainsKeywordsPredicate;
        return this;
    }

    public Predicate<Person> getCombinedPredicates() {
        Predicate<Person> predicate = PREDICATE_SHOW_ALL_PERSONS;
        if (this.nameContainsKeywordsPredicate != null) {
            predicate = predicate.and(nameContainsKeywordsPredicate);
        }
        if (this.phoneContainsKeywordsPredicate != null) {
            predicate = predicate.and(phoneContainsKeywordsPredicate);
        }
        if (this.emailContainsKeywordsPredicate != null) {
            predicate = predicate.and(emailContainsKeywordsPredicate);
        }
        if (this.departmentContainsKeywordsPredicate != null) {
            predicate = predicate.and(departmentContainsKeywordsPredicate);
        }
        if (this.roleContainsKeywordsPredicate != null) {
            predicate = predicate.and(roleContainsKeywordsPredicate);
        }
        return predicate;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == this) {
            return true;
        }

        if (!(otherObject instanceof PredicateContainer)) {
            return false;
        }

        PredicateContainer otherPredicateContainer = (PredicateContainer) otherObject;
        Boolean equals = true;
        if (this.nameContainsKeywordsPredicate != null) {
            equals = equals && (this.nameContainsKeywordsPredicate
                    .equals(otherPredicateContainer.nameContainsKeywordsPredicate));
        } else {
            equals = equals && (otherPredicateContainer.nameContainsKeywordsPredicate == null);
        }
        if (this.phoneContainsKeywordsPredicate != null) {
            equals = equals && this.phoneContainsKeywordsPredicate
                    .equals(otherPredicateContainer.phoneContainsKeywordsPredicate);
        } else {
            equals = equals && (otherPredicateContainer.phoneContainsKeywordsPredicate == null);
        }
        if (this.emailContainsKeywordsPredicate != null) {
            equals = equals && this.emailContainsKeywordsPredicate
                    .equals(otherPredicateContainer.emailContainsKeywordsPredicate);
        } else {
            equals = equals && (otherPredicateContainer.emailContainsKeywordsPredicate == null);
        }
        if (this.departmentContainsKeywordsPredicate != null) {
            equals = equals && this.departmentContainsKeywordsPredicate
                    .equals(otherPredicateContainer.departmentContainsKeywordsPredicate);

        } else {
            equals = equals && (otherPredicateContainer.departmentContainsKeywordsPredicate == null);
        }
        if (this.roleContainsKeywordsPredicate != null) {
            equals = equals && this.roleContainsKeywordsPredicate
                    .equals(otherPredicateContainer.roleContainsKeywordsPredicate);
        } else {
            equals = equals && (otherPredicateContainer.roleContainsKeywordsPredicate == null);
        }
        return equals;
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this);
        if (this.nameContainsKeywordsPredicate != null) {
            toStringBuilder.add("names", nameContainsKeywordsPredicate + "\n");
        }
        if (this.phoneContainsKeywordsPredicate != null) {
            toStringBuilder.add("phones", phoneContainsKeywordsPredicate + "\n");
        }
        if (this.emailContainsKeywordsPredicate != null) {
            toStringBuilder.add("emails", emailContainsKeywordsPredicate + "\n");
        }
        if (this.departmentContainsKeywordsPredicate != null) {
            toStringBuilder.add("departments", departmentContainsKeywordsPredicate + "\n");
        }
        if (this.roleContainsKeywordsPredicate != null) {
            toStringBuilder.add("roles", roleContainsKeywordsPredicate);
        }
        return toStringBuilder.toString();
    }

}
