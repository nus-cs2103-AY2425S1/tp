package hallpointer.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import hallpointer.address.commons.core.Config;
import hallpointer.address.commons.core.LogsCenter;
import hallpointer.address.commons.core.Version;
import hallpointer.address.commons.exceptions.DataLoadingException;
import hallpointer.address.commons.util.ConfigUtil;
import hallpointer.address.commons.util.StringUtil;
import hallpointer.address.logic.Logic;
import hallpointer.address.logic.LogicManager;
import hallpointer.address.model.HallPointer;
import hallpointer.address.model.Model;
import hallpointer.address.model.ModelManager;
import hallpointer.address.model.ReadOnlyHallPointer;
import hallpointer.address.model.ReadOnlyUserPrefs;
import hallpointer.address.model.UserPrefs;
import hallpointer.address.model.util.SampleDataUtil;
import hallpointer.address.storage.HallPointerStorage;
import hallpointer.address.storage.JsonHallPointerStorage;
import hallpointer.address.storage.JsonUserPrefsStorage;
import hallpointer.address.storage.Storage;
import hallpointer.address.storage.StorageManager;
import hallpointer.address.storage.UserPrefsStorage;
import hallpointer.address.ui.Ui;
import hallpointer.address.ui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 5, 1, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing HallPointer ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        HallPointerStorage hallPointerStorage = new JsonHallPointerStorage(userPrefs.getHallPointerFilePath());
        storage = new StorageManager(hallPointerStorage, userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s hall pointer and {@code userPrefs}. <br>
     * The data from the sample hall pointer will be used instead if {@code storage}'s hall pointer is not found,
     * and an empty hall pointer will be used instead if errors occur when reading {@code storage}'s hall pointer.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getHallPointerFilePath());

        Optional<ReadOnlyHallPointer> hallPointerOptional;
        ReadOnlyHallPointer initialData;
        try {
            hallPointerOptional = storage.readHallPointer();
            if (!hallPointerOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getHallPointerFilePath()
                        + " populated with a sample HallPointer.");
            }
            initialData = hallPointerOptional.orElseGet(SampleDataUtil::getSampleHallPointer);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getHallPointerFilePath() + " could not be loaded."
                    + " Will be starting with an empty HallPointer.");
            initialData = new HallPointer();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@link Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting HallPointer " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping HallPointer ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
