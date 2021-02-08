package com.project.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Money {
    @NotBlank(message = "Money can't be blank")
    @NotNull(message = "Money can't be null")
    @NotEmpty(message = "Money can't be empty")
    private String money;

    public String getMoney() {
        return money;
    }

    public void setMoney(final String money) {
        this.money = money;
    }
}
