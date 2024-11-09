package seedu.address.testutil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.util.DateTimeUtil;
import seedu.address.model.client.Address;
import seedu.address.model.client.Client;
import seedu.address.model.client.Company;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Transaction;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Client objects.
 */
public class ClientBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_COMPANY = "ABC Corp";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final Transaction DEFAULT_TRANSACTION_1 = new Transaction(
            "Buy raw materials", -100, "ABC Company",
            LocalDate.parse("2024-10-10", DateTimeUtil.DEFAULT_DATE_PARSER));
    public static final Transaction DEFAULT_TRANSACTION_2 = new Transaction(
            "Invest", -1000, "Nice Company",
            LocalDate.parse("2024-08-01", DateTimeUtil.DEFAULT_DATE_PARSER));

    private Name name;
    private Company company;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private List<Transaction> transactions;

    /**
     * Creates a {@code ClientBuilder} with the default details.
     */
    public ClientBuilder() {
        name = new Name(DEFAULT_NAME);
        company = new Company(DEFAULT_COMPANY);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        transactions = new ArrayList<>();
        transactions.add(DEFAULT_TRANSACTION_1);
    }

    /**
     * Initializes the ClientBuilder with the data of {@code clientToCopy}.
     */
    public ClientBuilder(Client clientToCopy) {
        name = clientToCopy.getName();
        company = clientToCopy.getCompany();
        phone = clientToCopy.getPhone();
        email = clientToCopy.getEmail();
        address = clientToCopy.getAddress();
        tags = new HashSet<>(clientToCopy.getTags());
        transactions = new ArrayList<>(clientToCopy.getTransactions());
    }

    /**
     * Sets the {@code Name} of the {@code Client} that we are building.
     */
    public ClientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Company} of the {@code Client} that we are building.
     */
    public ClientBuilder withCompany(String company) {
        this.company = new Company(company);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Client} that we are building.
     */
    public ClientBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Client} that we are building.
     */
    public ClientBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Client} that we are building.
     */
    public ClientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Client} that we are building.
     */
    public ClientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Transaction} of the {@code Client} that we are building.
     */
    public ClientBuilder withTransactions(Transaction ... transactions) {
        this.transactions = SampleDataUtil.getTransactionList(transactions);
        return this;
    }

    public Client build() {
        return new Client(name, company, phone, email, address, tags, transactions);
    }
}
