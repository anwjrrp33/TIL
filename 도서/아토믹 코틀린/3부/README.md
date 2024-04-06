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
### 구조 분해 선언