package com.example.musicalhealing;

import java.util.List;

public class JamendoResp<T> {
    public Object headers;      // unused but present in responses
    public List<T> results;
}
