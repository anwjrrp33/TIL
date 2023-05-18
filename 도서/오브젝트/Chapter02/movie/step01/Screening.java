package 도서.오브젝트.Chapter02.movie.step01;

import java.time.LocalDateTime;

public class Screening {
    private Movie movie; // 상영할 영화
    private int sequence; // 순번
    private LocalDateTime whenScreened; // 상영 시작 시간

    public Screening(Movie movie, int sequence, LocalDateTime whenScreened) {
        this.movie = movie;
        this.sequence = sequence;
        this.whenScreened = whenScreened;
    }

    public LocalDateTime getStartTime() { // 상영 시작 시간을 반환하는 메서드
        return whenScreened;
    }

    public boolean isSequence(int sequence) { // 순번의 일치 여부를 검사하는 메서드
        return this.sequence == sequence;
    }

    public Money getMovieFee() { // 기본 요금을 반환하는 메서드
        return movie.getFee();
    }

    public Reservation reserve(Customer customer, int audienceCount) { // 영화를 예매한 후 예매 정보를 반환하는 메서드
        return new Reservation(customer, this, calculateFee(audienceCount), audienceCount);
    }

    private Money calculateFee(int audienceCount) { // 예매 요금을 구하는 메서드
        return movie.calculateMovieFee(this).times(audienceCount);
    }
}
