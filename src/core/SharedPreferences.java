package core;

import java.util.prefs.Preferences;

public class SharedPreferences {
    // Preference key
    private static final String EMAIL = "email";
    private static final String KEYWORD = "keyword";

    public void saveEmail(String email) {
    	Preferences prefs = Preferences.userNodeForPackage(SharedPreferences.class);
        prefs.put(EMAIL, email);
    }
    
    public void saveKeyword(String keyword) {
    	Preferences prefs = Preferences.userNodeForPackage(SharedPreferences.class);
        prefs.put(KEYWORD, keyword);
    }
    
    public String readEmail() {
    	Preferences prefs = Preferences.userNodeForPackage(SharedPreferences.class);
        return prefs.get(EMAIL, "your@email.com");
    }
    
    public String readKeyword() {
    	Preferences prefs = Preferences.userNodeForPackage(SharedPreferences.class);
        return prefs.get(KEYWORD, "");
    }
}