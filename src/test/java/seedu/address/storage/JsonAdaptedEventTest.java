//package seedu.address.storage;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static seedu.address.testutil.TypicalEvents.AUG;
//
//import org.junit.jupiter.api.Test;
//
//
//public class JsonAdaptedEventTest {
//    private static final String INVALID_EVENT_NAME = "  ";
//
//    @Test
//    public void toModelType_validEventDetails_returnsPerson() throws Exception {
//        JsonAdaptedEvent event = new JsonAdaptedEvent(AUG);
//        assertEquals(AUG, event.toModelType());
//    }
//
//    @Test
//    public void toModelType_invalidEventName_throwsIllegalValueException() {
//        JsonAdaptedEvent event = new JsonAdaptedEvent(INVALID_EVENT_NAME);
//        String expectedMessage = EventName.MESSAGE_CONSTRAINTS;
//        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
//    }
//
//    @Test
//    public void toModelType_nullEventName_throwsIllegalValueException() {
//        JsonAdaptedEvent event = new JsonAdaptedEvent((String) null);
//        String expectedMessage = String.format(JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT,
//                EventName.class.getSimpleName());
//        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
//  }
//
//}
