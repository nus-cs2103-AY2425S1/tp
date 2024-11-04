package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.game.Game;
import seedu.address.model.person.Address;
import seedu.address.model.person.Blank;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.preferredtime.PreferredTime;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedGame> games = new ArrayList<>();
    private final List<JsonAdaptedPreferredTime> preferredTimes = new ArrayList<>();


    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("games") List<JsonAdaptedGame> games,
                             @JsonProperty("preferred times") List<JsonAdaptedPreferredTime> preferredTimes) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (games != null) {
            this.games.addAll(games);
        }
        if (preferredTimes != null) {
            this.preferredTimes.addAll(preferredTimes);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        games.addAll(source.getGames().values().stream()
                .map(JsonAdaptedGame::new)
                .collect(Collectors.toList()));
        preferredTimes.addAll(source.getPreferredTimes().stream()
                .map(JsonAdaptedPreferredTime::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }
        final Map<String, Game> personGames = new HashMap<>();
        for (JsonAdaptedGame game : games) {
            personGames.put(game.getGameName(), game.toModelType());
        }
        final List<PreferredTime> personPreferredTimes = new ArrayList<>();
        for (JsonAdaptedPreferredTime preferredTime : preferredTimes) {
            personPreferredTimes.add(preferredTime.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);


        final Phone modelPhone;
        if (phone.isEmpty()) {
            modelPhone = new Phone(new Blank());
        } else {
            if (!Phone.isValidPhone(phone)) {
                throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
            }
            modelPhone = new Phone(phone);
        }

        final Email modelEmail;
        if (email.isEmpty()) {
            System.out.println("help");
            modelEmail = new Email(new Blank());
        } else {
            if (!Email.isValidEmail(email)) {
                throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
            }
            System.out.println("uh oh");
            modelEmail = new Email(email);
        }

        final Address modelAddress;
        if (address.isEmpty()) {
            modelAddress = new Address(new Blank());
        } else {
            if (!Address.isValidAddress(address)) {
                throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
            }
            modelAddress = new Address(address);
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);
        final Map<String, Game> modelGames = new HashMap<>(personGames);
        final Set<PreferredTime> modelPreferredTimes = new HashSet<>(personPreferredTimes);

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelGames, modelPreferredTimes);
    }

}
