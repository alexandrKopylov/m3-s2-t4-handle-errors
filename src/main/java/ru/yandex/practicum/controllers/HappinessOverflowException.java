package ru.yandex.practicum.controllers;

public class HappinessOverflowException extends RuntimeException {
    private Integer happinessLevel;

    public HappinessOverflowException(Integer happinessLevel) {
        this.happinessLevel = happinessLevel;
    }

    public Integer getHappinessLevel() {
        return happinessLevel;
    }
}