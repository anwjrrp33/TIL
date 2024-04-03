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