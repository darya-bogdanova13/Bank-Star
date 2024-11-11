package com.skypro.bank_star.product.impl;

import com.skypro.bank_star.model.Recommendations;
import com.skypro.bank_star.product.RecommendationRuleSet;
import com.skypro.bank_star.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class TopSaving implements RecommendationRuleSet {

    private final RecommendationsRepository recommendationsRepository;
    private final Recommendations recommendations;
    public TopSaving(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
        this.recommendations = new Recommendations("Top Saving",
                UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"),
                "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский " +
                        "инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. " +
                        "Больше никаких забытых чеков и потерянных квитанций — всё под контролем!" +
                        "Преимущества «Копилки»:" +
                        "Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет " +
                        "автоматически переводить определенную сумму на ваш счет. " +
                        "Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс " +
                        "накопления и корректируйте стратегию при необходимости. " +
                        "Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним " +
                        "возможен только через мобильное приложение или интернет-банкинг. " +
                        "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!");
    }
    @Override
    public Optional<Object> getRecommendations(UUID users_id) {
        if (recommendationsRepository.hasDebitProduct(users_id) &&
                (recommendationsRepository.getDebitAmount(users_id) >= 50_000 ||
                        recommendationsRepository.getSavingAmount(users_id) >= 50_000) &&
                recommendationsRepository.getDebitAmount(users_id) > recommendationsRepository.getDebitExpenses(users_id)
        ) {
            return Optional.of(recommendations);
        }
        return Optional.empty();
    }
}