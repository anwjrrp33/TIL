package 도서.오브젝트.Chapter02.movie.step01;

public class SequenceCondition implements DiscountCondition { // 할인조건 인터페이스를 상속
    private int sequence; // 순번

    public SequenceCondition(int sequence) {
        this.sequence = sequence;
    }

    public boolean isSatisfiedBy(Screening screening) { // 할인 여부를 반환하는 메서드
        return screening.isSequence(sequence);
    }
}