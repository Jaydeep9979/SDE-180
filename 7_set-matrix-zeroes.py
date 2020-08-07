class Solution:
    def setZeroes(self, arr: List[List[int]]) -> None:
        """
        Do not return anything, modify matrix in-place instead.
        """
        is_col=False
        for r in range(len(arr)):
            if arr[r][0]==0:
                is_col=True
            for c in range(1,len(arr[0])):
                if arr[r][c]==0:
                    arr[0][c]=0
                    arr[r][0]=0
        for r in range(1,len(arr)):
            for c in range(1,len(arr[0])):
                if arr[r][0]==0 or arr[0][c]==0:
                    arr[r][c]=0
        if arr[0][0]==0:
            for i in range(len(arr[0])):
                arr[0][i]=0
        if is_col:
            for i in range(len(arr)):
                arr[i][0]=0
        
        
                
