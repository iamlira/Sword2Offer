class Solution {
    public int findRepeatNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i =0; i < nums.length; i++){
            if(map.get(nums[i]) != null){
                return nums[i];
            }
            map.put(nums[i], i);
        }
        return 0;
    }
}
