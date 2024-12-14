package com.skypro.bank_star.listener;

import com.skypro.bank_star.dto.UserDto;
import com.skypro.bank_star.request.TelegramBotEmoji;
import com.skypro.bank_star.service.UserDynamicRecommendationsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class TelegramBotUpdatesMethods {

    private final UserDynamicRecommendationsService userDynamicRecommendationsService;

    private final String STICKER = "CAACAgIAAxkBAAEJ7LJnMQWrHjz2qx3Yls1ZJugsDHZ-WwACRwIAAkcVaAnk9we1CELDHjYE";

    String sendHelloMessage(String firstName) {
        return "Привет, " + firstName + "! "
                + TelegramBotEmoji.RAISED_HANDS.get() + "\n"
                + TelegramBotEmoji.ROBOT_FACE.get() +
                " Бот находит лучшие рекомендации продуктов" +
                " Банка \"Стар\" для своих клиентов.\n" +
                "Чтобы узнать как им пользоваться введи команду */help*";
    }

    String sendHelpMessage() {
        return TelegramBotEmoji.STAR.get() +
                " Чтобы узнать, какие продукты больше всего тебе подходят,\n" +
                "отправь команду */recommend* и свой *username* через пробел.\n" +
                "Например: */recommend luke.skywalker*";
    }

    String sendNullMessage() {
        return TelegramBotEmoji.PILL.get() +
                " Я сломался, мне нужен доктор...";
    }

    String sendLengthMessage() {
        return TelegramBotEmoji.X.get() +
                " Нужно написать после /recommend *только*" +
                " свой *username*, *ничего больше*!";
    }

    String sendUserListMessage() {
        return TelegramBotEmoji.DISAPPOINTED.get() +
                " Пользователь не найден (";
    }

    String sendSuccessMessage(UserDto userDTO) {
        String recommendation = userDynamicRecommendationsService
                .getAllDynamicRulesRecommendationsForTelegramBot(userDTO.id());
        return (TelegramBotEmoji.TADA.get() +
                " *Поиск нашел рекомендацию!*\n\n" +
                "Здравствуйте, *%s* *%s*!\n\n" +
                "Новые продукты для вас:\n\n\n%s").formatted(
                userDTO.firstName(),
                userDTO.lastName(),
                recommendation);
    }

    String sendErrorMessage() {
        return "Нужно обязательно указать через *пробел* после" +
                " */recommend* свой *username*\n\n"
                + TelegramBotEmoji.WRITING_HAND.get() +
                " Например:\n*/recommend jabba.hutt*";
    }

    String sendElseMessage() {
        return TelegramBotEmoji.X.get() +
                " *Сообщение не распознано.*\n" +
                "Повторите ввод.\n\n" +
                "Чтобы начать новый поиск, отправьте: /start";
    }

}