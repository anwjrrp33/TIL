## 아톰 30. 확장 함수
### 확장 함수
* 어떤 상황과 필요한 것을 제공하는 라이브러리에서 멤버 함수만 한두가지 더 있으면 문제를 해결할 수 있을 것 같을 때 추가적인 기능이 필요한데 라이브러리는 내 코드도 아니고 변경 및 제어가 불가능하며 새로운 버전이 나올 때마다 반복해서 적용해야 하지만 확장 함수를 통해서 해결할 수 있다.
* 코틀린의 `확장 함수`은 기존 클래스에 멤버 함수를 추가하는 것과 같은 효과를 내며 확장할 대상 타입은 `수신 객체 타입`이라고 한다.
  ```
  fun String.singleQuote() = "'$this'"
  fun String.doubleQuote() = "\"$this\""

  fun main() {
    // 출력 'Hi'
    pringln("Hi".singleQuote())
    // 출력 "Hi"
    pringln("Hi".doubleQuote())
  }
  ```
* this 키워드를 사용해 다른 확장 함수나 멤버 함수에 접근할 수 있다.
  ```
  fun String.singleQuote() = "'$this'"
  fun String.doubleQuote() = "\"$this\""
  fun String.strangeQuote() = this.singleQuote().singleQuote() // [1]
  fun String.tooManyQuotes() = this.doubleQuote().doubleQuote() // [2]

  fun main() {
    // 출력 ''Hi''
    pringln("Hi".strangeQuote())
    // 출력 ""Hi""
    pringln("Hi".tooManyQuotes())
  }
  ```
* 자신이 작성한 클래스에 대해 확장을 정의하는 것이 더 간단한 코드를 생성하기도 한다.
  ```
  class Book(val title: String)

  fun Book.categorize(category: String) = """title: "$title", category: $category"""

  fun main() {
    // 출력 title: "Dracula", category: Vampire
    println(Book("Dracula").categorize("Vampire"))
  }
  ```
* 확장 함수는 확장 대상 타입의 public 원소에만 접근할 수 있기 때문에 확장은 일반 함수가 할 수 있는 일만 처리할 수 있다.
* 확장 함수를 사용하는 이유는 this를 사용함으로써 구문적 편의를 얻기 때문이며 이런 문법 설탕은 강력하다.
* 호출하는 코드에서 확장 함수는 멤버 함수와 똑같아 보이며 IDE는 객체에 대해 점 표기법으로 호출할 수 있는 함수 목록에 확장을 포함시킨다.

## 아톰 31. 이름 붙은 인자와 디폴트 인자
### 인자
* 함수를 호출하면서 인자의 이름을 지정할 수 있으며 인자 목록이 긴 경우 인자에 이름을 붙이면 코드 가독성이 좋아진다.
  * [1] 인자의 역할을 알아내려면 함수 문서를 살펴봐야 한다.
  * [2] 모든 인자의 의미가 명확하다.
  * [3] 모든 인자에 이름을 붙이지 않아도 된다.
  ```
  fun color(red: Int, green: Int, blue: Int) = "($red, $green, $blue)"

  fun main() {
    color(1, 2, 3) // [1]
    color(red = 76, green = 89, blue = 0) // [2]
    color(52, 34, blue = 0) // [3]
  }
  ```
* 이름 붙은 인자는 디폴트 인자(파라미터의 디폴트 값을 함수 정의에서 지정)와 결합하면 더 유용하다.
  * 함수 호출 시 값을 지정하지 않은 인자는 디폴트 값으로 지정한다.
  * 인자 목록이 길면 디폴트 인자를 통해서 생략 시 코드 작성이 쉬워지며 가독성이 좋아진다.
  ```
  fun color(
    red: Int = 0, 
    green: Int = 0, 
    blue: Int = 0
  ) = "($red, $green, $blue)"

  fun main() {
    color(139)
    color(blue = 139)
    color(255, 165)
    color(red = 128, blue = 128)
  }
  ```
* 파리미터에 덧붙은 콤마(마지막 파라미터뒤에 콤마를 추가로 작성)를 사용할 수 있다.
  * 파라미터 값을 여러 줄에 걸쳐 쓰는 경우에는 덧붙은 콤마가 유용하다.
  * 덧붙은 콤마를 추가하거나 뺴지 않아도 새로운 아이템을 추가하거나 아이템의 순서를 바꿀 수 있다.
  * 이름 붙은 인자와 디폴트 인자, 덧붙은 콤마는 생성자에 써도 된다.
  ```
  class Color(
    val red: Int = 0,
    val green: Int = 0,
    val blue: Int = 0, // 덧붙은 콤마
  ) {
    overrid fun toString() = "($red, $green, $blue)"
  }

  fun main() {
    Color(red = 77).toString()
  }
  ```
* joinToString()은 디폴트 인자를 사용하는 표준 라이브러리 함수다.
  ```
  fun main() {
    val list = listOf(1, 2, 3,)
  }
  ```
* 객체 인스턴스를 디폴트 인자로 전달하는 경우 호출할 때마다 인스턴스가 반복해서 전달된다.
  * 함수 g()는 똑같은 객체 주소이며, 함수 h()는 서로 다른 객체 주소로 인해서 다른 객체인 것을 알 수 있다.
  ```
  class DefaultArg
  val da = DefaultArg()
  
  fun g(d: DefaultArg = da) = println(d)
  
  fun h(d: DefaultArg = DefaultArg()) = println(d)
  
  fun main() {
      g()
      g()
      h()
      h()
  }
  // 출력
  com.example.kotilnbasic.DefaultArg@52cc8049
  com.example.kotilnbasic.DefaultArg@52cc8049
  com.example.kotilnbasic.DefaultArg@5b6f7412
  com.example.kotilnbasic.DefaultArg@27973e9b
  ```
* 인자 이름을 붙였을 때 `가독성이 향상되는 경우에만 인자 이름을 지정`해야 한다.

## 아톰 32. 오버로딩
### 오버로딩
* 디폴트 인자를 지원하지 않는 언어에서 종종 디폴트 인자를 흉내내기 위해 오버로딩을 활용한다.
* 파라미터 목록에서 여러 다른 함수에 같은 이름을 사용하는게 오버로딩이다.
  ```
  Class Overloading {
    fun f() = 0
    fun f(n: Int) = n + 2
  }
  ```
* 함수를 오버로딩할 때는 함수 파라미터 리스트를 서로 다르게 만들어야 한다.
* 함수의 반환 타입은 오버로딩의 대상이 아니다.
* 클래스 안에 확장 함수와 시그니처가 똑같은 멤버 함수가 있으면 멤버 함수를 우선하며 확장 함수를 갖고 멤버 함수를 오버로딩 할 수 있다.
  * [1] 멤버 함수가 우선시되며 호출되지 않는다.
  * [2] 멤버 함수를 확장 함수로 오버로딩할 수 있다.
  ```
  class My {
    fun foo() = 0
  }

  fun My.foo() = 1 // [1]
  fun My.foo(i: Int) = i + 2 // [2]
  ```
* 디폴트 인자를 흉내내기 위해 확장 함수를 사용하면 안된다.
  ```
  fun f(n: Int) = n + 373
  fun f() = f(0)
  ```
* 파라미터가 없는 함수는 첫 번째 함수만 호출하며 디폴트 인자를 사용해 한 함수로 바꿀 수 있다.
  ```
  fun f(n: Int = 0) = n + 373

  fun main() {
    f()
  }
  ``` 
* 함수 오버로딩과 디폴트 인자를 함께 사용하는 경우 오버로딩한 함수를 호출하면 함수 시그니처와 함수 호출이 가장 가깝게 일치되는 함수를 호출한다.
  * [1] 호출되지 않는다.
  * [2] 호출된다.
  ```
  fun foo(n: Int = 99) = trace("foo-1-$n") // [1]

  fun foo() { // [2]
    trace("foo-2")
    foo(14)
  }

  fun main() {
    foo()
  }
  ```

### 오버로딩이 유용한 이유
* 오버로딩을 사용하면 `같은 주제를 다르게 변경한다`는 개념을 명확하게 표현할 수 있다.
  ```
  fun add(i: Int, j: Int) = i + j
  fun add(i: Double, j: Double) = i + j
  ```
* 오버로딩이 없다면 함수의 역할이나 함수가 대상을 처리하는 방법을 조합해서 유일한 이름을 만들어 내지만 오버로딩을 사용하면 훨씬 코드가 깔끔하다.
* 언어가 오버로딩을 지원하지 않는 것이 큰 단점은 아니지만 단순성을 얻을 수 있어서 읽기 좋은 코드를 작성할 수 있다.
* 오버로딩을 사용하면 함수 자체에 대해 설명하는 이름을 써서 추상화 수준을 높이고 읽기에 대한 정신적인 부담을 줄일 수 있으며 불필요한 중복을 줄여준다.
* 함수 파라미터에 따른 반복적인 함수를 줄일 수 있다.

## 아톰 33. when 식
### when 식
* 프로그램에는 패턴에 따라 어떤 동작을 수행하는 코드가 많은데 두세 가지 이상의 선택지가 있는 경우 when 식을 사용하면 좋다.
* when 식은 어떤 값을 여러 가지 가능성과 비교해 선택한다.
  * 식은 when으로 시작한다.
  * when 뒤에는 괄호 안에 있는 비교 대상 값이 온다.
  * 비교 대상 값 뒤에는 값과 일치할 수 있는 여러 매치가 들어있는 본문이 온다.
  * 각 매치는 식, 오른쪽 화살표로 시작한다.
  * 화살표 오른쪽에는 결괏값을 계산하는 식이 온다.
* when 식을 계산할 때는 비교 대상 값과 각 매치에 있는 화살표 왼쪽 값을 순서대로 비교하며, 일치하는 값이 있으면 화살표 오른쪽 값을 계산한 값이 전체 when 식의 결괏값이 된다.
  * [1] 매치식과 비교한다.
  * [2] 가장 먼저 일치하는 매치식에서 실행이 끝난다.
  * [3] else 키워드는 일치하는 매치식이 없을 때 사용할 식을 제공한다.
  ```
  val numbers = mapOf(
    1 to "eins", 2 to "zwei", 3 to "drei",
    4 to "vier", 5 to "fuenf", 6 to "sechs",
    7 to "sieben", 8 to "acht", 9 to "neun",
    10 to "zehn", 11 to "elf", 12 to "zwoelf",
    13 to "dreizehn", 14 to "vierzehn",
    15 to "fuenfzehn", 16 to "sechzehn",
    17 to "siebzehn", 18 to "achtzehn",
    19 to "neunzehn", 20 to "zwanzig"
  )

  fun ordinal(i: Int): String =
    when (i) { // [1]
      1 -> "erste" // [2]
      3 -> "dritte"
      7 -> "siebte"
      8 -> "achte"
      20 -> "zwanzigste"
      else -> numbers.getValue(i) + "te" // [3]
    }

  fun main() {
    // 출력 zweite
    println(ordinal(2))
    // 출력 dritte
    println(ordinal(3))
    // 출력 elfte
    println(ordinal(11))
  }
  ```
* when 식에서 else를 없애면 컴파일 타입 오류가 발생하는데 when 식을 문처럼 취급하는 경우에만 else를 생략할 수 있으며 이 경우 일치하는 매치가 없으면 아무 일도 발생하지 않고 when 문이 종료된다.
* when의 인자로는 임의의 식이 올 수 있고, 매치 조건에도 아무 값이나 올 수 있다.
* when 식과 if 식은 겹치는 부분이 있지만 when이 더 유연하며 `선택의 여지가 있다면 when을 사용하는 것을 권장`한다.
* when에는 인자를 취급하지 않는 특별한 형태가 존재하는데 `인자가 없으면 각 매치 가지를 Boolean 조건에 따라 검사`하기 떄문에 `Boolean 타입의 식`을 넣어야한다.

> when을 사용한 해법은 여러 선택지 중 하나를 선택하는 좀 더 우아한 해법이다.

## 아톰 34. 이넘
### enum class
* 이넘은 이름을 모아둔 것으로 코틀린 enum class는 모아둔 이름을 관리하는 편리한 방법이다.
  ```
  enum class Level {
    Overflow, High, Medium, Low, Empty
  }
  ```
* enum을 만들면 enum의 이름에 해당하는 문자열을 돌려주는 toString()이 생성된다.
* 이넘 이름을 사용할 때는 이름을 한정시켜야 하지만 import를 사용해 이넘에 정의된 모든 이름을 현재의 이름 공간으로 불러오면 이름을 한정시키지 않아도 된다.
  * [1] Level 이넘에 있는 모든 이름을 임포트하지만 Level이라는 이름을 임포트하지는 않는다.
  * [2] enum 클래스가 정의된 파일에서 enum 값을 임포트해서 사용할 수 있다.
  ```
  import com.example.kotilnbasic.Level.* // [1]

  enum class Level {
      Overflow, High, Medium, Low, Empty
  }
  
  fun main() { // [2]
      println(Overflow)
  }
  ```
* when 식을 사용해 enum 항목마다 다른 동작을 수행할 수 있다.
  ```
  fun checkLevel(level: Level) {
    when (level) {
      Level.Overflow -> println(">>> Overflow!")
      Level.Empty -> println("Alert: Empty")
      else -> println("Level $level OK")
    }
  }
  
  fun main() {
    checkLevel(Empty)
    checkLevel(Low)
    checkLevel(Overflow)
  }
  ```
* 이넘은 인스턴스 개수가 미리 정해져있고 클래스 본문 안에 모든 인스턴스가 나열되어 있는 특별한 종류의 클래스이며 이 점을 제외하면 일반 클래스와 똑같이 동작한다.
* 멤버 함수나 멤버 프로퍼티를 이넘에 정의할 수 있고, 마지막 이넘 값 다음에 세미콜론을 추가한 후 정의를 포함시켜야 한다.
  ```
  enum class Direction(val notation: String) {
    North("N"), South("S"),
    East("E"), West("W");  // 세미콜론이 꼭 필요함
    val opposite: Direction
      get() = when (this) {
        North -> South
        South -> North
        West -> East
        East -> West
      }
  }
  
  fun main() {
    // 출력 N
    println(Direction.North.notation)
    // 출력 South
    println(Direction.North.opposite)
    // 출력 West
    println(Direction.West.opposite.opposite)
    // 출력 S
    println(Direction.North.opposite.notation)
  }
  ```

> 이넘은 코드 가독성을 높여주기 때문에 항상 사용하는 게 바람직하다.

## 아톰 35. 데이터 클래스
* 데이터 저장만 담당하는 클래스가 필요하면 데이터 클래스를 사용해 코드양을 줄이면서 여러 가지 공통 작업을 편하게 수행할 수 있다.
* data라는 키워드를 사용해 클래스를 정의한다.
  * data 키워드는 몇 가지 기능을 클래스에 추가하라고 코틀린에게 지시한다.
  * 모든 생성자 파라미터를 var, val로 선언해야 한다.
  * toString()를 작성하지 않아도 보기 좋은 형식으로 표현해준다.
  * equals()가 자동으로 생성되며 생성자 파라미터에 열거된 모든 프로퍼티가 같은지 검사한다.
  ```
  data class Simple(
    val arg1: String,
    var arg2: Int
  )

  fun main() {
    val s1 = Simple("Hi", 29)
    val s2 = Simple("Hi", 29)
    // 출력 Simple(arg1=Hi, arg2=29)
    println(s1)
    // 출력 true
    println(s1 == s2)
  }
  ```
* copy()라는 함수를 통해 현재 객체의 모든 데이터를 포함한 새 객체를 생성해주며 몇몇 값을 새로 지정할 수 있다.
  * copy()의 파라미터 이름은 생성자 파라미터의 이름과 같다.
  * 모든 인자는 각 프로퍼트의 현재 값이 디폴트 인자이다.
  * 변경하고 싶은 인자만 이름 붙은 인자로 지정하면 된다.
  ```
  data class DetailedDContact(
    val name: String,
    val surname: String,
    val number: String,
    val address: String
  )

  fun main() {
    val contact = DetiledContact(
      "Miffy",
      "Miller"
      "1-234-567890"
      "1600 Amphitheatre Parkway"
    )
    val newContact = contact.copy(
      number = "098-765-4321"
      address = "Brandschenkestrasse 110"
    )
  }
  ```

### data 클래스에서의 HashMap과 HashSet
* data 클래스를 만들면 객체를 HashMap, HashSet에 넣을 때 키로 사용할 수 있는 해시 함수를 자동으로 생성해준다.
* HashMap, HashSet에선 hashCode()를 equals()와 함께 사용해 Key를 빠르게 검색한다.
* hashCode()를 equals()의 생성을 data 클래스가 대신해준다.
  ```
  data class Key(val name: String, val id: Int)

  fun main() {
    val korvo: Key = Key("Korvo", 19)

    val map = HashMap<Key, String>()
    map[korvo] = "Alien"
    // 출력 Alien
    println(map[korvo])
    val set = HashSet<Key>()
    set.add(korvo)
    // 출력 true
    println(set.contains(korvo))
  }
  ```

## 아톰 36. 구조 분해 선언
### Pair 클래스
* 표준 라이브러리에 있는 Pair 클래스를 쓰면 두 값을 Triple 클래스를 쓰면 세 값을 반환할 수 있다.
  ```
  fun compute(input: Int): Pair<Int, String> =
    if (input > 5)
      Pair(input * 2, "High")
    else
      Pair(input * 2, "Low")
  ```
* Pair, Triple을 사용해 여러 값을 반환하면 편리하다.

### 구조 분해 선언
* 구조 분해 선언을 사용하면 여러 식별자를 동시에 선언하면서 초기화할 수 있다.
  ```
  val (a, b, c) = 여러_값이_들어있는_값
  ```
* 구조 분해 구문에서는 식별자 이름을 괄호 안에 넣어야 한다.
  ```
  fun compute(input: Int): Pair<Int, String> =
    if (input > 5)
      Pair(input * 2, "High")
    else
      Pair(input * 2, "Low")
      
  fun main {
    val (val, description) = compute(7)
  }
  ```
* Triple 클래스는 최대 세 가지 값까지만 묶고, 더 많은 값을 저장하거나 코드에서 Pair, Triple을 많이 사용한다면 상황에 맞는 특별한 클래스를 작성해야 하는데 data 클래스는 자동으로 구조 분해 선언을 지원한다.
  * Pair, Triple을 반환하는 것보다 데이터 클래스인 Computation을 반환하는 것이 나으면 결괏값의 타입에 이름을 붙이는 것은 중요한 일이다.
  * Computation을 사용하면 정보를 추가하거나 제거하는 것이 쉽다.
  ```
  data class Computation(
    val data: Int,
    val info: String
  )

  fun evaluate(input: Int) = 
    if (input > 5)
      Computation(input * 2, "High")
    else
      Computation(input * 2, "Low")

  fun main {
    val (val, description) = evaluate(7)
  }
  ```
* data 클래스의 인스턴스를 구조 분해할 때는 생상자에 각 프로퍼티가 나열된 순서대로 값이 대입된다.
  * [1] 구조 분해 선언으로 선언할 식별자 일부가 필요하지 않는 경우 이름 대신 밑줄을 사용할 수 있고, 맨 뒤쪽 이름들은 아예 생략할 수 있다.
  ```
  data class Tuple(
    val i: Int,
    val d: Double,
    val s: String,
    val b: Boolean,
    val l: List<Int>
  )

  fun main() {
    val tuple = Tuple(1, 3.14, "Mouse", false, listOf())
    val (i, d, s, b, l) = tuple
    val (_, _, animal) = tuple // [1]
  }
  ```
* for 루프를 사용하면 튜플이나 다른 data 클래스의 객체로 이뤄진 Map, List에 대해 이터레이션하면서 값의 각 부분을 구조 분해로 얻을 수 있다.
  ```
  fun main() {
    var result = ""
    val map = mapOf(1 to "one", 2 to "two")
    for ((key, value) in map) {
      result += "$key = $value, "
    }
    // 출력 1 = one, 2 = two,
    println(result)
  
    result = ""
    val listOfPairs =
      listOf(Pair(1, "one"), Pair(2, "two"))
    for ((i, s) in listOfPairs) {
      result += "($i, $s), "
    }
    // 출력 (1, one), (2, two), 
    println(result)
  }
  ```
* withIndex()는 표준 라이브러리가 List에 대해 제공하는 확장 함수로 컬렉션 값을 IndexedValue라는 타입의 객체에 담아서 반환하며, 이 객체를 구조 분해할 수 있다.
  ```
  fun main() {
    val list = listOf('a', 'b', 'c')
    for ((index, value) in list.withIndex()) {
      // 출력
      // 0:a 
      // 1:b
      // 2:c
      println("$index:$value")
    }
  }
  ```
* 구조 분해 선언은 지역 var, val에만 적용가능하며 클래스 프로퍼티를 정의할 때는 사용할 수 없다.

## 아톰 37. 널이 될 수 있는 타입
### 널이 될 수 있는 타입
* 결과가 없는 함수가 발생해도 자체적으로 오류를 발생시키지 않고 단지 값 없음일 뿐이다.
* Map에 주어진 키에 해당하는 값이 없으면 값 없음을 의미하는 null 참조를 돌려준다.
  ```
  fun main() {
    val map = mapOf(0 to "yes", 1 to "no")
    // 출력 null
    println(map[2])
  }
  ```
* 자바 언어에서는 null을 허용하지만 정상적인 값과 같은 방식으로 다루면 실패(NullPointerException)이 발생한다.
* null 참조를 생각한 토니 호어(Tony Hoare)는 이를 자신의 100만 불짜리 실수라고 표현했다.
* null 문제를 해결하는 방법은 null을 허용하지 않는 것이지만 코틀린은 자바와 상호 작용해야 하는데 자바는 null을 사용해서 코틀린은 두 접근 방식을 가장 잘 절충한 기본적으로 널이 될 수 없는(non-nullable) 타입을 사용한다.
  * 무언가 null 결과를 내놓을 수 있다면 `타입 뒤에 물음표`를 붙인다.
  * [1] null 참조가 아니다.
  * [2] null이 될수 없는 String에 지정해서 오류가 발생한다.
  * [3] null 참조를 저장할 수 있는 식별자를 정의하려면 타입 이름 뒤에 물음표를 붙이며 null, 정상적인 값을 모두 담을 수 있다.
  * [4] null과 정상적인 값을 널이 될수 있는 타입의 식별자에 대입할 수 있다.
  * [5] 널이 될 수 있는 타입의 식별자를 널이 될 수 없는 타입의 식별자에 대입할 수 없다.
  * [6] 타입 추론을 통해서 코틀린이 적절한 타입을 만들어낸다.
  ```
  fun main() {
    val s1 = "abc" // [1]
    
    // 컴파일 오류
    // val s2: String = null // [2]

    // 널이 될 수 있는 정의
    val s3: String? = null // [3]
    val s4: String? = s1 // [4]

    // 컴파일 오류
    // val s5: String = s4 // [5]
    val s6 = s4 // [6]
  }
  ```
* 타입 이름 끝에 ?는 같은 타입 같지만 실제로는 다른 타입이다.
* Map에 각괄호를 사용해서 값을 가져오면 각괄호에 해당하는 연산의 기저 구현인 자바 코드가 null을 돌려주기 때문에 널이 될 수 있는 결과를 얻을 수 있다.
* 많은 연산은 null이 아닌 결과를 가정하기 때문에 어떤 값이 null이 될 수 있는지 아는 것이 중요하다.
* 자바에서는 대부분의 값이 null이 될 수 있기 때문에 함수 호출 시 NPE가 발생할 수 있기 때문에 결과가 null인지 검사하는 코드를 작성하거나 null 가능성을 차단해주는 코드의 다른 부분에 의존해야 한다.
* 코틀린에서는 null을 될 수 있는 타입을 단순히 역참조할 수 없다.
  * [1] 널이 될 수 없는 타입의 멤버에 접근할 수 있다.
  * [2] 널이 될 수 있는 타입의 멤버를 참조하면 코틀린은 오류를 발생시킨다.
  * 대부분의 타입의 값은 메모리에 있는 객체에 대한 참조로 저장되는데 `객체에 접근하기 위해서는 메모리에서 객체`를 가져와야하며 역참조의 의미가 바로 이것을 의미한다.
  ```
  fun main() {
    val s1: String = "abc"
    val s2: String? = s1

    s1.length // [1]
    // 컴파일되지 않는다.
    // s2.length // [2]
  }
  ```
* NPE를 발생시키지 않도록 명시적으로 null인지 검사하는 것이다.
  * 명시적으로 if 검사를 수행하면 코틀린이 널이 될 수 있는 객체를 참조하도록 허용해준다.
  * 매번 if 검사를 수행하면 코드가 간결하지 못하기 때문에 코틀린은 간결한 구문을 제공한다.
  ```
  fun main() {
    val s: String? = "abc"
    if (s != null)
      s.length
  }
  ```
* 새 `클래스를 정의할 때`마다 코틀린은 `자동으로 널이 될 수 있는 타입과 널이 될 수 없는 타입을 추가`해준다.

## 아톰 38. 안전한 호출과 엘비스 연산자
> 코틀린은 널 가능성을 편리하게 처리할 수 있도록 여러 연산자를 제공한다.

### 안전한 호출
* 널이 될수 있는 타입에는 여러 제약이 가해지며 널이 될 수 있는 타입의 참조를 단순히 역참조할 수 없다.
  * [1] 컴파일 오류가 발생하며 안전한 호출이나 단언 호출만 사용할 수 있다고 표현한다.
  ```
  fun main() {
    val s: String? = null
    // 컴파일되지 않는다.
    // s.length // [1]
  }
  ```
* 안전한 호출은 일반 호출에 사용하는 점을 물음표와 점으로 바꾼 것이다.
* 안전한 호출을 사용하면 널이 될 수 있는 타입에 접근을 허용하며 수신 객체가 null이 아닐 때만 연산을 수행하기 때문에 NPE도 발생하지 않게 해준다.
  * [1] null이 아니기 때문에 echo()를 호출한다.
  * [2] null이기 때문에 echo()를 호출하지 않는다.
  ```
  fun String echo() {
    println(this.toUpperCase())
    println(this)
    pringln(this.toLowerCase())
  }

  fun main() {
    val s1: String? = "Howdy!"
    s1?.echo() // [1]
    val s2: String? = null
    s2?.echo() // [2]
  }
  ```
* 안전한 호출을 사용하면 if를 사용하는 것보다 깔끔한 결과를 얻을 수 있다.


### 엘비스 연산자
* 안전한 호출의 결과로 null을 만들어내는 것이 이상의 일이 필요하다면 엘비스 연산자가 대안을 제공한다.
* 엘비스 연산자는 물음표 뒤에 콜론을 붙인 연산자다.
* 상당수의 프로그래밍 언어가 코틀린 엘비스 연산자와 같은 역할을 하는 `널 복합 연산자`를 제공한다.
* ?:의 왼쪽 식의 값이 null이 아니면 왼쪽 식의 값이 전체 엘비스 식의 결괏값이 되며 왼쪽 식이 null이면 ?:의 오른쪽 식의 값이 전체 결괏값이 된다.
  ```
  fun main() {
    val s1: String? = "abc"
    // 출력 abc
    println(s1 ?: "---")
    val s2: String? = null
    // 출력 ---
    println(s2 ?: "---")
  }
  ```
* 보통은 안전한 호출이 null 수신 객체에 대해 만들어내는 null 대신 디폴트 값을 제공하기 위해서 `안전한 호출 다음에 엘비스 연산자를 사용`한다.
  ```
  fun checkLength(s: String?) {
    val length1 = if(s != null) s.length else 0
    val length2 = s?.length ?: 0
  }
  ```
* 호출을 연쇄시키는 중간에 null이 결과로 나올 수 있는데 안전한 호출과 엘비스 연산자를 사용해서 여러 호출을 간결하게 연쇄시킬 수 있다.
  ```
  class Person(
    val name: String,
    var friend: Person? = null
  )

  fun main() {
    val alice = Person("Alice")
    alice.friend?.friend?.name == null
    val bob = Person("Bob")
    val charlie = Person("Charlie", bob)
    bob.friend = charlie
    bob.friend?.friend?.name == "Bob"
    alice.friend?.friend?.name ?: "Unknown" == "Unknown"
  }
  ```

## 아톰 39. 널 아님 단언
> 널이 될 수 있는 타입을 처리하는 접근 방법으로 어떤 참조가 null이 될 수 없다는 사실을 특별히 알 수 있는 경우에 사용된다.

### 널 아님 단언
* 널 아님 단언(non-null assertion)은 null이 될 수 없다고 주장하기 위해 느낌표 두 개(!!)를 쓴다.
* null과 관련된 모든 문제의 근원은 어떤 대상이 절대 null이 될 수 없다고 믿는 것이기 때문으로 null이 아니라는 점을 보증하는 것이다.
  ```
  fun main() {
    var x: String? = "abc"
    x!! == "abc"
    x = null
    // NPE 발생
    val s: String = x!!
  }
  ```
* 일반적으로 널 아님 단언을 그냥 쓰는 경우는 없고 보통 역참조와 함께 사용한다.
  ```
  fun main() {
    val s: String? = "abc"
    s!!.length == 3
  }
  ```
* 널 아님 단언을 한줄에 하나씩만 사용하면 NPE 예외가 발생했을 때 줄 번호를 보고 쉽게 오류 위치를 찾을 수 있다.
* 안전한 호출은 단일 연산자지만 널 아님 단언 호출은 널 아님 단언과 역참조로 이뤄졌으며 널 아님 단언만 사용해도 된다.
* 일반적으로 널 아님 단언이 아닌 안전한 호출이나 명시적인 null 검사를 활용하는 쪽을 권장한다.
* 널 아님 단언은 코틀린과 자바가 상호 작용하는 경우에 아주 드물게 코틀린이 널 가능성을 제대로 검사하지 못하는 대상이 null이 아님을 알 수 있는 경우를 위해 도입됐다.
* 코드에서 같은 연산에 대해 널 아님 단언을 자주 사용한다면 적절한 단언과 함께 함수를 분리하는 것이 좋다.
  ```
  fun main() {
    val map = mapOf(1 to "one")
    map[1]!!.toUpperCase() == "ONE"
    map.getValue(1).toUpperCase() == "ONE"
    // NullPointerException 발생
    map[2]!!.toUpperCase()
    // NoSuchElementException 발생
    map.getValue(2).toUpperCase()
  }
  ```


> 최적의 코드는 항상 안전한 호출과 자세한 예외를 반환하는 특별한 함수만 사용하며 널 아님 단언 호출은 꼭 필요할 때만 사용해야 한다.
> 널 아님 단언이 자바와 상호 작용하기 위해 추가된 것이지만 자바와 상호 작용할 때 활용할 수 있는 더 나은 방법이 있다.

## 아톰 40. 확장 함수와 널이 될 수 있는 타입
### 확장 함수와 널이 될 수 있는 타입
* s?.f()는 널이 될 수 있는 타입을 암시하며 t.()는 널이 될 수 없는 타입임을 암시하는 것처럼 보이지만 t가 꼭 널이 될 수 없는 타입인 것은 아니다.
* 코틀린 표준 라이브러리는 String 확장 함수를 제공한다.
  * isNullOrEmpty()
    * 수신 Strgin이 null이거나 빈 문자열인지 검사한다.
  * isNullOrBlank()
    * isNullOrEmpty()와 같은 검사를 수행하고, 온전히 공백 문자로만 구성되어 있는지 검사한다.
  * 널이 될 수 있는 타입이지만 isNullOrEmpty(), isNullOrBlank() 호출이 가능한데 String?의 확장 함수로 정의되어 있기 때문이다.
  ```
  fun main() {
    val s1: String? = null
    s1.isNullOrEmpty() == true
    s1.isNullOrBlank() == true

    val s2 = ""
    s2.isNullOrEmpty() == true
    s2.isNullOrBlank() == true

    val s3 = " \t\n"
    s3.isNullOrEmpty() == false
    s3.isNullOrBlank() == true
  }
  ```
  * isNullOrEmpty()를 널이 될 수 있는 파라미터를 받는 비확장 함수로 다시 작성할 수 있다.
    * 널이 될 수 있는 타입이라서 null 여부와 빈 문자열 여부를 검사할 수 있다.
    * 쇼트 서킷을 사용해서 첫번쨰가 true면 두번째 식을 계산하지 않기 때문에 null이여도 NPE가 두번째 식에서 발생하지 않는다.
  ```
  fun isNullOrEmpty(s: String?): Boolean = 
    s == null || s.isEmpty()
  ```
* 확장 함수는 this를 사용해 수신 객체를 표현하며 수신 객체를 널이 될 수 있는 타입으로 지정할 수 있다.
  ```
  fun String?.isNullOrEmpty(): Boolean =
    this == null || isEmpty()
  ```
* 널이 될 수 있는 타입을 확장할 때는 조심해야하는데 수신 객체가 null일 수 있음을 암시하는 경우에는 널이 될 수 있는 타입의 확장함수가 유용하지만 일반적으로는 널이 될 수 없는 보통의 확장을 정의하는 편이 낫다.

> 안전한 호출과 명시적인 검사는 수신 객체의 널 가능성을 더 명백힌 드러내지만 널이 될 수 있는 타입의 확장 함수는 널 가능성을 감추고 코드를 읽는 사람을 혼란스럽게 할 수 있다.

## 아톰 41. 제네릭스 소개
### 제네릭스
* 제네릭스는 `파라미터화한 타입`을 만들며 파라미터화한 타입은 `여러 타입에 대해 작동할 수 있는 컴포넌트`다.
* generic이라는 용어는 여러가지 클래스에 적합한/여러 가지 클래스와 관계있는 뜻이다.
* 프로그래밍에서 제네릭스의 의도는 클래스나 함수를 작성할 때 타입 제약을 느슨하게 해서 프로그래머에게 표현력을 최대로 제공하는 것이다.
* List, Set, Map과 같은 컬렉션 클래스는 다른 객체를 저장하는 객체인데 가장 재사용성이 좋은 클래스로 제네릭스 도입을 촉발시켰다.
  ```
  data class Automobile(val brand: String)

  class RigidHolder(private val a: Automobile) {
    fun getValue() = a
  }

  fun main() {
    val holder = RigidHolder(Automobile("BMW"))
    holder.getValue == Automobile("BMW")
  }
  ```
* 하나의 객체의 타입을 사용하는 것은 재사용성에 그다지 좋지 않은데 하나의 타입밖에 담을 수 없기 때문으로 여러 다른 타입에 대해 각 타입에 맞는 새로운 타입 보관소 클래스를 만들면 좋다.
  * 제네릭 타입을 정의하려면 클래스 이름 뒤, 내부에 하나 이상의 제네릭 플레이스 홀더가 들어가 있는 부등호를 추가해야 한다.
  * T라는 플레이스 홀더는 지금은 알수 없지만 제네릭 클래스 안에서는 일반 타입처럼 쓰인다.
  * [1] GenericHolder는 T 타입의 객체를 저장하며 getValue()는 T 타입의 값을 반환한다.
  * [2], [3], [4] getValue()를 호출할 때 결과 타입이 자동으로 올바른 타입으로 지정된다.
  ```
  data class Automobile(val brand: String)

  class GenericHolder<T>(
    private val vlaue: T // [1]
  ) {
    fun getValue(): T = value
  }

  fun main() {
    val h1 = GenericHolder(Automobile("Ford"))
    val a: Automobile = h1.getValue() // [2]
    a == Automobile("Ford")

    val h2 = GenericHolder(1)
    val i: Int = h2.getValue() // [3]
    i == 1

    val h3 = GenericHolder("Chartreuse")
    val s: String = h3.getValue() // [4]
    s == "Chartreuse"
  }
  ```

### 유니버셜 타입
* 제네릭스를 사용하지 않고 유니버셜 타입을 사용해서 해결할 수도 있다.
* 유니버셜 타입은 모든 타입의 부모 타입으로 코틀린에서는 Any가 유니버셜 타입이다.
* Any 타입은 모든 타입의 인자를 허용한다.
  * 간단한 경우에는 Any가 동작하지만 구체적인 타입이 필요해지면 제대로 작동하지 않는다.
  * 객체를 Any 타입으로 대입하면 추적할 수 없기 때문으로 제네릭을 사용하면 실제 컬렉션에 Dog를 담고 있다는 정보를 유지할 수 있다.
  ```
  class GenericHolder<T>(
    private val vlaue: T
  ) {
    fun getValue(): T = value
  }

  class AnyHolder(private val value: Any) {
    fun getValue(): Any = value
  }

  class Dog {
    fun bark() = "Ruff!"
  }

  fun main() {
    val holder = AnyHolder(Dog())
    val any = holder.getValue()
    // 컴파일 에러 발생
    // any.bark()

    val genericHolder = GenericHolder(Dog())
    val dog = genericHolder.getValue()
    dog.bark() == "Ruff!"
  }
  ```

### 제네릭 함수
* 제네릭 함수를 정의할 때 부등호로 둘러싼 제네릭 타입 파라미터를 함수 이름 앞에 붙인다.
  ```
  fun <T> identity(arg: T): T = arg
  
  class Dog {
    fun bark() = "Ruff!"
  }

  fun main() {
    identity("Yellow") == "Yellow"

    identity(1) == 1

    val d: Dog = identity(Dog())
    d.bark == "Ruff!"
  }
  ```
* 코틀린 표준 라이브러리는 컬렉션을 위한 여러 제네릭 함수를 제공하며 제네릭 확장 함수를 쓰려면 수신 객체 앞에 제네릭 명세를 위치시켜야한다.
  * [1] Int?가 반환된다.
  * [2] String?가 반환된다.
  * [1], [2] 모두 식별자 타입에 ?를 요구하는데 ?를 제거하면 컴파일 에러가 발생한다.
  ```
  fun <T> List<T>.first(): T {
    if (isEmpty())
      throw NoSuchElementException("Empty List")
    return this[0]
  }

  fun <T> List<T>.firstOrNull(): T? =
    if (isEmpty()) null else this[0]

  fun main() {
    listOf(1, 2, 3).first() == 1
    
    val i: Int? = listOf(1, 2, 3).firstOrNull() // [1]
    i == 1

    val s: String? = listOf<String>().firstOrNull() // [2]
    s == null
  }
  ```

## 아톰 42. 확장 프로퍼티
### 확장 프로퍼티
* 확장 함수를 정의할 수 있는 것처럼 확장 프로퍼티를 정의할 수도 있다.
* 확장 대상 타입이 함수나 프로퍼티 이름 바로 앞에 온다.
  ```
  fun ReceiverType.extensionFunction() { ... }
  val ReceiverType.extensionProperty: PropType
    get() { ... }
  ```
* 확장 프로퍼티에는 커스텀 게더가 필요하며 확장 프로퍼티에 접근할 때마다 프로퍼티 값이 계산된다.
  ```
  val String.indices: IntRange
    get() = 0 until length

  fun main() {
    "abc".indices == 0..2
  }
  ```
* 파라미터가 없는 확장 함수는 항상 확장 프로퍼티로 변환할 수 있지만 `기능이 단순하고 가독성을 향상키시는 경우에만 프로퍼티를 권장`한다.
* 제네릭 확장 프로퍼티를 정의할 수도 있다.
  ```
  val <T> List<T>.firstOrNull: T?
    get() = if (isEmpty()) null else this[0]
  
  fun main() {
    listOf(1, 2, 3).firstOrNull == 1
    listOf<String>().firstOrNull == null
  }
  ```
* 코틀린 스타일 가이드는 함수가 예외를 던질 경우 프로퍼티보다는 함수를 사용하는 것을 권장한다.
* 제네릭 인자 타입을 사용하지 않으면 *로 대신할 수 있는데 이를 `스타 프로젝션`이라고 한다
  * List<*>를 사용하면 담긴 원소 타입 정보를 모두 잃어버려 List<*>에서 얻은 원소는 Any?에만 대입할 수 있다.
  * List<*>에 저장된 값이 널이 될 수 있는 타입인지에 대해서는 아무 정보가 없어서 Any? 타입의 변수에만 대입할 수 있다.
  ```
  val List<*>.indices: IntRange
    get() = 0 until size
  
  fun main() {
    listOf(1) == 0..0
    listOf('a', 'b', 'c', 'd').indices 0..3
    
    val list: List<*> = listOf(1, 2)
    val any: Any? = list[0]
    any == 1
  }
  ```

## 아톰 42 break와 continue
### break, continue
* 초기에는 여러 가지 선택을 표현할 때 코드 여러 위치로 직접 점프하는 방식으로 이를 구현했는데 조건 없이 점프하는 명령이 복잡하고 유지보수하기 어려운 코드를 만들어냈고 그 이후 상당수의 후속 언어에서는 무조건 점프를 채택하지 않았다.
* 코틀린은 break, continue를 사용해 `제한적인 점프`를 제공하며 for, while, do-while 루프 요소와 엮여 이런 루프 안에서만 제헌적으로 사용할 수있다.
  * continue는 루프 시작 위치로만 점프
  * break는 루프의 끝으로만 점프
* 실전 코틀린 코드에서 break, continue를 쓰는 일은 드물며 가끔 유용하지만 코틀린이 더 나은 메커니즘을 제공한다.

### 레이블
* 단순한 break, continue는 자신이 속한 범위보다 더 밖으로 점프할 수 없지만 레이블을 사용해서 자신을 둘러싼 여러 루프의 경계 중 한군데로 점프할 수 있다.
* `레이블@`와 같이 @을 사용해 레이블을 붙을 수 있다.
  ```
  fun main() {
    val strings = mutableListOf<String>()
    outer@ for(c in 'a'..'e') {
      for (i in 1..9) {
        if (i == 5) continue@outer
        if ("$c$i" == "c3") break@outer
        strings.add("$c$i")
      }
    }
    strings == listOf("a1", "a2", "a3", "a4", "b1", "b2", "b3", "b4", "c1", "c2")
  }
  ```
* 레이블은 while, do-while에서도 사용할 수 있다.

> break, continue을 사용하면 코드가 복잡해지고 유지보수가 어려워질 수 있기 때문에 이터레이션 조건을 명시적으로 작성하거나 코드 구조를 재구성해 새로운 함수를 도입할 수도 있다.
> 여러 가지 다른 접근 방법을 찾아보고 간단하고 읽기 좋은 해법을 선택해야하며 보통 간단하고 읽기 좋은 해법에는 break, continue가 없는 경우가 많다.