package 도서.오브젝트.Chapter02.movie.step02;

import 도서.오브젝트.Chapter02.movie.step01.Money;
import 도서.오브젝트.Chapter02.movie.step01.Screening;

public class NoneDiscountPolicy implements DiscountPolicy {
    @Override
    public Money calculateDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
