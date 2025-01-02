package com.skypro.bank_star.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TelegramBotCommand {

    RECOMMEND("/recommend"),
    START("/start"),
    HELP("/help");

    private final String command;

}