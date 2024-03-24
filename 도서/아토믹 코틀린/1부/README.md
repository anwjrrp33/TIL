## 아톰 01. 소개
* 한 장에서 하나의 개념만을 소개하며 각 장을 더 작은 단위로 나누지 못하게 했다.
* 이 책의 각 장을 `아톰(atom)`이라고 부른다.

## 아톰 02. 왜 코틀린인가?
### 그루비(Groovy)
* 값이 없는 경우를 다루는 코틀린 연산자 ?.과 ?:은 그루비에서 처음 사용됐다.

### 가독성
* 코틀린 문법은 간결하며 대부분의 경우 준비 코드 없이 복잡한 아이디어를 표현할 수 있다.

### 도구
* 젯브레인즈가 만든 언어로 최상급의 도구 지원을 받을 수 있으며, 언어 기능이 도구 지원을 염두에 두고 설계됐다.

### 다중 패러다임
* 여러 프로그래밍 패러다임을 지원한다.
  * 명령형 프로그래밍
  * 함수형 프로그래밍
  * 객체지향 프로그래밍

### 다중 플랫폼
* 여러 타깃 플랫폼으로 컴파일할 수 있다.
  * JVM
  * 안드로이드
  * 자바스크립트
  * 네이티브 바이너리

### 코틀린의 두 가지 특징
1. 노력이 필요없는 자바 상호 운용성
   * 코틀린은 하위 호환될 필요없이 JVM에서 제대로 작동해야 할 필요만 있다.
   * 코틀린은 기존 자바 프로젝트에 코틀린을 통합할 수 있으며 작게 코틀린 기능을 작성해서 기존 자바 코드 사이에 끼워 넣을 수 있다.
2. 빈값 표현 방식
   * 존재하지 않는 단어의 뜻을 물어본다면 없는 단어의 의미를 마음대로 만들어내면 항상 결과를 돌려줄 수 있다.
   * 자바는 실행 시점에 오류를 발생시켜 초기화되지 않은 값에 대한 참조를 금지해서 초기화되지 않은 값을 발견할 수 있지만 프로그램에서 초기화되지 않은 값이 있는지 알아내는 유일한 방법이 프로그램을 실행해서 이런 오류가 발생하는지 살펴보는 것뿐이라 널 포인터 문제를 해결 해주지 못하고 자바에선 이런 유형의 버그가 넘쳐난다.
   * 프로그래머는 널 포인트 문제와 같은 오류를 찾기 위해 엄청난 시간을 낭비하고 있지만 코틀린 널 오류를 발생시킬 가능성이 있는 연산을 컴파일 시점에 프로그램이 실행되기도 전에 금지함으로써 이런 문제를 해결해서 자바의 널 오류를 최소화하거나 아예 없앨 수 있으며 코틀린을 채택해야 하는 가장 중요한 특징이다.

## 아톰 03. Hello, World!
### 주석
* // 한줄 짜리 주석
  * // 이후에 있는 모든 내용이 그 줄이 끝날 때까지 무시되며 다음 줄이 시작해야 코틀린은 다시 코드에 신경을 쓴다.
* /* 여러 줄 주석 */
  * 주석이 긴 경우에는 여러 줄 주석을 사용해서 /* 가 시작되고 */로 끝날 때까지 주석이 계속되며 줄이 바뀌는 부분을 새줄 문자라고 부른다.
  * */로 주석을 끝낸 다음에 새 주석을 덧붙여도 되지만 혼동하기 쉬워서 사용하는 경우는 별로 없다.

### 키워드(Keyword) 언어
* 키워드는 언어에 의해 예약되어 있고 특별한 의미를 지니는 단어를 뜻한다.
* fun은 function을 줄인 단어다.
* 함수란 함수 이름을 사용해 실행될 수 있는 코드 모음을 뜻한다.
* main()은 사실 특별한 의미를 가진 함수로 코틀린 프로그램의 진입점(entry point)을 뜻하며, 프로그램을 실행하면 main()이 자동으로 호출된다.
* 함수 이름 뒤에는 파라미터 목록(parameter list)이 온다.

### 세미클론
* 코틀린은 다른 언어와 달리 식 끝에 세미클론을 붙이지 않아도 된다.
* 한 줄 안에 여러 식을 넣고 싶을 때만 식과 식 사이에 세미클론 넣으면 되지만 이런 식의 코드는 권장하지 않는다.

> 주석은 코드만 보고 분명히 알 수 없는 정보를 덧붙이기 위해 쓰이며 단순히 코드의 내용을 반복 한다면 사람들은 짜증을 내며 주석을 무시하게 된다.
> 프로그래머가 코드를 바꾸면서 주석 내 용을 함께 바꾸는 것을 잊어버릴 때도 있기 떄문에 주석은 주의 깊게 사용해야 하며 코드에 서 어려운 부분을 자세히 설명하기 위해 사용하는 것이 좋다.

## 아톰 04. var 와 val
### 식별자
* 식별자(identifier)는 프로그램을 이루는 요소를 가리키기 위해 사용한다.
* 데이터를 가리키는 식별 자를 사용할 때는 가장 기본적으로 선택해야 하는 사항이 있다.
  * 프로그램을 실행하는 동안 변화
  * 단 한 번만 어떤 값을 지정하면 되는지 여부

### 변경 가능성 제어를 위한 두 가지 키워드
* var
  * 변할 수 있는 수(variable)의 약자로, 내용을 재대입할 수 있다.
  * `var 식별자 = 초기화`
  * 시작은 유니코드 문자 또는 밑줄
  * 두 번째 글자부터는 유니코드 문자, 밑줄, 숫자 모두 사용 가능
  ```
  // var 정의
  fun main() {
    var whole = 11
    var fractional = 1.4
    var words = "Twas Brillig"
    println(whole)
    printin(fractional)
    println(words)
  }
  // 출력
  11
  1.4
  Twas Brillig
  ```
  * var에 저장된 값은 달라질 수 있고 var 변수를 가변(mmable)이다.
  ```
  // var 가변
  fun main() {
    var sum = 1 
    sum = sum + 2 
    sum += 3 
    println(sum)
  // 출력
  6
  ```
* val
  * 값(value)의 약자로，식별자의 값을 단 한 번만 초기화할 수 있고 일단 값을 초기화하고나면 내용을 변경할 수 없다.
  * `val 식별자 = 초기화`
  * val은 값을 뜻하며, 값이란 변할 수 없는 것을 가리키는데 val 변수는 불변(immable)이다.
  ```
  // val 선언
  fun main() {
    val whole = 11
    // whole = 15 // 오류 발생! 
    val fractional = 1.4
    val words = "Twas Brillig" 
    println(whole) 
    println(fractional) 
    println(words)
  }
  // 출력
  11
  1.4
  Twas Brillig
  ```
  * val을 초기화하고 나면 재대입할 수 없는데 다른 값을 대입하려고 시도하면 코틀린이 `val을 재대입할 수 없음(val cannot be reassigned)`이라고 불평한다.

> 식별자에 이름을 불일 때 서술적인 이름을 붙이면 코드를 이해하기도 쉽고 주석을 추가 할 필요성도 줄어든다.

### var과 val의 장단점
* var는 프로그램이 실행될 때 변경되어야만 하는 값을 표현할 때 유용하며 `프로그램이 실행되 면서 변경되어야 한다`라는 명제는 자주 있음직한 요구 조건으로 보이지만, 실전에서는 피해갈 수 있다.
* 일반적으로 val만 사용하면 프로그램을 확장하고 유지 보수하기가 더 쉬워지지만, 문제를 해결하기에 너무 복잡해지는 경우도 존재한다.
* 코틀린에서는 var도 허용해 유연성을 제공한다.

> val과 시간을 보내다 보면, 프로그램에서 var가 필요한 경우가 거의 없다는 사실과 var를 사용하지 않으면 프로그램이 더 안전해지고 신뢰성도 높아진다는 사실을 알게 된다.

## 아톰 05. 데이터 타입
### 타입
* 코틀린에서는 소수를 Double이라 부르고, 정수를 Int라 부른다.
* 타입(type, 데이터 타입)은 대상 데이터를 어떤 식으로 사용할 지를 코틀린에게 말한다.
* 타입은 어떤 식이 취할 수 있는 값의 집합을 제공하는데 데이터에 대해 적용할 수 있는 연산, 데이터의 의미, 타입에 속한 값을 (컴퓨터 메모리에) 저장하는 방식을 정의한다.
```
// 숫자 + 문자 덧셈
fun main() { 
  println("Sally" + 5.9)
}
// 출력
Sally5.9
```
* 타입을 아래와 같이 조합하면 코틀린이 이해하지 못해서 오류를 표시한다.
```
// 숫자 + 문자 곱셈
fun main() { 
  println("Sally" * 5.9) // 에러 발생
}
```
* 장황하게 타입을 지정할 수 있다.
  * `var 식별자: 타입 = 초기화`
* val, var 키워드로 시작하고 뒤에 식별자, 콜론, 타입, =, 초기화가 온다.
  ```
    // 코틀린 자동 타입 추론
    val n = 1 // Int
    var p = 1.2 // Double
    // 타입을 명시적으로 지정
    val n: Int = 1
    var p: Double = 1.2
  ```

### 코틀린의 기본 타입
1. 소수점 이하 부분이 없는 Int 데이터 타입은 정수(integer)
2. 소수가 있는 데이터 타입은 실수(Double)
3. 특별한 값 true와 false만 저장할 수 있는 Boolean 데이터 타입
4. 큰 따옴표로 둘러 싼 문자로 이뤄진 시퀀스를 저장할 수 있는 String 데이터 타입
5. 한 문자만 담을 수 있는 Char 데이터 타입
6. 여러 줄에 걸친 문자열을 만들거나 특수 문자가 들어간 문자열을 만들어야 하는 경우 큰 따옴표 세개(""")로 감싸며 삼중 큰따옴표 String, 로(raw) String 데이터 타입
```
fun main() {
  val whole: Int = 11 // [1]
  val fractional: Double = 1.4 // [2]
  val trueOrFalse: Boolean = true // [3]
  val words: String = "A value" // [4]
  val character: Char = 'z' // [5]
  val lines: String = """Triple quotes let
      |you have many lines 
      |in your string""".trimMargin() // [6]
  println(whole)
  println(fractional)
  println(trueOrFalse)
  println(words)
  println(character)
  println(lines)
}
// 출력
11
1.4
true
A value
z
Triple quotes let
you have many lines 
in your string
```

### 코틀린 타입 추론
* 코틀린은 타입 추론을 사용해 전체 문장이나 식의 의미를 결정한다.
```
// 타입 추론
fun main() {
  val n = 1 + 1.2
  pringln(n)
}
// 출력
2.2
```
1. n을 계산할 때 코틀린이 타입 추론을 사용해 Double이라 결정한다.
2. 생성된 결과가 Double에 대한 규칙을 준수하는지 확인한다.

> 코틀린의 타입 추론을 프로그래머를 돕기 위한 코틀린의 전략 중 하나로 타입을 지정하지 않아도 보통은 코톨린이 타입을 추론해준다.

## 아톰 06. 함수
> 함수(function)는 이름이 있는 작은 프로그램과 같으며, 다른 함수에서 그 이름으로 실행하거나 호출(invoke)할 수 있다.

### 함수
* 함수는 일련의 동작을 묶어주고 프로그램을 체계적으로 구성하고 코드를 재사용하는 가장 기본적인 방법이다.
* 함수에 정보를 전달하면 함수는 정보를 이용해 계산을 수행해 결과를 만든다.
  ```
  // 함수의 기본적인 형태
  fun 함수이름(p1: 타입1, p2: 타입2, ...): 반환타입 {
    여러 줄의 코드(한 줄 또는 빈 줄)
    return 결과
  }
  ```

### 파라미터
* p1, p2는 함수에 전달할 `파라미터`이다.
* 파라미터 이름으로 쓰일 식별자(p1, p2), 콜론, 파라미터의 타입으로 이루어진다.
* 파라미터는 함수에 정보를 전달하는 방법을 정의한다.

### 함수 본문
* 함수 본문에 있는 코드는 중괄호({})로 둘러싸인다.
* return 키워드 뒤에 오는 식은 함수가 끝날 때 만들어낼 결과를 뜻한다.

### 함수 시그니처
* 파라미터는 전달할 정보를 넣을 장소이며 인자는 함수에 전달하는 실제 값이다.
* 이름 파라미터, 반환 타입을 합쳐서 `함수 시그니처`라고 부른다.

### 코드 재사용 도구
1. fun 키워드, 함수 이름, 파라미터 목록
2. 함수 본문, return을 통해서 값을 함수 결과로 반환한다.
3. 함수에 인자를 넘겨서 호출하고 결과 값을 val r이라는 변수에 넣는다.
```
fun multiplyByTwo(x: Int): Int { // [1]
  printIn("Inside multiplyByTwo") // [2]
  return x * 2
}

fun main() {
  val r = multiplyByTwo(5) // [3]
  println(r)
}
```
* 함수 호출은 함수 코드를 실행하고 결괏값을 가져온다는 사실을 줄여 쓴 것이며 이것이 함수가 프로그래밍에서 가장 기본적인 단순화 도구이자 `코드 재사용 도구인 이유`다.
* println(r)도 함수 호출이며, println이 코틀린이 제공하는 함수라는 점으로 코틀린이 정의한 함수는 `표준 라이브러리 함수`라 부른다.

### 반환 타입 Unit
* Unit은 의미 있는 결과를 제공하지 않는 함수의 반환 타입이다.
* Unit을 명시해도 되지만, 코틀린에서는 Unit을 생략해도 된다.
```
fun sayHello() {
  println("Hallo!")
}

fun sayGoodbye(): Unit {
  priintln("Auf Wiedersehen!")
}

fun main() {
  sayHello()
  sayGoodbye()
}
// 출력
Hallo!
Auf Wiedersehen!
```

### 본문
* 함수 본문이 하나의 식으로만 이뤄진 경우 등호(=) 뒤에 식을 넣어서 함수를 짧게 작성할 수 있다.
  * `fun 함수이름(p1: 타입1, p2: 타입2, ...): 반환타입 = 식`
* 함수 본문이 중괄호로 둘러싸인 경우를 `블록 본문`이라고 한다.
* 등호 뒤에 식이 본문으로 지정된 경우를 `식 본문`이라 한다.
* 코틀린은 식 본문의 반환 타입만 추론하며, 블록 본문에서 반환 타입을 명시하지 않으면 Unit으로 인식한다.

> 함수를 작성할 때는 서술적인 이름을 사용해야 코드를 쉽게 읽어 주석을 남길 필요성을 줄인다.

## 아톰 07. if 식
### if 키워드
* if 키워드는 식을 검사해 값이 true나 false 중 어느 것인지 알아내 결과에 따라 작업을 수행한다.
* 참이나 거짓을 표시하는 식은 불리언(Boolean) 방식이라고 한다.
* if 뒤에 있는 괄호 안의 식은 반드시 true나 false로 평가되어야 한다.

## 아톰 08. 문자열 템플릿
### 문자열 템플릿
* 문자열 템플릿은 `String을 프로그램으로 만드는 방법`이다.
* 식별자 이름 앞에 $를 붙이면 문자열 템플릿이 식별자의 내용을 String에 넣어준다.
  1. $answer의 값이 동일한 식별자를 가진 변수 answer의 값으로 치환된다.
  2. 동일한 식별자가 존재하지 않아서 프로그램 식별자로 인식되지 않아서 아무 일도 일어나지 않는다.
  ```
  fun main() {
    val answer = 42
    println("Found $answer!") // [1]
    println("printing a $1") // [2]
  }
  // 출력
  Found 42! 
  printing a $1
  ```
* 문자열 연결(+)로 String에 값을 넣을 수도 있다.
  ```
  fun main() {
    val s = "hi\n" // \n은 새줄 문자
    val n = 11
    val d = 3.14

    println("first: " + s + "second: " + n + ", third: " + d)
  }
  // 출력
  first: hi
  second: 11, third: 3.14
  ```
* ${} 중괄호 안에 식을 넣어 식을 평가 후 결괏값을 String으로 변환해 String에 결과를 삽입한다.
  1. if (condition) 'a' else 'b'의 결과가 전체 ${} 식을 대신한다.
  ```
  fun main() {
    val condition = true
    println("${if (condition) 'a' else 'b'}") // [1]
    val x = 11
    println("$x + 4 = ${x + 4}")
  }
  // 출력
  a
  11 + 4 = 15
  ```
* String 안에 특수 문자를 넣어야하는 경우 역슬래시를 사용해 이스케이프하거나 String 리터럴을 사용해야 한다.
  ```
  fun main() {
    val s = "value"
    println("s = \"$s\".")
    println("""s = "$s".""")
  }
  // 출력
  s = "value".
  s = "value".
  ```

## 아톰 09. 수 타입
### 수 타입
* 수(숫자) 타입은 타입에 따라 서로 다른 방식으로 저장된다.
* 식별자를 만들고 정숫값을 대입하면 코틀린은 타입을 추론하며, 가독성을 위해 숫자 사이에 밑줄을 넣도록 허용한다.
  ```
  fun main() {
    val million = 1_000_000 // Int를 추론
    println(million)
  }
  // 출력
  1000000
  ```
* 기본적인 수학 연산자인 더하기, 빼기, 곱하기, 나누기, 나머지를 제공한다.
  ```
  fun main() {
    val numerator: Int = 19
    val denominator: Int = 10
    printIn(numerator + denominator) // 더하기
    printIn(numerator - denominator) // 빼기
    printIn(numerator * denominator) // 곱하기
    printIn(numerator / denominator) // 나누기
    printIn(numerator % denominator) // 나머지
  }
  // 출력 
  29
  9
  190
  1
  9
  ```
* 연산의 순서는 기본적인 산순 연산 순서를 따르며, 먼저 실행하고 싶으면 괄호를 사용한다.
  ```
  fun main() {
    println(45 + 5 * 6) // 곱셈 연산이 먼저 수행된 뒤 덧셈 연산 수행
    println((45 + 5) * 6) // 덧셈 연산이 먼저 수행된 뒤 곱셈 연산 수행
  }
  // 출력
  75
  300
  ```
* 정수와 실수의 나눗셈 처리 방법은 다르다.
  * 정수 나눗셈에서 나머지를 처리하는 일반적인 방법은 버림으로 소수점 이하를 잘라내 버린다.
* 프로그래밍 언어마다 정수에 저장할 수 있는 값의 범위가 정해져 있어서 값을 넘어서는 경우 Overflow가 일어나게 된다.
  * Int보다 큰 수를 저장할려면 Long을 사용하면 된다.
* Int, Long형에서 Int.MAX_VALUE, Int.MIN_VALUE, Long.MAX_VALUE, Long.MIN_VALUE 와 같은 미리 정의된 상수를 지원한다.
* Long 타입의 val를 정의하고 싶으면 수 리터럴 뒤에 L을 붙여서 코틀린에게 명시하면 된다.
  ```
  fun main() {
    val i = 0 // Int 타입을추론
    val l1 = OL // L을 사용해 Long 타입으로 지정
    val l2: Long = 0 // 명시적으로 타입 지정 
    println("$l1 $l2")
  }
  // 출력
  0 0
  ```

## 아톰 10. 불리언
### 논리곱(and)와 논리합(or) 연산자
* &&(논리곱)
  * 연산자 오른쪽과 왼쪽에 있는 Boolean 식이 모두 true일 때만 true를 돌려준다.
* ||(논리합)
  * 연산자 오른쪽과 왼쪽에 있는 Boolean 식 중 하나라도 true면 true를 돌려준다.
* 우선순위는 괄호가 없는 경우 그리고(and)가 먼저 평가가 되고 또는(or)이 평가된다.

## 아톰 11. while로 반복하기
> 컴퓨터는 반복 작업을 수행하기에 이상적인 존재다.

### while 키워드
* 가장 기본적인 반복으로 사용되고 주어진 Boolean 식이 true인 동안 블록을 반복 수행한다.
  ```
  while (Boolean 식) {
    // 반복할 코드
  }
  ```
* 루프를 시작할 때 Boolean 식을 한 번 평가하고, 블록을 다시 반복하기 직전에 매번 다시 평가한다.
  1. 비교 연산자에 따라 Boolean 결과를 내놓고 condition() 함수의 결과 타입을 Boolean을 추론한다.
  2. condition()이 true를 반환하는 동안 본문의 코드를 반복한다.
  3. i에 10을 더한 결과를 다시 i에 대입해준다.
  ```
  fun condition(i: Int) = 1 < 100 // [1]
  fun main () {
    var i = 0
    while (condition(i)) { // [2]
      print(".") 
      i += 10 // [3]
    }
  }
  // 출력
  ..........
  ```
* while을 사용하는 다른 방법은 do와 함께 쓰는 방법이다.
  ```
  do {
    // 반복할 코드
  } while (Boolean 식)
  ```

### while과 do-while의 차이
* do-while
  * Boolean 식이 false를 돌려줘도 본문이 최소 한 번은 실행된다.
* while
  * 처음에 조건문이 false면 본문이 결코 실행되지 않는다.
* do-while은 while 보다 덜 쓰인다.

> 어떤 정수 범위 값을 차례로 반복(iteration)하기 위해서 while 루프를 쓰지 않고 for 루프를 사용한다.

## 아톰 12. 루프와 범위
### for 루프
* for 키워드는 주어진 순열에 속한 각 값에 대해 코드 블록을 실행한다.
  ```
  for (v in 값들) {
    // v를 사용해 어떤 일을 수행한다.
  }
  ```

### 범위
* 범위는 양 끝을 표현하는 두 정수를 사용해 구간을 정의하며, 기본적으로 두가지 방법이 있다.
  1. 양 끝 값을 포함한 범위를 만든다.
  2. until을 사용해 다음에 오는 값을 제외한 범위를 만들며 10은 제외된다.
  ```
  fun main() {
    val range1 = 1..10 [1]
    val range2 = 0 until 10 [2]
  }
  // 출력
  1..10
  0..9
  ```
  * 코틀린 1.8부터 `..<` 연산자가 도입되어 0 until 10을 0 ..< 10이라고 쓸 수 있다.
  * 코틀린 1.8에서도 여전히 실수에 until을 쓸 수 없다.
* 범위를 역방향으로 이터페이션 할 수 있으며, step 값을 사용하면 값의 간격을 1이 아닌 값으로 조정할 수 있다.
  1. downTo는 감소하는 범위를 만든다.
  2. step는 간격을 변경하며 값을 2를 지정해서 2씩 변한다.
  3. until과 step을 함께 쓸 수 있다.
  ```
  fun showRange(r: IntProgression) { 
    for (i in r) {
        print("$i ")
    }
    print(" // $r") 
    println()
  }
  fun main() {
    showRange(1..5)
    showRange(0 until 5)
    showRange(5 downTo 1) // [1]
    showRange(0..9 step 2) // [2]
    showRange(0 until 10 step 3) // [3]
    showRange(9 downTo 2 step 3) 
  }
  // 출력
  1 2 3 4 5 // 1..5
  0 1 2 3 4 // 0..4
  5 4 3 2 1 // 5 downTo 1 step 1
  0 2 4 6 8 // 0..8 step 2
  0 3 6 9 // 0..9 step 3
  9 6 3 // 9 downTo 3 step 3
  ```
* 이터레이션은 정수, 문자처럼 하나하나 값이 구분되는 양에 대해서만 가능하며 부동소수점 수에 대해서는 할 수 없다.

### IntProgression
* 산술적인 순열을 의미하며 코틀린이 제공하는 기본 타입이다.
* 입력한 값을 표준적인 형태로 변환해 표현한다.

### 문자열 범위
* 문자 범위도 가능하다.
  ```
  fun main() {
    for (c in 'a'..'z') {
      print(c)
    }
  }
  // 출력
  abcdefghijklmnopqrstuvwxyz
  ```
* 각괄호([])를 사용해 숫자 인덱스를 통해 문자열의 문자에 접근할 수 있다.
  ```
  fun main() {
    val s = "abc"
    for (i in 0..s.lastIndex) {
      print(s[i] + 1)
    }
  }
  // 출력
  bcd
  ```
* 문자는 아스키 코드에 해당하는 숫자 값을 저장되서 정수를 문자에 더하면 새 코드 값에 해당하는 새로운 문자를 얻을 수 있다.
  ```
  fun main() {
    val ch: Char = 'a'
    println(ch + 25)
    println(ch < 'z')

    for(ch in "Jnskhm ") {
      print(ch + 1)
    }
  }
  // 출력
  z
  true
  Kotiln!
  ```

### repaet
* 어떤 동작을 단순히 정해진 횟수만큼 반복하고 싶을 때 for 루프 대신 repeat()를 사용해도 된다.
* repeat()는 키워드가 아닌 표준 라이브러리 함수다.
  ```
  fun main() {
    repeat(2) {
      println("hi!")
    }
  }
  // 출력
  hi!
  hi!
  ```

## 아톰 13. in 키워드
### in 키워드
* in 키워드는 어떤 값이 주어진 범위 안에 들어 있는지 검사한다.
  ```
  fun main() {
    val percent = 35
    println(percent in 1..100)
    println(0 <= percent && percent <= 100)
  }
  // 출력
  true
  true
  ```
  * 인텔리J IDEA는 두 번째 형태가 읽기 더 쉬우므로 첫 번째 형태를 두 번째 형태로 바꾸라고 제안한다.
* in 키워드는 이터레이션, 원소 여부 검사, 문자열 여부 검사할 때 사용된다.
  ```
  fun main() {
    // 이터레이션
    val values = 1..3
    for (v in values) {
      println("iteration $v")
    }
    // 원소 여부 검사
    val v = 2
    if (v in values)
      println("$v is a member of $values")
    // 문자열 여부 검사
    println('t' int ")
    println('a' in '0'..'9')
    println('5' in '0'..'9')
    println('z' !in '0'..'9')
  }
  // 출력
  interation 1
  interation 2
  interfation 3
  2 is a member of 1..3
  true
  false
  false
  true
  true
  ```
* 원소인지 여부를 검사할 때만 Double의 범위를 쓸 수 있다.
  ```
  fun main() {
    println("0.999999 in 1.0..10.0? ${0.999999 in 1.0..10.0}")
    println("5.0 in 1.0..10.0? ${5.0 in 1.0..10.0}")
    println("10.0 in 1.0..10.0? ${10.0 in 1.0..10.0}")
    println("10.0000001 in 1.0..10.0? ${10.0000001 in 1.0..10.0}")
  }
  // 출력
  0.999999 in 1.0..10.0? false
  5.0 in 1.0..10.0? true
  10.0 in 1.0..10.0? true
  10.0000001 in 1.0..10.0? false
  ```
  * 부동 소수점에서 범위를 만들 때 ..만 쓸 수 있었지만, 코틀린 1.8부터는 반 열린 범위를 만들어내는 ..<를 쓸 수 있다.
* String이 어떤 String 범위 안에 속하는지 검사할 수 있다.
  ```
  fun main() {
    println("ab" in "aa".."az")
    println("ba" in "aa".."az")
  }
  // 출력
  true
  false
  ```
  * 알파벳순으로 문자열을 비교한다.

## 아톰 14. 식과 문
> 문과 식은 대부분의 프로그래밍 언어에서 가장 작은 코드 조각이다.

### 식과 문의 차이
* 문은 효과를 발생시키지만 결과를 내놓지 않는다.
* 식은 항상 결과를 만들어낸다.

### 문(statement)
* 부수 효과(side effect)를 얻기 위해 문을 사용한다.
* 결과를 내놓지 않기 때문에 쓸모 있으려면 문을 들러싸고 있는 주변 상태를 변경해야 한다.
* A Statement chanages state(문은 상태를 변경한다).

### 식(expression)
* 식은 결괏값을 만들어낸다.
* 식을 뜻하는 영어 expression에서 express는 힘을 주거나 짜내서 무언가를 배출하다라는 뜻이다.
* An expression expresses(식은 값을 짜낸다).

### 문과 for 루프
* 코틀린의 `for 루프는 문`이며, 아무런 결괏값도 만들어내지 않기 때문에 for 문을 다른 변수에 대입할 수는 없다.
* for 루프는 부수 효과를 위해서만 사용된다.
  ```
  fun main() {
    // 다음과 같이 할 수는 없다.
    val f = for (i in 1..10) {}
    // 컴파일러 오류 발생 메세지
    // for is not an expression, and only expressions are allowed here
  }
  ```
* 문은 다른 식의 일부분이 되거나 변수에 대입할 수 없는 최상위 요소다.
  * 코틀린에서 최상위 요소는 unitFun(), main() 처럼 단독으로 존재하는 코드를 의미한다.

### 식과 함수 호출
* `모든 함수 호출 코드는 식`이며, 함수가 Unit을 반환하며 부수 효과를 목적으로 호출되도 여전히 함수 호출 결과를 변수에 대입할 수 있다.
* 식은 값을 돌려주기 때문에 이 값을 변수에 대입하거나 다른 식의 일부분으로 쓸 수 있다.
  ```
  fun unitFun() = Unit
  fun main() {
    println(unitFun())
    val u1: Unit = println(42) // Unit 타입은 오직 Unit이라는 값만 포함
    println(u1) 
    val u2: println(0) // 타입 추론
    println(u2)
  }
  // 출력
  kotlin.Unit
  42
  kotlin.Unit
  0
  kotlin.Unit
  ```

### 식과 if문
* if는 식을 만들 수 있고, if의 결과를 변수에 대입할 수 있다.
  1. 5가 할당된다.
  2. 코드 블록 안에서 a라는 변수를 정의했고, if 쪽 블록의 마지막에 있는 식의 결과가 if 전체의 결과가 되기 때문에 11과 42를 더한 53이 되며 a는 임시적 변수로 코드 블록을 벗어나면 접근 할 수 없고 영역을 벗어나면 a도 버려진다.
  3. Unit이 할당된다.
  ```
  fun main() {
    val result1 = if (11 > 42) 9 else 5 // [1]
    val result2 = if (1 < 2) { // [2]
      val a = 11
      a + 42
    } else 42
    
    val result3 = // [3]
      if ('x' < 'y')
        println("x < y")
      else
        println("x > y")

    println(result1)
    println(result2)
    println(result3)
  }
  // 출력
  x < y
  5
  53
  kotlin.Unit
  ```

### 식과 연산자
* 증가 연산자, 전위 연산자, 후위 연산자의 경우 문처럼 보일지 몰라도 실제로는 식이다.