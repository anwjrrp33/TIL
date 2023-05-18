package 도서.오브젝트.Chapter02.movie.step02;

import 도서.오브젝트.Chapter02.movie.step01.Money;
import 도서.오브젝트.Chapter02.movie.step01.Screening;

public interface DiscountPolicy {
    Money calculateDiscountAmount(Screening screening); // 할인 정책을 통해서 할인 요금을 계산해서 반환하는 추상 메서드
}