package 도서.오브젝트.Chapter11.eternity.billing.step04;

import 도서.오브젝트.Chapter11.eternity.money.Money;

import java.time.Duration;

public class RateDiscountableAndTaxableRegularPhone
        extends RateDiscountableRegularPhone {
    private double taxRate;

    public RateDiscountableAndTaxableRegularPhone(Money amount, Duration seconds, Money discountAmount, double taxRate) {
        super(amount, seconds, discountAmount);
        this.taxRate = taxRate;
    }

    @Override
    protected Money afterCalculated(Money fee) {
        return super.afterCalculated(fee).plus(fee.times(taxRate));
    }
}
