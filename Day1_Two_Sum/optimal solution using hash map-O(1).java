import java.util.HashMap;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>(); // num -> index

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i]; // What we need to find

            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i }; // Found the pair
            }

            map.put(nums[i], i); // Store the num and its index
        }

        // As per constraint, there's always one solution
        throw new IllegalArgumentException("No solution found");
    }
}
