## 아톰 16. 객체는 모든 곳에 존재한다.

> 객체는 코틀린을 비롯한 수많은 언어의 기초이며 객체는 프로퍼티(val, var)를 사용해 데이터를 저장하고, 함수를 사용해 이런 데이터에 대한 연산을 수행한다.

### 객체 관련 용어
* 클래스
  * 새로운 데이터 타입의 기초가 될 프로퍼티와 함수를 정의한다.
  * 사용자 정의 타입이라고 부르기도 한다.
* 멤버
  * 클래스에 속한 프로퍼티나 함수를 말한다.
* 멤버 함수
  * 함수 안에 정의되며 특정 클래스에 속한 객체가 있어야만 사용될 수 있는 함수를 말한다.
* 객체 생성
  * 클래스에 해당하는 val이나 var 값을 만드는 과정이다.
  * 클래스의 인스턴스를 생성한다고 말하기도 한다.

### 코틀린에서 지원하는 여러 클래스
* IntRange
  * 범위를 가리키는 순열을 만든다.
  * sum()과 같은 멤버 함수를 지원한다.
  ```
  fun main() {
    val r = IntRange(0, 10)
    println(r.sum()) // sum 함수를 통해서 합계를 구한다.
  }
  // 출력
  55
  ```
* String
  * 문자의 순서를 거꾸로 뒤집는 reversed()
  * 모든 문자를 소문자로 변경하는 toLowerCase()
  ```
  fun main() {
    val s = "AbcD"
    println(s.reversed())
    println(s.toLowerCase())
  }
  // 출력
  DcbA
  abcd
  ```

> 클래스를 잘 정의하면 프로그래머가 이해하기 쉬운 코드를 작성 할 수 있고, 이런 코드는 가독성이 높아 읽기도 쉽다.

## 아톰 17. 클래스 만들기
> IntRange, String처럼 미리 정의된 타입이 아닌 직접 원하는 객체의 타입을 정의할 수도 있다.

### 클래스
* 객체는 가진 문제를 해결하는 방법 중 일부분이며 문제를 해결할 때 필요한 개념을 표현하는 객체를 생각하는 것부터 시작해야하는데 대략적인 해법으로 문제에서 물건을 찾아내고, 해법에서 찾아낸 물건을 객체로 표현한다.
* class 라는 키워드를 사용해 새로운 유형의 객체를 만든다.
* class 키워드 다음에 클래스 이름으로 쓸 식별자를 넣는다.
* 클래스 이름은 반드시 글자(A-Z, 대소문자, 한글 등 각국 언어를 표기하는 문자)로 시작해야하며, 두 번째 자리부터는 숫자나 밑줄을 포함할 수 있다.
* 관례적으로 첫 번째 글자는 대문자로 표기하며, val, var로 쓰이는 이름의 첫 번째 글자는 소문자로 표기한다.
  ```
  // class들을 정의한다.
  class Giraffe
  class Bear
  class Hippo

  fun main() {
    // 객체를 만든다, 정의한 클래스에 속하는 객체(인스턴스) 네 개 생성
    val g1 = Giraffe()
    val g2 = Giraffe()
    val b = Bear()
    val h = Hippo()
    // Giraffe(), Bear()처럼 클래스이름()을 호출해 만든 객체는 각각 고유한 정체성(메모리 주소)을 가진다.
    println(g1)
    println(g2)
    println(h)
    println(b)
  }
  // 출력, @앞은 클래스 이름이며 뒤는 객체가 존재하는 컴퓨터 메모리의 위치로 16진 표기법으로 표현한다.
  Giraffe@34c45dca
  Giraffe@52cc8049
  Hippo@5b6f7412
  Bear@27973e9b
  ```
  * 복잡한 클래스를 정의할 때는 중괄호({})를 사용해서 클래스의 특성이나 행동 양식을 포함하는 `클래스 본문`을 정의한다.
  * 클래스 본문 안에 정의된 함수는 해당 클래스에 속하며 이런 함수를 클래스의 `멤버 함수`라고 부르지만 자바와 같은 객체 지향 언어에서는 멤버 함수를 `메서드`라고 부른다.
  * 코틀린 설계자들은 메서드라는 용어를 채택하지 않고, 함수라는 표현을 언어 전반에서 사용하며 코틀린의 함수적인 특성을 강조하기 위해서이다.

### 코틀린의 함수 구분
* 멤버 함수
  * 클래스에 속한 함수
* 최상위 함수
  * 클래스에 속하지 않는 함수


### 멤버 함수
* 멤버 함수를 호출할 때는 객체 이름 다음에 .(점/마침표)과 함수 이름, 파라미터 목록을 나열한다.
  ```
  class Cat {
    fun meow() = "mrrrow!"
  }

  fun main() {
    val cat = Cat()
    // 'cat'에 대해 'meow()'를 호출한다.
    val m1 = cat.meow()
    println(m1)
  }
  // 출력
  mrrrow!
  ```
  * 멤버 함수는 어떤 클래스에 속한 특정 인스턴스에 대해 작용하며 함수가 호출되는 동안 함수 내부에서는 지정한 객체의 다른 멤버에 접근할 수 없다.
  * 멤버 함수 호출할 때 객체를 가리키는 참조를 함수에 전달해서 객체를 추적하며 멤버 함수 안에서 this라는 이름으로 참조에 접근할 수 있다.
  * 멤버 함수는 클래스에 속한 다른 요소들을 멤버 이름만 사용하는 방법으로 접근할 수 있다.

> 불필요한데도 this를 명시한 경우를 볼 수 있는데 코드를 읽는 사람을 왜 코드를 이런 식으로 작성했는지 괜히 한번 고민하느라 시간을 보내거나 헷갈리 수 있기 때문에 불필요한 this는 사용하지 않는 것이 좋다.

## 아톰 18. 프로퍼티
> 프로퍼티는 클래스에 속한 var나 val이다.

### 프로퍼티
* 프로퍼티를 정의함으로써 클래스 안에서 `상태를 유지`하는데 함수 작성 대신 클래스를 작성하는 주된 이유가 상태 유지다.
* var 프로퍼티는 재대입이 가능하지만 val 프로퍼티는 그렇지 않고, 각각의 객체는 프로퍼티를 저장할 자신만의 공간을 할당받는다.
* var, val은 클래스의 `일부분`이 되며 `점 표기법`을 사용해야 프로퍼티 값에 접근할 수 있으며, 멤버함수는 점 표기법을 사용하지 않고 자신이 속한 객체의 프로퍼티에 접근할 수 있다.
* 클래스 밖에서는 멤버 함수와 프로퍼티 모두 한정시켜야하며, 최상위 프로퍼티도 정의할 수 있는데 변경할 수 없으므로 최상위 수준에 val을 정의해도 안전하지만 프로그램이 복잡해질수록 공유된 가변 상태에 대해서 추론하기 어려워지기 때문에 `가변(var) 최상위 프로퍼티를 선언하는 일은 안티패턴`으로 간주한다.
* 객체를 선언해도 그 객체를 가리키는 것은 `참조`이며 var는 객체의 참조가 가능하며 val는 객체가 아니라 참조를 제한한다.

> 객체에서 가변성은 내부 상태를 바꿀 수 있다는 뜻이다.

## 아톰 19. 생성자
> 생성자에 정보를 전달해 새 객체를 초기화할 수 있다.

### 생성자
* 각각의 객체는 독립된 세계지만 객체의 모임이므로 개별 객체를 제대로 초기화하면 초기화 문제 중 상당수를 해결할 수 있다.
* 코틀린은 객체를 제대로 최기화할 수 있는 메커니즘을 생성자를 통해서 제공한다.
    ```
    class Wombat

    fun main() {
        val wombat = Wombat()
    }
    ```
* 코틀린에서는 new가 불필요한 중복이기 때문에 제외한다.
* 생성자에게 정보를 전달할 때는 파라미터 목록을 사용한다.
  1. 생성자 밖에서는 name에 접근할 수 없기 때문에 에러가 발생한다.
  ```
  class Alien(name: String) {
    val greeting = "Poor $name!"
  }

  fun main() {
    val alien = Alien("Mr. Meeseeks)
    println(alien.greeting)
    // alien.name // Error // [1]
  }
  // 출력
  Poor Mr. Meeseeks
  ```
* 클래스 본문 밖에서도 생성자 파라미터에 접근할 수 있게 하려면 파라미터 목록에 var, val를 지정해야한다.
* pintln()은 문자열 대신 객체를 전달받은 경우 객체의 toString()을 호출한 결과를 출력한다.
  * 클래스에 직접 toString()을 정의하지 않으면 디폴트 toString()이 호출된다.
  * pringln(message: Any?)라는 모든 타입의 값(널 포함)을 받을 수 있는 함수가 오버로드되어 있기 때문에 이런 함수 호출이 가능하다.
* toString()은 그다지 유용하지 않다, 클래스 이름과 물리적 주소를 출력할 뿐이기 때문에 커스텀을 오버라이드를 통해서 재정의해 사용할 수 있다.

> 객체이 내용을 알아보기 쉬운 형태로 표시하는 toString() 함수는 프로그램 오류를 찾고 수정할 때 유용하며 디버깅 과정을 더 쉽게 수행할 수 있도록 IDE는 프로그램의 실행을 한단계씩 살펴보면서 내부를 들여보다볼 수 있는 디버그를 제공한다.

## 아톰 20. 가시성 제한하기
> 작성한 코드를 며칠 또는 몇 주 동안 보지 않다가 다시 살펴보면 그 코드를 작성하는 더 좋은 방법이 보일 수도 있다.

### 리팩터링
* 리팩터링은 코드를 고쳐 써서 더 읽기 좋고 이해하기 쉽게 만들어서 더 유지 보수하기 쉽게 만드는 것이다.
* 코드를 변경하고 개선하려는 욕구에는 긴장감이 감도는데 소비자(클라이언트)는 작성한 코드가 안정적이고 그대롷 유지되길 바라고 개발자는 코드를 변경하고 싶어한다.
* 라이브러리의 긴장이 큰 문제가 된다, 라이브러리 버전이 바뀌어도 새 버전으로 코드를 다시 작성하고 싶어하지 않는데, 라이브러리 제작자는 자신이 변경한 내용이 클라이언트 코드에 영향을 끼치지 않는다는 확신을 바탕으로 자유롭게 수정하고 개선할 수 있어야 한다.

### 소프트웨어 설계 시 고려해야할 내용
* 변화해야 하는 요소와 동일하게 유지되어야 하는 요소를 분리하라

### 가시성을 제한하는 접근 제한자
* 라이브러리 개발자는 public, private, protected, internal 등의 변경자를 사용해 클라이언트 프로그래머가 어떤 부분에 접근할 수 있고 어떤 부분에 접근할 수 없는지를 결정한다.
* 클라이언트 프로그래머는 public 정의에 접근할 수 있고, public으로 선언된 정의를 변경하면 클라이언트 코드에 직접적으로 영향을 끼치지만 지정하지 않으면 자동으로 public이 되기 때문에 불필요한 중복일 뿐이지만 의도를 명확히 드러내기 위한 경우가 가끔 있다.
* private이 붙은 클래스 최상위 함수, 최상위 프로퍼티는 정의가 들어있는 파일 내부에서만 접근이 가능하다, private 정의는 숨겨져 있으며 같은 클래스에 속한 다른 멤버들만 이 정의에 접근할 수 있으며 private 정의를 변경하거나 삭제하더라도 클라이언트 프로그래머에게 직접적인 영향이 없다.

### private 키워드
* private 키워드는 같은 클래스에 속한 멤버 외에는 아무도 이 멤버에 접근할 수 없다는 뜻이다.
* 다른 클래스와의 협력을 외부로부터 단절시키기 때문에 다른 클래스에 영향을 끼칠 걱정없이 코드를 변경할 수 있어서 라이브러리 설계자는 필요한 함수와 클래스만 외부로 노출시키고 가능한 한 많은 요소를 private로 선언한다.
* 도우미 함수로 쓰이는 멤버 함수를 private으로 선언하면 외부에서 실수로 쓰는 경우를 방지할 수 있고 내부에서만 참조해서 함수를 마음대로 변경하거나 제거하기 편리하다.
* 클래스 내의 프로퍼티도 노출시켜야 하는 경우를 제외하고 private으로 만들어야 한다.

### 에일리어싱(aliasing)
* 한 객체에 대해 참조를 여러 개 유지하는 경우를 에일리어싱이라고 한다.

### 모듈
* 모듈은 코드 기반상에서 논리적으로 독립적인 각 부분을 뜻한다.
* 실제 사용하는 프로그램은 훨씬 더 큰데 큰 프로그램을 하나 이상의 모듈로 분리하면 유용하다.
* internal 정의는 그 정의가 포함된 모듈 내부에서만 접근할 수 있으며 internal은 private과 public 사이에 위치한다.
* 모듈은 고수준 개념이다.

### 아톰 21. 패키지
> 프로그래밍에서 근본적인 원칙 DRY, 반복하지 말라(Don't Repeat Yourself)는 의미를 지닌 약자로 나타낸다.

### 코드의 반복
* 코드에서 같은 내용이 여러 번 반복되면 수정 또는 개선될 때마다 더 많은 유지 보수가 필요해지며, 반복될 때마다 실수할 가능성이 커진다.
* import 키워드를 써서 다른 파일에 정의돈 코드를 재사용할 수 있으며 import를 쓰는 방법 중 하나는 클래스, 함수, 프로퍼티 이름을 지정하는 것이다.

### 패키지
* 패키지는 연관 있는 코드를 모아둔 것이다.
* 각 패키지는 특정 문제를 풀기 위해 고안되며, 여러 함수와 클래스를 포함하곤 한다.
* kotiln.math 라이브러리로부터 수학 상수와 함수를 임포트할 수 있다.
* as 키워드를 사용하면 임포트하면서 이름을 변경할 수 있다.
    ```
    import kotlin.math.PI as circleRatio
    import kotlin.math.cos as cosine
    ```
* 가독성은 떨어지지만 출저를 명확하게 알 수 있도록 코드 상에서 임포트 패키지 경로를 전부 쓸수 있다.
    ```
    fun main() {
        kotiln.math.PI
        kotiln.math.cos(kotiln.math.PI)
    }
    ```
* 패키지의 모든 내용을 임포트할 땐 별표(*)를 사용한다.
    ```
    import kotiln.math.*
    ```
* 코드를 재사용하려면 package 키워드를 사용해 패키지를 만들어야 한다, package 문은 파일에서 주석이 아닌 코드의 가장 앞부분에 위치해야 하며 뒤에는 만들 패키지의 이름이 오며 관례적으로 소문자만 사용한다.
* 파일 이름이 항상 클래스 이름과 같아야하는 자바와 달리 코틀린에선 소스 코드 파일 이름으로 아무 이름이나 붙여도 좋고, 패키지 이름 또한 아무 이름이나 선택할 수 있지만 패키지 이름과 파일이 들어있는 디렉터리 경로를 똑같이 하는 게 좋은 스타일로 여긴다.

## 아톰 22. 테스트
> 프로그램을 빠르게 개발하려면 지속적인 테스트가 필수다.

### 테스트
* 코드가 망가졌을 때 발견하지 못하면 문제의 원인을 찾기 위해 오랜 시간을 들여다봐야 하지만 테스트를 통해서 즉시 발견할 수 있다.
* 코드의 올바름을 검증할 때 println()을 사용하는 것은 매번 출력을 살펴보고 확인해야하기 떄문에 부실한 접근 방법이다.
* 사람의 눈으로 결과가 이상한지 확인하는 방식은 신뢰성이 떨어지며 테스트 사용의 이점을 확인하면 프로그래밍 과정에 테스트를 도입해야겠다는 생각이 들게 된다.

### 테스트 시스템
* JUnit
  * 자바에서 가장 널리 쓰이는 테스트 프레임워크이며, 코틀린에서도 유용하게 쓸 수 있다.
* 코테스트(Kotest)
  * 코틀린 전용으로 설계됐으며, 코틀린 언어의 여러 기능을 살려서 작성됐다.
* 스펙(Spek) 프레임워크
  * 명세 테스트(specification test)라는 다른 형태의 테스트를 제공한다.

### 프로그램의 일부분인 테스트
* 테스트를 작성하면 원하는 결과를 확실히 얻을 수 있기 때문에 `테스트는 소프트웨어 개발 과정에 포함되어 있어야 가장 효과적`이다.
* 구현 전에 테스트를 작성하는 걸 권장하는 사람도 많으며 코드를 작성하기 전에 테스트를 먼저 작성해 실패시킨 후 나중에 테스트를 통과하도록 코드를 작성하는 기법을 `테스트 주도 개발(Test Driven Development)`
* 테스트할 수 있는 코드를 작성하면 코드를 작성하는 방식이 `결과를 어떻게 테스트하지?`란 질문과 테스트를 위해 함수가 무언가를 반환하도록 한다.
* 파라미터를 입력 받아서 결괏값만 만들어내고 다른 일을 하지 않는 함수를 사용하면 설계가 자연스럽게 더 나아진다.

## 아톰 23. 예외
### 예외
* 예외 상황은 현재 함수, 영역의 진행을 막으며 문제가 발생하면 계속 처리를 진행할 수 없게되서 처리를 중단하고 적절한 조치를 취할 수 있는 다른 맥락으로 문제를 넘겨야 한다.
* 예외적인 상황과 일반적인 문제(문제를 처리하는데 충분한 정보가 현재 맥락에 존재하는 경우)를 구분하는 것이 중요한데 예외 상황에선 처리할 수 없기 때문에 문제를 바깥쪽 맥락으로 내보내는 `예외를 던지면` 발생한다.
* 예외는 `오류가 발생한 지점에서 던져지는 객체`이다.
* String을 Int로 변환하는 toInt()를 하면 아래와 같이 발생된다.
  * [1]에서 예외가 발생한다.
  * 예외가 던져지면 실행 경로가 중단되고 예외 객체는 현재 문맥을 벗어난다.
  * 코틀린은 코드를 수정해야 한다는 예외에 대한 정보를 표시하고 프로그램을 종료시킨다.
  * 예외를 잡아내지 않으면 프로그램이 중단되면서 상세 정보가 있는 스택 트레이스가 출력된다.
  * 스택 트레이스는 예외가 발생한 파일, 위치와 같은 상세 정보를 표시한다.
    ```
    fun erroneousCode() {
    var i = "1$".toInt() // [1]
    }
    fun main() {
        erroneousCode()
    }
    // 출력
    Exception in thread "main" java.lang.NumberFormatException: For input string: "1$"
    at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:67)
    at java.base/java.lang.Integer.parseInt(Integer.java:668)
    at java.base/java.lang.Integer.parseInt(Integer.java:786)
    at com.example.kotilnbasic/com.example.kotilnbasic.MainTestKt.erroneousCode(MainTest.kt:86)
    at com.example.kotilnbasic/com.example.kotilnbasic.MainTestKt.main(MainTest.kt:89)
    at com.example.kotilnbasic/com.example.kotilnbasic.MainTestKt.main(MainTest.kt)
    ```
  * 코틀린은 변환할 수 없는 문자열이 들어있으면 Null을 반환하는 String.toIntOrNull() 함수를 제공한다.
    ```
    fun main() {
        "1$".toIntOrNull()
    }
    ```
* 자세한 오류 메세지가 포함된 구체적인 예외를 던지면 유용하다.
    ```
    fun averageIncome(income: Int, months: Int) = 
        if (months == 0)
            throw IllegalArgumentException("Months can't be zero")
        else
            income / months
        
    fun main() {
        averageIncome(5000, 0)
    }
    ```

> 예외의 목표는 향후 애플리케이션을 더 쉽게 지원할 수 있도록 가장 유용한 메시지를 제공하는 것이다.

## 아톰 24. 리스트
### 리스트
* List는 컨테이너, 다른 객체를 담는 객체에 속하며 컨테이너는 컬렉션이라고 부른다.
* List는 표준 코틀린 패키지에 들어가 있어서 import할 필요가 없다.
  * sorted(), reverse()는 새로운 List을 돌려준다.
  * 함수 이름을 sort라고 붙이면 원본 List를 직접(인플레이스) 바꾼다.
    ```
    fun main() {
        val ints = listOf(99, 3, 5, 7, 11, 13)
        val doubles = listOf(1.1, 2.2, 3.3, 4.4)
        val strings = listOf("Twas", "Brillig", "And", "Slithy", "Toves")
        strings.sorted() // 정렬
        strings.reverse() // 순서 역전
    }
    ```

### 파라미터화한 타입
* 타입 추론은 코드를 더 깜글하고 읽기 쉽게 만들어주기 때문에 사용하는 것이 좋은 습관이다.
* 코틀린이 타입 추론을 하지 못할 때는 직접 타입을 명시해야한다.
  * 코틀린은 초기화 값을 사용해 타입을 추론한다.
  * 명시적인 타입을 써서 정의한 경우 홑화살괄호(<>)는 타입 파라미터를 표시한다.
    ```
    fun main() {
        // 타입을 추론
        val numbers = listOf(1, 2, 3)
        val strings = listOf("one", "two", "three")
        // 타입을 명시
        val numbers2: List<Int> = listOf(1, 2, 3)
        val strings2: List<String> = listOf("one", "two", "three")
    }
    ```
* 반환값의 타입이 타입 파라미터를 포함할 수도 있다.
  * 단순히 List라고 적으면 오류를 표시하기 때문에 타입 파라미터를 반드시 명시해야 한다.
  * 반환 타입을 명시해 의도를 분명히 적으면, 코틀린은 함수가 반환하는 값의 타입이 의도와 같게 되도록 강제해준다.
    ```
    // 반환 타입을 추론
    fun inferred(p: Char, q: Char) = listOf(p, q)
    // 반환 타입을 명시
    fun explicit(p: Char, q: Char): List<Char> = listOf(p, q)
    ```

### 읽기 전용과 가변 List
* 가변 List는 필요하다고 명시적으로 표시해야만 얻을 수 있다.
* listOf()는 읽기 전용 리스트를 만들어내고 mutableListOf()는 변경할 수 있는 MutableList를 반환한다.
    ```
    fun main() {
        val list = listOf(1, 2, 3)
        val mutalbeList = mutableListOf<Int>()
        mutalbeList.add(1)
        mutalbeList.addAll(listOf(2, 3))
        mutalbeList += 4
        mutalbeList += listOf(5, 6)
    }
    ```
* MutableList도 List로 취급할 수 있지만 이 경우 내용을 변경할 수 없으며 그 반대의 경우 List를 MutableList로 취급할 수 없다.
  * 리턴 시 읽기 전용 List로 바뀐다.
  * 내부 구현을 MutableList로 유지하면서 참조를 유지했다가 가변 Lis에 대한 참조를 통해 원소를 변경하면 내부를 변경할 수 있다.
    ```
    fun getList(): List<Int> {
        return mutableListOf(1, 2 ,3)
    }

    fun main() {
        val first = getList()
        val second = first

        first += 2 // second는 변경될 수 없지만 first가 변경되면 참조기 때문에 변경된다.
    }
    ```

### +=의 비밀
* += 연산자를 쓰면 불변 리스트가 마치 가변 리스트인 것처럼 보인다.
  * 불변성을 위배하는 것처럼 보이지만 아니다.
  * list가 var라서 이런 코드가 가능하다.
    ```
    fun main() {
        var list = listOf('X') // 불변 리스트
        list += 'Y' // 가변 리스트처럼 보임
    }
    ```
* += 동작은 다른 컬렉션에서도 마찬가지로 이로 인해 발생할 수 있는 혼동을 방지하는 것도 식별자를 정의할 때 `var보다는 val을 써야하는 이유`가 된다.

## 아톰 25. 가변 인자 목록
### 가변 인자 목록
* vararg는 가변 인자 목록(variable argument list)을 줄일 말로 vararg 키워드를 사용하면 listOf처럼 임의의 길이로 인자를 받을 수 있는 함수를 정의할 수 있다.
    ```
    fun v(s: String, vararg d: Double) {}

    fun main() {
        v("abc", 1.0, 2.0)
        v("def", 1.0, 2.0, 3.0, 4.0)
        v("ghi", 1.0, 2.0, 3.0, 4.0, 5.0, 6.0) 
    }
    ```
* vararg 키워드는 파라미터 목록에서 어떤 위치에 있든 선언할 수 있지만 마지막 파리미터로 선언하는 것이 편하며 vararg로 선언된 인자가 최대 하나만 있어야 한다.
* vararg를 통해서 임의의 개수만큼 인자를 전달할 수 있고, 모든 인자는 지정한 타입에 속한다.

### Array와 List
* Array와 List는 비슷해 보이지만 전혀 다르게 구현되는데 List는 일반적인 라이브러리 클래스지만 Array는 특별한 저수준 지원이 필요하며 자바같은 다른 언어와 호환되야 하는 코틀린의 요구사항에 의해 생겨난 타입이다.
* 일상적인 프로그래밍에서 간단한 시퀀스가 필요하면 List를 서드파티 API가 요구하거나 vararg을 다뤄야하는 경우에만 Array를 써야한다.
* vararg가 필요한 위치에 임의의 타입 Array를 넘길 수 있으며, Array를 만들기 위해서는 arrayOf()를 사용한다.
* Array를 인자 목록으로 변환하고 싶으면 스프레드 연산자(*)를 사용한다.
  ```
  fun main() {
    val array = intArrayOf(4, 5)
    intArrayOf(*array) // [1]
    intArrayOf(array) // [2] 컴파일 에러 발생
		}
  ```
* 스프레드 연산자는 vararg로 받은 파라미터를 다시 다른 vararg를 요구하는 함수에 전달할 때 특히 유용하다.
  ```
  fun first(vararg numbers: Int): String {
				var result = ""
    for (i in numbers) {
      result += "[$i]"
    }
    return result
  }
  
  fun second(vararg numbers: int) = first(*numbers)

  fun main() {
    second(7, 9, 32)
  }
  ```

### 명령줄 인자
* 명령줄에서 프로그램을 시작할 때 프로그램에서 원하는만큼 인자를 전달할 수 있으며 main() 함수에 미리 정해진 파라미터를 지정해야 한다.
  * 파라미터 이름은 전통적으로 args로 짓고(다른 이름도 상관X), 타입은 Array<String>(String으로 이뤄진 Array)이어야 한다.
  ```
  fun main(args: Array<String>) {
    for (a in args) {
        pringln(a)
    }
  }
  ```
* 인텔리제이를 사용하면 실행 설정을 변경해서 프로그램에 인자를 전달할 수 있다.
* kotilnc 컴파일러를 사용해 명령줄 프로그램을 생성할 수 있다.
  * kotilnc 미설치 시 코틀린 기본 사이트가서 프로그램 설치
  ```
  // 기본 실행
  kotilnc MainArgs.kt
  // 인자 주입 실행
  kotilnc MainArg.kt hamster 42 3.14159
  ```
* 코틀린이 제공하는 변환 함수를 사용해서 String 파라미터를 원하는 타입으로 변경할 수 있다.
  * [1]의 경우 충분한 인자가 주어지지 않으면 프로그램 종료
  * [2], [3]의 경우 변환할 수 없는 값이 주어진 경우 런타임 오류가 발생한다.
  ```
  fun main(args: Array<String>) {
    if (args.size < 3) return [1]
    val first = args[0]
    val second = args[1].toInt() [2]
    val third = args[2].toFloat() [3]
    pringln("$first $second $third")
  }
  ```
* main

## 아톰 26. 집합
### Set
* Set은 각각의 값이 오직 하나만 존재할 수 있는 컬렉션이다.
* 일반적인 Set 연산은 in이나 contains()를 사용해 원소인지 검사하는 것이다.
  ```
  fun main() {
    val intSet = setOf(1, 1, 2, 3, 9, 9, 4)
    // [1] 중복이 존재하지 않는다.
    // 출력 [1, 2, 3, 9, 4]
    println(intSet)
    // [2] 원소의 순서는 중요하지 않다.
    // 출력 true
    println(setOf(1, 1, 2, 3, 9, 9, 4) == setOf(9, 4, 3, 2, 1));
    // [3] in 키워드를 통한 원소인지 검사
    // 출력 true
    println(9 in intSet)
    // 출력 false
    println(99 in intSet)
    // [4] 이 집합이 다른 집합에 포함하는지 여부
    // 출력 true
    println(intSet.containsAll(setOf(1, 9, 2)))
    // [5] 합집합
    // 출력 [3, 4, 5, 6, 1, 2, 9] 
    println(setOf(3, 4, 5, 6).union(setOf(1, 2, 3, 4, 5, 6, 9)))
    // [6] 교집합
    // 출력 [1, 2]
    println(intSet intersect setOf(0, 1, 2, 7, 8))
    // [7] 차집합
    // 출력 [2, 3, 4]
    println(intSet subtract setOf(0, 1, 9, 10))
    println(intSet - setOf(0, 1, 9, 10))
  }
  ```
* List에서 중복을 제거하려면 Set으로 변환하면 된다.
  ```
  fun main() {
    val list = listOf(3, 3, 2, 1, 3)
    // 출력 [3, 2, 1]
    println(list.toSet())
    // 출력 [3, 2, 1]
    println(list.distinct())
    // 출력 [a, b, c]
    println("abbcc".toSet())
  }
  ```
* setOf는 읽기 전용 집합으로 불변이므로 가변이 필요한 경우 mutableSetOf()를 사용한다.
  ```
  fun main() {
    val mutableSet = mutableSetOf<Int>()
    mutableSet += 42
    mutableSet.add(42)
    // 출력 [42]
    println(mutableSet)
    mutableSet -= 42
    mutableSet.remove(42)
    // 출력 []
    println(mutableSet)
  }
  ```

## 아톰 27. 맵
### Map
* Map은 키와 값을 연결하고 키가 주어지면 그 키와 연결된 값을 찾아준다.
* 키-값 쌍을 mapOf()에 전달해 Map을 만들 수 있고 키와 값을 분리하려면 to를 사용한다.
  ```
  fun main() {
    // [1] Map 생성 및 키와 값 분리
    val constants = mapOf(
      "Pi" to 3.141,
      "e" to 2.718,
      "phi" to 1.618
    )
    // [2] 키에 해당하는 값을 찾는다.
    // 출력 2.718
    println(constants["e"])
    // [3] 키-값 쌍을 이터레이션한다.
    // 출력 Pi=3.141, e=2.718, phi=1.618,
    var s = ""
    for (entry in constants) {
        s += "${entry.key}=${entry.value}, "
    }
    println(s)
    // [4] 이터레이션을 하면서 키와 값을 분리한다.
    // 출력 Pi=3.141, e=2.718, phi=1.618,
    var s = ""
    for ((key, value) in constants) {
        s += "$key=$value, "
    }
    println(s)
  }
  ```
* 일반 Map은 읽기 전용으로 불변이므로 가변은 MutableMap을 사용한다.
  ```
  fun main() {
    val m = mutableMapOf(5 to "five", 6 to "six")
    // 출력 five
    println(m[5])
    m += 4 to "four"
    // 출력 {5=five, 6=six, 4=four}
    println(m)
  }
  ```
* mapOf(), mutableMapOf()는 원소가 전달된 순서를 유지해주지만 다른 Map 타입에서는 순서가 보장되지 않을 수 있다.
* 읽기 전용 Map은 상태 변경을 허용하지 않는다.
  * [1]은 키-값 쌍이 아니라 에러가 발생한다. 
  * [2]는 기존 Map의 상태 값을 변경하는 방법으로 불변이라서 실패한다.
  * [3]은 + 연산으로 기존 맵 원소와 더해진 원소를 포함하는 새 Map을 만들고 원래의 Map에는 영향을 미치지 않는다, 읽기 전용 Map에서 원소를 추가하는 유일한 방법은 새로운 Map을 만드는 것뿐이다.
  ```
  fun main() {
    val m = mapOf(5 to "five", 6 to "six")
    // [1] 실패
    m[5] = "five"
    // [2] 실패
    m += 4 to "four"
    // [3] 성공
    m + (4 to "four")
  }
  ```
* 주어진 키에 해당하는 원소가 없으면 null을 반환한다.
  * null이 될 수 없는 결과를 원하면 getValue()를 사용할 경우 NosuchElementException이 발생한다.
  * 일반적으로 `getOrDefault()가 null 반환이나 예외를 던지는 함수보다 나은 대안`이다.
* 클래스 인스턴스를 Map의 값으로 저장할 수도 있고, 클래스 인스턴스를 Map의 키로 사용할 수도 있다.
  ```
  class Contact(val name: String, val phone: String) {
      override fun toString(): String {
          return "Contact('$name, '$phone')"
      }
  }
  
  fun main() {
      val miffy = Contact("Miffy", "1-234-567890")
      val cleo = Contact("Cleo", "098-765-4321")
      val contacts = mapOf(miffy.phone to miffy, cleo.phone to cleo)
      // 출력 {1-234-567890=Contact('Miffy, '1-234-567890'),   098-765-4321=Contact('Cleo, '098-765-4321')}
      println(contacts)
  }
  ```
* Map은 간단하고 작은 데이터베이스와 비슷하며 키와 값을 연관(연결)시켜주기 때문에 연관 배열이라고 부르기도 한다.

> 완전한 기능을 갖춘 데이터베이스와 비교하면 맵의 기능은 아주 제한적이지만 Map은 매우 유용하며 데이터베이스에 비해서 훨씬 더 효율적이다.

## 아톰 28. 프로퍼티 접근자
### Property(프로퍼티)
* 프로퍼티 이름을 사용해 프로퍼티를 읽고 대입 연산자(=)를 사용해 가변 프로퍼티에 값을 대입한다.
* 코틀린은 `함수를 호출해 프로퍼티 읽기와 쓰기 연산을 수행`한다.
* 프로퍼티 접근자를 작성해서 프로퍼티 읽기와 쓰기 연상을 커스텀화하는 방법이 있다.
  * 프로퍼티 값을 얻기 위해 사용하는 접근자를 게터(getter)라고 하며 프로퍼티 정의 바로 다음에 get()을 정의하면 정의할 수 있다.
  * 가변 프로퍼티를 갱신하기 위해 사용하는 접근자는 세터(setter)라고 하며 프로퍼티 정의 바로 다음에 set()을 정의하면 정의할 수 있다.
  * 코틀린은 들여쓰기를 신경쓰지 않는다.
  ```
  class Default {
    var i: Int = 0
      get() {
        return field
      }
      set(value) {
        field = value
      }
  }

  fun main() {
    val d = Default()
    // 출력 0
    println(get())
    set(2)
    // 출력 2
    println(get())
  }
  ```
  * setter를 private하고 getter는 pulic으로 할수 있다.
  ```
  class Counter {
    val value: Int = 0
      private set
    fun inc() = value++
  }
  ```
  * 일반적으로 프로퍼티는 값을 필드에 저장하지만 필드가 없는 프로퍼티를 정의할 수도 있다.
  ```
  class Cage(private val maxCapacity: Int) {
    private val hamster = mutableListOf<Hamster>()
    val capacity: Int
      get() = maxCapacity - hamsters.size
    val full: Boolean
      get() = hamsters.size == maxCapacity
    
    fun put(hamster: Hamster): Boolean = 
      if (full)
        false
      else {
        hamster += hamster
        true
      }
    fun take(): Hamseter = hamsters.removeAt(0)
  }
  ```
  * 프로퍼티를 사용한 코드가 가독성이 좋지만 모든 함수를 프로퍼티로 변환하지는 말고 어떻게 읽히는지 살펴봐야한다.
  ```
  class Cage(private val maxCapacity: Int) {
    private val hamster = mutableListOf<Hamster>()
    fun capacity: Int = maxCapacity - hamsters.size
    fun full: Boolean = hamsters.size == maxCapacity
  }
  ```
  * 코틀린 스타일 가이드에선 계산 비용이 많이 들지 않고 객체 상태가 바뀌지 않는 한 같은 결과를 내놓는 함수의 경우 프로퍼티를 사용하는 편이 낫다고 안내한다.

> 프로퍼티 접근자는 프로퍼티에 대한 일종의 보호 수단을 제공하며, 많은 객체지향언어가 프로퍼티에 대한 접근을 제어하기 위해 물리적인 필드를 private으로 정의하는 방식에 의존한다.
> 프로퍼티 접근자를 사용하면 필드 접근과 같은 방식으로 쉽게 프로퍼티에 접근하도록 허용하면서 동시에 프로퍼티 접근을 제어하거나 변경할 수 있는 코드를 쉽게 추가할 수 있다.