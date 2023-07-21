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