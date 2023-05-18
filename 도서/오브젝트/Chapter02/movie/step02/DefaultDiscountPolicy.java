package 도서.오브젝트.Chapter02.movie.step02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import 도서.오브젝트.Chapter02.movie.step01.DiscountCondition;
import 도서.오브젝트.Chapter02.movie.step01.Money;
import 도서.오브젝트.Chapter02.movie.step01.Screening;

public abstract class DefaultDiscountPolicy implements DiscountPolicy {
    private List<DiscountCondition> conditions = new ArrayList<>();

    public DefaultDiscountPolicy(DiscountCondition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    @Override
    public Money calculateDiscountAmount(Screening screening) { // 할인 정책을 통해서 할인 요금을 계산해서 반환하는 메서드
        for(DiscountCondition each : conditions) {
            if (each.isSatisfiedBy(screening)) {
                return getDiscountAmount(screening);
            }
        }

        return Money.ZERO;
    }

    abstract protected Money getDiscountAmount(Screening Screening); // 할인된 금액을 반환하는 추상 메서드
}
