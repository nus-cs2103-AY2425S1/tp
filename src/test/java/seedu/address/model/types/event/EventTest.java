package seedu.address.model.types.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ART_EXHIBIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ART_EXHIBIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOOK_FAIR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_ART_EXHIBIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CULTURE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.ANIME;
import static seedu.address.testutil.TypicalEvents.ART_EXHIBIT;
import static seedu.address.testutil.TypicalEvents.BARBEQUE;
import static seedu.address.testutil.TypicalEvents.BOOK_FAIR;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;


public class EventTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Event event = new EventBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> event.getTags().remove(0));
    }

    @Test
    public void isSameEvent() {
        // same object -> returns true
        assertTrue(ART_EXHIBIT.isSameEvent(ART_EXHIBIT));

        // null -> returns false
        assertFalse(ART_EXHIBIT.isSameEvent(null));

        // same name, location, and startTime -> returns true
        Event editedArtExhibit = new EventBuilder(ART_EXHIBIT).withAddress(VALID_ADDRESS_ART_EXHIBIT)
                .withStartTime(VALID_START_TIME_ART_EXHIBIT).withTags(VALID_TAG_CULTURE).build();
        assertTrue(ART_EXHIBIT.isSameEvent(editedArtExhibit));

        // different name -> returns false
        editedArtExhibit = new EventBuilder(ART_EXHIBIT).withName(VALID_NAME_BOOK_FAIR).build();
        assertFalse(ART_EXHIBIT.isSameEvent(editedArtExhibit));

        // name differs in case, all other attributes same -> returns false
        Event editedBookFair = new EventBuilder(BOOK_FAIR).withName(VALID_NAME_BOOK_FAIR.toLowerCase()).build();
        assertFalse(BOOK_FAIR.isSameEvent(editedBookFair));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOOK_FAIR + " ";
        editedBookFair = new EventBuilder(BOOK_FAIR).withName(nameWithTrailingSpaces).build();
        assertFalse(BOOK_FAIR.isSameEvent(editedBookFair));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event animeCopy = new EventBuilder(ANIME).build();
        assertTrue(ANIME.equals(animeCopy));

        // same object -> returns true
        assertTrue(ANIME.equals(ANIME));

        // null -> returns false
        assertFalse(ANIME.equals(null));

        // different type -> returns false
        assertFalse(ANIME.equals(5));

        // different event -> returns false
        assertFalse(ANIME.equals(BARBEQUE));

        // different name -> returns false
        Event editedAnime = new EventBuilder(ANIME).withName(VALID_NAME_ART_EXHIBIT).build();
        assertFalse(ANIME.equals(editedAnime));

        // different location -> returns false
        editedAnime = new EventBuilder(ANIME).withAddress(VALID_ADDRESS_ART_EXHIBIT).build();
        assertFalse(ANIME.equals(editedAnime));

        // different startTime -> returns false
        editedAnime = new EventBuilder(ANIME).withStartTime(VALID_START_TIME_ART_EXHIBIT).build();
        assertFalse(ANIME.equals(editedAnime));

        // different tags -> returns false
        editedAnime = new EventBuilder(ANIME).withTags(VALID_TAG_CULTURE).build();
        assertFalse(ANIME.equals(editedAnime));
    }

    @Test
    public void toStringMethod() {
        String expected = Event.class.getCanonicalName() + "{name=" + ANIME.getName()
                + ", location=" + ANIME.getLocation() + ", startTime=" + ANIME.getStartTime()
                + ", tags=" + ANIME.getTags() + "}";
        assertEquals(expected, ANIME.toString());
    }
}
