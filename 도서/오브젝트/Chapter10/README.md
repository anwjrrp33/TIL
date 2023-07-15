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
자바의 초기 버전에서 상속을 잘못 사용한 대표적인 사례는 java.util.Properties와 java.util.Stack이다. 공통점은 부모 클래스에서 상속받은 메서드를 사용할 경우 자식 클래스의 규칙이 위반될 수 있었다.

#### Stack 클래스에서의 상속 문제
* Stack은 LIFO 자료 구조인 스택을 구현한 클래스,Vector는 임의의 위치에서 요소를 추출하고 삽입할 수 있는 리스트 자료 구조의 구현체다.
* 초기에는 요소의 추가/삭제를 제공하는 Vector를 재사용하기 위해 Stack을 Vector의 자식 클래스로 구현했다.
* Vector는 임의의 위치에서 요소를 조회/추가/삭제하는 오퍼레이션을 제공하는 반면, Stack은 맨 마지막 위치에서만 요소를 추가/제가하는 오퍼레이션을 제공한다.
* 그러나 Stack이 Vector를 상속받기 때문에 Stack의 퍼블릭 인터페이스에 Vector의 퍼블릭 인터페이스가 합쳐지고, Stack에서 Vector의 인터페이스를 이용하면 임의의 위치에서 요소를 추가/삭제할 수 있다. Stack의 규칙을 쉽게 위반할 수 있는 것이다.

<img src="./image/그림%2010.1.png">

#### Properties 클래스에서의 상속 문제
* Properties 클래스는 키와 쌍을 보관한다는 점에서 Map과 유사하지만, 다양한 타입을 저장할 수 있는 Map과 달리 키와 값의 타입으로 오직 String만 가질 수 있다.
* 그러나 Properties는 Map의 조상인 Hashtable을 상속받는다. 자바에 제네릭이 도입되기 이전에 만들어졌기 때문에 컴파일러가 키와 값의 타입이 String인지 여부를 체크할 수 있는 방법이 없었고 Hashtable의 인터페이스의 put을 이용하면 String 타입이 아니더라도 Properties에 저장할 수 있다.

<img src="./image/그림%2010.2.png">

퍼블릭 인터페이스에 대한 고려 없이 단순히 코드 재사용을 위해 상속을 이 용하는 것이 얼마나 위험한지를 잘 보여준다. 객체지향의 핵심은 객체들의 협력으로 단순히 코드를 재사용하기 위해 불필요한 오퍼레이션이 인터페이스에 스며들도록 방치해서는 안 된다.

> 상속받은 부모 클래스의 메서드는 자식 클래스의 내부 구조에 대한 규칙을 깨트릴 수 있다.

### 메서드 오버라이딩의 오작용 문제
HashSet의 구현에 강하게 결합된 InstrumentedHashSet 클래스가 존재하는데 InstrumentedHashSet은 HashSet의 내부에 저장된 요소의 수를 셀 수 있는 기능을 추가한 클래스로, HashSet의 자식 클래스다.

```
public class InstrumentedHashSet<E> extends HashSet<E> {
    private int addCount = 0;

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }
}
```
```
-- 코드를 실행 후 결과를 살펴본다.
InstrumentedHashSet<String> languages = new InstrumentedHashSet<>();
languages.addAll(Arrays.asList("Java", "Ruby", "Scala"));
```
실행 후 addCount의 값이 3이 될 것 같지만 실제로는 6이 되는데 HashSet의 addAll 안에서 add 메서드를 호출하기 때문이다.
1. InstrumentedHashSet의 addAll이 호출돼서 addCount에 3이 더해진다.
2. super.addAll이 호출되고 제어가 부모 클래스인 HashSet으로 이동한다.
3. HashSet은 각각의 요소를 추가하기 위해 내부적으로 add를 호출한다.
4. 결과적으로 InstrumentedHashSet의 add가 세번 호출되어 addCount에 3이 더해진다.

#### InstrumentedHashSet의 문제점 임시적인 해결
* InstrumentedHashSet의 addAll을 제거하면 해결되겠지만, 나중에 HashSet의 addAll이 add를 전송하지 않도록 수정된다면 addAll을 이용해 추가되는 요소들에 대한 카운트가 누락될 것이다.
* 미래의 수정까지 감안해서 더 좋은 해결책은 InstrumentedHashSet의 addAll를 오버라이딩하고 추가되는 각 요소에 대해 한 번씩 add를 호출하는 것이다.
```
public class InstrumentedHashSet<E> extends HashSet<E> {
    private int addCount = 0;

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c)
            if (add(e))
                modified = true;
        return modified;
    }
}
```
* 그러나 오버라이딩된 addAll의 구현은 HashSet의 것과 동일하다. 즉, 코드가 중복된 것이다.
* 자식 클래스가 부모 클래스의 메서드를 오버라이딩할 경우, 부모 클래스가 자신의 메서드를 사용하는 방법에 자식 클래스가 결합될 수 있다.
* 조슈아 블로치는 클래스가 상속되기를 원한다면 상속을 위해 클래스를 설계하고 문서화해야 하며, 그렇지 않은 경우에는 상속을 금지시켜야 한다고 주장한다. 문서화 또한 내부 구현을 공개하는 행위이기 떄문에 캡슐화를 위반한다.

> 설계는 트레이드오프 활동이며 상속은 코드 재사용을 위해 캡슐화를 희생한다. 완벽한 캡슐화를 원한다면 재사용을 포기하거나 상속 이외의 다른 방법을 사용해야 한다.
  
### 부모 클래스와 자식 클래스의 동시 수정 문제
음악 목록을 추가할 수 있는 플레이리스트를 구현해본다.
* 음악 정보를 저장할 Song 클래스
* 음악 목록을 저장할 Playlist 클래스

```
public class Song {
    private String singer; -- 가수의 이름
    private String title; -- 노래 제목

    public Song(String singer, String title) {
        this.singer = singer;
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public String getTitle() {
        return title;
    }
}
```
```
public class Playlist {
    private List<Song> tracks = new ArrayList<>();

    public void append(Song song) { // 노래를 추가할 수 있는 메서드
        getTracks().add(song);
    }

    public List<Song> getTracks() {
        return tracks;
    }
}
```

1. 플레이리스트에서 노래를 삭제할 수 있는 기능이 추가된 PersonalPlaylist가 필요하다.
```
public class PersonalPlaylist extends Playlist {
    public void remove(Song song) {
        getTracks().remove(song);
    }
}
```

2. 요구사항이 변경돼서 Playlist에서 노래의 목록뿐만 아니라 가수별 노래의 제목도 함께 관리해야 한다.
```
public class Playlist {
    private List<Song> tracks = new ArrayList<>();
    private Map<String, String> singers = new HashMap<>();

    public void append(Song song) { // 노래를 추가하면서 노래의 정보까지 관리하는 메서드
        tracks.add(song);
        singers.put(song.getSinger(), song.getTitle());
    }

    public List<Song> getTracks() {
        return tracks;
    }

    public Map<String, String> getSingers() {
        return singers;
    }
}
```
```
public class PersonalPlaylist extends Playlist {
    public void remove(Song song) {
        getTracks().remove(song);
        getSingers().remove(song.getSinger());
    }
}
```
위 요구사항이 제대로 반영돼려면 Playlist와 PersonalPlaylist 모두 수정해야 한다. 부모 클래스의 메서드를 오버라이딩하거나 불필요한 인터페이스를 상속받지 않았음에도 함께 수정해야 할 수도 있다. 결국 상속을 사용하면 자식 클래스가 부모 클래스의 구현에 강하게 결합된다.

> 클래스를 상속하면 결합도로 인해 자식 클래스와 부모 클래스의 구현을 영원히 변경하지 않거나, 두 클래스를 동시에 변경하거나 둘 중 하나를 선택할 수밖에 없다.

## 03. Phone 다시 살펴보기
상속으로 인한 피해를 최소화할 수 있는 방법을 찾아야 한다.

### 추상화에 의존하자
부모와 자식 모두 추상화에 의존하도록 수정해야 한다. 저자가 코드 중복을 제거하기 위해 상속을 도입할 때 따르는 두 가지 원칙이 있다.
* 두 메서드가 유사하게 보인다면 차이점을 메서드로 추출하라. 추출을 통해 두 메서드를 동일한 형태로 보이도록 만들 수 있다.
* 부모 클래스의 코드를 하위로 내리지 말고 자식 클래스의 코드를 상위로 올려라. 부모 클래스의 구체적인 메서드를 자식 클래스로 내리는 것보다 자식 클래스의 추상적인 메서드를 부모 클래스로 올리는 것이 재사용성과 응집도 측면에서 더 뛰어나다.

### 차이를 메서드로 추출하라
중복 코드 안에서 차이점을 별도의 메서드로 추출하는 것이다.
* 변하는 것으로부터 변하지 않는 것을 분리하라 
* 변하는 부분을 찾고 이를 캡슐화하라
```
public class Phone {
    private Money amount;
    private Duration seconds;
    private List<Call> calls = new ArrayList<>();

    public Phone(Money amount, Duration seconds) {
        this.amount = amount;
        this.seconds = seconds;
    }

    public Money calculateFee() {
        Money result = Money.ZERO;

        for(Call call : calls) {
            result = result.plus(amount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
        }

        return result;
    }
}
```
```
-- Phone과 유사하지만 구현 일부와 인스턴스 변수의 목록이 조금 다르다.
public class NightlyDiscountPhone {
    private static final int LATE_NIGHT_HOUR = 22;
    
    private Money nightlyAm이jnt;
    private Money regularAmount;
    private Duration seconds;
    private List<Call> calls = new ArrayList<>();

    public NightlyDiscountPhone(Money nightlyAmount, Money regularAmount, Duration seconds) { 
        this.nightlyAmount = nightlyAmount;
        this.regularAmount = regularAmount;
        this.seconds = seconds;
    }

    public Money calculateFee() { 
        Money result = Money.ZERO;
        
        for(Call call : calls) {
            if (call.getFromO.getHour() 〉= LATE_NIGHT_HOUR) {
                result = result.plus(nightlyAmount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
            } else {
                result = result.plus(regularAmount.times(call.getDuration().getSeconds() / seconds.getSeconds()));
            }
        }
        
        return result; 
    }
}
```

두 클래스의 메서드에서 다른 부분을 별도의 메서드로 추출한다.
```
public class Phone {
    ...
    public Money calculateFee() { 
        Money result = Money.ZERO;
        
        for(Call call : calls) {
            result = result.plus(calculateCallFee(call));
        }
        
        return result; 
    }

    private Money calculateCallFee(Call call) { // 추출된 메서드
        return amount.times(call.getDuration().getSecondsO / seconds.getSeconds());
    } 
}
```
```
public class NightlyDiscountPhone {
    ...
    public Money calculateFee() { 
        Money result = Money.ZERO;
        
        for(Call call : calls) {
            result = result.plus(calculateCallFee(call));
        }
        
        return result; 
    }
    
    private Money calculateCallFee(Call call) { // 추출된 메서드
        if (call .getFrom().getHour() 〉= LATE_NIGHT_HOUR) {
            return nightlyAmount.times(call.getDuration().getSeconds() / seconds.getSeconds()); 
        } else {
            return regularAmount.times(call.getDuration().getSeconds() / seconds.getSeconds()); 
        }
    } 
}
```

### 중복 코드를 부모 클래스로 올려라
부모 클래스를 추가해서 목표는 모든 클래스들이 추상화에 의존하도록 만드는 것이기 때문에 이 클래스는 추상 클래스로 구현하는 것이 적합하다.
```
public abstract class AbstractPhone {
public class Phone extends AbstractPhone { ... }
public class NightlyDiscountPhone extends AbstractPhone { ... }
```
Phone과 NightlyDiscountPhone의 공통 부분을 부모 클래스로 이동시킨다.
* 공통 코드를 옮길 때 인스턴스 변수보다 메서드를 먼저 이동시키는 게 편하다.
* 메서드를 옮기고 나면 컴파일 에러를 통해 자동으로 알 수 있기 때문이다.
```
public abstract class AbstractPhone {
    private List<Call> calls = new ArrayList<>();

    public Money calculateFee() {
        Money result = Money.ZERO;

        for(Call call : calls) {
            result = result.plus(calculateCallFee(call));
        }

        return result;
    }

    abstract protected Money calculateCallFee(Call call);
}
```
```
public class Phone extends AbstractPhone {
    private Money amount;
    private Duration seconds;

    public Phone(Money amount, Duration seconds) {
        this.amount = amount;
        this.seconds = seconds;
    }

    @Override
    protected Money calculateCallFee(Call call) {
        return amount.times(call.getDuration().getSeconds() / seconds.getSeconds());
    }
}
```
```
public class NightlyDiscountPhone extends AbstractPhone {
    private static final int LATE_NIGHT_HOUR = 22;

    private Money nightlyAmount;
    private Money regularAmount;
    private Duration seconds;

    public NightlyDiscountPhone(Money nightlyAmount, Money regularAmount, Duration seconds) {
        this.nightlyAmount = nightlyAmount;
        this.regularAmount = regularAmount;
        this.seconds = seconds;
    }

    @Override
    protected Money calculateCallFee(Call call) {
        if (call.getFrom().getHour() >= LATE_NIGHT_HOUR) {
            return nightlyAmount.times(call.getDuration().getSeconds() / seconds.getSeconds());
        } else {
            return regularAmount.times(call.getDuration().getSeconds() / seconds.getSeconds());
        }
    }
}
```

<img src="./image/그림%2010.3.png">

#### 상속 후 결과
* 자식 클래스들 사이의 공통점을 부모 클래스로 옮김으로써 실제 코드를 기반으로 상속 계층을 구성할 수 있다.
* 위로 올리기에서 실수하더라도 추상화할 코드는 눈에 띄고 결국 상위 클래스로 올려지면서 코드의 품질이 높아진다.
* 아래로 내리는 방식으로 현재 클래스를 구체에서 추상으로 변경하면 작은 실수 한 번으로도 구체적인 행동을 상위 클래스에 남겨 놓게 된다.

### 추상화가 핵심이다 
공통 코드를 이동시킨 후 각 클래스는 서로 다른 변경의 이유를 가지게 된다.
* AbstractPhone
  * 전체 통화 목록을 계산하는 방법이 변경될 때
* Phone
  * 일반 요금제의 통화 한 건을 계산하는 방식이 변경될 때
* NightlyDiscountPhone
  * 심야 할인 요금제의 통화 한 건을 계산하는 방식이 변경될 때

#### 수정 후의 결과
* AbstractPhone, Phone, NightlyDiscountPhone 클래스들은 변경 후에 구체적인 구현에 의존하지 않고, 오직 추상화에만 의존하기 때문에 응집도가 높고 낮은 결합도를 유지한다.
* 현재의 설계는 설계는 확장에는 열려 있고 수정에는 닫혀 있기 때문에 개방-폐쇄 원칙 역시 준수한다.
* 모든 장점은 클래스들이 추상화에 의존하기 때문에 얻어지는 장점이다.

> 상속 계층이 코드를 진화시키는 데 걸림돌이 된다면 추상화를 찾아내고 상속 계층 안의 클래스들이 그 추상화에 의존하도록 코드를 리팩터링하고 차이점을 메서드로 추출하고 공통적인 부분은 부모 클래스로 이동해야 한다.

### 의도를 드러내는 이름 선택하기
한 가지 아쉬운 점이 존재하는 데 클래스의 이름과 관련된 부분이다.
* NightlyDiscountPhone는 심야 할인 요금제와 관련된 내용을 구현한다는 사실을 명확하게 전달하는 반면, Phone은 그렇지 않다.
* NightlyDiscountPhone과 Phone은 사용자가 가입한 전화기의 한 종류지만 AbstractPhone은 전화기를 포괄한다는 의미를 명확하게 전달하기 못한다.
```
public abstract class Phone { ... }
public class RegularPhone extends Phone { ... }
public class NightlyDiscountPhone extends Phone { ... }
```
<img src="./image/그림%2010.4.png">

> 앞서 나온 예시들은 상속 계층을 구성하기 위해서는 상속 계층 안에 속한 클래스들이 구현이 아닌 추상화에 의존해야 한다는 사실을 보여준다.

### 세금 추가하기
더 쉽게 변경되는지는 적용해보기 전까진 알 수 없다.
* 통화 요금에 세금을 부과하는 요구사항을 적용한다.
* 공통 코드를 담고 있는 추상 클래스 Phone을 수정하면 자식 클래스 간에 수정 사항을 공유할 수 있을 것이다.
```
public abstract class Phone {
    private double taxRate;
    private List<Call> calls = new ArrayList<>();

    public Phone(double taxRate) {
        this.taxRate = taxRate;
    }

    public Money calculateFee() {
        Money result = Money.ZERO;

        for(Call call : calls) {
            result = result.plus(calculateCallFee(call));
        }

        return result.plus(result.times(taxRate));
    }

    protected abstract Money calculateCallFee(Call call);
}
```

#### 이것으로 끝난 것일까?
하지만 이것으로 끝난 것이 아니다. RegularPhone과 NightlyDiscountPhone의 생성자에서도 taxRate를 초기화시켜주어야 한다.

```
public class RegularPhone extends Phone {
    ...
    public RegularPhone(Money amount, Duration seconds, double taxRate) {
        super(taxRate); // 추가된 부분
        ...
    }
    ...
}
```
```
public class NightlyDiscountPhone extends Phone {
    ...
    public NightlyDiscountPhone(Money nightlyAmount, Money regularAmount, Duration seconds, double taxRate) {
        super(taxRate); // 추가된 부분
        ...
    }
    ...
}
```
클래스 사이의 상속은 부모 클래스의 구현 뿐만 아니라 인스턴스 변수에 대해서도 결합되게 만든다. 인스턴스 변수의 변화 없이 행동만 변경된다면 상속 계층에 속한 각 클래스들을 독립적으로 진화시킬 수 있다. 인스턴스 변수의 변경은 종종 상속 계층 전반에 걸친 변경을 유발한다.

하지만 인스턴스 초기화 로직을 변경하는 것이 동일한 세금 계산 코드를 중복시키는 것보다는 현명한 선택이다. 객체 생성 로직에 대한 변경을 막기보다는 핵심 로직의 중복을 막아야 한다. 핵심 로직은 한 곳에 모아 놓고 캡슐화해야 한다. 공통적인 핵심 로직은 최대한 추상화해야 한다.

> 상속으로 인한 클래스 사이의 결합을 피할 수 있는 방법은 없고, 메서드 구현에 대한 결합은 추상 메서드를 추가해서 어느 정도 완화할 수 있지만 인스턴스 변수에 대한 잠재적인 결합을 제거할 수 있는 방법은 없다.

## 04. 차이에 의한 프로그래밍
