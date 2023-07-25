package 도서.오브젝트.Chapter11.eternity.billing.step05;

import java.time.Duration;
import java.time.LocalDateTime;

public class Call {
	private LocalDateTime from;
	private LocalDateTime to;
	
	public Call(LocalDateTime from, LocalDateTime to) {
		this.from = from;
		this.to = to;
	}

	public Duration getDuration() {
		return Duration.between(from, to);
	}
	
	public LocalDateTime getFrom() {
		return from;
	}

}
