# 14. 일관성 있는 협력
### 객체와 협력
* 객체는 협력을 위한 존재하고, 협력은 객체가 존재하는 이유와 문맥을 제공한다.
* 객체지향 설계의 목표는 협력을 기반으로 결합도가 낮고 재사용 가능한 코드 구조를 창조하는 것이다.
* 유사한 요구사항을 계속 추가해야 하는 상황에서 각 협력이 서로 다른 패턴 을 따를 경우에는 전체적인 설계의 일관성이 서서히 무너진다.
* 객체지향 패러다임의 장점은 설계의 재사용성이지만 재사용을 위해서는 객체들의 협력 방식을 일관성 있게 만들어야 하며 일관성은 설계에 드는 비용을 감소시킨다.
* 가능하면 유사한 기능을 구현하기 위해 유사한 협력 패턴을 사용해서 시스템을 이해하고 확장하기 위해 요구되는 정신적인 부담을 줄일 수 있다.

> 일관성 있는 협력 패턴을 적용하면 코드가 이해하기 쉽고 직관적이며 유연해진다.

## 01. 핸드폰 과금 시스템 변경하기
### 기본 정책 확장
* 과금 시스템의 요금 정책을 수정해야 한다.
* 기본 정책을 4가지 방식으로 확장한다.

기본 정책을 구성하는 4가지 방식
* 고정요금 방식
  * 일정 시간 단위로 동일한 요금을 부과하는 방식
* 시간대별 방식
  * 하루 24시간을 특정한 시간 구간으로 나눈 후 각 구간별로 서로 다른 요금을 부과하는 방식
* 요일별 방식
  * 요일별로 요금을 차등 부과하는 방식
* 구간별 방식
  * 전체 통화 시간을 일정한 통화 시간에 따라 나누고 각 구간별로 요금을 차등 부과하는 방식

#### 기본 정책의 종류
<table>
    <tr>
        <th>유형</th>
        <th>형식</th>
        <th>예</th>
    </tr>
    <tr>
        <td>고정요금 방식</td>
        <td>A초당 B원</td>
        <td>10초당 18원</td>
    </tr>
    <tr>
        <td>시간대별 방식</td>
        <td>A시부터 B시까지 C초당 D원<br>B시부터 이까지 C초당 E원</td>
        <td>00시부터 19시까지 10초당 18원<br>19시부터 24시까지 10초당 15원</td>
    </tr>
    <tr>
        <td>요일별 방식</td>
        <td>평일에는 A초당 B원<br>공휴일에는 A초당 C원</td>
        <td>평일에는 10초당 38원<br>공휴일에는 10초당 19원</td>
    </tr>
    <tr>
        <td>구간별 방식</td>
        <td>초기 A분 동안 B초당 C원<br>A분~D분까지 B초당 D원<br>D분 초과 시 B초당 E원</td>
        <td>초기 분 동안 10초당 50원<br>초기 1분 이후 10초당 20원</td>
    </tr>
</table>

#### 조합 가능한 모든 경우의 수
<img src="./image/그림%2014.1.png">


#### 클래스의 구조
* 고정요금 방식 → FixedFeePolicy
* 시간대별 방식 → TimeOfDayDiscountPolicy
* 요일별 방식 → DayOfWeekDiscountPolicy
* 구간별 방식 → DurationDiscountPolicy

### 고정요금 방식 구현하기
* 고정요금 방식은 일반요금제와 동일하기 때문에 클래스 RegularPolicy → FixedFeePolicy로 수정한다.
```
public class FixedFeePolicy extends BasicRatePolicy {       
    private Money amount;
    private Duration seconds;

    public FixedFeePolicy(Money amount, Duration seconds) { 
        this.amount = amount;
        this.seconds = seconds;
    }

    @Override
    protected Money calculateCallFee(Call call) {
        return amount.times(call.getDuration().getSeconds() / seconds.getSeconds()); 
    }
}
```

### 시간대별 방식 구현하기
* 시간대별 방식의 통화 요금을 계산하기 위해서는 통화의 시작 시간과 종
료 시간뿐만 아니라 시작 일자와 종료 일자도 함께 고려해야 한다.
* 핵심은 규칙에 따라 통화 시간을 분할하는 방법을 결정하는 것으로 DateTimelnterval 클래스를 추가한다.
```
public class DateTimelnterval { 
    private LocalDateTime from; 
    private LocalDateTime to;

    public static DateTimelnterval of(LocalDateTime from, LocalDateTime to) { 
        return new DateTimelnterval(from, to);
    }

    public static DateTimelnterval toMidnight(LocalDateTime from) { 
        return new DateTimelnterval(from, LocalDateTime.of(from.toLocalDate()z LocalTime.of(23, 59, 59)));
    }

    public static DateTimelnterval fromMidnight(LocalDateTime to) { 
        return new DateTimelnterval(LocalDateTime.of(to.toLocalDate(), LocalTime.of(0, 0)), to); 
    }

    public static DateTimelnterval during(LocalDate date) {     
        return new DateTimelnterval(
            LocalDateTime.of(date, LocalTime.of(0, 0)), 
            LocalDateTime.of(date, LocalTime.of(23, 59, 59)));
    }

    private DateTimelnterval(LocalDateTime from, LocalDateTime to) { 
        this.from = from;
        this.to = to;
    }

    public Duration duration() {
        return Duration.between(from, to);
    }
    
    public LocalDateTime getFrom() { 
        return from;
    }

    public LocalDateTime getTo() { 
        return to;
    } 
}
```
```
public class Call {
	private DateTimeInterval interval;

	public Call(LocalDateTime from, LocalDateTime to) {
		this.interval = DateTimeInterval.of(from, to);
	}

	public Duration getDuration() {
		return interval.duration();
	}

	public LocalDateTime getFrom() {
		return interval.getFrom();
	}

	public LocalDateTime getTo() {
		return interval.getTo();
	}

	public DateTimeInterval getInterval() {
		return interval;
	}

	public List<DateTimeInterval> splitByDay() {
		return interval.splitByDay();
	}
}
```

전체 통화 시간을 일자와 시간 기준으로 분할해서 계산해보는데 요금 계산 로직을 아래와 같이 두 단계로 나눠 구현한다.
* 통화 기간을 일자별로 분리한다.
* 일자별로 분리된 기간을 다시 시간대별 규칙에 따라 분리한 후 각 기간에 대해 요금을 계산한다.

```
public class TimeOfDayDiscountPolicy extends BasicRatePolicy {
    private List<LocalTime> starts = new ArrayList<LocalTime>();
    private List<LocalTime> ends = new ArrayList<LocalTime>();
    private List<Duration> durations = new ArrayList<Duration>();
    private List<Money>  amounts = new ArrayList<Money>();

    @Override
    protected Money calculateCallFee(Call call) {
        Money result = Money.ZERO;
        for(DateTimeInterval interval : call.splitByDay()) {
            for(int loop=0; loop < starts.size(); loop++) {
                result.plus(amounts.get(loop).times(Duration.between(from(interval, starts.get(loop)),
                        to(interval, ends.get(loop))).getSeconds() / durations.get(loop).getSeconds()));
            }
        }
        return result;
    }

    private LocalTime from(DateTimeInterval interval, LocalTime from) {
        return interval.getFrom().toLocalTime().isBefore(from) ? from : interval.getFrom().toLocalTime();
    }

    private LocalTime to(DateTimeInterval interval, LocalTime to) {
        return interval.getTo().toLocalTime().isAfter(to) ? to : interval.getTo().toLocalTime();
    }
}
```

### 요일별 방식 구현하기
* 요일의 목록, 단위 시간, 단위 요금이라는 세 가지 요소로 구성된다.
* DayOfWeekDiscountRule이라는 하나의 클래스로 구현하는 것이 더 나은 설계라고 판단했다.
* DayOfWeekDiscountRule 클래스는 규칙을 정의하기 위해 필요한 요일의 목록(dayOfWeeks), 단위 시간(duration), 단위 요금(amount)을 인스턴스 변수로 포함한다.
```
public class DayOfWeekDiscountRule {
    private List<DayOfWeek> dayOfWeeks = new ArrayList<>();
    private Duration duration = Duration.ZERO;
    private Money amount = Money.ZERO;

    public DayOfWeekDiscountRule(List<DayOfWeek> dayOfWeeks,
                                 Duration duration, Money  amount) {
        this.dayOfWeeks = dayOfWeeks;
        this.duration = duration;
        this.amount = amount;
    }

    public Money calculate(DateTimeInterval interval) {
        if (dayOfWeeks.contains(interval.getFrom().getDayOfWeek())) {
            return amount.times(interval.duration().getSeconds() / duration.getSeconds());
        }

        return Money.ZERO;
    }
}
```
* 요일별 방식 역시 통화 기간이 여러 날에 걸쳐있을 수 있다는 사실로 시간대별 방식과 동일하게 통화 기간을 날짜 경계로 분리하고 분리된 각 통화 기간을 요일별로 설정된 요금 정책에 따라 적절하게 계산해야 한다.
```
public class DayOfWeekDiscountPolicy extends BasicRatePolicy {
    private List<DayOfWeekDiscountRule> rules = new ArrayList<>();

    public DayOfWeekDiscountPolicy(List<DayOfWeekDiscountRule> rules) {
        this.rules = rules;
    }

    @Override
    protected Money calculateCallFee(Call call) {
        Money result = Money.ZERO;
        for(DateTimeInterval interval : call.getInterval().splitByDay()) {
            for(DayOfWeekDiscountRule rule: rules) { result.plus(rule.calculate(interval));
            }
        }
        return result;
    }
}
```