# 10. 상속과 코드 재사용
### 객체지향 프로그래밍의 장점
* 코드를 재사용하기가 용이하다.
* 전통적인 패러다임에서 코드를 재사용하기 위해 `새로운 코드`를 추가하지만 객체지향에서는 `새로운 클래스`를 추가하는 것이다.

### 재사용에서의 상속과 합성
* 상속은 클래스 안에 정의된 인스턴스 변수와 메서드를 자동으로 새로운 클래스에 추가하는 구현 기법이다.
* 합성은 새로운 클래스의 인스턴스 안에 기존 클래스의 인스턴스를 포함시키는 방법이다.

> 코드를 재사용하려는 강력한 동기 이면에는 중복된 코드를 제거하려는 욕망이 숨어 있다.

## 01. 상속과 중복 코드
### DRY 원칙
* DRY는 `반복하지 마라`라는 뜻의 Don't Repeat Yourself의 약자로 동일한 지식을 중복하지 말라는 것이다.
* DRY 원칙은 한 번, 단 한번(Once and Only Once) 원칙 또는 단일 지점 제어(Single-Point Control) 원칙이라고도 부르며 `이름이 무엇이건 핵심은 코드 안에 중복이 존재해서는 안된다`는 것이다.

### 중복과 변경
#### 전화 요금 계산 애플리케이션
한 달에 한 번씩 가입자별로 전화 요금을 계산하는 간단한 애플리케이션의 코드를 예시로 본다.
* 10초당 5원의 통화료를 부과하는 요금제에 가입돼 있는 가입자가 100초 동안 통화를 하면 50원(100 / 10 * 5)이 부과된다.
* 통화 시작 시간, 통화 종료 시간 변수를 포함한다.
```
public class Call {
    private LocalDateTime from;  -- 통화 시작 시간
    private LocalDateTime to; -- 통화 종료 시간

    public Call(LocalDateTime from, LocalDateTime to) { 
        this.from = from;
        this.to = to;
    }

    public Duration getDuration() { -- 통화 시작 시간과 통화 종료 시간 사이의 간격을 알려주는 메서드
        return Duration.between(from, to);
    }

    public LocalDateTime getFrom() { 
        return from;
    }
}
```
* 통화 요금을 계산할 객체가 필요한데 일반적으로 통화 목록은 전화기 안에 보관된다. 따라서 Call의 목록을 관리할 정보 전문가는 Phone이다.
```
public class Phone {
    private Money amount;
    private Duration seconds;
    private List<Call> calls = new ArrayList<>();

    public Phone(Money amount, Duration seconds) { 
        this.amount = amount;
        this.seconds = seconds;
    }

    public void call(Call call) { 
        calls.add(call);
    }

    public List<Call> getCalls() { 
        return calls;
    }
    
    public Money getAmount() { 
        return amount;
    }

    public Duration getSeconds() { 
        return seconds;
    }

    public Money calculateFee() {  // 요금을 계산하드 메서드
        Money result = Money.ZERO;
        
        for(Call call : calls) {
            result = result.plus(amount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
        }
        
        return result; 
    }
}
```

#### 심야 할인 요금제
전화 요금 계산 애플리케이션이 성공적으로 출시하고 심야 할인 요금제라는 새로운 요금 방식을 추가해야 한다고 요구사항이 접수됐다.
* Phone에 구현된 기존 요금제는 일반 요금제로 부른다.
* 구사항을 해결하는 가장 쉽고 빠른 방법은 Phone의 코드를 복사해서 NightlyDiscountPhone라는 새로운 클래스를 만든 후 수정하는 것이다.
```
public class NightlyDiscountPhone {
    private static final int LATE_NIGHT_HOUR = 22;

    private Money nightlyAmount;
    private Money regularAmount;
    private Duration seconds;
    private List<Call> calls = new ArrayList<>();

    public NightlyDiscountPhone(Money nightlyAmount, Money regularAmount, Duration seconds) {
        this.nightlyAmount = nightlyAmount;
        this.regularAmount = regularAmount;
        this.seconds = seconds;
    }

    public Money calculateFee() { // 요금을 계산하드 메서드
        Money result = Money.ZERO;

        for(Call call : calls) {
            if (call.getFrom().getHour() >= LATE_NIGHT_HOUR) {
                result = result.plus(nightlyAmount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
            } else {
                result = result.plus(regularAmount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
            }
        }

        return result;
    }
}
```

NightlyDiscountPhone은 밤 10시를 기준으로 기준 요금을 결정하는 것 외에는 Phone과 유사하다. Phone과 NightlyDiscountPhone 사이에는 밤 10시를 기준으로 regularAmount와 nightlyAmount를 통한 기준 요금을 제외하면 `중복된 코드가 존재`한다.

#### NightDiscountPhone의 문제점
* Phone 코드를 복사해서 NightlyDiscountPhone를 추가하면 요구사항을 아주 짧은 시간에 구현할 수 있지만 절약한 시간을 대가로 지불해야 하는 비용은 예상보다 크다.
* 예를 들어 통화 요금에 세금이 부과된다고 할 때, 세금을 추가하기 위해서는 두 클래스를 함께 수정해야 한다.
```
public class Phone {
    ...
    private double taxRate;

    public Phone(Money amount, Duration seconds, double taxRate) {
        ...
        this.taxRate = taxRate; 
    }

    public Money calculateFee() { // 요금을 계산하는 메서드
        Money result = Money.ZERO;

        for(Call call : calls) {
            result = result.plus(amount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
        }

        return result.plus(result.times(taxRate)); // 세금을 부과하는 메서드
    }
}
```
```
public class NightlyDiscountPhone {
    ...
    private double taxRate;

    public NightlyDiscountPhone(Money nightlyAmount, Money regularAmount, Duration seconds, double taxRate) {
        ...
        this.taxRate = taxRate;
    }

    public Money calculateFee() { // 요금을 계산하는 메서드
        Money result = Money.ZERO;

        for(Call call : calls) {
            if (call.getFrom().getHour() >= LATE_NIGHT_HOUR) {
                result = result.plus(nightlyAmount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
            } else {
                result = result.plus(regularAmount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
            }
        }

        return result.minus(result.times(taxRate)); // 세금을 부과하는 메서드
    }
}
```
* 중복 코드는 항상 함께 수정돼야 하기 때문에 수정할 때 하나라도 빠뜨린다면 버그로 이어진다. 또한 서로 다르게 수정해버릴 수도 있다.
* 새로운 중복 코드는 새로운 중복 코드를 부르고 중복 코드가 늘어날수록 변경에 취약해지고 버그가 발생할 가능성이 높아진다.
* 코드의 양과 버그가 많아질수록 그에 비례해 코드를 변경하는 속도는 점점 느려져서 생산성이 떨어진다.

> 기회가 생길 때마다 코드를 DRY하게 만들기 위해서 노력해야 한다.

#### 중복 코드 해결하기
* 두 클래스 사이의 중복 코드를 제거하는 방법 중 하나는 클래스를 하나로 합치는 것이다.
* 요금제를 구분하는 타입 코드를 추가해 타입 코드의 값에 따라 로직을 분기 시켜서 Phone과 NightlyDiscountPhone를 하나로 합친다.
```
public class Phone {
    private static final int LATE_NIGHT_HOUR = 22;
    enum PhoneType { REGULAR, NIGHTLY }

    private PhoneType type;

    private Money amount;
    private Money regularAmount;
    private Money nightlyAmount;
    private Duration seconds;
    private List<Call> calls = new ArrayList<>();

    public Phone(Money amount, Duration seconds) {
        this(PhoneType.REGULAR, amount, Money.ZERO, Money.ZERO, seconds);
    }

    public Phone(Money nightlyAmount, Money regularAmount,
                 Duration seconds) {
        this(PhoneType.NIGHTLY, Money.ZERO, nightlyAmount, regularAmount,
                seconds);
    }

    public Phone(PhoneType type, Money amount, Money nightlyAmount,
                 Money regularAmount, Duration seconds) {
        this.type = type;
        this.amount = amount;
        this.regularAmount = regularAmount;
        this.nightlyAmount = nightlyAmount;
        this.seconds = seconds;
    }

    public Money calculateFee() { // 요금을 계산하는 메서드
        Money result = Money.ZERO;

        for(Call call : calls) { // 조건문과 타입을 통해서 분기를 태운다.
            if (type == PhoneType.REGULAR) {
                result = result.plus(amount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
            } else {
                if (call.getFrom().getHour() >= LATE_NIGHT_HOUR) {
                    result = result.plus(nightlyAmount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
                } else {
                    result = result.plus(regularAmount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
                }
            }
        }

        return result;
    }
}
```
* 하지만 타입 코드를 사용하는 클래스는 낮은 응집도와 높은 결합도라는 문제에 시달리게 된다.
* 객체지향 프로그래밍 언어는 타입 코드를 사용하지 않는 중복 코드를 관리할 수 있는 효과적인 방법인 객체지향 프로그래밍을 대표하는 기법인 `상속`을 제공한다.

### 상속을 이용해서 중복 코드 제거하기
* 상속은 이미 존재하는 클래스와 유사한 클래스가 필요하다면 코드를 복사하지 말고 상속을 이용해 코드를 재사용하라는 것이다.
* NightlyDiscountPhone가 Phone 클래스를 상속받으면 코드를 중복시키지 않고 Phone 클래스의 코드 대부분을 재사용할 수 있다.
```
public class NightlyDiscountPhone extends Phone { // Phone을 상속
    private static final int LATE_NIGHT_HOUR = 22;

    private Money nightlyAmount;

    public NightlyDiscountPhone(Money nightlyAmount, Money regularAmount, Duration seconds) {
        super(regularAmount, seconds);
        this.nightlyAmount = nightlyAmount;
    }

    @Override // 부모클래스를 오버라이드해서 재정의 후 사용
    public Money calculateFee() { // 요금을 계산하는 메서드
        // 부모클래스의 calculateFee 호출
        Money result = super.calculateFee();

        Money nightlyFee = Money.ZERO;
        for(Call call : getCalls()) {
            if (call.getFrom().getHour() >= LATE_NIGHT_HOUR) {
                nightlyFee = nightlyFee.plus(
                        getAmount().minus(nightlyAmount).times(
                                call.getDuration().getSeconds() / getSeconds().getSeconds()));
            }
        }

        return result.minus(nightlyFee);
    }
}
```

하지만 이상한 점이 있는데 10시 이전의 요금 10시 이후의 요금의 합이 아니라, 10시 이전의 요금에서 10시 이후의 요금을 빼주고 있다. 그 이유는 Phone의 일반 요금제는 1개의 요금 규칙으로 구성돼 있는 반면, NightlyDiscountPhone의 심야 할인 요금제는 10시를 기준으로 분리된 2개의 요금제로 구성돼 있다고 분석한 것이다.

코드가 이해가 안되더라도 중요한 것은 개발자의 `가정을 이해하기 전에는 코드를 이해하기 어렵다는 점`이다. 상속을 염두에 두고 설계되지 않은 클래스를 상속을 이용해 재사용하는 것은 쉽지 않다. 개발자는 재사용을 위해 상속 계층 사이에 무수히 많은 가정을 세웠을지도 모른다. 그 가정은 코드를 이해하기 어렵게 만들고 직관에도 어긋날 수 있다.

`실제 프로젝트에서 마주치게 될 코드는 훨씬 더 엉망`일 확률이 높다. 단지 두 클래스 사이의 상속 관계였지만 `실제 프로젝트에서 마주치게 될 클래스의 상속 계층은 매우 깊고 이해하기 어려운 가정`을 마주하게 된다.

> 상속을 위해서는 자식 클래스의 작성자는 부모 클래스의 구현 방법에 대한 정확한 지식을 가져야 한다. 따라서 상속은 결합도를 높이고 강한 결합도로 인해서 코드를 수정하기 어렵게 만든다.

### 강하게 결합된 Phone과 NightlyDiscountPhone
* 부모 클래스와 자식 클래스 사이의 결합이 문제인 이유를 살펴본다.
* 세금을 부가하는 요구사항이 추가된다면 taxRate를 이용해 세금을 부가한다.
```
public class Phone {
    ...
    private double taxRate;

    public Money calculateFee() {
        ...
        return result.plus(result.times(taxRate));
    }

    public double getTaxRate() { // 세금을 반환하는 메서드
        return taxRate;
    }
}
```
```
public class NightlyDiscountPhone extends Phone {
    ...

    public NightlyDiscountPhone(Money nightlyAmount, Money regularAmount, Duration seconds, double taxRate) {
        super(regularAmount, seconds, taxRate);
        ...
    }

    @Override
    public Money calculateFee() {
        // 부모클래스의 calculateFee() 호출
        Money result = super.calculateFee();

        Money nightlyFee = Money.ZERO;
        for(Call call : getCalls()) {
            if (call.getFrom().getHour() >= LATE_NIGHT_HOUR) {
                nightlyFee = nightlyFee.plus(
                        getAmount().minus(nightlyAmount).times(
                                call.getDuration().getSeconds() / getSeconds().getSeconds()));
            }
        }

        return result.minus(nightlyFee.plus(nightlyFee.times(getTaxRate()))); // 세금을 부가하는 메서드
    }
}
```
NightlyDiscountPhone을 Phone의 자식 클래스로 만든 이유는 Phone의 코드를 재사용하고 중복 코드를 제거하기 위해서지만 세금을 부가하는 로직을 추가하기 위해 Phone을 수정할 때 유사한 코드를 NightlyDiscountPhone에 추가해야 한다.

자식 클래스가 부모 클래스의 구현에 강하게 결합될 경우 부모 클래스의 변 경에 의해 자식 클래스가 영향을 받는다는 사실을 잘 보여준다. 이처럼 상속 관계로 연결된 자식 클래스가 부모 클래스의 변경에 취약해지는 현상을 가리켜 `취약한 기반 클래스 문제`라고 부른다.

> 취약한 기반 클래스 문제는 코드 재사용을 목적으로 상속을 사용할 때 발생하는 가장 대표적인 문제다.

## 02. 취약한 기반 클래스 문제
* 부모 클래스의 변경에 의해 자식 클래스가 영향을 받는 현상을 취약한 기반 클래스 문제라고 부르고 상속을 사용한다면 피할 수 없는 객체지향 프로그래밍의 근본적인 취약성이다.
* 상속은 `자식 클래스를 점진적으로 추가해서 기능을 확장하는 데는 용이하지만 높은 결합도로 인해 부모 클래스를 점진적으로 개선하는 것은 어렵게` 만든다.
* 취약한 기반 클래스 문제는 캡슐화를 약화시키고 결합도를 높인다.
  * 자식 클래스가 부모 클래스의 구현 세부사항에 의존하도록 만든다.
  * 부모 클래스의 퍼블릭 인터페이스가 아닌 구현을 변경하더라도 자식 클래스가 영향을 받기 쉬워진다.

> 객체지향의 기반은 캡슐화를 통한 변경의 통제인데 상속은 코드의 재사용을 위해 캡슐화의 장점을 희석 시키고 구현에 대한 결합도를 높임으로써 객체지향이 가진 강력함을 반감시킨다.

### 불필요한 인터페이스 상속 문제
