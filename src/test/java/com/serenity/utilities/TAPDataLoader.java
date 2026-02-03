package com.serenity.utilities;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * TAPDataLoader is a utility class to load TAP-related test data from tap_testdata.json.
 * It uses the existing JsonReader utility for file reading and parsing.
 * Provides convenience methods to access TAP users, accounts, and opportunity details.
 */
public class TAPDataLoader {
    private static final String TAP_TESTDATA_PATH = "src/test/resources/testdata/tap_testdata.json";
    private static TAPDataLoader instance;
    private JsonObject rootObject;

    private TAPDataLoader() {
        loadTestData();
    }

    /**
     * Singleton instance getter.
     * @return TAPDataLoader instance
     */
    public static TAPDataLoader getInstance() {
        if (instance == null) {
            synchronized (TAPDataLoader.class) {
                if (instance == null) {
                    instance = new TAPDataLoader();
                }
            }
        }
        return instance;
    }

    /**
     * Loads the tap_testdata.json file into a JsonObject.
     */
    private void loadTestData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(TAP_TESTDATA_PATH))) {
            JsonElement element = JsonParser.parseReader(reader);
            if (element.isJsonObject()) {
                rootObject = element.getAsJsonObject();
            } else {
                rootObject = new JsonObject();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("TAP test data file not found: " + TAP_TESTDATA_PATH, e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading TAP test data file: " + TAP_TESTDATA_PATH, e);
        }
    }

    /**
     * Returns a list of TAP users from the test data.
     * @return List of user maps, or empty list if not found
     */
    public List<Map<String, Object>> getTapUsers() {
        if (rootObject == null || !rootObject.has("tapUsers")) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
        return new Gson().fromJson(rootObject.get("tapUsers"), listType);
    }

    /**
     * Returns a list of TAP accounts from the test data.
     * @return List of account maps, or empty list if not found
     */
    public List<Map<String, Object>> getTapAccounts() {
        if (rootObject == null || !rootObject.has("tapAccounts")) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
        return new Gson().fromJson(rootObject.get("tapAccounts"), listType);
    }

    /**
     * Returns a list of opportunity details for TAP Deal Intake scenarios.
     * @return List of opportunity detail maps, or empty list if not found
     */
    public List<Map<String, Object>> getTapOpportunityDetails() {
        if (rootObject == null || !rootObject.has("tapOpportunityDetails")) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
        return new Gson().fromJson(rootObject.get("tapOpportunityDetails"), listType);
    }

    /**
     * Returns a convenience method to fetch a TAP user by username.
     * @param username Username to search
     * @return User map or null if not found
     */
    public Map<String, Object> getTapUserByUsername(String username) {
        List<Map<String, Object>> users = getTapUsers();
        if (users == null) return null;
        for (Map<String, Object> user : users) {
            if (username.equals(user.get("username"))) {
                return user;
            }
        }
        return null;
    }

    /**
     * Returns a TAP account by account name.
     * @param accountName Account name to search
     * @return Account map or null if not found
     */
    public Map<String, Object> getTapAccountByName(String accountName) {
        List<Map<String, Object>> accounts = getTapAccounts();
        if (accounts == null) return null;
        for (Map<String, Object> account : accounts) {
            if (accountName.equals(account.get("accountName"))) {
                return account;
            }
        }
        return null;
    }

    /**
     * Returns opportunity details by opportunity name.
     * @param opportunityName Opportunity name to search
     * @return Opportunity detail map or null if not found
     */
    public Map<String, Object> getTapOpportunityDetailByName(String opportunityName) {
        List<Map<String, Object>> opportunities = getTapOpportunityDetails();
        if (opportunities == null) return null;
        for (Map<String, Object> opp : opportunities) {
            if (opportunityName.equals(opp.get("opportunityName"))) {
                return opp;
            }
        }
        return null;
    }
}
