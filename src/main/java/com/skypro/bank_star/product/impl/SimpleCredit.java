package com.skypro.bank_star.product.impl;

import com.skypro.bank_star.model.Recommendations;
import com.skypro.bank_star.product.RecommendationRuleSet;
import com.skypro.bank_star.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SimpleCredit implements RecommendationRuleSet {

    private final RecommendationsRepository recommendationsRepository;

    private final Recommendations recommendations;

    public SimpleCredit(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
        this.recommendations = new Recommendations("Простой кредит",
                UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"),
                "Откройте мир выгодных кредитов с нами!" +
                        "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит" +
                        " — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и" +
                        " индивидуальный подход к каждому клиенту." +
                        "Почему выбирают нас:" +
                        "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки " +
                        "занимает всего несколько часов." +
                        "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном" +
                        " приложении." +
                        "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку " +
                        "недвижимости, автомобиля, образование, лечение и многое другое." +
                        "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!");
    }

    @Override
    public Optional<Object> getRecommendations(UUID users_id) {
        if (!recommendationsRepository.hasCreditProduct(users_id) &&
                recommendationsRepository.getDebitAmount(users_id) > recommendationsRepository.getDebitExpenses(users_id) &&
                recommendationsRepository.getDebitExpenses(users_id) > 100_000
        ) {
            return Optional.of(recommendations);
        }
        return Optional.empty();
    }
}