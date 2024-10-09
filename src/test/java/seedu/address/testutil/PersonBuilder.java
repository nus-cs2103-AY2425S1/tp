package seedu.address.testutil;

import seedu.address.model.person.*;

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
    public static final String DEFAULT_CONTRACT_START_DATE = "2024-10-09";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Department department;
    private Role role;
    private ContractEndDate contractEndDate;

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
        contractEndDate = new ContractEndDate(DEFAULT_CONTRACT_START_DATE);
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
        this.contractEndDate = new ContractEndDate(date);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, department, role, contractEndDate);
    }

}
