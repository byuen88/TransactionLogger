package persistence;

import org.json.JSONObject;

// Modelled after the JsonSerializationDemo sample application
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
