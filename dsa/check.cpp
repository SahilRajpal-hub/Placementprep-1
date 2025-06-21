#include <bits/stdc++.h>
using namespace std;

int solve(vector<int> v){
    int dp[coins.size() + 1][amount + 1];
    for (int i = 0; i <= coins.size(); i++)
        for (int j = 0; j <= amount; j++)
        {
            if (j == 0)
                dp[i][j] = 0;
            if (i == 0)
                dp[i][j] = INT_MIN - 1;
        }

    for (int i = 1; i <= coins.size(); i++)
    {
        for (int j = 1; j <= amount; j++)
        {
            if (coins[i - 1] <= j)
            {
                dp[i][j] = max(1 + dp[i][j - coins[i - 1]], dp[i - 1][j]);
            }
            else
            {
                dp[i][j] = dp[i - 1][j];
            }
        }
    }
    return dp[coins.size()][amount] == INT_MIN - 1 ? -1 : dp[coins.size()][amount];
}

int main(){
    int n,a,b,c;
    cin>>n>>a>>b>>c;
    vector<int> v;
    v.push_back(a);
    v.push_back(b);
    v.push_back(c);
    solve()
    return 0;
}