package ru.yandex.practicum.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/dogs")
public class DogsInteractionController {
    private String owner;
    private int happiness = 0;

    @GetMapping("/converse")
    public Map<String, String> converse() {
        checkHappiness();
        happiness += 2;
        return Map.of("talk", "Гав!");
    }

    @GetMapping("/happiness")
    public Map<String, Integer> happiness() {
        return Map.of("happiness", happiness);
    }

    @PostMapping("/owner")
    public Map<String, String> setOwner(@RequestParam(required = false) final String newOwner) {
        if (newOwner == null) {
            throw new IncorrectParameterException("Параметр newOwner равен null.");
        }

        owner = newOwner;

        return Map.of("owner", owner);
    }

    @PutMapping("/pet")
    public Map<String, String> pet(
            @RequestParam(required = false) final Integer count,
            @RequestParam(required = false) final String user
    ) {
        if (owner == null) {
            throw new IncorrectParameterException("Необходимо установить параметр owner.");
        }
        if (count == null) {
            throw new IncorrectParameterException("Параметр count равен null.");
        }
        if (count <= 0) {
            throw new IncorrectParameterException("Параметр count имеет отрицательное значение.");
        }
        if (user == null) {
            throw new IncorrectParameterException("Параметр user равен null.");
        }
        if (!user.equals(owner)) {
            throw new UnauthorizedUserException(user, owner);
        }

        checkHappiness();

        happiness += count;
        return Map.of("action", "Вильнул хвостом. ".repeat(count));
    }

    private void checkHappiness() {
        if (happiness > 10) {
            throw new HappinessOverflowException(happiness);
        }
    }
}