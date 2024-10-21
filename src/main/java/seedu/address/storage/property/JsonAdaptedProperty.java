// package seedu.address.storage.property;

// import com.fasterxml.jackson.annotation.JsonCreator;
// import com.fasterxml.jackson.annotation.JsonProperty;

// import javafx.beans.property.Property;
// import seedu.address.commons.exceptions.IllegalValueException;
// import seedu.address.model.meetup.PhoneNumber;

// /**
//  * Jackson-friendly version of {@link Property}.
//  */
// public class JsonAdaptedProperty {
//     public static final String MISSING_FIELD_MESSAGE_FORMAT = "Property's %s field is missing!";

//     private final String landlordName;
//     private final String location;
//     private final String askingPrice;
//     private final String phoneNumber;
//     private final String propertyType;

//     /**
//      * Constructs a {@code JsonAdaptedProperty} with the given meet up details.
//      */
//     @JsonCreator
//     public JsonAdaptedProperty(@JsonProperty("landlordName") String landlordName,
//             @JsonProperty("location") String location,
//             @JsonProperty("askingPrice") String askingPrice, @JsonProperty("phoneNumber") String phoneNumber,
//             @JsonProperty("propertyType") String propertyType) {

//         this.landlordName = landlordName;
//         this.location = location;
//         this.askingPrice = askingPrice;
//         this.phoneNumber = phoneNumber;
//         this.propertyType = propertyType;
//     }

//     /**
//      * Converts a given {@code Property} into this class for Jackson use.
//      */
//     public JsonAdaptedProperty(Property source) {
//         landlordName = source.getLandlordName().toString();
//         location = source.getLocation().toString();
//         askingPrice = source.getAskingPrice().toString();
//         phoneNumber = source.getPhoneNumber().toString();
//         propertyType = source.getPropertyType().toString();

//     }

//     /**
//      * Converts this Jackson-friendly adapted property object into the model's {@code Property} object.
//      *
//      * @throws IllegalValueException if there were any data constraints violated in the adapted property.
//      */
//     public Property toModelType() throws IllegalValueException {
//         // This can only be implemented after model is refactored
//         if (landlordName == null) {
//             throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
//                     LandlordName.class.getSimpleName()));
//         }
//         if (!LandlordName.isValidName(landlordName)) {
//             throw new IllegalValueException(LandlordName.MESSAGE_CONSTRAINTS);
//         }
//         final LandlordName modelLandlordName = new LandlordName(landlordName);

//         if (location == null) {
//             throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
//                     Location.class.getSimpleName()));
//         }
//         if (!Location.isValidInfo(location)) {
//             throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
//         }
//         final Location modelLocation = new Location(location);

//         if (askingPrice == null) {
//             throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
//                     AskingPrice.class.getSimpleName()));
//         }
//         if (!AskingPrice.isValidFrom(askingPrice)) {
//             throw new IllegalValueException(AskingPrice.MESSAGE_CONSTRAINTS);
//         }
//         final AskingPrice modelAskingPrice = new AskingPrice(askingPrice);

//         if (phoneNumber == null) {
//             throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
//                     PhoneNumber.class.getSimpleName()));
//         }
//         if (!PhoneNumber.isValidPhoneNumber(phoneNumber)) {
//             throw new IllegalValueException(PhoneNumber.MESSAGE_CONSTRAINTS);
//         }
//         final PhoneNumber modelPhoneNumber = new PhoneNumber(phoneNumber);

//         if (propertyType == null) {
//             throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
//                     PropertyType.class.getSimpleName()));
//         }
//         if (!PropertyType.isValidPropertyType(propertyType)) {
//             throw new IllegalValueException(PropertyType.MESSAGE_CONSTRAINTS);
//         }
//         final PropertyType modelpropertyType = new PropertyType(propertyType);

//         return new Property(modelLandlordName, modelLocation, modelAskingPrice, modelPhoneNumber, modelpropertyType);
//     }
// }
