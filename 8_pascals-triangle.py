from math import factorial as fact
class Solution:
    def generate(self, n: int) -> List[List[int]]:
        def ncr(n,r):
            return fact(n)//(fact(r)*fact(n-r))
        ans=[] 
        for i in range(n):
                a=[]
                for j in range(i+1):
                    a.append(ncr(i,j))
                ans.append(a) 
        return ans
        
#2nd Solution 
class Solution:
    def generate(self, numRows: int) -> List[List[int]]:
        if numRows==0:
            return []
        if numRows==1:
            return [[1]]
        ans=[[1]]
        
        for i in range(1,numRows):
            arr=[0 for j in range(i+1)]
            arr[0],arr[i]=1,1
            for j in range(1,i):
                arr[j]=(ans[i-1][j-1])+(ans[i-1][j])
 
            ans.append(arr)
        return ans
 
