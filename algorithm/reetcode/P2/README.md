## [2. Add Two Numbers](https://leetcode.com/problems/add-two-numbers/)

![2. Add Two Numbers](https://user-images.githubusercontent.com/38122225/211732383-3429daee-188f-40ee-84fb-af5267307248.png)

Linked List로 구현된 임의의 객체 ListNode 2개의 숫자 합을 구하는 문제이다. ListNode의 값은 반대로 정렬되있고 결과 값 또한 ListNode로 구현해야한다.

주의사항은 Linked List의 range 값이 1~100이기 때문에 `BigInteger`를 사용해서 첫 번째 코드를 작성했다. 하지만 아래와 같이 코드를 작성할 경우 속도가 매우 느리다. 합을 구하기 위해서 reverse를 사용하고 매번 ListNode 객체를 생성하고 형 변환 또한 자주 일어난다.

### 변경 전 코드

```
import java.math.BigInteger;
import java.util.Objects;

class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        BigInteger number = Number(l1).add(Number(l2));
        return createListNode(number);
    }

    private static BigInteger Number(ListNode listNode) {
        StringBuilder sb = new StringBuilder();

        while (true) {
            sb.append(listNode.val);

            if (Objects.isNull(listNode.next)) {
                break;
            }

            listNode = listNode.next;
        }

        return new BigInteger(String.valueOf(sb.reverse()));
    }

    private static ListNode createListNode(BigInteger number) {
        StringBuilder sb = new StringBuilder().append(String.valueOf(number));
        ListNode listNode = new ListNode(Integer.parseInt(String.valueOf(sb.charAt(0))));

        for (int i = 1; i < sb.length(); i++) {
            listNode = new ListNode(Integer.parseInt(String.valueOf(sb.charAt(i))), listNode);
        }

        return listNode;
    }
}
```

두번 째로 작성한 코드는 이러한 문제점을 해결하기 위해서 구현했다 어처피 두 숫자의 합을 계산하기 위해서 어처피 일의 숫자부터 계산을 한 후 넘어가는 숫자를 다음 노드에 넘겨주는 방식으로 구현했다. 이 코드의 핵심은 `call by reference`를 활용해서 ListNode 값을 생성한다.

### 변경 후 코드

```
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode node = new ListNode();
        ListNode temp = node;
        int carry = 0;

        while (l1 != null || l2 != null || carry != 0) {
            int sum = 0;

            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }

            sum += carry;
            carry = sum / 10;

            temp.next = new ListNode(sum % 10);
            temp = temp.next;
        }

        return node.next;
    }
}
```
