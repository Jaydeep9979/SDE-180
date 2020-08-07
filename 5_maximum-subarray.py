class Solution:
    def maxSubArray(self, nums: List[int]) -> int:
        curr_sum=0
        max_sum=float('-inf')
        for ele in nums:
            curr_sum=max(ele,ele+curr_sum)
            max_sum=max(max_sum,curr_sum)
        return max_sum
# another way
# class Solution:
#     def maxSubArray(self, nums: List[int]) -> int:
#         for i in range(1, len(nums)):
#             if nums[i - 1] > 0: 
#                 nums[i] += nums[i - 1]
#         return max(nums)
        
        
# if no element is allowed
# class Solution:
#     def maxSubArray(self, nums: List[int]) -> int:
#         curr_sum=0
#         max_sum=float('-inf')
#         for ele in nums:
#             curr_sum=max(0,ele+curr_sum)
#             max_sum=max(max_sum,curr_sum)
#         return max_sum
        
