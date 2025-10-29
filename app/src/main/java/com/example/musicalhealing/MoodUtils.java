package com.example.musicalhealing;

import java.util.*;

public class MoodUtils {
    private static final Map<String, List<String>> SYN = new HashMap<>();
    static {
        SYN.put("happy", Arrays.asList("uplifting", "feel good", "upbeat", "cheerful"));
        SYN.put("sad", Arrays.asList("melancholic", "somber", "emotional"));
        SYN.put("calm", Arrays.asList("ambient", "chill", "relaxing", "lofi"));
        SYN.put("angry", Arrays.asList("intense", "heavy", "energetic"));
    }

    public static String expandQuery(String mood) {
        if (mood == null) mood = "";
        String key = mood.toLowerCase(Locale.US).trim();
        List<String> extras = SYN.getOrDefault(key, Collections.<String>emptyList());
        List<String> picks = new ArrayList<>();
        picks.add(mood);
        for (String e : extras) {
            if (picks.size() >= 3) break;
            picks.add(e);
        }
        return String.join(" ", picks);
    }
}
