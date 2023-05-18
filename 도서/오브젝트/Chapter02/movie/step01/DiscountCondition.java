package 도서.오브젝트.Chapter02.movie.step01;

public interface DiscountCondition {
    boolean isSatisfiedBy(Screening screening); // 할인 여부를 판단하는 추상 메서드
}
