package 도서.오브젝트.Chapter02.movie.step01;

public class PercentDiscountPolicy extends DiscountPolicy {
    private double percent; // 할인 비율

    public PercentDiscountPolicy(double percent, DiscountCondition... conditions) {
        super(conditions);
        this.percent = percent;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) { // 할인 요금을 반환하는 메서드
        return screening.getMovieFee().times(percent);
    }
}