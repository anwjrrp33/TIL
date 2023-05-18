package 도서.오브젝트.Chapter02.movie.step02;

import java.time.Duration;

import 도서.오브젝트.Chapter02.movie.step01.DiscountPolicy;
import 도서.오브젝트.Chapter02.movie.step01.Money;
import 도서.오브젝트.Chapter02.movie.step01.Screening;

public class Movie {
    private String title; // 제목
    private Duration runningTime; // 상영시간
    private Money fee; // 기본요금
    private DiscountPolicy discountPolicy; // 할인 정책

    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = discountPolicy;
    }

    public Money getFee() {
        return fee;
    }

    public Money calculateMovieFee(Screening screening) { // 할인요금을 반환하는 메서드
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }

    public void changeDiscountPolicy(DiscountPolicy discountPolicy) { // 할인 정책을 변경하는 메서드
        this.discountPolicy = discountPolicy;
    }
}