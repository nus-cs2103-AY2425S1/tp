package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.BuyerList;
import seedu.address.model.MeetUpList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PropertyList;
import seedu.address.model.ReadOnlyBuyerList;
import seedu.address.model.ReadOnlyMeetUpList;
import seedu.address.model.ReadOnlyPropertyList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleBuyerDataUtil;
import seedu.address.model.util.SampleMeetUpDataUtil;
import seedu.address.model.util.SamplePropertyDataUtil;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.buyer.BuyerListStorage;
import seedu.address.storage.buyer.JsonBuyerListStorage;
import seedu.address.storage.meetup.JsonMeetUpListStorage;
import seedu.address.storage.meetup.MeetUpListStorage;
import seedu.address.storage.property.JsonPropertyListStorage;
import seedu.address.storage.property.PropertyListStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 2, 2, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing BuyerList ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        BuyerListStorage buyerListStorage = new JsonBuyerListStorage(userPrefs.getBuyerListFilePath());
        MeetUpListStorage meetUpListStorage = new JsonMeetUpListStorage(userPrefs.getMeetUpListFilePath());
        PropertyListStorage propertyListStorage = new JsonPropertyListStorage(userPrefs.getPropertyListFilePath());
        storage = new StorageManager(buyerListStorage, userPrefsStorage, meetUpListStorage, propertyListStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s buyer list and {@code userPrefs}. <br>
     * The data from the sample buyer list will be used instead if {@code storage}'s buyer list is not found,
     * or an empty buyer list will be used instead if errors occur when reading {@code storage}'s buyer list.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getBuyerListFilePath());
        logger.info("Using meetUp file : " + storage.getMeetUpListFilePath());
        logger.info("Using property file : " + storage.getPropertyListFilePath());

        Optional<ReadOnlyBuyerList> buyerListOptional;
        Optional<ReadOnlyMeetUpList> meetUpListOptional;
        Optional<ReadOnlyPropertyList> propertyListOptional;
        ReadOnlyBuyerList initialData;
        ReadOnlyMeetUpList initialMeetUpList;
        ReadOnlyPropertyList initialPropertyList;
        try {
            buyerListOptional = storage.readBuyerList();
            if (!buyerListOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getBuyerListFilePath()
                        + " populated with a sample BuyerList.");
            }
            initialData = buyerListOptional.orElseGet(SampleBuyerDataUtil::getSampleBuyerList);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getBuyerListFilePath() + " could not be loaded."
                    + " Will be starting with an empty BuyerList.");
            initialData = new BuyerList();
        }

        try {
            meetUpListOptional = storage.readMeetUpList();
            if (!meetUpListOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getMeetUpListFilePath()
                        + " populated with a sample MeetUpList.");
            }

            initialMeetUpList = meetUpListOptional.orElseGet(SampleMeetUpDataUtil::getSampleMeetUpList);

        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getMeetUpListFilePath() + " could not be loaded."
                    + " Will be starting with an empty MeetUpList.");
            initialMeetUpList = new MeetUpList();
        }

        try {
            propertyListOptional = storage.readPropertyList();
            if (!propertyListOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getPropertyListFilePath()
                        + " populated with a sample PropertyList.");
            }

            initialPropertyList = propertyListOptional.orElseGet(SamplePropertyDataUtil::getSamplePropertyList);
            logger.info("initial list is " + initialPropertyList);

        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getPropertyListFilePath() + " could not be loaded."
                    + " Will be starting with an empty PropertyList.");
            initialPropertyList = new PropertyList();
        }

        return new ModelManager(initialData, userPrefs, initialMeetUpList, initialPropertyList);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
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
        logger.info("Starting BuyerList " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping BuyerList ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
