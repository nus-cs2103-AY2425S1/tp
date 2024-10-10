package seedu.address.ui;

import javafx.scene.layout.HBox;

/**
 * Represents the main window panel of the application that organizes layout and display of subpanels.
 * This class extends {@link HBox}, a horizontal box layout container in JavaFX, which arranges its children in a single
 * horizontal row.
 *
 * <p>This panel is designed to include a list panel on one side and a details or additional operations panel on the
 * other side.
 * It ensures that the application's main interface is divided into distinct sections for displaying lists of items and
 * their details or related operations.
 *
 * <p>Usage example:
 * <pre>
 *     PersonListPanel personListPanel = new PersonListPanel();
 *     RightPanel rightPanel = new RightPanel();
 *     MainWindowPanel mainWindowPanel = new MainWindowPanel(personListPanel, rightPanel);
 * </pre>
 */
public class MainWindowPanel extends HBox {

    /**
     * Constructs a new MainWindowPanel with specified subpanels.
     * This constructor initializes the MainWindowPanel with two subpanels:
     * one for the person list and another for additional details or operations.
     * It also sets the spacing between these panels.
     *
     * @param personListPanel The panel that displays a list of persons, typically on the left side.
     *                        This panel must be initialized prior to being passed to this constructor.
     * @param rightPanel      The panel that is used for displaying details or performing operations related
     *                        to the selected person or task, typically placed on the right.
     *                        This panel must also be initialized prior to being passed to this constructor.
     */
    public MainWindowPanel(PersonListPanel personListPanel, RightPanel rightPanel) {
        this.getChildren().addAll(personListPanel.getRoot(), rightPanel.getRoot());
        this.setSpacing(10);
    }
}
