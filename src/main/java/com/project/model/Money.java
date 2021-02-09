package com.project.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Money {
    @NotBlank(message = "{Money.money.notEmpty}")
    @NotNull(message = "{Money.money.notNull}")
    @NotEmpty(message = "{Money.money.notBlank}")
    private String money;

    public String getMoney() {
        return money;
    }

    public void setMoney(final String money) {
        this.money = money;
    }
}
