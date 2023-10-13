package com.example.sns.domain.messaging.event;

import java.util.ArrayList;
import java.util.List;

public class Events {
    private static ThreadLocal<List<Event>> eventThreadLocal = ThreadLocal.withInitial(ArrayList::new);

    private Events() {
        throw new IllegalStateException("Do not Initialize.");
    }

    public static <T> void register(T event) {
        if (event == null) {
            return;
        }
        eventThreadLocal.get().add((Event) event);
    }

    public static List<Event> getEvents() {
        return eventThreadLocal.get();
    }

    public static void clear() {
        eventThreadLocal.get().clear();
    }
}
