package com.example.spital.utils;

import com.example.spital.utils.events.Event;

public interface Observer<E extends Event> {
    void update(E e);
}
