package 도서.오브젝트.Chapter02.movie.step01;

public class AmountDiscountPolicy extends DiscountPolicy {
    private Money discountAmount; // 할인 요금

    public AmountDiscountPolicy(Money discountAmount, DiscountCondition... conditions) {
        super(conditions);
        this.discountAmount = discountAmount;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) { // 할인 요금을 반환하는 메서드
        return discountAmount;
    }
}
