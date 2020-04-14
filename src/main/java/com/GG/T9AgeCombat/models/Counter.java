package com.GG.T9AgeCombat.models;

import org.springframework.stereotype.Component;

@Component
public class Counter {
    private int count;

    public int getCount() {
        return count;
    }

    public void increaseCount() {
        count++;
    }
}