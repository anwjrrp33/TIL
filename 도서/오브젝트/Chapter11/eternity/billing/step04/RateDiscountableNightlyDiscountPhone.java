package 도서.오브젝트.Chapter11.eternity.billing.step04;

import 도서.오브젝트.Chapter11.eternity.money.Money;

import java.time.Duration;

public class RateDiscountableNightlyDiscountPhone extends NightlyDiscountPhone {
    private Money discountAmount;

    public RateDiscountableNightlyDiscountPhone(Money nightlyAmount,
                                                Money regularAmount, Duration seconds, Money discountAmount) {
        super(nightlyAmount, regularAmount, seconds);
        this.discountAmount = discountAmount;
    }

    @Override
    protected Money afterCalculated(Money fee) {
        return fee.minus(discountAmount);
    }
}
