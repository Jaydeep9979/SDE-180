https://leetcode.com/problems/next-permutation/submissions/

class Solution:
    def nextPermutation(self, arr: List[int]) -> None:
        """
        Do not return anything, modify nums in-place instead.
        """
        
        n=len(arr)
        flag=1
        for i in range(n-1,0,-1):
            if arr[i-1]<arr[i]:
                ind=i-1
                flag=0
                break
        if flag:
            arr.reverse()
            return arr
        else:
            for i in range(len(arr)-1,ind,-1):
                if arr[ind]<arr[i]:
                    arr[ind],arr[i]=arr[i],arr[ind]
                    break
            
            ind+=1
            n-=1
            while ind<n:
                arr[ind],arr[n]=arr[n],arr[ind]
                ind+=1
                n-=1
            return arr

##CPP problem which i refred to solve this problem
class Solution {
public:
    void nextPermutation(vector<int>& nums) {
    	int n = nums.size(), k, l;
    	for (k = n - 2; k >= 0; k--) {
            if (nums[k] < nums[k + 1]) {
                break;
            }
        }
    	if (k < 0) {
    	    reverse(nums.begin(), nums.end());
    	} else {
    	    for (l = n - 1; l > k; l--) {
                if (nums[l] > nums[k]) {
                    break;
                }
            } 
    	    swap(nums[k], nums[l]);
    	    reverse(nums.begin() + k + 1, nums.end());
        }
    }
}; 
