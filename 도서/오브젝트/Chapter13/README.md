# 13. 서브클래싱과 서브타이핑
### 상속의 용도
1. 타입 계층 구현
   * 부모 클래스는 일반적인 개념을 구현하고 자식 클래스는 특수한 개념을 구현한다.
     * 부모 클래스는 자식 클래스의 일반화(generalization)
     * 자식 클래스는 부모 클래스의 특수화(specialization)
2. 코드 재사용
   * 간단한 선언만으로 부모 클래스의 코드를 재사용할 수 있다.
   * 상속을 통해 점진적으로 기능을 확장할 수 있지만 재사용을 위해 상속을 사용하면 부모와 자식 클래스가 강하게 결합된다.

상속을 사용하는 이유는 객체의 `행동`을 기반으로 하는 타입 계층을 구현해야 하며 타입 사이의 관계를 고려하지 않은 채 단순히 코드를 재사용하기 위해 상속을 사용해서는 안된다.

#### 객체지향 프로그래밍과 객체기반 프로그래밍
* 객체기반 프로그래밍(Object-Based Programming)
  * 상태와 행동을 캡슐화한 객체를 조합해서 프로그램을 구성하는 방식
  * 초기 버전의 비주얼 베이직은 객체라는 개념은 존재하지만 클래스 사이의 상속과 다형성은 존재하지 않는다.
  * 프로토타입 기반 언어인 자바스크립트와 같은 클래스를 사용하지 않는 프로그래밍 방식
* 객체지향 프로그래밍(Object-Oriented Programming)
  * 객체기반 프로그래밍의 한 종류
  * 객체기반 프로그래밍 동일하게 조합해서 애플리케이션을 개발하지만 상속과 다형성을 지원하지 않는다.
  * Java, C++, 루비, C#등의 언어는 상속과 다형성 지원하기 때문에 객체지향 프로그래밍이다.
  * 클래스를 사용하는 프로그래밍 방식

## 01. 타입
객체지향 프로그래밍에서 타입의 의미를 이해하려면 두 가지 관점의 타입을 이해할 필요가 있다
* 프로그래밍 언어 관점의 타입
* 개념 관점의 타입

### 개념 관점의 타입
개념 관점에서 타입이란 우리가 인지하는 세상의 사물의 종류를 의미한다.
* 타입
  * 인식하는 객체들에 적용하는 개념이나 아이디어
  * 사물을 분류하기 위한 틀로 사용된다.
  * 자바, 루비, 자바스크립트, C를 프로그래밍 언어라고 부를 때 우리는 이것들을 프로그래밍 언어라는 타입으로 분류하고 있는 것이다.
* 인스턴스
  * 어떤 대상이 타입으로 분류될 때의 대상을 가리켜 인스턴스라고 부른다.
* 객체
  * 일반적으로 타입의 인스턴스를 가리켜 객체라고 부른다.
* 심볼
  * 타입에 이름을 붙인 것이다. 
  * 프로그래밍 언어가 타입의 심볼에 해당한다.
* 내연(intension)
  * 타입의 정의로서 타입에 속하는 객체들이 가지는 공통적인 속성이나 행동
  * 예를 들어 프로그래밍 언어의 정의인 컴퓨터에게 특정한 작업을 지시하기 위한 어휘와 문법적 규칙의 집합이 바로 내연이다.
* 외연(extension)
  * 타입에 속하는 객체들의 집합
  * 예를 들어 프로그래밍 언어 타입의 경우에는 자바, 루비, 자바스크립트, C가 속한 집합이 외연을 구성한다.

### 프로그래밍 언어 관점의 타입
* 프로그래밍 언어 관점에서 타입은 연속적인 비트에 의미와 제약을 부여하기 위해 사용된다.
* 비트에 담긴 데이터를 문자열로 다룰지 정수로 다룰지는 전적으로 데이터를 사용하는 애플리케이션에 의해 결정된다.
* 프로그래밍 언어의 관점에서 타입은 `비트 묶음에 의미를 부여하기 위해 정의된 제약과 규칙`을 가리킨다.

프로그래밍 언어에서 타입은 두 가지 목적을 위해 사용된다.
* 타입에 수행될 수 있는 유효한 오퍼레이션의 집합을 정의한다.
  * 자바에서 '+' 연산자는 원시형 숫자 타입이나 문자열 타입의 객체에서는 사용할 수 있지만 다른 클래스의 인스턴스에 대해서는 사용할 수 없다.
  * C++ / C# 에서는 연산자 오버로딩을 통해 '+' 연산자 사용이 가능하다.
  * 모든 객체지향 언어들은 객체의 타입에 따라 적용 가능한 연산자의 종류를 제한함으로써 프로그래머의 실수를 막아준다.
* 타입에 수행되는 오퍼레이션에 대해 미리 약속된 문맥을 제공한다.
  * 정수 a + b는 a와 b를 더한다. 문자열 a + b는 a와 b에 대해 문자열을 합치는데 따라서 a와 b에 부여된 타입이 '+' 연산자의 문맥을 정의한다.
  * new 연산자는 타입에 정의된 크기만큼 저장 공간을 할당하고 생성된 객체를 초기화하기 위해 타입의 생성자를 자동으로 호출하는데 이 경우 객체를 생성하는 방법에 대한 문맥을 결정하는 것은 바로 객체의 타입이다.

### 객체지향 패러다임 관점의 타입
타입은 다음과 같은 두 가지 관점에서 정의할 수 있다.
* 개념 관점에서 타입이란 공통의 특징을 공유하는 대상들의 분류
* 프로그래밍 언어 관점에서 타입이란 동일한 오퍼레이션을 적용할 수 있는 인스턴스들의 집합

> 객체지향 프로그래밍에서 오퍼레이션은 객체가 수신할 수 있는 메시지를 의미하고 따라서 객체의 타입이란 객체가 수신할 수 있는 메시지의 종류를 정의하는 것이다.

#### 퍼블릭 인터페이스
* 수신할 수 있는 메시지의 집합
* 객체지향 프로그래밍에서 타입을 정의하는 것은 객체의 퍼블릭 인터페이스를 정 의하는 것과 동일하다.

#### 개념 관점의 타입
* 개념 관점에서 타입은 공통의 특성을 가진 객체들을 분류하기 위한 기준이다.
* 객체의 퍼블릭 인터페이스가 타입을 결정하는데 따라서 동일한 퍼블릭 인터페이스를 제공하는 객체들은 동일한 타입으로 분류된다.
* 객체들이 동일한 상태를 가지고 있더라도 퍼블릭 인터페이스가 다르면 서로 다른 타입으로 분류된다.

> 객체를 바라볼 때는 항상 객체가 외부에 제공하는 행동에 초점을 맞춰야 하며 객체의 타입을 결정하는 것은 객체가 외부에 제공하는 행동이다.

## 02. 타입 계층
### 타입 사이의 포함관계
수학에서 집합은 다른 집합을 포함할 수 있다.
* 타입 역시 객체들의 집합이기 때문에 다른 타입을 포함하는 것이 가능하다
* 타입 안에 포함된 객체들은 좀 더 상세한 기준으로 묶어 새로운 타입을 정의하면 이 새로운 타입은 자연스럽게 기존 타입의 부분 집합이 된다.

<img src="./image/그림%2013.1.png">

<img src="./image/그림%2013.2.png">

#### 언어의 타입
* 자바느 프로그래밍 언어인 동시에 객체지향 언어에 속하고 프로그래밍 기반 언어 타입에 속한다.
* 프로그래밍 언어 타입은 객체지향 언어 타입보다 더 일반적이고 객체지향 언어 타입은 클래스 기반 언어 타입보다 일반적이다.
* 포함하는 타입은 외연 관점에서는 더 크고 내연 관점에서는 더 일반적이고 이와 반대로 포함되는 타입은 외연 관점에서는 더 작고 내연 관점에서는 더 특수한데 이것은 `포함 관계로 연결된 타입 사이에 개념적으로 일반화와 특수화 관계가 존재한다는 것을 의미`한다.

<img src="./image/그림%2013.3.png">

타입 계층을 표현할 때는 더 일반적인 타입을 위쪽에 더 특수한 타입을 아래쪽에 배치하는 것이 관례다.
* 슈퍼타입(supertype)
  * 타입 계층을 구성하는 두 타입 간의 관계에서 더 일반적인 타입
  * 프로그래밍 언어 타입은 객체지향 언어, 절차적 언어의 슈퍼타입이다.
* 서브타입(subtype) 
  * 타입계층을 구성하는 두 타입 간의 관계에서 더 특수한 타입

#### 일반화와 특수화
내연과 외연 관점은 아래와 같다.
* 내연 관점
  * 일반화
    * 어떤 타입의 정의를 좀 더 보편적이고 추상적으로 만드는 과정
  * 특수화
    * 어떤 타입의 정의를 좀 더 구체적이고 문맥 종속적으로 만드는 과정
* 외연 관점
  * 일반화
    * 특수한 타입의 인스턴스 집합을 표현하는 슈퍼셋(superset)
  * 특수화
    * 일반적인 타입의 인스턴스 집합에 포함된 서브셋(subset)

> 일반화는 다른 타입을 완전히 포함하거나 내포하는 타입을 식별하는 행위 또는 행위의 결과, 특수화는 다른 타입 안에 전체적으로 포함되거나 완전히 내포되는 타입을 식별하는 행위 또는 행위의 결과

* 슈퍼타입
  * 집합이 다른 집합의 모든 멤버를 포함한다.
  * 타입 정의가 다른 타입보다 좀 더 일반적이다.
* 서브타입
  * 집합에 포함되는 인스턴스들이 더 큰 집합에 포함된다.
  * 타입 정의가 다른 타입보다 좀 더 구체적이다.

### 객체지향 프로그래밍과 타입 계층
* 객체의 타입을 결정하는 것은 퍼블릭 인터페이스다.
* 일반적인 타입이란 비교하려는 타입에 속한 객체들의 퍼블릭 인터페이스보다 더 일반적인 퍼블릭 인터페이스를 가지는 객체들의 타입을 의미한다.

퍼블릭 인터페이스의 관점에서 슈퍼타입과 서브타입을 다음과 같이 정의할 수 있다.
* 슈퍼타입이란 서브타입이 정의한 퍼블릭 인터페이스를 일반화시켜 상대적으로 범용적이고 넓은 의미로 정의한 것이다.
* 서브타입이란 슈퍼타입이 정의한 퍼블릭 인터페이스를 특수화시켜 상대적으로 범용적이고 좁은 의미로 정의한 것이다.

> 서브타입의 인스턴스는 슈퍼타입의 인스턴스로 간주될 수 있고, 상속과 다형의 관계를 이해하기 위한 출발점이다.

## 03. 서브클래싱과 서브타이핑
1. 객체지향 프로그래밍 언어에서 타입을 구현하는 일반적인 방법은 클래스를 이용하는 것이다.
2. 그리고 타입 계층을 구현하는 일반적인 방법은 상속을 이용하는 것이다.

* 부모 클래스
  * 슈퍼타입의 역할
* 자식 클래스
  * 서브타입의 역할

### 언제 상속을 사용해야 하는가?
마틴 오더스키는 아래 질문에서 모두 예라고 답할 수 있는 경우에만 상속을 사용하라고 조언한다.
* 상속관계가 is-a 관계를 모델링하는가?
  * 일반적으로 자식 클래스 is 부모 클래스 라고 말해도 이상하지 않다면 상속을 사용할 후보로 간주할 수 있다.
* 클라이언트 입장에서 부모 클래스의 타입으로 자식 클래스를 사용해도 무방한가?
  * 상속 계층을 사용하는 클라이언트 입장에서 부모 클래스와 자식 클래스의 차이점을 몰라야 한다.
  * 이를 자식 클래스와 부모 클래스 사이의 행동 호환성이라 부른다.

> 중요한 점은 클라이언트 입장에서 부모 클래스의 타입으로 자식 클래스를 사용해도 무방한가? 이 질문에 포커스를 맞춰야 하고, 클라이언트 관점에서 두 클래스에 대해 기대하는 행동이 다르다면 is-a 관계가 성립하더라도 상속을 사용해서는 안된다.

### is-a 관계
* 마틴 오더스키 조언에 따르면 두 클래스가 어휘적으로 is-a 관계를 모델링할 경우에만 상속을 사용해야 한다.
* is-a 관계가 생각처럼 직관적이고 명쾌한 것은 아니며 스콧 마이어스는 is-a 관계가 직관을 쉽게 배신할 수 있다는 사실을 보여준다.

두 가지 사실에서 이야기를 시작한다.
* 펭귄은 새다
* 새는 날 수 있다
```
public class Bird {
    public void fly() { ... }
}

public class Penguin extends Bird {
    ...
}
```

* 펭귄은 분명 새지만 날 수 없는 새기 때문에 이 코드는 반은 맞고 반은 틀리다. 
* 이 예는 어휘적인 정의가 아니라 기대되는 행동에 따라 타입 계층을 구성해야 한다는 사실을 잘 보여준다. 
* 어휘적으로는 펭귄은 새지만 만약 새의 정의에 날 수 있다는 행동이 포함된다면 펭귄은 새의 서브타입이 될 수 없다.
* 타입 계층의 의미는 행동 이라는 문맥에 따라 달라질 수 있고 따라서 슈퍼타입과 서브타입 관계에서는 is-a보다 행동 호환성이 더 중요하다.

> 어떤 두 대상을 언어적으로 is-a라고 표현할 수 있더라도 일단은 상속을 사 용할 예비 후보 정도로만 생각하고 너무 성급하게 상속을 작용하려고 서두르지 말아야 한다.
> 애플리케이션 안에서 두 가지 후보 개념이 어떤 방식으로 사용되고 협력하는지 살펴본 후에 상속의 적용 여부를 결정해도 늦지 않다.

### 행동 호환성
* 타입 사이에 개념적으로 어떤 연관성이 있다고 하더라도 행동에 연관성이 없다면 is-a 관계를 사용하지 말아야 한다.
* 결론은 두 타입 사이에 행동이 호환되는 경우에만 타입 계층으로 묶어야 한다.
* 행동의 호환 여부를 판단하는 기준은 `클라이언트 관점`이다.
* 클라이언트가 두 타입이 동일하게 행동할 것이라고 기대한다면 두 타입을 타입 계층으로 묶을 수 있지만 클라이언트가 두 타입이 동일하게 행동하지 않을 것이라고 기대한다면 두 타입을 타입 계층으로 묶어서는 안된다.

클라이언트가 날 수 있는 새를 원한다면 아래와 같은 방법을 사용할 수 있다.
* fly 메서드를 오버라이딩해서 내부 구현을 비워둔다.
  * Penguin과 Bird의 행동은 호 환되지 않기 때문에 올바른 타입 계층이라고 할 수 없다.
  ```
  public class Penguin extends Bird {
    @Override
    public void fly() {} 
  }
  ```
* fly 메서드를 오버라이딩한 후 예외를 던진다.
  * 클라이언트는 예외가 던져질 것이라고는 기대하지 않았을 것이고 클라이언트의 관점에서 Bird와 Penguin의 행동이 호환되지 않는다.
  ```
  public class Penguin extends Bird {
    ...
    @Override
    public void fly() {
        throw new UnsupportedOperationExceptionO;
    } 
  }
  ```
* 펭귄이 아닌 경우에만 메시지를 전송한다.
  * 날 수 없는 또 다른 새가 상속 계층에 추가된다면 문제가 발생한다.
  * instanceof처럼 객체의 타입을 확인하는 코드는 새로운 타입을 추가할 때마다 코드 수정을 요구하기 때문에 개방-폐쇄 원칙을 위반한다.
  ```
  public void flyBird(Bird bird) {
    // 인자로 전달된 모든 bird가 Penguin의 인스턴스가 아닐 경우에만 
    // fly() 메시지를 전송한다
    if (!(bird instanceof Penguin)) {
        bird.flyO;
    } 
  }
  ```

### 클라이언트 기대에 따라 계층 분리하기
* 행동 호환성을 만족시키지 않는 상속 계층을 그대로 유지한 채 클라이언트의 기대를 충족시킬 수 있는 방법을 찾기란 쉽지 않고 문제를 해결할 수 있는 방법은 `클라이언트의 기대에 맞게 상속 계층을 분리`하는 것뿐이다.

```
public class Bird {
    ...
}

public class FlyingBird extends Bird { 
    public void fly() { ... }
}

public class Penguin extends Bird { 
    ...
}
```

<img src="./image/그림%2013.4.png">

#### 인터페이스 분리 원칙(Interface Segregation Principle, ISP)
* 클라이언트에 따라 인터페이스를 분리하면 변경에 대한 영향을 더 세밀하게 제어할 수 있다.
* 대부분의 경우 인터페이스는 클라이언트의 요구가 바뀜에 따라 변경된다.
* 클라이언트에 따라 인터페이스를 분리하면 각 클라이언트의 요구가 바뀌더라도 영향의 파급효과를 효과적으로 제어할 수 있게 된다.
* 이처럼 `인터페이스를 클라이언트의 기대에 따라 분리함으로써 변경에 의해 영향을 제어하는 설계 원칙`을 `인터페이스 분리 원칙(Interface Segregation Principle, ISP)`라고 한다.

#### 비대한 클래스
* 클라이언트 사이에 이상하고 해로운 결합이 생기게 만든다
* 클라이언트가 비대한 클래스에 변경을 가하면 나머지 모든 클래스가 영향을 받는다

#### 비대한 인터페이스의 단점 해결하기
* 클라이언트는 자신이 실제로 호출하는 메서드에만 의존해야 한다.
* 비대한 클래스의 인터페이스를 여러개로 분리함으로써 해결할 수 있다.
* 호출되지 않는 메서드에 대한 클라이언트의 의존성을 끊고 클라이언트가 서로에 대해 독립적이 되게 만들 수 있다.

> 요점은 자연어에 현혹되지 말고 요구사항 속에서 클라이언트가 기대하는 행동에 집중하라는 것으로 클래스의 이름 사이에 어떤 연관성이 있다는 사실은 아무런 의미도 없고, 두 클래스 사이에 행동이 호환되지 않는다면 올바른 타입 계층이 아니기 때문에 상속을 사용해서는 안 된다.

### 서브클래싱과 서브타이핑
* 서브클래싱(subclassing)
  * 다른 클래스의 코드를 재사용할 목적으로 상속을 사용하는 경우
  * 자식 클래스와 부모 클래스의 행동이 호환되지 않기 때문에 자식 클래스의 인스턴스가 부모 클래스의 인스턴스를 대체할 수 없다.
  * 구현상속(implementation inheritance) 또는 클래스 상속 (class inheritance)이라고 부른다.
* 서브타이핑(subtyping)
  * 타입 계층을 구성하기 위해 상속을 사용하는 경우
  * 자식클래스와 부모 클래스의 행동이 호환되기 때문에 자식 클래스의 인스턴스가 부모 클래스의 인스턴스를 대체할 수 있다.
  * 서브타이핑을 인터페이스 상속 (interface inheritance) 이라고 한다.
  * 서브타입이 슈퍼타입이 하는 모든 행동을 동일하게 할 수 있어야 한다.
  * 행동 호환성을 만족 시켜야 한다.


#### 슈퍼타입과 서브타입 사이의 관계
* 슈퍼타입과 서브타입 사이의 관계에서 가장 중요한 것은 `퍼블릭 인터페이스`이다.
* 슈퍼타입 인스턴스를 요구하는 모든 곳에서 서브타입의 인스턴스를 대신 사용하기 위해 만족해야 하는 최소한의 조건은 서브타입의 퍼블릭 인터페이스가 슈퍼 타입에서 정의한 퍼블릭 인터페이스와 동일하거나 더 많은 오퍼레이션을 포함해야 한다.
* 개념적으로 서브타입이 슈퍼타입의 퍼블릭 인터페이스를 상속받는 것처럼 보이게 된다.

#### 행동 호환성과 대체 가능성
* 서브타이핑 관계가 유지되기 위해서는 `서브타입이 슈퍼타입이 하는 모든 행동을 동일하게 할 수 있어야` 하는데 어떤 타입이 다른 타입의 서브타입이 되기 위해서는 `행동 호환성(behavioral substitution)`을 만족시켜야 한다.
* 자식 클래스가 부모 클래스를 대신할 수 있기 위해서는 자식 클래스가 부모 클래스가 사용되는 모든 문 맥에서 자식 클래스와 동일하게 행동할 수 있어야 하며 행동 호환성을 만족하는 상속 관계는 부모 클래스를 새로운 자식 클래스로 대체하더라도 시스템이 문제없이 동작할 것이라는 것을 보장해야 하는데 자식 클래스와 부모 클래스 사이의 행동 호환성은 부모 클래스에 대한 자식 클래스의 `대체 가능성(substitutability)`을 포함한다.

## 04. 리스코프 치환 원칙
* 바바라 리스코프는 올바른 상속 관계의 특징을 정의하기 위해 `리스코프 치환 원칙(Liskov Substitution Principle, LSP)`을 발표했다.
* 상속 관계로 연결한 두 클래스가 서브타이핑 관계를 만족시키기 위해서는 다음의 조건을 만족해야 한다.
  * S형의 각 객체 o1에 대해 T형의 객체 o2가 하나 있고, T에 의해 정의된 모든 프로그램 P에서 T가 S로 치환될 때 P의 동작이 변하지 않으면 S는 T의 서브타입이다.
* 리스코프 치환 원칙을 한마디로 `서브타입은 그것의 기반 타입에 대해 대체 가능해야 한다`로 클라이언트가 `차이점을 인식하지 못한 채 파생 클래스의 인터페이스를 통해 서브클래스를 사용할 수 있어야 한다`는 것이다.
* 리스코프 치환 원칙은 행동 호환성을 설계 원칙으로 정리한 것이다.
    * 자식 클래스가 부모 클래스와 행동 호환성을 유지함으로써 부모 클래스를 대체할 수 있도록 구현된 상속 관계만을 서브타이핑이라고 부른다.

사각형을 구현한 예제를 살펴보다.
* 대부분의 사람들은 직사각형은 사각형이다(Square is-a Rectangle)라는 이야기를 당연하게 생각하지만 직사각형은 사각형이 아닐 수도 있다.
* 직사각형과 사각형의 상속 관계는 리스코프 치환 원칙을 위반하는 고전적인 사례 중 하나다.

<img src="./image/그림%2013.7.png">

정사각형은 너비와 높이가 동일하고, 직사각형은 너비와 높이가 다르다고 가정하기 때문에 업캐스팅될 수 있고 문제는 여기서 발생한다.

> Rectangle은 is-a라는 말이 얼마나 우리의 직관에서 벗어날 수 있는지를 잘 보여주며 중요한 것은 클라이언트 관점에서 행동이 호환되는지 여부로 행동이 호환될 경우에만 자식 클래스가 부모 클 래스 대신 사용될 수 있다.

### 클라이언트와 대체 가능성
* 리스코프 치환 원칙은 자식 클래스가 부모 클래스를 대체하기 위해서는 부모 클래스에 대한 클라이언트의 가정을 준수해야 한다는 것을 강조하는데 즉 상속관계는 클라이언트의 관점에서 자식 클래스가 부모클래스를 대체할 수 있을때만 올바르다.
* 상속 관계는 클라이언트의 관점에서 자식 클래스가 부모 클래스를 대체할 수 있을 때만 올바르다.

> 대체 가능성을 결정하는 것은 클라이언트다.

### is-a 관계 다시 살펴보기
* is-a는 클라이언트 관점에서 is-a일 때만 참이다.
* is-a 관계는 객체지향에서 중요한 것은 객체의 속성이 아니라 `객체의 행동`이라는 점을 강조한다.
  * 클라이언트를 고려하지 않은 채 개념과 속성의 측면에서 상속 관계를 정할 경우 리스코프 치환 원칙을 위반하는 서브클래싱에 이르게 될 확률이 높다.
* 객체지향과 관련된 대부분의 규칙이 그런 것처럼 is-a 관계 역시 행동이 우선 이다.

> 상속이 서브타이핑을 위해 사용될 경우에만 is-a 관계이며 서브클래싱을 구현하기 위해 상속을 사용했다면 is-a 관계라고 말할 수 없다.

### 리스코프 치환 원칙은 유연한 설계의 기반이다
* 리스코프 치환 원칙은 클라이언트가 어떤 자식 클래스와도 안정적으로 협력할 수 있는 상속 구조를 구현할 수 있는 가이드라인을 제공한다.
* 새로운 자식 클래스를 추가하더라도 클라이언트의 입장에서 동일하게 행동하기만 한다면 클라이언트를 수정하지 않고도 상속 계층을 확장할 수 있다.

리스코프 치환 원칙을 따르는 설계는 유연할뿐만 아니라 확장성이 높다.
* 중복할인 정책을 구현해도 기존의 DiscountPolicy 상속 계층에 새로운 자식 클래스인 OverlappedDiscountPolic를 추가하더라도 클라이언트를 수정할 필요가 없었다.
```
public class OverlappedDiscountPolicy extends DiscountPolicy {

	private List<DiscountPolicy> discountPolicies = new ArrayList<>();

	public OverlappedDiscountPolicy(DiscountPolicy ... discountPolicies) {
    this.discountPolicies = Arrays.aslist(discount Policies); 
  }
	
	@Override 
  protected Money getDiscount Amount (Screening screening) { 
    Money result =Money ZERO;
		for(Discount Policy each: discount Policies) {
			result = result.plus(each.calculateDiscountAmount(screening)); 
		}
		return result;
	}
}
```

위 코드의 설계는 의존성 역전 원칙과 개방-폐쇄 원칙, 리스코프 치환 원칙이 한데 어우러져 설계를 확장 가능하게 만든 대표적인 예다.
* 의존성 역전 원칙
  * 구체 클래스인 Moive와 OverlappedDiscountPolicy 모두 추상 클래스인 DiscountPolicy에 의존한다.
  * 상위 수준의 모듈인 Movie와 하위수준 모듈인 OverlappedDiscountPolicy는 모두 추상 클래스인 DisCountPolicy의 의존하느데 따라서 DIP를 만족한다.
* 리스코프 치환 원칙
  * DiscountPolicy와 협력하는 Movie 관점에서 DicountPolicy대신 OverlappedDiscountPolicy와 렵력하더라도 아무 문제가 없다 따라서 이 설계는 LSP를 만족한다.
* 개방-폐쇄원칙
  * 중복 할인 정책이라는 새로운 기능을 추가하기 위해 DiscountPolicy의 자식 클래스인 OverlappedDiscountPolicy를 추가하더라도 Movie에는 영향을 끼치지 않는다. 다시 말해서 기능 확장을 하면서 기존 코드를 수정할 필요가 없다.

리스코프 치환 원칙은 개방-폐쇄 원칙을 아래와 같이 지원한다. 
* 자식 클래스가 클라이언트 관점에서 부모 클래스를 대체할 수 있다면 기능 확장을 위해 자식 클래스를 추가하더라도 코드를 수정 할 필요가 없어지기 떄문이다. 
* 반대로 둘중하나라도 위반하면 다른 한쪽을 잠재적으로 위반한다.

<img src="./image/그림%2013.8.png">

### 타입 계층과 리소코프 치환 원칙
* 클래스 `상속은 타입 계층을 구현할 수 있는 방법 중 하나`일 뿐이다.
* 구현 방법은 중요하지 않으며 핵심은 구현 방법과 무관하게 `클라이언트의 관점에서 슈퍼타입에 대해 기대하는 모든 것이 서브타입에게도 적용`돼야 하는 것이다.

## 05. 계약에 의한 설계와 서브타이핑
계약에 의한 설계(Design By Contract, DBC)
* 클라이언트와 서버 사이의 협력을 의무와 이익으로 구성된 계약의 관점에서 표현하는 것을 계약에 의한 설계(Design By Contract, DBC)라고 부른다.

계약에 의한 설계의 세 가지 요소
* 사전조건(precondition)
  * 클라이언트가 정상적으로 메서드를 실행하기 위해 만족시켜야 하는 조건
* 사후조건(postcondition)
  * 메서드가 실행된 후에 클라이언트에게 보장해야 하는 조건
* 클래스 불변식(class invariant)
  * 메서드 실행 전과 실행 후에 인스턴스가 만족시켜야 하는 조건

리스코프 치환 원칙은 어떤 타입이 서브타입이 되기 위해서는 슈퍼타입의 인스턴스와 협력하는 클라이언트 관점에서 서브타입의 인스턴스가 슈퍼타입을 대체하더라도 협력에 지장이 없어야 한다는 것을 의미한다.

리스코프 치환 원칙과 계약에 의한 설계
* 서브타입이 리스코프 치환 원칙을 만족시키기 위해서는 `클라이언트와 슈퍼타입 간에 체결된 계약을 준수`해야한다.

### 서브타입과 계약
계약의 관점에서 `상속이 초래하는 가장 큰 문제는 자식 클래스가 부모 클래스의 메서드를 오버라이딩할 수 있다는 것`이다.

계약의 관점에서 볼때 아래의 규칙을 지켜야 한다.
* 서브타입에서 더 강력한 사전조건을 정의할 수 없다.
* 서브타입에 슈퍼타입과 같거나 더 약한 사전조건을 정의할 수 있다.
* 서브타입에 슈퍼타입과 같거나 더 강한 사후조건을 정의할 수 있다.
* 서브타입에 더 약한 사후조건을 정의할 수 없다.

계약에 의한 설계는 클라이언트 관점에서의 대체 가능성을 계약으로 설명할 수 있다는 사실을 잘 보여 주는데 서브타이핑을 위해 상속을 사용하고 있다면 부모 클래스가 클라이언트와 맺고 있는 계약 에 관해 깊이 있게 고민해야 한다.

> 상속은 타입 계층을 구현할 수 있는 전통적인 방법이지만 유일한 방법은 아니며 상속을 사용하지 않고도 타입 계층을 구현할 수 있는 다양한 방법이 존재한다.