if __name__=='__main__':
    n = int(input())
    m = int(input())
    #x = input()
    map = []
    for i in range(n):
        line = [int(x) for x in input().split()]
        map.append(list(line))
    dp = [list(x) for x in map]     #一维列表浅拷贝用list(),二维列表浅拷贝用[list(x) for x in dp]  三维用[[list(i) for i in x] for x in dp]
    last_dp = [list(x) for x in map]
    for k in range(m - 1):     #共循环m-1轮，每一次循环后的dp矩阵元素代表i和j之间的长度为m的最短路径
        for i in range(n):
            for j in range(n):
                tmp = [last_dp[i][x] + map[x][j] for x in range(n) if x != i and x != j]
                dp[i][j] = min(tmp)
        # copy
        last_dp = [list(x) for x in dp]
 
    print(dp)
