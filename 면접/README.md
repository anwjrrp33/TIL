## Java

### JVM(Java Virtual Machine)
* JVM은 자바 가상 머신으로 자바 프로그램 실행환경을 만들어주는 소프트웨어입니다. 자바 애플리케이션을 컴파일하여 클래스 로더를 통해 실행합니다. 메모리 관리인 GC를 수행하며 스택 기반의 가상머신입니다. 자바는 JVM을 통해서 실행되기 때문에 운영체제에 종속적이지 않고 어떠한 플랫폼에도 영향을 받지 않고 동일하게 수행됩니다.
![Java 기본 실행 과정](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F56cSc%2FbtruTEtjRXJ%2Fr1JNTkEuEeY8cSKtqcXCRK%2Fimg.png) 
*  JVM 동작 방식
    * 1. 자바 프로그램이 실행되면 JVM은 OS로 부터 메모리를 할당받는다.
    * 2. 자바 컴파일러가 자바 코드(.java)를 자바 바이트코드(.class)로 컴파일한다.
    * 3. 자바 바이트코드를 클래스 로더를 통해서 Runtime Data Area로 로딩한다.
    * 4. Runtime Data Area에 로딩된 자바 바이트 코드를 Execution Engine을 통해서 해석한다.
    * 5. 해석된 바이트 코드는 Runtime Data Area의 각 영역에 배치되어 수행하며 Execution Engine에 의해서 GC 작동과 쓰레드 동기화가 된다.
![JVM 동작 방식](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcQRqku%2Fbtru0vJ6Ixx%2F9qCTW7ChXc80fGfQUrT4B0%2Fimg.png)
* JVM 구조
    * 클래스 로더(Class Loader): JVM내에 클래스를 로드하고 링크를 통해 배치하는 작업을 수행하는 모듈
    * 실행 엔진(Execution Engine): 바이트 코드를 실행시키는 역할
        * 인터프리터: 바이트 코드를 한줄씩 실행한다.
        * JIT 컴파일러: 인터프리터의 효율을 높이기 위한 컴파일러로 인터프리터가 반복되는 코드를 발견하면 JIT 컴파일러가 반복되는 코드를 네이티브 코드로 변환하고 반복되는 코드를 인터프리터는 네이티브 코드로 변환된 컴파일 코를 바로 사용한다.
    * Runtime Data Areas: 프로그램 실행 중에 사용되는 다양한 영역
        * PC register: 쓰레드가 시작될 때 생성되며 현재 수행 중인 JVM 명령의 주소를 갖고 있는다.
        * Stack Area: 지역 변수, 파라미터 등이 생성되는 영역으로 실제 객체는 Heap에 할당되고 해당 레퍼런스만 Stack에 저장된다.
        * Heap Area: 동적으로 생성된 오브젝트와 배열이 저장되는 곳으로 GC의 대상 영역이다.
        * Method Area: 클래스 멤버 변수, 메소드 정보, Type 정보, Constant Pool, static, final 변수 등이 생성된다. 상수 풀(Constant Pool)은 모든 Symbol Reference를 포함한다.
    * JNI(Java Native Interface): 자바 애플리케이션에서 C, C++, 어셈블리어로 작성된 함수를 사용할 수 있는 방법을 제공해준다. Native 키워드를 통해서 메소드를 호출하고 대표적인 메소드로 currentThread()가 존재한다.
    * Native Method Library: C, C++로 작성된 라이브러리다.


### 가비지 컬렉션(Garbage Collection, GC)
* JVM의 힙 영역에서 불필요한 메모리를 정리해주는 역할입니다. 자바는 개발자가 직접 메모리를 해제하지 않기 때문에 그 역할을 가비지 컬렉션(Garbage Collection, GC)이 해당 역할을 수행합니다.
* GC의 종류 중 한 종류인 Serial GC로 설명을 하면 Minor GC와 Major GC로 구분되는데 Minor GC는 Young 영역 Major GC는 Old 영역에서 일어 납니다.
* GC가 실행되는 동작 방식에는 공통적으로 2단계로 나뉘어지는데 Stop The World와 Mark and Sweep입니다. Stop The World는 GC를 실행하기 위해서 JVM이 애플리케이션의 실행을 멈추는 작업으로 GC를 싱행하는 쓰레드를 제외한 모든 쓰레드가 중단됩니다. GC의 성능 개선을 위해서 튜닝을 한다고하면 보통 stop-the-world의 시간을 줄이는 작업을 하며 JVM에서도 이런 문제를 해결하기 위해서 다양한 실행 옵션을 제공합니다. Stop the World에서 모든 작업이 중단되면 GC는 스캔을 통해서 사용되고 있는 메모리를 식별하고 식별되지 않은 객체들을 메모리에서 제거하는 것을 Mark the Sweep라고 합니다.
* Minor GC는 1개의 Eden 영역과 2개의 Survivor 영역 총 3개의 영역으로 나뉘어집니다. 객체가 생성되면 Eden 영역에 할당되고 Eden 영역이 꽉 차면 Minor GC가 발생하면서 사용되지 않은 메모리는 해체되고 사용중인 객체는 Survivor 영역으로 옮겨집니다. 이 과정이 반복되다가 Survivor 영역이 가득차면 해당 영역에서 Minor GC가 일어나고 다음 Survivor 영역으로 이동시킵니다. 이러한 과정에서 하나의 Survivor 영역은 반드시 빈 상태로 유지합니다. 해당 과정을 반복해서 살아남은 객체는 Old 영역으로 이동됩니다.
![Minor GC](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FCyho2%2FbtqURvZRql6%2F4a7u6mMGofkpuURKQz0RT1%2Fimg.png)
    * Eden: 새로 생성된 객체가 할당되는 영역
    * Survivor: 최소 1번의 GC이상 살아남은 객체가 존재하는 영역
* Major GC는 Young 영역에서 객체들이 이동되서 메모리가 부족해지면 발생하는데 Old 영역은 Young 영역보다 크기가 크기 때문에 Minor GC보다 시간이 오래걸립니다.
![Garbage Collection](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdM4wqf%2FbtqUWs2lW8H%2FGvRECmsUIfZ2jhDoKhSCD0%2Fimg.png)


### 오버로딩(Overloading)과 오버라이딩(Overriding)
* 오버로딩은 같은 클래스 내에서 메소드의 이름이 중복되더라도 매개변수의 타입또는 개수가 다르면 중복된 이름을 사용해서 정의할 수 있습니다. 또한 컴파일 타임 다형성이기에 정적 다형성입니다.
```
public class Overloading {
    
    public void overloading() {}

    public void overloading(String overloading) {}

    public void overloading(String overloading1, String overloading2) {}

    public void overloading(int overloading) {}
}
```
* 오버라이딩은 상위 클래스의 메소드를 재정의해서 사용하는 것을 의미합니다. 또한 런타임 다형성이기에 동적 다형성입니다.
```
class Parent {

    public void overriding() {}
}

class Child extends Parent {

    @Override
    public void overriding() {}
}
```

### 어노테이션(Annotation)
* 소스코드에 추가해서 사용할 수 있는 메타 데이터의 일종입니다. 주석처럼 코드에 달아 클래스에 특별한 의미를 부여하거나 기능을 주입할 수 있습니다.
* 빌트인 어노테이션과, 메타 어노테이션이 존재하며 빌트인 어노테이션의 경우 자바에서 제공하는 어노테이션을 뜻하며 메타 어노테이션의 경우 커스텀해서 사용할 어노테이션을 만들 때 사용됩니다.

### SOLID 원칙(OOP의 5가지 원칙)
* 단일 책임 원칙(SRP)은 한 클래스의 하나의 책임만 가지는 것을 의미하며 목적과 취지에 맞는 속성과 메소드로 구성해야 합니다.
* 개방-폐쇠 원칙(OCP)은 확장에는 열려 있어야 하지만 변경에는 닫혀 있는 것을 의미하며 다형성의 성질을 가진 인터페이스를 사용해서 직접적인 연동과 변경을 피하고 메소드를 재정의 해서 사용해야 합니다.
* 리스코프 치환 원칙(LSP)은 하위 클래스의 인스턴스는 상위형 객체 참조 변수에 대입해 상위 클래스의 인스턴스 역할을 하는데 논리적으로 문제가 없어야 합니다. 여기서 상위와 하위를 나누는 것은 계층적인 구조가 아닌 분류를 의미합니다. 즉 상속과 확장은 동일합니다.
```
아버지와 아들 // 아들은 아버지의 한 종류 X
포유류와 고래 // 고래는 포유류의 한 종류 O
```
* 인터페이스 분리 원칙(ISP)은 클라이언트는 자신이 사용하지 않는 메소드에 의존 관계를 맺으면 안되는 원칙입니다. 상관에 관련 있는 메소드만 제공하라는 의미이며 비대한 인터페이스보단 작고 구체적인 인터페이스로 분리해야 합니다.
* 의존관계 역전 원칙(DIP)은 추상적인 것은 자신보다 구체적인 것에 의존하지 않고, 변화하기 쉬운 것에 의존해서는 안된다는 원칙입니다. 자신보다 변하기 쉬운 것에 의존하면 안되며 구현클래스에 의존하는 것이 아닌 다형성의 특징을 가진 인터페이스에 의존을 해서 변화에 영향 받지 않도록 의존 관계를 역전시켜야 합니다.

### OOP의 4가지 특성
* 캡슐화는 데이터와 코드의 형태를 외부로부터 알 수 없게 하고, 데이터의 구조와 역할, 기능을 하나의 캡슐 형태로 만드는 방법입니다.
* 추상화는 클래스들의 공통적인 특성(변수, 메소드)들을 묶어 표현하는 것 입니다.
* 상속화는 부모 클래스에 정의된 변수 및 메서드를 자식 클래스에서 상속받아 사용하는 것 입니다.
* 다형화는 다양한 형태로 표현이 가능한 구조입니다.

### 정적(static)
* static은 클래스 멤버라고 하며, 클래스 로더가 클래스를 로딩해서 메소드 메모리 영역에 적재할 때 클래스별로 관리됩니다.
* Heap 영역이 아닌 Staic 영역에 할당되기에 GC가 관리하는 영역 밖이며 모든 객체가 공유해서 하나의 멤버를 어디서든 참조할 수 있지만 프로그램 종료 시까지 메모리에 할당된 채로 존재하기에 남발하면 성능에 악영향을 미칩니다. 하지만 특정한 상황에서는 시스템 성능을 높일 수 있습니다.

### 접근 제한자
* private, default, protected, public이 있습니다. private은 해당 클래스 내에서만 접근 가능하고, default는 해당 패키지, protected는 상속한 클래스, public은 전체 영역에서 접근 가능합니다.
* 접근 제어자를 사용하는 이유는 객체의 로직인 변수나 메소드를 보호하기 위해서 외부의 접근을 허용하거나 차단하는 보안목적으로 사용됩니다. 결국 접근 제한자는 캡슐화에 해당합니다.

### 인터페이스
* 클래스들이 필수로 구현해야 하는 추상 자료형입니다. 확장에는 열려있고 변경에는 닫혀있는 객체 간 결합도를 낮춘 유연한 방식의 개발이 가능합니다. 인터페이스는 다형성의 특징을 가지고 있습니다.

### 다형성
* 하나의 객체나 메소드가 여러가지 다른 형태를 가질 수 있는 것을 말합니다. 오버로딩과 오버라이딩 그리고 상속받은 객체의 참조변수 형변환 등이 존재합니다.

## Spring



## DB



## CS


