package seedu.address.testutil;

import seedu.address.model.person.Address;
import seedu.address.model.person.ContractEndDate;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DEPARTMENT = "IT";
    public static final String DEFAULT_ROLE = "SWE";
    public static final String DEFAULT_CONTRACT_END_DATE = "2024-10-09";
    public static final boolean DEFAULT_IS_EMPLOYEE = true;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Department department;
    private Role role;
    private ContractEndDate contractEndDate;
    private boolean isEmployee;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        department = new Department(DEFAULT_DEPARTMENT);
        role = new Role(DEFAULT_ROLE);
        contractEndDate = ContractEndDate.of(DEFAULT_CONTRACT_END_DATE);
        isEmployee = DEFAULT_IS_EMPLOYEE;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        department = personToCopy.getDepartment();
        role = personToCopy.getRole();
        contractEndDate = personToCopy.getContractEndDate();
        isEmployee = personToCopy.isEmployee();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Department} of the {@code Person} that we are building.
     */
    public PersonBuilder withDepartment(String department) {
        this.department = new Department(department);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Person} that we are building.
     */
    public PersonBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Sets the {@code ContractEndDate} of the {@code Person} that we are building.
     */
    public PersonBuilder withContractEndDate(String date) {
        this.contractEndDate = ContractEndDate.of(date);
        return this;
    }

    /**
     * Sets the {@code isEmployee} of the {@code Person} that we are building.
     */
    public PersonBuilder withIsEmployee(boolean isEmployee) {
        this.isEmployee = isEmployee;
        return this;
    }

    /**
     * Builds the Person object.
     */
    public Person build() {
        if (isEmployee) {
            return new Person(name, phone, email, address, department, role, contractEndDate, isEmployee);
        } else {
            return new Person(name, phone, email, address, department, role, ContractEndDate.empty(), isEmployee);
        }
    }
}
