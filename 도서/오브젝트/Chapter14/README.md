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

### 구간별 방식 구현하기
* 지금까지 구현한 고정요금 방식, 시간대별 방식, 요일별 방식의 구현 클래스를 보면 괜찮은 구현으로 보이지만 모아놓으면 문제점이 보인다.
* 큰 문제점은 이 클래스들이 유사한 문제를 해결하 고 있음에도 불구하고 설계에 일관성이 없다는 것으로 구현 방식에 있어서는 완전히 제각각이라는 것이다.

#### 비일관성
* 비일관성은 두 가지 상황에서 두 가지 상황에서 발목을 잡는다.
  1. 새로운 구현을 추가해야 하는 상황
  2. 기존의 구현을 이해해야 하는 상황
* 비일관성이 문제가 되는 이유는 개발자로서 우리가 수행하는 대부분의 활동이 코드를 추가하고 이해하는 일과 깊숙히 연관돼 있기 때문이다.
* 일관성이 없는 설계는 개발자가 여러가지 해결 방법 중에서 가장 적절한 방법을 찾아야하는 부담을 안게되고, 결론은 유사한 기능을 서로 다른 방식으로 구현해서는 안 된다.
* 유지보수 가능한 시스템을 구축하는 첫걸음은 협력을 일관성 있게 만드는 것으로 유사한 기능은 유사한 방식으로 구현해야 한다.

요일별 방식의 경우처럼 규칙을 정의하는 새로운 클래스를 추가한다.
* DurationDiscountRule 클래스 생성
* 코드를 재사용하기 위해 FixedFeePolicy 클래스를 상속
```
public class DurationDiscountRule extends FixedFeePolicy {
    private Duration from;
    private Duration to;

    public DurationDiscountRule(Duration from, Duration to, Money amount, Duration seconds) {
        super(amount, seconds);
        this.from = from;
        this.to = to;
    }

    public Money calculate(Call call) {
        if (call.getDuration().compareTo(to) > 0) {
            return Money.ZERO;
        }

        if (call.getDuration().compareTo(from) < 0) {
            return Money.ZERO;
        }

        // 부모 클래스의 calculateFee(phone)은 Phone 클래스를 파라미터로 받는다.
        // calculateFee(phone)을 재사용하기 위해 데이터를 전달할 용도로 임시 Phone을 만든다.
        Phone phone = new Phone(null);
        phone.call(new Call(call.getFrom().plus(from),
                            call.getDuration().compareTo(to) > 0 ? call.getFrom().plus(to) : call.getTo()));

        return super.calculateFee(phone);
    }
}
```
```
public class DurationDiscountPolicy extends BasicRatePolicy {
    private List<DurationDiscountRule> rules = new ArrayList<>();

    public DurationDiscountPolicy(List<DurationDiscountRule> rules) {
        this.rules = rules;
    }

    @Override
    protected Money calculateCallFee(Call call) {
        Money result = Money.ZERO;
        for(DurationDiscountRule rule: rules) {
            result.plus(rule.calculate(call));
        }
        return result;
    }
}
```
* 기존의 설계가 어떤 가이드도 제공하지 않기 때문에 새로운 기본 정책을 구현해야 하는 상황에서 또 다른 개발자는 또 다른 방식으로 기본 정책을 구현할 가능성이 높다. 
* 시간이 흐를수록 설계의 일관성은 더욱더 어긋나게 될 것이다.

#### 코드 재사용을 위한 상속은 해롭다.
* DurationDiscountRule은 FixedFeePolicy 클래스의 서브타입이 아닌데 상속을 사용하고 있다.
* DurationDiscountRule은 amount, seconds와 calculateFee 메서드를 재사용하기 위해서 즉 코드 재사용을 위해서 상속을 사용했다.
* 하지만 두 클래스 사이의 강한 결합도는 설계 개선과 새로운 기능의 추가를 방해한다.
* 이 코드는 이해하기도 어렵고, calculate 메서드 안에서 Phone과 Call의 인스턴스를 생성하는 것이 꽤나 부자연스러워 보인다.
* 상속을 위해 설계된 클래스가 아닌 재사용을 위해 억지로 코드를 비튼 결과다.
* 배경지식을 모르는 상태에서 이 코드와 처음 대면했다면 쉽게 이해할 수 없을 것이다.

## 02. 설계에 일관성 부여하기
일관성 있는 설계를 위한 조언
1. 다양한 설계 경험
   * 풍부한 설계 경험을 가진 사람은 어떤 변경이 중요한지, 그리고 그 변경을 어떻게 다뤄야 하는지에 대한 통찰력을 갖게 된다.
   * 설계 경험이 풍부하면 풍부할수록 어떤 위치에서 일관성을 보장해야 하고 일관성 을 제공하기 위해 어떤 방법을 사용해야 하는지를 직관적으로 결정할 수 있다.
   * 하지만 이러한 설계 경험은 단기간에 쌓아 올리기 어렵다.
2. 디자인 패턴
   * 변경이라는 문맥 안에서 디자인 패턴을 적용해보는 것이다.
   * 디자인 패턴을 학습하면 빠른 시간 안에 전문가의 경험을 흡수할 수 있다.
   * 디자인 패턴이 반복적으로 적용할 수 있는 설계 구조를 제공한다고 하더라도 모든 경우에 적합한 패턴을 찾을 수 있는 것은 아니다.

협력을 일관성 있게 만들기 위한 기본 지침
* 변하는 개념을 변하지 않는 개념으로부터 분리하라.
* 변하는 개념을 캡슐화하라.

> 설명했던 모든 원칙과 개념들 역시 대부분 변경의 캡슐화라는 목표를 향한다.
> 바뀌는 부분을 따로 뽑아서 캡슐화한다. 그렇게 하면 나중에 바뀌지 않는 부분에는 영향을 미치지 않은 채로 그 부분만 고치거나 확장할 수 있다.

### 조건 로직 대 객체 탐색
#### 변경을 다루는 전통적인 방법
* 절차적인 프로그램
  * 절차적인 프로그램에서 변경을 처리하는 전통적인 방법은 조건문의 분기를 추가하거나 개별 분기 로직을 수정하는 것이다.
* 객체지향 프로그램
  * 객체지향 프로그램에서 변경을 다루는 전통적인 방법은 조건 로직을 객체 사이의 이동으로 바꾸는 것이다.

<img src="./image/그림%2014.10.png">

실제로 협력에 참여하는 주체는 구체적인 객체다.
* 객체들은 협력 안에서 DiscountPolicy와 DiscountCondition을 대체 할 수 있어야 한다.

<img src="./image/그림%2014.11.png">

#### 훌륭한 추상화
* 핵심은 훌륭한 추상화를 찾아 추상화에 의존하도록 만드는 것이다. 
* 추상화에 대한 의존은 결합도를 낮 추고 결과적으로 대체 가능한 역할로 구성된 협력을 설계할 수 있게 해준다. 
* 따라서 선택하는 추상화의 품질이 캡슐화의 품질을 결정한다.

#### 캡슐화하는 방법
* 타입을 캡슐화하고 낮은 의존성을 유지하기 위해서는 지금까지 살펴본 다양한 기법들이 필요하다.
  * 살펴본 인터페이스 설계 원칙
  * 의존성 관리 기법
  * 코드 재사용을 위해 상속
  * 합성을 사용
  * 리스코프 치환 원칙을 준수하는 타입 계층을 구현

> 변경에 초점을 맞추고 캡슐화의 관점에서 설계를 바라보면 일관성 있는 협력 패턴을 얻을 수 있다.

### 캡슐화 다시 살펴보기
* 캡슐화를 하면 `데이터 은닉(data hiding)`을 떠올리지만 캡슐화는 데이터 은닉 이상이다.
* GOF(GoF의 디자인 패턴)의 조언에 따르면 캡슐화는 데이터를 감추는 것이 아닌 소프트웨어 안에서 변할 수 있는 모든 `개념`을 감추는 것이다.
* 캡슐화의 가장 대표적인 예는 객체의 퍼블릭 인터페이스를 분리하는 것으로 객체와 협력하는 클라이언트 개발자는 인터페이스가 변하는 것을 원하지 않기 때문에 자주 변경이 일어나는 내부 구현을 퍼블릭 인터페이스 뒤로 숨겨야 한다.

<img src="./image/그림%2014.12.png">

기존의 설계에는 다양한 종류의 캡슐화가 공존한다.
* 데이터 캡슐화
  * Movie 클래스의 인스턴스 변수 title 의 가시성은 private0|7| 때문에 외부에서 직접 접근할 수 없다.
  * 속성에 접근할 수 있는 유일한 방법은 메서드를 이용하는 것뿐이다.
  * 클래스는 내부에 관리하는 데이터를 캡슐화 한다.
* 메서드 캡슐화
  * DiscountPolicy 클래스에서 정의돼 있는 getDiscountAmount 메서드의 가시성은 protected다.
  * 클래스 의 외부에서는 이 메서드에 직접 접근할 수 없고 클래스 내부와 서브클래스에서만 접근이 가능하다.
  * 클래스 외부에 영향을 미치지 않고 메서드를 수정할 수 있으며 클래스의 내부 행동을 캡슐화하고 있는 것이다.
* 객체 캡슐화
  * Movie 클래스는 DiscountPolicy 타입의 인스턴스 변수 discountPolicy를 포함한다.
  * 인스턴스 변수는 private 가시성을 가지기 때문에 Movie와 DiscountPolicy 사이의 관계를 변경하더라도 외부에는 영향을 미치지 않는다.
  * 객체와 객체 사이의 관계를 캡슐화하며 객체 캡슐화가 합성을 의미한다.
* 서브타입 캡슐화
  * Movie는 DiscountPolicy에 대해서는 알고 있지만 AmountDiscountPolicy와 PercentDiscountPolicy에 대해서는 알지 못하지만 실행 시점에는 이 클래스들의 인스턴스와 협력할 수 있다.
  * 파생 클래스인 DiscountPolicy와의 추상적인 관계가 AmountDiscountPolicy와 PercentDiscountPolicy의 존재를 감추고 있기 때문이다.
  * 서브타입의 종류를 캡슐화하고 있는 것으로 캡슐화가 다형성의 기반이 된다.

<img src="./image/그림%2014.13.png">

> 캡슐화는 단지 데이터 은닉을 의미하는 것이 아니며 코드 수정으로 인한 파급효과를 제어할 수 있는 모든 기법이 캡슐화의 일종이다.

서브타입 캡슐화와 객체 캡슐화를 적용하는 방법
* 변하는 부분을 분리해서 타입 계층을 만든다.
  * 변하지 않는 부분으로부터 변하는 부분을 분리한다.
  * 변하는 부분들의 공통적인 행동을 추상 클래스나 인터페이스로 추상화한 후 변하는 부분들이 이 추상 클래스나 인터페이스를 상속받게 만든다.
  * 영화 예매 시스템의 DiscountPolicy를 추상 클래스, Discountcondition를 인터페이스로 구현한 부분
* 변하지 않는 부분의 일부로 타입 계층을 합성한다.
  * 앞에서 구현한 타입 계층을 변하지 않는 부분에 합성한다.
  * 변하지 않는 부분에서는 변경되는 구체적인 사항에 결합돼서는 안되며 의존성 주입과 같이 결합도를 느슨하게 유지할 수 있는 방법을 이용해 오직 추상화에만 의존하게 만든다.
  * Movie가 Discountpolicy를 합성 관계로 연결하고 생성자를 통해 의존성을 해결한 이유가 바로 이 때문이다.

> 조건 로직을 객체 이동으로 대체함으로써 변경을 캡슐화할 수 있는 다양한 방법 중에서 가장 대표적인 방법일 뿐이다.

## 03. 일관성 있는 기본 정책 구현하기
### 변경 분리하기
* 변경을 캡슐화하는 가장 좋은 방법은 변하지 않는 부분으로 변하는 부분을 분리하는 것이다.

#### 고정요금, 시간대별, 요일별. 구간별 방식의 규칙 패턴
<table>
    <tr>
        <th>방식</th>
        <th>예</th>
        <th>규칙</th>
    </tr>
    <tr>
        <td>고정요금 방식</td>
        <td>10초당 18원</td>
        <td>[단위시간]당 [요금]원</td>
    </tr>
    <tr>
        <td>시간대별 방식</td>
        <td>00시~19시까지 10초당 18원<br>19시~24시까지 10초당 15원</td>
        <td>[시작시간]〜[종료시간]까지<br>[단위시간]당 [요금]원</td>
    </tr>
    <tr>
        <td>요일별 방식</td>
        <td>평일에는 10초당 38원<br>공휴일에는 10초당 19원</td>
        <td>[요일]별<br>[단위시간]당 [요금]원</td>
    </tr>
    <tr>
        <td>구간별 방식</td>
        <td>초기 분 동안 10초당 50원<br>초기 1분 이후 10초당 20원</td>
        <td>[통화구간] 동안<br>[단위시간]당 [요금]원</td>
    </tr>
</table>

시간대별, 요일별, 구간별 방식의 공통점은 각 기본 정책을 구성하는 방식이 유사하다.
* 기본 정책은 한 개 이상의 규칙으로 구성
* 하나의 규칙은 적용조건과 단위요금의 조합