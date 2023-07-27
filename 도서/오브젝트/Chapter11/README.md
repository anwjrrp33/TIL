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