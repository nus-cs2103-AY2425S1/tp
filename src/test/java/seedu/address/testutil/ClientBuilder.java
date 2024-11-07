package seedu.address.testutil;

import java.math.BigInteger;

import seedu.address.logic.commands.CommandCommons;
import seedu.address.model.client.Address;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Income;
import seedu.address.model.client.Job;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Remark;
import seedu.address.model.status.Status;
import seedu.address.model.tier.Tier;

/**
 * A utility class to help with building Client objects.
 */
public class ClientBuilder {

    // defaults shown here belong to mandatory fields, and are hence not present in CommandCommons
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_JOB = "Engineer";
    public static final BigInteger DEFAULT_INCOME = BigInteger.ZERO;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Job job;
    private Income income;
    private Tier tier;
    private Remark remark;
    private Status status;

    /**
     * Creates a {@code ClientBuilder} with the default details.
     */
    public ClientBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        job = new Job(DEFAULT_JOB);
        income = new Income(DEFAULT_INCOME);
        tier = new Tier(CommandCommons.DEFAULT_TIER);
        remark = new Remark(CommandCommons.DEFAULT_REMARK);
        status = new Status(CommandCommons.DEFAULT_STATUS);
    }

    /**
     * Initializes the ClientBuilder with the data of {@code clientToCopy}.
     */
    public ClientBuilder(Client clientToCopy) {
        name = clientToCopy.getName();
        phone = clientToCopy.getPhone();
        email = clientToCopy.getEmail();
        address = clientToCopy.getAddress();
        job = clientToCopy.getJob();
        income = clientToCopy.getIncome();
        tier = clientToCopy.getTier();
        remark = clientToCopy.getRemark();
        status = clientToCopy.getStatus();
    }

    /**
     * Sets the {@code Name} of the {@code Client} that we are building.
     */
    public ClientBuilder withName(String name) {
        this.name = new Name(name);
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
     * Sets the {@code Address} of the {@code Client} that we are building.
     */
    public ClientBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Job} of the {@code Client} that we are building.
     */
    public ClientBuilder withJob(String job) {
        this.job = new Job(job);
        return this;
    }

    /**
     * Sets the {@code Income} of the {@code Client} that we are building.
     */
    public ClientBuilder withIncome(BigInteger income) {
        this.income = new Income(income);
        return this;
    }
    /**
     * Parses the {@code tier} into a {@code Set<Tier>} and set it to the {@code Client} that we are building.
     */
    public ClientBuilder withTier(String tier) {
        this.tier = new Tier(tier);
        return this;
    }

    /**
     * Sets the {@code remark} of the {@code Client} that we are building.
     */
    public ClientBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code status} of the {@code Client} that we are building.
     */
    public ClientBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    public Client build() {
        return new Client(name, phone, email, address, job, income, tier, remark, status);
    }

}
