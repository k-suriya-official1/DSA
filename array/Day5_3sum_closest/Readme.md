# 3Sum Closest Problem - Detailed Notes

## Table of Contents
1. [Introduction](#introduction)
2. [Problem Statement](#problem-statement)
3. [Solution Approach](#solution-approach)
4. [Algorithm Walkthrough](#algorithm-walkthrough)
5. [Code Analysis](#code-analysis)
6. [Visual Guide to the Algorithm](#visual-guide-to-the-algorithm)
7. [Relationship with Original 3Sum Problem](#relationship-with-original-3sum-problem)
8. [Time and Space Complexity Analysis](#time-and-space-complexity-analysis)
9. [Two-Pointer Technique Explained](#two-pointer-technique-explained)
10. [Sorting as an Optimization Strategy](#sorting-as-an-optimization-strategy)
11. [Complete Walkthrough Example](#complete-walkthrough-example)
12. [Common Pitfalls and Edge Cases](#common-pitfalls-and-edge-cases)
13. [Problem Variations](#problem-variations)
14. [Summary and Key Takeaways](#summary-and-key-takeaways)

## Introduction

The 3Sum Closest problem is a variation of the classic 3Sum problem and is frequently asked in coding interviews. This problem tests understanding of array manipulation, searching algorithms, and optimization techniques. At its core, it requires finding three numbers in an array that, when summed, are closest to a given target value.

### What is a "Sum" Problem?

Sum problems involve finding combinations of elements in an array that add up to a specific value or come close to it. These problems test your ability to:
- Efficiently search through combinations
- Use techniques to avoid redundant calculations
- Apply pattern recognition to optimize solutions

### Key Techniques for Sum Problems

1. **Sorting**: Arranges elements to enable systematic traversal
2. **Two-Pointer Technique**: Uses two indices that move toward each other
3. **Fixed Element + Search**: Fix one element and search for others that meet criteria

## Problem Statement

**LeetCode 16: 3Sum Closest**

Given an integer array `nums` of length n and an integer `target`, find three integers in `nums` such that the sum is closest to the target. Return the sum of the three integers.

### Examples:

**Example 1:**
- Input: `nums = [-1,2,1,-4]`, `target = 1`
- Output: `2`
- Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2)

**Example 2:**
- Input: `nums = [0,0,0]`, `target = 1`
- Output: `0`
- Explanation: The sum that is closest to the target is 0. (0 + 0 + 0 = 0)

**Custom Example:**
- Input: `nums = [1,3,4,7,10]`, `target = 15`
- Output: `14`
- Explanation: The sum that is closest to the target is 14. (3 + 4 + 7 = 14)

### Constraints:
- 3 <= nums.length <= 500
- -1000 <= nums[i] <= 1000
- -10^4 <= target <= 10^4

## Solution Approach

### Brute Force Approach

The naive approach would be to consider all possible triplets in the array and find the one with the sum closest to the target. This would require three nested loops, resulting in a time complexity of O(n³).

```java
// Brute Force Approach (not recommended)
public int threeSumClosestBruteForce(int[] nums, int target) {
    int n = nums.length;
    int closestSum = nums[0] + nums[1] + nums[2];
    
    for (int i = 0; i < n - 2; i++) {
        for (int j = i + 1; j < n - 1; j++) {
            for (int k = j + 1; k < n; k++) {
                int sum = nums[i] + nums[j] + nums[k];
                if (Math.abs(target - sum) < Math.abs(target - closestSum)) {
                    closestSum = sum;
                }
            }
        }
    }
    
    return closestSum;
}
```

### Optimized Approach

We can optimize this using sorting and the two-pointer technique:

1. **Sort the array** first (O(n log n))
2. **Fix one element** and use two pointers to find the other two elements
3. **Update the result** whenever we find a closer sum

This approach reduces the time complexity to O(n²).

## Algorithm Walkthrough

1. Sort the input array in ascending order
2. Initialize `resultSum` with the sum of the first three elements
3. Initialize `minDifference` to keep track of the minimum difference found
4. Iterate through the array:
   - For each element at index `i`, use two pointers (`left` and `right`) for the remaining elements
   - Calculate the current sum = `nums[i] + nums[left] + nums[right]`
   - If the sum equals the target, return the target (exact match found)
   - If the sum is less than the target, increment the left pointer
   - If the sum is greater than the target, decrement the right pointer
   - Update `resultSum` and `minDifference` if the current sum is closer to the target
5. Return the closest sum found

## Code Analysis

```java
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        // Sort the array for the two-pointer technique
        Arrays.sort(nums);
        
        // Initialize result with the sum of first three elements
        int resultSum = nums[0] + nums[1] + nums[2];
        int minDifference = Integer.MAX_VALUE;
        
        // Iterate through the array, fixing the first element
        for (int i = 0; i < nums.length - 2; i++) {
            // Two pointers for the remaining array
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                // Calculate current sum
                int sum = nums[i] + nums[left] + nums[right];
                
                // If we found exact match, return immediately
                if (sum == target) 
                    return target;
                
                // Adjust pointers based on comparison with target
                if (sum < target) 
                    left++;
                else 
                    right--;
                
                // Update result if this sum is closer to target
                int diffToTarget = Math.abs(sum - target);
                if (diffToTarget < minDifference) {
                    resultSum = sum;
                    minDifference = diffToTarget;
                }
            }
        }
        
        return resultSum;
    }
}
```

### Line-by-Line Analysis:

1. **Sorting**: `Arrays.sort(nums)` - Enables the two-pointer approach (O(n log n))
2. **Initialization**:
   - `resultSum = nums[0] + nums[1] + nums[2]` - Starting with the smallest possible sum
   - `minDifference = Integer.MAX_VALUE` - Using MAX_VALUE ensures the first comparison will update these values
3. **Main Loop**: `for (int i = 0; i < nums.length - 2; i++)` - Fixes the first element, leaving at least 2 elements for pointers
4. **Two Pointers**:
   - `left = i + 1` - Start right after the fixed element
   - `right = nums.length - 1` - Start at the end of the array
5. **While Loop**: `while (left < right)` - Continue as long as pointers don't cross
6. **Sum Calculation**: `sum = nums[i] + nums[left] + nums[right]` - Current triplet sum
7. **Early Return**: `if (sum == target) return target` - Exact match found, no need to continue
8. **Pointer Adjustment**:
   - If sum < target: Move left pointer right to increase sum
   - If sum > target: Move right pointer left to decrease sum
9. **Update Logic**:
   - Calculate absolute difference: `diffToTarget = Math.abs(sum - target)`
   - Update if closer: `if (diffToTarget < minDifference)`
10. **Return Result**: `return resultSum` - The closest sum found

## Visual Guide to the Algorithm

### Step 1: Sort the Array
```
Original: [-1, 2, 1, -4]  →  Sorted: [-4, -1, 1, 2]
```

### Step 2: Fix First Element and Use Two Pointers

```
Iteration with first element = -4:
                  i    left         right
                  ↓     ↓            ↓
Array:           [-4,   -1,    1,    2]
                        ↑            ↑
                       left        right

Sum = -4 + (-1) + 2 = -3  (diff from target 1 = 4)
Move left pointer right to increase sum
```

### Step 3: Update Pointers Based on Comparison

```
                  i           left   right
                  ↓            ↓      ↓
Array:           [-4,   -1,    1,     2]
                                ↑      ↑
                              left   right

Sum = -4 + 1 + 2 = -1  (diff from target 1 = 2)
This is closer than previous (-3), so update resultSum = -1
```

### Step 4: Repeat Process with Next Fixed Element

```
                       i    left  right
                       ↓     ↓      ↓
Array:           [-4,  -1,    1,    2]
                        ↑            ↑
                       i            

Sum = -1 + 1 + 2 = 2  (diff from target 1 = 1)
This is closer than previous (-1), so update resultSum = 2
```

## Number Line Visualization for "Closest" Concept

To understand what "closest" means in this context, consider a number line visualization:

```
Target = 1
                      ↓
|----|----|----|----|----|----|----|----|----|
-4   -3   -2   -1    0    1    2    3    4

Possible triplet sums:
1. -4 + (-1) + 2 = -3 (difference = 4)
2. -4 + 1 + 2 = -1 (difference = 2)
3. -1 + 1 + 2 = 2 (difference = 1) ← Closest to target
```

The closest sum is the one with the smallest absolute difference from the target.

## Relationship with Original 3Sum Problem

### Original 3Sum Problem:
- Find all triplets in the array such that `nums[i] + nums[j] + nums[k] = 0`
- Return all unique triplets that sum to zero

### 3Sum Closest Problem:
- Find three integers such that their sum is closest to a given target
- Return the sum of these three integers

### Compare the Code:

**Original 3Sum:**
```java
public List<List<Integer>> threeSum(int[] nums) {
    if (nums == null || nums.length < 3) return new ArrayList<>();
    
    Arrays.sort(nums);  // Sort the array
    Set<List<Integer>> result = new HashSet<>();
    
    for (int i = 0; i < nums.length - 2; i++) {
        int left = i + 1;
        int right = nums.length - 1;
        
        while (left < right) {
            int sum = nums[i] + nums[left] + nums[right];
            
            if (sum == 0) {
                result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                left++;
                right--;
            } else if (sum < 0) {
                left++;
            } else {
                right--;
            }
        }
    }
    
    return new ArrayList<>(result);
}
```

**3Sum Closest:**
```java
public int threeSumClosest(int[] nums, int target) {
    Arrays.sort(nums);
    int resultSum = nums[0] + nums[1] + nums[2];
    int minDifference = Integer.MAX_VALUE;
    
    for (int i = 0; i < nums.length - 2; i++) {
        int left = i + 1;
        int right = nums.length - 1;
        
        while (left < right) {
            int sum = nums[i] + nums[left] + nums[right];
            
            if (sum == target) 
                return target;
            
            if (sum < target) 
                left++;
            else 
                right--;
            
            int diffToTarget = Math.abs(sum - target);
            if (diffToTarget < minDifference) {
                resultSum = sum;
                minDifference = diffToTarget;
            }
        }
    }
    
    return resultSum;
}
```

### Key Structural Similarities:
1. Both use sorting as a prerequisite
2. Both employ a loop to fix one element
3. Both use the two-pointer technique to find the other elements
4. Both have the same core decision logic for pointer movement:
   - If sum < target: move left pointer right
   - If sum > target: move right pointer left

### Key Implementation Differences:
1. **Result Format**: 
   - 3Sum: Returns all unique triplets (List<List<Integer>>)
   - 3Sum Closest: Returns a single value (int)
2. **Comparison Logic**:
   - 3Sum: Compares sum to exactly 0
   - 3Sum Closest: Finds minimum difference from target
3. **Early Termination**:
   - 3Sum Closest has early termination if exact match is found
4. **Duplicates Handling**:
   - 3Sum uses a Set to remove duplicates
   - 3Sum Closest doesn't need to handle duplicates as we only return one sum

## Time and Space Complexity Analysis

### Time Complexity Analysis:
- **Sorting**: O(n log n)
- **Two nested loops**: O(n²)
- **Overall**: O(n²) as the nested loops dominate

### Space Complexity:
- O(1) - Constant extra space (excluding the space for sorting)
- The sorting algorithm might require O(log n) space for the call stack

## Two-Pointer Technique Explained

The two-pointer technique is a common approach used for solving problems involving sorted arrays. It's particularly useful for problems that require finding pairs or combinations of elements that satisfy certain conditions.

### How It Works:
1. After fixing the first element `nums[i]`, we need to find two more elements that make the sum closest to target
2. We use two pointers:
   - `left` starting at `i+1`
   - `right` starting at the end of the array
3. Based on the current sum compared to target:
   - If sum < target: Move `left` pointer right to increase the sum
   - If sum > target: Move `right` pointer left to decrease the sum
   - If sum == target: Return target immediately (exact match found)

### Two-Pointer Technique Diagram:

```
Fixed Element: -1
Target: 1

Step 1: left=1, right=3
                Fixed   left       right
                  ↓      ↓           ↓
Array:           [-1,    1,     2,    4]
                         ↑           ↑
                       left        right
Sum = -1 + 1 + 4 = 4
|4 - 1| = 3
Move right pointer left since sum > target

Step 2: left=1, right=2
                Fixed   left   right
                  ↓      ↓       ↓
Array:           [-1,    1,     2,    4]
                         ↑       ↑
                       left    right
Sum = -1 + 1 + 2 = 2
|2 - 1| = 1
This is the closest so far
```

### Why It Works:
- When the array is sorted, moving the left pointer increases the sum, and moving the right pointer decreases it
- This allows us to systematically explore all possible combinations without redundancy
- The approach reduces time complexity from O(n²) for nested loops to O(n) for the two-pointer part

## Sorting as an Optimization Strategy

Sorting is a key prerequisite for the two-pointer technique in this problem. Here's why it's essential:

### Why Sorting is Essential:
1. **Enables Systematic Search**: With a sorted array, we can make intelligent decisions about which direction to move our pointers
2. **Prevents Redundant Checks**: We can avoid checking the same combinations multiple times
3. **Allows Directional Movement**: Moving left increases the sum, moving right decreases it

### Visual Explanation of Sorting Benefit:

**Unsorted Array**: [3, -1, 5, 0, -2]
- If we want to find a sum close to 0, which two elements should we choose with -1?
- We would need to check all combinations: (-1,3), (-1,5), (-1,0), (-1,-2)

**Sorted Array**: [-2, -1, 0, 3, 5]
- With -1 as our fixed element, we can place pointers at the start and end
- If sum is too small, we move left pointer right to increase sum
- If sum is too large, we move right pointer left to decrease sum

```
Fixed: -1
            left           right
             ↓               ↓
[-2,        -1,     0,    3,    5]

Sum = -1 + 0 + 5 = 4 (too large)
→ Move right pointer left

            left      right
             ↓          ↓
[-2,        -1,     0,    3,    5]

Sum = -1 + 0 + 3 = 2 (better)
```

## Complete Walkthrough Example

Let's trace through the solution with a detailed example:

**Input Array**: `[2, -3, 4, 1, 0]`
**Target**: `2`

### Step 1: Sort the array
`[-3, 0, 1, 2, 4]`

### Step 2: Initialize variables
- `resultSum = -3 + 0 + 1 = -2` (first three elements)
- `minDifference = Integer.MAX_VALUE`

### Step 3: Iterate through the array

#### Iteration 1: Fix element at index 0 (value = -3)
- left = 1, right = 4
  - Sum = -3 + 0 + 4 = 1
  - Difference from target = |1 - 2| = 1
  - Update: resultSum = 1, minDifference = 1

- left = 2, right = 4
  - Sum = -3 + 1 + 4 = 2
  - Difference from target = |2 - 2| = 0 (Exact match!)
  - Return 2 immediately

Since we found an exact match to the target, we return 2 as the answer.

## Flowchart of the Algorithm

```
┌───────────────────────────┐
│ Start                     │
└───────────┬───────────────┘
            ▼
┌───────────────────────────┐
│ Sort the array            │
└───────────┬───────────────┘
            ▼
┌───────────────────────────┐
│ Initialize resultSum      │
│ and minDifference         │
└───────────┬───────────────┘
            ▼
┌───────────────────────────┐
│ Loop for each i from 0    │
│ to length-3               │
└───────────┬───────────────┘
            ▼
┌───────────────────────────┐
│ Set left = i+1            │
│ Set right = length-1      │
└───────────┬───────────────┘
            ▼
┌───────────────────────────┐
│ While left < right        │◄────┐
└───────────┬───────────────┘     │
            ▼                     │
┌───────────────────────────┐     │
│ Calculate current sum     │     │
└───────────┬───────────────┘     │
            ▼                     │
┌───────────────────────────┐     │
│ If sum == target          │     │
├───────────┬───────────────┘     │
│ Yes       │ No                  │
▼           └────────┬────────────┘
┌─────────┐          ▼
│ Return  │ ┌───────────────────────────┐
│ target  │ │ If sum < target           │
└─────────┘ │  Move left pointer right  │
            │ Else                      │
            │  Move right pointer left  │
            └───────────┬───────────────┘
                        ▼
            ┌───────────────────────────┐
            │ Calculate diffToTarget    │
            └───────────┬───────────────┘
                        ▼
            ┌───────────────────────────┐
            │ If diffToTarget <         │
            │ minDifference             │
            │  Update resultSum and     │
            │  minDifference            │
            └───────────┬───────────────┘
                        │
                        └────────────────┐
                                         │
            ┌───────────────────────────┐│
            │ End of while loop         ├┘
            └───────────┬───────────────┘
                        ▼
            ┌───────────────────────────┐
            │ End of for loop           │
            └───────────┬───────────────┘
                        ▼
            ┌───────────────────────────┐
            │ Return resultSum          │
            └───────────────────────────┘
```

## Common Pitfalls and Edge Cases

1. **Not Sorting First**: The two-pointer technique relies on a sorted array
2. **Improper Initialization**: Initialize `minDifference` with a sufficiently large value
3. **Missing Early Termination**: Return immediately when an exact match is found
4. **Off-by-One Errors**: Be careful with loop bounds (ensure we leave room for two more elements)
5. **Misunderstanding "Closest"**: Remember to use absolute difference when comparing distances

### Edge Cases to Test:
- Array of exactly 3 elements: `[1, 2, 3]`, target = 6
- All elements are the same: `[2, 2, 2]`, target = 7
- Negative numbers in the array: `[-5, -3, -1]`, target = -10
- Multiple triplets with the same difference: `[1, 2, 3, 4, 5]`, target = 7

## Problem Variations

### 1. 4Sum Closest
- Find four elements that sum closest to a target
- Solution approach: Sort + fix two elements + two-pointer for remaining two
- Time complexity: O(n³)

### 2. K-Sum Closest
- Find K elements that sum closest to a target
- Solution approach: Recursively reduce to (K-1)-Sum problem
- Time complexity: O(n^(K-1))

### 3. 3Sum Smaller
- Count triplets with sum less than target
- Solution approach: Similar to 3Sum Closest, but count valid triplets instead

## References

- [3sum-closest on LeetCode](https://leetcode.com/problems/3sum-closest/)
- [Three Sum Closest (LeetCode 16) | Full Solution with visual explanation | Interview Essential](https://youtu.be/uSpJQa6MRZ8?si=H6TG842diG5VNTMX)

## Summary and Key Takeaways

1. The 3Sum Closest problem is an extension of the original 3Sum problem
2. The algorithm works by:
   - Sorting the array
   - Fixing one element
   - Using two pointers to find the other two elements
3. Time complexity is O(n²) after sorting
4. The problem guarantees exactly one solution, but our algorithm can handle cases with multiple equally close sums
5. The algorithm works efficiently with duplicates without additional handling
6. For very large arrays, the time complexity is dominated by the sorting step

The 3Sum Closest problem demonstrates how understanding fundamental problems like 3Sum can help solve variations efficiently. The combination of sorting and the two-pointer technique is a powerful pattern that applies to many array-based problems requiring finding combinations of elements.