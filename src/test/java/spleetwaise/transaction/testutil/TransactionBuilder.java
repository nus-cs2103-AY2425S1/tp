package spleetwaise.transaction.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import spleetwaise.address.model.person.Person;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.util.IdUtil;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * A utility class to help with building Transaction objects.
 */
public class TransactionBuilder {

    public static final String DEFAULT_AMOUNT = "12.3";
    public static final String DEFAULT_DESCRIPTION = "Test";
    public static final String DEFAULT_DATE = "01012024";
    public static final Category DEFAULT_CATEGORY = new Category("FOOD");
    public static final Set<Category> DEFAULT_CATEGORY_SET = new HashSet<>(Arrays.asList(DEFAULT_CATEGORY));
    public static final Set<Category> DEFAULT_EMPTY_CATEGORY_SET = new HashSet<>();
    private static final Person DEFAULT_PERSON = TypicalPersons.ALICE;
    private static final String DEFAULT_POSITIVE_AMOUNT = "1.23";
    private static final String DEFAULT_NEGATIVE_AMOUNT = "-1.23";
    private static final String DEFAULT_STATUS = "Not Done";

    private String id;
    private Person person;
    private Amount amount;
    private Description description;
    private Date date;
    private Set<Category> categories;
    private Status status;

    /**
     * Creates a {@code TransactionBuilder} with the default details.
     */
    public TransactionBuilder() {
        id = IdUtil.getId();
        person = DEFAULT_PERSON;
        amount = new Amount(DEFAULT_POSITIVE_AMOUNT);
        description = new Description(DEFAULT_DESCRIPTION);
        date = new Date(DEFAULT_DATE);
        categories = new HashSet<>(Arrays.asList(DEFAULT_CATEGORY));
        status = new Status(DEFAULT_STATUS);
    }

    /**
     * Initializes the TransactionBuilder with the data of {@code transactionToCopy}.
     */
    public TransactionBuilder(Transaction transactionToCopy) {
        id = transactionToCopy.getId();
        person = transactionToCopy.getPerson();
        amount = transactionToCopy.getAmount();
        description = transactionToCopy.getDescription();
        date = transactionToCopy.getDate();
        categories = transactionToCopy.getCategories();
        status = transactionToCopy.getStatus();
    }


    /**
     * Sets the {@code id} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the {@code Person} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withPerson(Person person) {
        this.person = person;
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Category} hashset of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withCategories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    /**
     * Builds and returns the {@code Transaction} object.
     */
    public Transaction build() {
        return new Transaction(id, person, amount, description, date, categories, status);
    }
}
