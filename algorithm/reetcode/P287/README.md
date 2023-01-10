## 287. Find the Duplicate Number

![e2b21cfc-074b-4b1c-b01e-2b50926d815f](https://user-images.githubusercontent.com/38122225/211435869-50999fad-e8e5-477c-96a0-a6d82d6df4b5.png)

정수 배열이 주어지면 반복되는 숫자를 찾는 문제인데 `You must solve the problem without modifying the array nums and uses only constant extra space.` 배열을 수정하지 않고 문제를 해결해야한다.

처음에는 배열을 수정하지 않는 의미를 추가 메모리 공간을 사용하지 말라는 의미로 해석해서 단순 이중 for문으로 문제를 해결했다. 이런 경우 최악의 경우 시간 복잡도는 O(n²)로 속도가 처참하다.

```
public int findDuplicate(int[] nums) {
    int result = 0;

    for (int i = 0; i < nums.length - 1; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            if (nums[i] == nums[j]) {
                result = nums[i];
                break;
            }
        }
    }

    return result;
}
```

다른 방식을 생각했을땐 2가지 방법이 떠올랐는데 정렬 후 for문을 통해서 문제를 해결할지 HashSet을 통해서 문제를 해결할지 정렬의 경우 시간 복잡도는 O(n log n)으로 HashSet을 이용한 O(n) 방식이 좀 더 올바른 문제 풀이라고 생각했고 아래의 코드로 문제를 해결했다.

```
public int findDuplicate(int[] nums) {
    Set<Integer> set = new HashSet<Integer>();

    for (int num : nums) {
        if (set.contains(num)) {
            return num;
        }
        set.add(num);
    }

    return 0;
}
```
