# 11. 합성과 유연한 설계
### 코드 재사용 기법
* 상속
  * 부모 클래스와 자식 클래스를 연결해서 부모 클래스의 코드를 재사용한다.
  * is-a 관계
* 합성
  * 전체를 표현하는 객체가 부분을 표현하는 객체를 포함해서 부분 객체의 코드를 재사용한다.
  * has-a 관계

### 합성과 상속
* 합성은 상속과 달리 구현에 의존하지 않고 합성은 구현이 아닌 퍼블릭 인터페이스에 의존한다.
* 상속 관계는 코드 작성 시점에 결정한 후 변경이 불가능한 정적인 관계이지만 합성 관계는 실행 시점에 동적으로 변경할 수 있는 동적인 관계다.
* 상속은 부모 클래스 안에 구현된 코드 자체를 재사용하지만 합성은 포함되는 객체의 퍼블릭 인터페이스를 재사용한다.

## 01. 상속을 합성으로 변경하기
상속을 남용했을 때 직면하는 세 가지 문제점
* 불필요한 인터페이스 상속 문제
* 메서드 오버라이딩의 오작용 문제
* 부모 클래스와 자식 클래스의 동시 수정 문제

합성을 사용하면 문제점을 문제점을 해결할 수 있고, 상속을 합성으로 바꾸려면 자식 클래스에 선언된 상속 관계를 제거하고 부모 클래스의 인스턴스를 자식 클래스의 인스턴스 변수로 선언하면 된다.

### 불필요한 인터페이스 상속 문제: java.util.Properties와 java.util.Stack
Hashtable과 Properties 사이의 상속 관계를 합성 관계로 바꿔본다.
```
-- Properties에서 상속 관계를 제거하고 Hashtable을 Properties의 인스턴스 변수로 포함시키면 된다.
public class Properties {
    private Hashtable<String, String> properties = new Hashtable <>();

    public String setProperty(String key, String value) {
        return properties.put(key, value);
    }

    public String getProperty(String key) {
        return properties.get(key);
    }
}
```
#### 결과
* 이제 더 이상 불필요한 Hashtable의 오퍼레이션들이 Properties의 퍼블릭 인터페이스를 오염시키지 않는다.
* String 타입의 키/값만 허용하는 Properties의 규칙을 어길 위험성이 사라졌다.
* 상속과 달리 합성은 내부 구현에 관해 알지 못한다.

Vector를 상속받는 Stack 역시 Vector의 인스턴스 변수를 Stack의 인스턴스 변수로 선언함으로써 합성 관계로 변경할 수 있다.
```
public class Stack<E> {
    private Vector<E> elements = new Vector<>();

    public E push(E item) {
        elements.addElement(item);
        return item;
    }

    public E pop() {
        if (elements.isEmpty()) {
            throw new EmptyStackException();
        }
        return elements.remove(elements.size() - 1);
    }
}
```

#### 결과
* Stack의 퍼블릭 인터페이스에는 불필요한 Vector의 오퍼레이션들이 포함되지 않는다.
* 마지막 위치에서만 요소를 추가 하거나 삭제할 수 있다는 Stack의 규칙을 어길 수 없게 된다.
* 합성 관계로 변경함으로써 클라이언트가 Stack을 잘못 사용할 수도 있다는 가능성을 깔끔하게 제거한 것이다.

### 메서드 오버라이딩의 오작용 문제: InstrumentedHashSet
HashSet 인스턴스를 내부에 포함한 후 HashSet의 퍼블릭 인터페이스에서 제공하는 오퍼레이션들을 이용해 필요한 기능을 구현한다.
```
public class InstrumentedHashSet<E> {
    private int addCount = 0;
    private Set<E> set;

    public InstrumentedHashSet(Set<E> set) {
        this.set = set;
    }

    public boolean add(E e) {
        addCount++;
        return set.add(e);
    }

    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return set.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}
```
* Properties와 Stack을 합성으로 변경한 이유는 불필요한 오퍼레이션들이 퍼블릭 인터페이스에 스며드는 것을 방지하기 위해서다.
* InstrumentedHashSet의 경우에는 HashSet이 제공하는 퍼블릭 인터페이스를 그대로 제공해야 한다.
* 인터페이스를 사용하면 구현 결합도는 제거하면서도 퍼블릭 인터페이스는 그대로 상속받을 수 있다.
* HashSet은 Set 인터페이스를 실체화하는 구현체 중 하나다.
* InstrumentedHashSet이 Set 인터페이스를 실체화하면서 내부에 HashSet의 인스턴스를 합성하면 된다.
```
public class InstrumentedHashSet<E> implements Set<E> {
    private int addCount = 0;
    private Set<E> set;

    public InstrumentedHashSet(Set<E> set) {
        this.set = set;
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return set.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return set.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }

    @Override public boolean remove(Object o) { return set.remove(o); } 
    @Override public void clearO { set.clear(); }
    @Override public boolean equals(Object o) { return set.equals(o); } 
    @Override public int hashCodeO { return set.hashCode(); }
    @Override public Spliterator<E> spliteratorO { return set.spliterator(); } ©Override public int size() { return set.size(); }
    @Override public boolean isEmpty() { return set.isEmpty(); }
    @Override public boolean contains(Object o) { return set.contains(o); }
    @Override public Iterator<E> iterator() { return set.iterator(); }
    @Override public Object[] toArray() { return set.toArray(); }
    @Override public <T> T[] toArray(T[] a) { return set.toArray(a);}
    @Override public boolean containsAll(Collection<?> c) { return set.containsAll(c); } 
    @Override public boolean retainAll(Collection<?> c) { return set.retainAll(c); } 
    @Override public boolean removeAll(Collection<?> c) { return set.removeAll(c); }
}
```

#### 결과
* Set의 오퍼레이션을 오버라이딩한 인스턴스 메서드에서 내부의 HashSet 인스턴스에게 동일한 메서드 호출을 그대로 전달한다.
* 이를 `포워딩`이라 하고, 동일한 메서드를 호출하기 위해 추가된 메서드를 `포워딩 메서드`라고 한다.
* 기존 클래스의 인터페이스를 그대로 외부에 제공하면서 구현에 대한 결합 없이 일부 작동 방식을 변경할 수 있다.

### 부모 클래스와 자식 클래스의 동시 수정 문제: PersonalPlaylist
Playlist의 경우에는 합성으로 변경하더라도 PersonalPlaylist를 함께 수정해야 하는 문제가 해결되지는 않는다.
```
public class PersonalPlaylist {
    private Playlist playlist = new Playlist()；
    
    public void append(Song song)  { 
        playlist.append(song)；
    }

    public void remove(Song song)  { 
        playlist.getTracks().remove(song)；
        playlist.getSingers().remove(song.getSinger());
    }   
}
```

그래도 향후 Playlist의 내부 구현에 대한 변경의 파급효과를 최대한 PersonalPlaylist 내부로 캡슐화할 수 있기 때문에, 상속보다는 합성을 사용하는 게 좋고 대부분의 경우 구현에 대한 결합보다는 인터페이스에 대한 결합이 더 좋다.


## 02. 상속으로 인한 조합의 폭발적인 증가
상속으로 인해 결합도가 높아지면 생기는 일반적인 두 가지 문제점
* 하나의 기능을 추가하거나 수정하기 위해 불필요하게 많은 수의 클래스를 추가하거나 수정해야 한다.
* 단일 상속만 지원하는 언어에서는 상속으로 인해 오히려 중복 코드의 양이 늘어날 수 있다.

> 합성을 사용하면 상속으로 인해 발생하는 클래스의 증가와 중복 코드 문제를 간단하게 해결할 수 있다.

### 기본 정책과 부가 정책 조합하기
핸드폰 과금 시스템에 새로운 요구사항을 추가해본다.
* 기본 정책
  * 일반 요금제
  * 심야 할인 요금제
* 부가 정책
  * 세금 정책
  * 기본 요금 할인 정책

<img src="./image/그림%2011.1.png">

부가 정책은 통화량과 무관하게 기본 정책에 선택적으로 추가할 수 있는 요금 방식이다.
* 기본 정책의 계산 결과에 적용된다.
* 선택적으로 적용할 수 있다.
* 조합 가능하다.
* 부가 정책은 임의의 순서로 적용 가능하다.

> 기본 정책과 부가 정책을 조합해서 만들 수 있는 모든 요금 정책의 조합 가능한 수가 매우 많기 때문에 유연해야 한다.

### 상속을 이용해 기본 정책 구현하기
상속을 이용해 기본 정책과 부가 정책을 구현해본다.
* 기본 정책은 Phone 추상 클래스를 루트로 삼는 기존의 상속 계층을 그대로 이용한다.
* 일반 요금제를 구현하는 RegularPhone과 심야 할인 요금제 
를 구현하는 NightlyDiscountPhone은 Phone의 자식 클래스로 구현한다.
```
public abstract class Phone {
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
public class RegularPhone extends Phone {
    private Money amount;
    private Duration seconds;

    public RegularPhone(Money amount, Duration seconds) {
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
public class NightlyDiscountPhone extends Phone {
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
        }
        return regularAmount.times(call.getDuration().getSeconds() / seconds.getSeconds());
    }
}
```
* RegularPhone과 NightlyDiscountPhone의 인스턴스만 단독으로 생성한다는 것은 부가 정책은 적용하지 않고 오직 기본 정책으로 요금을 계산한다는 것을 의미한다.

### 기본 정책에 세금 정책 조합하기
일반 요금제에 세금 정책을 조합해야 한다.
* 일반 요금제에 세금 정책을 조합해야 할 때, 가장 간단한 방법은 RegularPhone을 상속받은 TaxableRegularPhone을 추가하는 것이다.
```
public class TaxableRegularPhone extends RegularPhone {
    private double taxRate;

    public TaxableRegularPhone(Money amount, Duration seconds,
                               double taxRate) {
        super(amount, seconds);
        this.taxRate = taxRate;
    }

    @Override
    public Money calculateFee() {
        Money fee = super.calculateFee();
        return fee.plus(fee.times(taxRate));
    }
}
```
부모 클래스의 메서드를 재사용하기 위해 super 호출을 사용하면 결과를 얻을 순 있지만 결합도가 높아진다.
* 결합도를 낮추는 방법은 부모 클래스에 추상 메서드를 제공하는 것이다.
* 그러면 자식 클래스는 부모 클래스의 구체적인 구현이 아닌 추상화에 의존하게 된다.

Phone에 추상 메서드 afterCalculated를 추가한다. 
* 이는 전체 요금을 계산한 후에 수행할 로직을 추가할 수 있게 해준다.
```
public abstract class Phone {
    private List<Call> calls = new ArrayList<>();

    public Money calculateFee() {
        Money result = Money.ZERO;

        for(Call call : calls) {
            result = result.plus(calculateCallFee(call));
        }

        return afterCalculated(result);
    }

    protected abstract Money calculateCallFee(Call call);
    protected abstract Money afterCalculated(Money fee);
}
```
```
public class RegularPhone extends Phone {
    ...
    @Override
    protected Money afterCalculated(Money fee) {
        return fee;
    }
}
```
```
public class NightlyDiscountPhone extends Phone {
    ...
    @Override
    protected Money afterCalculated(Money fee) {
        return fee;
    }
}
```
부모 클래스에 추상 메서드를 추가하면 모든 자식 클래스들이 추상 메서드를 오버라이딩해야 하는 문제가 발생한다.

모든 추상 메서드의 구현도 동일하다. 그럼 Phone에서 afterCalculated에 대한 기본 구현을 함께 제공하도록 해본다.

```
public abstract class Phone {
    ...
    protected Money afterCalculated(Money fee) { 
        return fee;
    }
    
    protected abstract Money calculateCallFee(Call call); 
}
```
이처럼 기본 구현을 제공하는 메서드를 훅 메서드라고 한다.
* 추상 메서드와 동일하게 자식 클래스에서 오버라이딩할 의도로 메서드를 추가했지만 편의를 위해 기본 구현을 제공한다.

나머지 코드도 수정한다
* TaxableRegularPhone
* TaxableNightlyDiscountPhone
```
public class TaxableRegularPhone extends RegularPhone {
    private double taxRate;

    public TaxableRegularPhone(Money amount, Duration seconds, double taxRate) {
        super(amount, seconds);
        this.taxRate = taxRate;
    }

    @Override
    protected Money afterCalculated(Money fee) {
        return fee.plus(fee.times(taxRate));
    }
}
```
```
public class TaxableNightlyDiscountPhone extends NightlyDiscountPhone {
    private double taxRate;

    public TaxableNightlyDiscountPhone(Money nightlyAmount, Money regularAmount, Duration seconds, double taxRate) {
        super(nightlyAmount, regularAmount, seconds);
        this.taxRate = taxRate;
    }

    @Override
    protected Money afterCalculated(Money fee) {
        return fee.plus(fee.times(taxRate));
    }
}
```

<img src="./image/그림%2011.3.png">

이제 조합에 따라 원하는 인스턴스를 생성하면 된다. 하지만 문제는 TaxableNightlyDiscountPhone과 TaxableRegularPhone 사이에 코드를 중복했다는 것이다. 대부분의 객체지향 언어는 단일 상속만 지원하기 때문에 상속으로 인해 발생하는 중복 코드 문제를 해결하기 쉽지 않다.

### 기본 정책에 기본 요금 할인 정책 조합하기
기본 요금 할인 정책을 Phone의 상속 계층에 추가해본다.
```
public class RateDiscountableRegularPhone extends RegularPhone {
    private Money discountAmount;

    public RateDiscountableRegularPhone(Money amount, Duration seconds, Money discountAmount) {
        super(amount, seconds);
        this.discountAmount = discountAmount;
    }

    @Override
    protected Money afterCalculated(Money fee) {
        return fee.minus(discountAmount);
    }
}
```
```
public class RateDiscountableNightlyDiscountPhone extends NightlyDiscountPhone {
    private Money discountAmount;

    public RateDiscountableNightlyDiscountPhone(Money nightlyAmount,
                                                Money regularAmount, Duration seconds, Money discountAmount) {
        super(nightlyAmount, regularAmount, seconds);
        this.discountAmount = discountAmount;
    }

    @Override
    protected Money afterCalculated(Money fee) {
        return fee.minus(discountAmount);
    }
}
```

어떤 클래스를 선택하느냐에 따라 적용하는 요금제의 조합이 결정된다. 하지만 이번에도 두 클래스 사이에는 중복 코드가 존재한다.

<img src="./image/그림%2011.4.png">

### 중복 코드의 덫에 걸리다
자유롭게 조합할 수 있어야하고, 순서 역시 임의로 결정할 수 있다.
* 표준 요금제에 기본 할인 정책을 적용한 후 세금을 나중에 부과하고 싶으면 RateDiscountableAndTaxableRegularPhone 클래스를 추가한다.
* 계산 결과에 세금 정책을 적 용한 후 기본 요금 할인 정책을 적용하는 케이스 TaxableAndDiscountableNightlyDiscountPhone 구현한다.

<img src="./image/그림%2011.6.png">

#### 문제점
* 상속을 이용한 해결 방법은 모든 가능한 조합별로 자식 클래스를 추가하는 것이다.
* 새로운 정책을 추가하기 위해서는 불필요하게 많은 수의 클래스를 상속 계층 안에 추가해야 한다.
* 상속의 남용으로 하나의 기능을 추가하기 위해 필요 이상으로 많은 수의 클래스를 추가하는 걸 클래스 폭발/조합의 폭발 문제라고 부른다.
* 클래스 폭발 문제는 자식 클래스가 부모 클래스의 구현에 강하게 결합되도록 강요하는 상속의 근본적인 한계로 발생한다.
* 클래스 폭발 문제는 새로운 기능 추가 뿐만 아니라 수정 시에도 문제가 된다.

## 03. 합성 관계로 변경하기
상속 관계는 컴파일타임에 결정되고 고정되기 때문에 코드를 실행하는 도중에는 변경할 수 없다. 따라서 여러 기능을 조합해야 하는 설계의 경우 `클래스 폭발 문제`가 발생하게 된다. 하지만 합성은 컴파일타임 관계를 런타임 관계로 변경함으로써 이 문제를 해결한다.

컴파일타임 의존성과 런타임 의존성의 거리가 멀수록 설계가 유연하다.
* 상속은 컴파일타임 의존성과 런타임 의존성을 동일하게 만든다.
* 합성은 퍼블릭 인터페이스를 사용하여 두 의존성을 다르게 만들 수 있다.
* 두 의존성의 거리가 멀수록 설계의 복잡도는 상승하지만, 변경에 따르는 고통이 커지고 있다면 유연성을 택하는 것이 좋다.

합성을 사용하면 구현 시점에 정책들의 관계를 고정시킬 필요가 없으며 실행 시점에 유연하게 변경할 수 있다.
* 상속
  * 조합의 결과를 개별 클래스 안으로 밀어 넣는 방법
* 합성
  * 조합을 구성하는 요소들을 개별 클래스로 구현한 후 실행 시점에 인스턴스를 조립하는 방법

### 기본 정책 합성하기
각 정책을 별도의 클래스로 구현한다.
* 기본 정책과 부가 정책을 포괄하는 인터페이스 추가
* 기본 정책을 구성하는 일반 요금제와 심야 할인 요금제의 중복 코드를 담을 추상 클래스를 추가
* Phone 수정
```
public interface RatePolicy {
    Money calculateFee(Phone phone);
}
```
```
public abstract class BasicRatePolicy implements RatePolicy {
    @Override
    public Money calculateFee(Phone phone) {
        Money result = Money.ZERO;

        for(Call call : phone.getCalls()) {
            result.plus(calculateCallFee(call));
        }

        return result;
    }

    protected abstract Money calculateCallFee(Call call);
}

public class RegularPolicy extends BasicRatePolicy {
    private Money amount;
    private Duration seconds;

    public RegularPolicy(Money amount, Duration seconds) {
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
public class Phone {
    private RatePolicy ratePolicy;
    private List<Call> calls = new ArrayList<>();

    public Phone(RatePolicy ratePolicy) {
        this.ratePolicy = ratePolicy;
    }

    public List<Call> getCalls() {
        return Collections.unmodifiableList(calls);
    }

    public Money calculateFee() {
        return ratePolicy.calculateFee(this);
    }
}
```
* Phone 내부에 RatePolicy에 대한 참조자가 포함돼 있다. 이것이 바로 합성이다.
* Phone은 생성자를 통해 RatePolicy의 인스턴스에 대한 의존성을 주입받는다.
* 다양한 종류의 객체와 협력하기 위해 합성을 사용하는 경우, 합성하는 객체의 타입을 인터페이스나 추상 클래스로 선언하고 의존성 주입으로 런타임에 필요한 객체를 설정할 수 있도록 구현하는 것이 일반적이다.

<img src="./image/그림%2011.7.png">

```
Phone phone = new Phone(new RegularPolicy(Money.wons(10)z Duration.ofSeconds(10)));
Phone phone = new Phone(new NightlyDiscountPolicy(Money.wons(5), Money.wons(10), Duration.ofSeconds(10)));
```
컴파일 시점의 Phone 클래스와 RatePolicy 인터페이스 사이의 관계가 런타임에 Phone 인스턴스와 RegularPolicy 인스턴스 사이의 관계로 대체됐다.
* 현재의 설계에 부가 정책을 추가해 보면 합성의 강력함을 실감할 수 있다.

### 부가 정책 적용하기
부가 정책은 기본 정책에 대한 계산이 끝난 후에 적용되므로 두 가지 제약이 존재한다.
* 부가 정책은 기본 정책이나 다른 부가 정책의 인스턴스를 참조할 수 있어야 한다. 부가 정책의 인스턴스는 어떤 종류의 정책과도 합성될 수 있어야 한다.
* Phone의 입장에서는 메시지를 전송하는 대상이 기본 정책의 인스턴스인지 부가 정책의 인스턴스인지 몰라야 한다. 기본 정책과 부가 정책은 협력 안에서 동일한 '역할'을 수행해야 한다. 즉, 동일한 RatePolicy 인터페이스를 구현해야 한다.

부가 정책은 RatePolicy 인터페이스를 구현해야 하며, 내부에 또 다른 RatePolicy 인스턴스를 합성할 수 있어야 한다.
```
public abstract class AdditionalRatePolicy implements RatePolicy {
    private RatePolicy next;

    public AdditionalRatePolicy(RatePolicy next) {
        this.next = next;
    }

    @Override
    public Money calculateFee(Phone phone) {
        Money fee = next.calculateFee(phone);
        return afterCalculated(fee) ;
    }

    abstract protected Money afterCalculated(Money fee);
}
```

AdditionalRatePolicy의 calculateFee는 먼저 next가 참조하는 인스턴스에게 메시지를 전송한 후, 반환된 요금에 부가 정책을 적용하기 위해 afterCalculated 메서드를 호출한다.
```
public class TaxablePolicy extends AdditionalRatePolicy {
    private double taxRatio;

    public TaxablePolicy(double taxRatio, RatePolicy next) {
        super(next);
        this.taxRatio = taxRatio;
    }

    @Override
    protected Money afterCalculated(Money fee) {
        return fee.plus(fee.times(taxRatio));
    }
}

public class RateDiscountablePolicy extends AdditionalRatePolicy {
    private Money discountAmount;

    public RateDiscountablePolicy(Money discountAmount, RatePolicy next) {
        super(next);
        this.discountAmount = discountAmount;
    }

    @Override
    protected Money afterCalculated(Money fee) {
        return fee.minus(discountAmount);
    }
}
```

<img src="./image/그림%2011.11.png">

### 기본 정책과 부가 정책 합성하기
```
-- 일반 요금제, 세금 정책
Phone phone = new Phone(new TaxablePolicy(0.05, new RegularPolicy(...));
-- 일반 요금제, 기본 요금 할인 정책
Phone phone = new Phone(new TaxablePolicy(0.05, new RateDiscountablePolicy(Money.wons(1000), new RegularPolicy(...)));
-- 세금 정책, 기본 요금 할인 정책 순서 변경
Phone phone = new Phone(new RateDiscountablePolicy(Money.wons(1000), new TaxablePolicy(0.05, new RegularPolicy(...)));
-- 심야 할인 요금제에 동일한 정책 적용
Phone phone = new Phone(new RateDiscountablePolicy(Money.wons(1000), new TaxablePolicy(0.05, new NightlyDiscountPolicy(...)));
```
* 객체를 조합하고 사용하는 방식이 상속을 사용한 방식보다 더 예측 가능하고 일관성 있다.
* 상속의 경우 새로운 부가 정책을 추가하기 위해서는 상속 계층에 불필요할 정도로 많은 클래스를 추가해야 한다.
* 합성의 경우 새로운 부가 정책 클래스 하나만 추가한 후 원하는 방식으로 조합하면 된다. 
* 수정 시에도 하나의 클래스만 수정하면 된다. 
* 합성을 이용한 설계는 단일 책임 원칙을 준수한다.

### 새로운 정책 추가하기
* 상속을 기반으로 한 설계에서는 정책을 추가하기 위해서 불필요할 정도로 많은 클래스를 추가해야 한다.
* 합성을 기반으로 한 설계에서는 클래스 하나만 추가하면 된다.
* 코드 재사용을 위해 상속보다는 합성을 사용하라고 하는지 그 이유를 이해할 수 있다.
* 상속은 정책을 수정하기 위해서 여러 클래스를 수정하지만 합성으로 변경한 설계는 클래스 하나만 변경하면 되는데 단일 책임 원칙을 준수한다.
<img src="./image/그림%2011.13.png">

### 객체 합성이 클래스 상속보다 더 좋은 방법이다.
* 상속은 코드 재사용을 위한 우아한 해결책은 아니다. 부모의 세부적인 구현에 자식을 강하게 결합시켜 코드의 진화를 방해한다.
* 상속이 구현을 재사용하는 데 비해 합성은 객체의 인터페이스를 재사용한다.

## 04. 믹스인
믹스인은 코드를 재사용하는 유용한 기법 중 한 가지로 상속과 합성의 특성을 모두 보유한다. 상속의 경우 클래스의 확장과 수정을 일관성 있게 표현할 수 있는 추상화의 부족으로 변경하기 어려운 코드를 야기한다. 구체적인 코드를 재사용하면서도 낮은 결합도를 유지하는 방법은 재사용에 적합한 추상화를 도입하는 것이다.

믹스인은 객체를 생성할 때 코드 일부를 클래스 안에 섞어 넣어 재사용하는 기법이다.
* 합성
  * 실행 시점에 객체를 조합하는 재사용 방법
* 믹스인 
  * 컴파일 시점에 필요한 코드 조각을 조합하는 재사용 방법

믹스인은 상속과 다르다.
* 상속의 목적은 자식을 부모와 동일한 개념적인 범주로 묶어 is-a 관계를 만들기 위함이다.
* 믹스인은 말 그대로 코드를 다른 코드 안에 섞어 넣기 위한 방법이다.
* 코드를 섞어 넣는다는 기본 개념을 구현하는 방법은 언어마다 다르다.
* 그 방법이 무엇이건 코드를 다른 코드 안에 유연하게 섞어 넣을 수 있다면 믹스인이라고 부를 수 있다.
* 믹스인은 Flavors라는 언어에서 처음으로 도입됐고 이후 CLOS(Common Lisp OBject System)에 의해 대중화됐다.

### 기본 정책 구현하기
* 기본 정책을 구현하는 클래스는 기본 정책에 속하는 전체 요금제 클래스들이 확장할 수 있도록 추상 클래스로 구현한다.
* 표준 요금제와 심야 할인 요금제는 기본 정책 요금제를 상속받아 calculateCallFee를 오버라이딩한다.
```

```