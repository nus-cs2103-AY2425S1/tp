package spleetwaise.transaction.storage.adapters;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.person.Person;
import spleetwaise.commons.exceptions.IllegalValueException;
import spleetwaise.commons.util.IdUtil;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * Adapter for serializing and deserializing {@link Transaction} objects into JSON.
 */
public class JsonAdaptedTransaction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";
    public static final String PERSON_ID_NOT_FOUND = "Person with id %s not found!";

    private final String id;
    private final String personId;
    private final JsonAdaptedAmount jsonAmount;
    private final String jsonDescription;
    private final String jsonDate;
    private final boolean isDone;
    private final List<JsonAdaptedCategory> jsonCategories = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTransaction} with the given transaction details.
     */
    @JsonCreator
    public JsonAdaptedTransaction(
            @JsonProperty("id") String id,
            @JsonProperty("personId") String personId,
            @JsonProperty("amount") JsonAdaptedAmount amount,
            @JsonProperty("description") String description,
            @JsonProperty("date") String date,
            @JsonProperty("isDone") boolean isDone,
            @JsonProperty("categories") List<JsonAdaptedCategory> categories
    ) {
        this.id = id;
        this.personId = personId;
        this.jsonAmount = amount;
        this.jsonDescription = description;
        this.jsonDate = date;
        this.isDone = isDone;
        if (categories != null) {
            this.jsonCategories.addAll(categories);
        }
    }

    /**
     * Converts a given {@code Transaction} into this class for Jackson use.
     */
    public JsonAdaptedTransaction(Transaction transaction) {
        this.id = transaction.getId();
        this.personId = transaction.getPerson().getId();
        this.jsonAmount = new JsonAdaptedAmount(transaction.getAmount());
        this.jsonDescription = transaction.getDescription().toString();
        this.jsonDate = transaction.getDate().getDate().format(Date.VALIDATION_FORMATTER);
        this.isDone = transaction.getStatus().isDone();
        this.jsonCategories.addAll(transaction.getCategories().stream().map(JsonAdaptedCategory::new)
                .toList());
    }

    public String getId() {
        return id;
    }

    public JsonAdaptedAmount getAmount() {
        return jsonAmount;
    }

    public String getDescription() {
        return jsonDescription;
    }

    public String getDate() {
        return jsonDate;
    }

    public boolean getIsDone() {
        return isDone;
    }

    /**
     * Converts this Jackson-friendly adapted transaction object into the model's {@code Transaction} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted transaction.
     */
    public Transaction toModelType(AddressBookModel addressBookModel) throws IllegalValueException {
        requireNonNull(addressBookModel);

        final Person person;
        final Amount amount;
        final Description description;
        final Date date;
        final Status status;
        final List<Category> transactionCategories = new ArrayList<>();

        for (JsonAdaptedCategory category : jsonCategories) {
            transactionCategories.add(category.toModelType());
        }

        // Check missing fields
        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "id"));
        }

        if (!IdUtil.isValidId(id)) {
            throw new IllegalValueException(IdUtil.MESSAGE_CONSTRAINTS);
        }

        if (personId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "personId"));
        }

        if (jsonAmount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }

        if (jsonDescription == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }

        if (jsonDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }

        if (!Description.isValidDescription(jsonDescription)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }

        if (!Date.isValidDate(jsonDate)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }

        Optional<Person> optionalPerson = addressBookModel.getPersonById(personId);
        if (optionalPerson.isEmpty()) {
            throw new IllegalValueException(String.format(PERSON_ID_NOT_FOUND, personId));
        }

        person = optionalPerson.get();
        amount = jsonAmount.toModelType();
        description = new Description(jsonDescription);
        date = new Date(jsonDate);
        status = new Status(isDone);
        Set<Category> categories = new HashSet<>(transactionCategories);

        return new Transaction(id.trim(), person, amount, description, date, categories, status);
    }
}
