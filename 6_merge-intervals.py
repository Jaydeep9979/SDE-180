class Solution:
    def merge(self, inters: List[List[int]]) -> List[List[int]]:
        if not inters:
            return []
        if len(inters)==1:
            return inters
        inters=sorted(inters,key= lambda x:(x[0],x[1]))
        ans=[]
        for inter in inters:
            if not ans or ans[-1][1]<inter[0]:
                ans.append(inter)
            else:
                ans[-1][1]=max(inter[1],ans[-1][1])
        return ans
            
        
        
# def merge(self, intervals):
#     out = []
#     for i in sorted(intervals, key=lambda i: i.start):
#         if out and i.start <= out[-1].end:
#             out[-1].end = max(out[-1].end, i.end)
#         else:
#             out += i,
#     return out
