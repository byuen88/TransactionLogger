package ui;


import model.Logger;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tabs.AnalysisTab;
import ui.tabs.HomeTab;
import ui.tabs.SettingsTab;

import javax.swing.*;

/*
 * Represents the graphical user interface in which the Transaction Logger
 * application is used.
 */
public class TransactionLogger extends JFrame {
    private static final String JSON_STORE = "./data/logger.json";
    private static final int HOME_TAB_INDEX = 0;
    private static final int ANALYSIS_TAB_INDEX = 1;
    private static final int SETTINGS_TAB_INDEX = 2;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;
    private Logger log;
    private JTabbedPane sideBar;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private HomeTab homeTab;
    private AnalysisTab analysisTab;
    private SettingsTab settingsTab;

    // MODIFIES: this
    // EFFECTS: constructs the Transaction Logger
    public TransactionLogger() {
        super("Transaction Logger");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initializeFields();
        initializeGraphics();
    }

    // getters
    public Logger getLog() {
        return log;
    }

    public JsonWriter getJsonWriter() {
        return jsonWriter;
    }

    public JsonReader getJsonReader() {
        return jsonReader;
    }

    public String getJsonStore() {
        return JSON_STORE;
    }

    // EFFECTS: updates logger in the tab classes
    public void updateLog(Logger logger) {
        log = logger;
        homeTab.setCalendar(logger);
        analysisTab.setCalendar(logger);
        settingsTab.setCalendar(logger);
    }

    // EFFECTS: initializes the fields in the class
    private void initializeFields() {
        log = new Logger();
    }

    // EFFECTS: initializes the graphics
    private void initializeGraphics() {

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        sideBar = new JTabbedPane();
        sideBar.setTabPlacement(JTabbedPane.LEFT);

        loadTabs();
        add(sideBar);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: loads the tabs in
    private void loadTabs() {
        homeTab = new HomeTab(this);
        analysisTab = new AnalysisTab(this);
        settingsTab = new SettingsTab(this);

        sideBar.add(homeTab, HOME_TAB_INDEX);
        sideBar.setTitleAt(HOME_TAB_INDEX, "Home");

        sideBar.add(analysisTab, ANALYSIS_TAB_INDEX);
        sideBar.setTitleAt(ANALYSIS_TAB_INDEX, "Analysis");

        sideBar.add(settingsTab, SETTINGS_TAB_INDEX);
        sideBar.setTitleAt(SETTINGS_TAB_INDEX, "Settings");
    }

    public static void main(String[] args) {
        new TransactionLogger();
    }
}

