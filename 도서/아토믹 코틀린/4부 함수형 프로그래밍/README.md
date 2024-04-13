## 아톰 44. 람다
### 람다
* 람다를 사용하면 이해하기 쉬운 간결한 코드를 작성할 수 있다.
* 람다는 부가적인 장식 덜 들어간 함수로 이름이 없고 함수 생성에 필요한 최소한의 코드만 필요하며, 다른 코드에 람다를 직접 삽입할 수 있다.
* map()은 List와 같은 컬렉션에 작용하는 함수로 map()의 파라미터는 컬렉션의 모든 원소에 적용할 변환 함수다.
  * result를 초기화할 때 중괄호 사이에 쓴 코드가 람다이며 파라미터 목록과 함수 본문 사이에는 ->가 들어간다.
  * 함수 본문은 하나 이상의 식이며 식이 여럿인 경우 마지막 식이 람다의 결과가 된다.
  ```
  fun main() {
    val list = listOf(1, 2, 3, 4)
    val result = list.map({ n: Int -> "[$n]" })
    // 출력 true
    println(result == listOf("[1]", "[2]", "[3]", "[4]"))
  }
  ```
* 보통 람다가 필요한 위치에 바로 람다를 적는데 이는 람다의 타입을 추론할 수 있다는 의미이다.
  ```
  fun main() {
    val list = listOf(1, 2, 3, 4)
    val result = list.map({ n -> "[$n]" })
    // 출력 true
    println(result == listOf("[1]", "[2]", "[3]", "[4]"))
  }
  ```
* 파라미터가 하나인 경우 코틀린은 자동으로 파라미터 이름을 it으로 만들며 더 이상 ->를 사용할 필요가 없다는 의미이다.
  ```
  fun main() {
    val list = listOf(1, 2, 3, 4)
    val result = list.map({ "[$it]" })
    // 출력 true
    println(result == listOf("[1]", "[2]", "[3]", "[4]"))
  }
  ```
* 함수의 파라미터가 람다뿐이면 람다 주변의 괄호를 없앨 수 있어서 깔끔하게 코드를 적을 수 있다.
  ```
  fun main() {
    val list = listOf('a', 'b', 'c', 'd')
    val result = list.map { "[${it.toUpperCase()}]" }
    // 출력 true
    println(result == listOf("[A]", "[B]", "[C]", "[D]"))
  }
  ```
* 함수가 여러 파라미터를 받고 람다가 마지막 파라미터인 경우에는 람다를 인자 목록을 감싼 괄호 다음에 위치시킬 수 있다.
  ```
  fun main() {
    val list = listOf(9, 11, 23, 32)
    // 출력 true
    println(list.joinToString(" ") { "[$it]" } == "[9] [11] [23] [32]")
  }
  ```
* 람다를 이름 붙은 인자로 호출하고 싶다면 인자 목록을 감싸는 괄호 안에 람다를 위치시켜야 한다.
  ```
  fun main() {
    val list = listOf(9, 11, 23, 32)
    list.joinToString(
        separator = " ",
        transform = { "[$it]" }
    ) == "[9], [11], [23], [32]"
  }
  ```
* 파라미터가 둘 이상있는 람다 구문도 존재한다.
  ```
  fun main() {
    val list = listOf('a', 'b', 'c')
    list.mapIndexed { index, element -> 
      "[$index: $element]"
    } == listOf("[0: a]", "[1: b]", "[2: c]")
  }
  ```
* 람다가 특정 인자를 사용하지 않는 경우 밑줄을 사용할 수 있는데 밑줄을 쓰면 람다가 무슨 인자를 사용하지 않는다는 컴파일러 경고를 무시할 수 있다.
  ```
  fun main() {
    val list = listOf('a', 'b', 'c')
    list.mapIndexed { index, _ -> 
      "[$index]"
    } == listOf("[0]", "[1]", "[2]")
  }
  ```
* 람다에 파라미터가 없을 수도 있기 때문에 파라미터가 없다는 사실을 강조하기 위해서 화살표를 남겨둘 수 있지만 코틀린 스타일 가이드에서 화살표를 사용하지 말라고 권장한다.
  ```
  fun main() {
    run { -> println("A Lambda") }
    run { println("Without args") }
  }
  ```
* 표준 라이브러리 함수 run()은 자신에게 인자로 전달된 람다를 호출하기만 한다.

> 일반 함수를 쓸 수 있는 모든 곳에 람다를 쓸 수 있지만 람다가 너무 복잡하거나 크면  이름 붙은 함수를 정의해서 사용하는 것이 낫다.

## 아톰 45. 람다의 중요성