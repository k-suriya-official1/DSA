# Two Pointer Technique Documentation

## Introduction to Two Pointers

The two pointer technique is a powerful approach for optimizing algorithms that work with arrays, linked lists, and strings. It significantly improves time complexity, often reducing it from O(n²) to O(n).

### Key Concepts

The two pointer technique involves using two reference points to traverse through a data structure, rather than using nested loops. This approach is especially effective for:
- Sorted arrays
- Finding pairs that satisfy specific conditions
- Problems involving subarrays or containers
- Removing duplicates

## Implementation Patterns

There are two main patterns for using the two pointer technique:

### Pattern 1: Opposite Directional Movement
- Start pointer begins at the array's start (index 0)
- End pointer begins at the array's end (index length-1)
- Pointers move toward each other based on conditions
- Traversal ends when pointers meet or cross

```
┌───┬───┬───┬───┬───┬───┬───┐
│ 2 │ 3 │ 4 │ 6 │ 8 │ 10│ 12│
└───┴───┴───┴───┴───┴───┴───┘
  ↑                       ↑
start                    end
```

### Pattern 2: Same Directional Movement
- Both pointers start near the beginning
- One pointer moves faster or only under certain conditions
- Creates a "window" or segment between pointers

```
┌───┬───┬───┬───┬───┬───┬───┐
│ 0 │ 0 │ 1 │ 1 │ 2 │ 3 │ 3 │
└───┴───┴───┴───┴───┴───┴───┘
  ↑       ↑
slow     fast
```

## Two Pointers vs. Nested Loops

### Nested Loops Approach:
```java
public int[] twoSumBruteForce(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            if (nums[i] + nums[j] == target) {
                return new int[] {i + 1, j + 1};  // 1-indexed
            }
        }
    }
    return new int[] {};
}
```

- Time Complexity: O(n²)
- Space Complexity: O(1)
- Checks every possible pair of elements

### Two Pointer Approach:
```java
public int[] twoSumTwoPointers(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left < right) {
        int currentSum = nums[left] + nums[right];
        if (currentSum == target) {
            return new int[] {left + 1, right + 1};  // 1-indexed
        } else if (currentSum < target) {
            left++;
        } else {
            right--;
        }
    }
    return new int[] {};
}
```

- Time Complexity: O(n)
- Space Complexity: O(1)
- Makes intelligent decisions about which pointer to move

## Example Problems

### Example 1: Two Sum II (Sorted Array)
**Problem:** Given a sorted array and a target value, find two numbers that add up to the target.

**Two Pointer Solution:**
```java
public int[] twoSum(int[] numbers, int target) {
    int left = 0, right = numbers.length - 1;
    
    while (left < right) {
        int currentSum = numbers[left] + numbers[right];
        
        if (currentSum == target) {
            return new int[] {left + 1, right + 1};  // 1-indexed
        } else if (currentSum < target) {
            left++;  // Need to increase sum
        } else {
            right--;  // Need to decrease sum
        }
    }
    return new int[] {};  // No solution found
}
```

**Visual Explanation:**
```
Target: 11

┌───┬───┬───┬───┬───┬───┐
│ 2 │ 3 │ 4 │ 6 │ 8 │ 9 │
└───┴───┴───┴───┴───┴───┘
  ↑                   ↑
left                right

Step 1: 2+9=11 (Target found! Return [1,6])
```

### Example 2: Container With Most Water
**Problem:** Given n non-negative integers representing heights of bars, find the container that holds the most water.

**Two Pointer Solution:**
```java
public int maxArea(int[] height) {
    int maxWater = 0;
    int left = 0, right = height.length - 1;
    
    while (left < right) {
        // Calculate width × height
        int width = right - left;
        int containerHeight = Math.min(height[left], height[right]);
        int area = width * containerHeight;
        maxWater = Math.max(maxWater, area);
        
        // Move the pointer with smaller height
        if (height[left] < height[right]) {
            left++;
        } else {
            right--;
        }
    }
    return maxWater;
}
```

**Visual Explanation:**
```
Heights: [1, 8, 6, 2, 5, 4, 8, 3, 7]

Step 1:
┌───┬───┬───┬───┬───┬───┬───┬───┬───┐
│ 1 │ 8 │ 6 │ 2 │ 5 │ 4 │ 8 │ 3 │ 7 │
└───┴───┴───┴───┴───┴───┴───┴───┴───┘
  ↑                               ↑
left                            right

Area = 8 * min(1,7) = 8

Step 2: (Move left since height[left] < height[right])
┌───┬───┬───┬───┬───┬───┬───┬───┬───┐
│ 1 │ 8 │ 6 │ 2 │ 5 │ 4 │ 8 │ 3 │ 7 │
└───┴───┴───┴───┴───┴───┴───┴───┴───┘
      ↑                           ↑
     left                       right

Area = 7 * min(8,7) = 49 (new max)
```

### Example 3: Remove Duplicates from Sorted Array
**Problem:** Remove duplicates from a sorted array in-place.

**Two Pointer Solution:**
```java
public int removeDuplicates(int[] nums) {
    if (nums.length == 0) {
        return 0;
    }
    
    // Position for next unique element
    int uniquePos = 1;
    
    for (int i = 1; i < nums.length; i++) {
        if (nums[i] != nums[i-1]) {
            nums[uniquePos] = nums[i];
            uniquePos++;
        }
    }
    
    return uniquePos;
}
```

**Visual Explanation:**
```
Original: [0,0,1,1,2,3,3]

Step 1:
┌───┬───┬───┬───┬───┬───┬───┐
│ 0 │ 0 │ 1 │ 1 │ 2 │ 3 │ 3 │
└───┴───┴───┴───┴───┴───┴───┘
      ↑
      i
  unique_pos=1

Step 2: Found new unique value '1'
┌───┬───┬───┬───┬───┬───┬───┐
│ 0 │ 1 │ 1 │ 1 │ 2 │ 3 │ 3 │
└───┴───┴───┴───┴───┴───┴───┘
          ↑
          i
      unique_pos=2

Final: [0,1,2,3,x,x,x] with 4 unique elements
```

## My Own Example: Find Triplet Sum

**Problem:** Given a sorted array, find if there exists a triplet (a, b, c) such that a + b + c = target.

**Two Pointer Solution:**
```java
public int[] findTriplet(int[] arr, int target) {
    int n = arr.length;
    
    for (int i = 0; i < n - 2; i++) {
        // Skip duplicates for first element
        if (i > 0 && arr[i] == arr[i-1]) {
            continue;
        }
        
        int left = i + 1;
        int right = n - 1;
        
        while (left < right) {
            int currentSum = arr[i] + arr[left] + arr[right];
            
            if (currentSum == target) {
                return new int[] {arr[i], arr[left], arr[right]};
            } else if (currentSum < target) {
                left++;
            } else {
                right--;
            }
        }
    }
    return new int[] {};  // No solution found
}
```

**Visual Explanation:**
```
Array: [1, 2, 4, 6, 8, 10]
Target: 14

For i=0 (value=1):
┌───┬───┬───┬───┬───┬───┐
│ 1 │ 2 │ 4 │ 6 │ 8 │ 10│
└───┴───┴───┴───┴───┴───┘
  ↑   ↑               ↑
  i  left           right

1+2+10=13 (less than target, move left)
1+4+10=15 (greater than target, move right)
1+4+8=13 (less than target, move left)
1+6+8=15 (greater than target, move right)

For i=1 (value=2):
┌───┬───┬───┬───┬───┬───┐
│ 1 │ 2 │ 4 │ 6 │ 8 │ 10│
└───┴───┴───┴───┴───┴───┘
      ↑   ↑           ↑
      i  left       right

2+4+10=16 (greater than target, move right)
2+4+8=14 (equal to target!)

Return [2,4,8]
```

## Time & Space Complexity Analysis

### With Two Pointers:
- **Time Complexity:** O(n) for most two-pointer solutions (may be O(n²) for triplet problems that use an outer loop)
- **Space Complexity:** O(1) since we only use a constant amount of extra space

### Without Two Pointers (Brute Force):
- **Time Complexity:** Typically O(n²) or O(n³) depending on the problem
- **Space Complexity:** O(1) to O(n) depending on implementation

## When to Use Two Pointers

Consider using the two pointer technique when:
1. Working with sorted arrays or linked lists
2. Searching for pairs, triplets, or subarrays
3. Looking to optimize a solution from O(n²) to O(n)
4. Dealing with problems about:
   - Finding elements that sum to a target
   - Container/trap problems
   - Removing duplicates
   - Palindrome verification
   - Cycle detection in linked lists

By mastering the two pointer technique, you can significantly improve the efficiency of many algorithms and perform well in coding interviews.
## References


- [Master the 2-Pointers Coding Pattern: Techniques and Examples for IT Jobs & FAANG Success](https://youtu.be/Fue0SgYBxrc?si=PqhZx0yZf94oNCaM)