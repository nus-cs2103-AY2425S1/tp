package spleetwaise.commons.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javafx.scene.image.Image;

@ExtendWith(MockitoExtension.class)
public class UiManagerTest {

    @InjectMocks
    private UiManager uiManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConstructor() {
        // Test that the UiManager object can be instantiated without any exceptions.
        assertNotNull(uiManager, "UiManager should be instantiated without any exceptions");
    }

    @Test
    public void testGetImage() {
        // Test getImage with a valid image path
        Image image = uiManager.getImage("/images/address_book_32.png");
        assertNotNull(image, "Image should not be null for a valid path");
    }
}
