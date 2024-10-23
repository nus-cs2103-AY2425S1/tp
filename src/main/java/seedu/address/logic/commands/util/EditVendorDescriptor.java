package seedu.address.logic.commands.util;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Budget;
import seedu.address.model.person.Company;

/**
 * Stores the details to edit the vendor with. Each non-empty field value will replace the
 * corresponding field value of the vendor.
 */
public class EditVendorDescriptor extends EditPersonDescriptor {
    private Company company;
    private Budget budget;

    public EditVendorDescriptor() {
    }

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditVendorDescriptor(EditVendorDescriptor toCopy) {
        super(toCopy);
        setCompany(toCopy.company);
    }

    /**
     * Returns true if at least one field is edited.
     */
    @Override
    public boolean isAnyFieldEdited() {
        return super.isAnyFieldEdited() || CollectionUtil.isAnyNonNull(company);
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Optional<Company> getCompany() {
        return Optional.ofNullable(company);
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Optional<Budget> getBudget() {
        return Optional.ofNullable(budget);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof EditVendorDescriptor)) {
            return false;
        }
        EditVendorDescriptor otherEditVendorDescriptor = (EditVendorDescriptor) other;
        return super.equals(other)
                && Objects.equals(company, otherEditVendorDescriptor.company)
                && Objects.equals(budget, otherEditVendorDescriptor.budget);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", getName().orElse(null))
                .add("phone", getPhone().orElse(null))
                .add("email", getEmail().orElse(null))
                .add("address", getAddress().orElse(null))
                .add("tags", getTags().orElse(null))
                .add("company", company)
                .add("budget", budget)
                .toString();
    }
}
