package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Department department;
    private final Role role;
    private final ContractEndDate contractEndDate;
    private final boolean isEmployee;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Department department, Role role,
                  ContractEndDate contractEndDate, boolean isEmployee) {
        requireAllNonNull(name, phone, email, address, department, role, contractEndDate, isEmployee);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.department = department;
        this.role = role;
        this.isEmployee = isEmployee;
        if (isEmployee) {
            assert !contractEndDate.equals(ContractEndDate.empty());
            this.contractEndDate = contractEndDate;
        } else {
            this.contractEndDate = ContractEndDate.empty();
        }
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Department getDepartment() {
        return department;
    }

    public Role getRole() {
        return role;
    }

    public ContractEndDate getContractEndDate() {
        return contractEndDate;
    }

    /**
     * Returns true if the person is an employee.
     */
    public boolean isEmployee() {
        return isEmployee;
    }

    /**
     * Returns true if the person is a potential hire.
     */
    public boolean isPotentialHire() {
        return !isEmployee();
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && department.equals(otherPerson.department)
                && role.equals(otherPerson.role)
                && isEmployee == otherPerson.isEmployee()
                && (contractEndDate.equals(otherPerson.contractEndDate) || !isEmployee);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return isEmployee
                ? Objects.hash(name, phone, email, address, department, role, contractEndDate, isEmployee)
                : Objects.hash(name, phone, email, address, department, role, isEmployee);
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("department", department)
                .add("role", role)
                .add("employee", isEmployee);
        if (isEmployee()) {
            return builder.add("contractEndDate", contractEndDate).toString();
        }
        return builder.toString();
    }

}
