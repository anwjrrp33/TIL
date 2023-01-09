## 26. Remove Duplicates from Sorted Array

![024 - Remove Duplicates from Sorted Array - LeetCode - leetcode com](https://user-images.githubusercontent.com/38122225/211257478-fd631c0d-23f6-47f2-9897-dae07a9b8c70.png)

해당 문제의 주의점은 `Do not allocate extra space for another array. You must do this by modifying the input array in-place with O(1) extra memory` 다른 배열에 메모리 공간을 부여하지않고 O(1)의 추가 메모리가 있는 입력 배열을 수정해서 제자리에서 수행해야한다.

처음에는 중복된 배열의 값을 삭제해야 했기 때문에 Set을 사용해서 문제를 풀었지만 위와 같은 문제의 요구사항을 보고 코드를 수정했다.

입력 배열을 수정해서 제자리에서 수행해야하기 때문에 `투 포인터 알고리즘`을 사용해서 문제를 해결했다.

```
class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int index = 1;

        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] < nums[i + 1]) {
                nums[index] = nums[i + 1];
                index++;
            }
        }

        return index;
    }
}
```
