class Solution:
    def missingNumber(self, nums: List[int]) -> int:
        # n=len(nums)
        # return (n*(n+1))//2 -sum(nums)
        x=0 
        a=0
        for i in range(len(nums)):
            x^=nums[i]
            a^=i
        a^=len(nums)
        return x^a
#Set
#Sorting
#Bit manipulation
#Math's Formula
