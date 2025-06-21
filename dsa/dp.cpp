#include <bits/stdc++.h>
using namespace std;

// ***? Link -> https://leetcode.com/discuss/study-guide/1437879/Dynamic-Programming-Patterns

// TODO 1. 0-1 Knapsack
//   * => Main Code
int solve(int w, int wt[], int val[], int n)
{
    // initialize dp array with 0
    // ? n->size and w->weight
    int dp[n + 1][w + 1];
    memset(dp, 0, sizeof(dp));

    for (int i = 1; i <= n; i++)
    {
        for (int j = 1; j <= w; j++)
        {
            if (wt[i - 1] <= j)
            {
                dp[i][j] = max(val[i - 1] + dp[i - 1][j - wt[i - 1]], dp[i - 1][j]);
            }
            else
            {
                dp[i][j] = dp[i - 1][j];
            }
        }
    }
    return dp[n][w];
}

//   ? Subset Sum Problem -> Given an array of non-negative integers, and a value sum, determine if there is a subset of the given set with sum equal to given sum.
//   * => code
bool solve(vector<int> arr, int sum, int n)
{
    bool dp[n + 1][sum + 1];
    for (int i = 0; i <= n; i++)
        dp[i][0] = true;
    for (int i = 1; i <= sum; i++)
        dp[0][i] = false;
    for (int i = 1; i <= n; i++)
    {
        for (int j = 1; j <= sum; j++)
        {
            if (arr[i - 1] <= j)
            {
                dp[i][j] = dp[i - 1][j - arr[i - 1]] || dp[i - 1][j];
            }
            else
            {
                dp[i][j] = dp[i - 1][j];
            }
        }
    }
    return dp[n][sum];
}

//   ? Equal sum partition ->  Given a non-empty array nums containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.
//   * => code
bool subset_sum_problem(vector<int> &nums, int sum, int n); // ! defined above
bool solve(vector<int> &nums)
{
    int sum = 0;
    for (int el : nums)
    {
        sum += el;
    }
    if (sum % 2 == 1)
        return false;
    sum = sum / 2;
    return subset_sum_problem(nums, sum, nums.size());
}

//   ? count of subset sum -> Given an array arr[] of length N and an integer X, the task is to find the number of subsets with a sum equal to X.
//   * => code
// HINT :: j is starting from 0 as array may contain zero as elements which is diff sub set than empty set, so count that too
int subsetSum(int a[], int n, int sum)
{
    int dp[n + 1][sum + 1];
    dp[0][0] = 1;
    for (int i = 1; i <= sum; i++)
        dp[0][i] = 0;
    for (int i = 1; i <= n; i++)
    {
        for (int j = 0; j <= sum; j++)
        {
            if (a[i - 1] <= j)
                dp[i][j] = dp[i - 1][j] + dp[i - 1][j - a[i - 1]];
            else
                dp[i][j] = dp[i - 1][j];
        }
    }
    return dp[n][sum];
}

//  ? minimum subset sum diff. ->
//   * => code
// 1. +ve ele only
int minimumDifference(vector<int>& arr) {
    int range = std::accumulate(arr.begin(),arr.end(),0);
    int diff = range;
    int n = arr.size();
    
    bool dp[n + 1][range + 1];
    for (int i = 0; i <= n; i++)
        dp[i][0] = true;
    for (int i = 1; i <= range; i++)
        dp[0][i] = false;
    for (int i = 1; i <= n; i++)
    {
        for (int j = 1; j <= range; j++)
        {
            if (arr[i - 1] <= j)
            {
                dp[i][j] = dp[i - 1][j - arr[i - 1]] || dp[i - 1][j];
            }
            else
            {
                dp[i][j] = dp[i - 1][j];
            }
        }
    }

    for(int i=0; i<range/2; i++){
        if(dp[n][i]){
            diff = min(diff, range-2*i);
        }
    }
    return diff;
}

//  ? Target Sum ->
//   * => code
// HINT : S1+S2=SUM & S1-S2=d
int findTargetSumWays(vector<int>& arr, int d) {
    int n = arr.size();
    long long sum = std::accumulate(arr.begin(),arr.end(),0LL);
    long long sm = (sum-d)/2;
    if((sum-d)%2!=0 || d>sum || abs(d)>sum) return 0;
    
    int md = 1000000007;
    
    vector<vector<int>> dp(n + 1, vector<int>(sum + 1, 0));
    dp[0][0]=1;
    
    for(int i=1; i<=n; i++){
        for(int j=0; j<=sum; j++){
            if(arr[i-1]<=j){
                dp[i][j] = (dp[i-1][j-arr[i-1]] + dp[i-1][j])%md; 
            }else{
                dp[i][j] = dp[i-1][j];
            }
        }
    }
    
    return dp[n][sm];
}

//  ? # of subsets with given diff. ->
//   * => code
int countPartitions(int n, int d, vector<int>& arr) {
    long long sum = std::accumulate(arr.begin(),arr.end(),0LL);
    long long sm = (sum-d)/2;
    if((sum-d)%2!=0 || d>sum) return 0;
    
    int md = 1000000007;
    
    vector<vector<int>> dp(n + 1, vector<int>(sum + 1, 0));
    dp[0][0]=1;
    
    for(int i=1; i<=n; i++){
        for(int j=0; j<=sum; j++){
            if(arr[i-1]<=j){
                dp[i][j] = (dp[i-1][j-arr[i-1]] + dp[i-1][j])%md; 
            }else{
                dp[i][j] = dp[i-1][j];
            }
        }
    }
    
    return dp[n][sm];
}




// TODO 2. Unbounded Knapsack
//   * => Main Code
int solve(int w, int wt[], int val[], int n)
{
    // initialize dp array with 0
    // ? n->size and w->weight
    int dp[n + 1][w + 1];
    memset(dp, 0, sizeof(dp));

    for (int i = 1; i <= n; i++)
    {
        for (int j = 1; j <= w; j++)
        {
            if (wt[i - 1] <= j)
            {
                dp[i][j] = max(val[i - 1] + dp[i][j - wt[i - 1]], dp[i - 1][j]);
            }
            else
            {
                dp[i][j] = dp[i - 1][j];
            }
        }
    }
    return dp[n][w];
}

//   ? Rod Cutting Problem -> Given a rod of length w inches and an array of prices that includes prices of pieces of size smaller than w. Determine the maximum value obtainable by cutting up the rod and selling the pieces.
//   * => code
int cutRod(int price[], int n)
{
    int wt[n];
    for (int i = 1; i <= n; i++)
        wt[i - 1] = i;

    int dp[n + 1][n + 1];
    memset(dp, 0, sizeof(dp));

    for (int i = 1; i <= n; i++)
    {
        for (int j = 1; j <= n; j++)
        {
            if (wt[i - 1] <= j)
            {
                dp[i][j] = max(price[i - 1] + dp[i][j - wt[i - 1]], dp[i - 1][j]);
            }
            else
            {
                dp[i][j] = dp[i - 1][j];
            }
        }
    }

    return dp[n][n];
}

//  ? Coin Change Proble 1 Problem -> You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money. Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1
//  * => code
int coinChange(vector<int> &coins, int amount)
{
    int dp[coins.size() + 1][amount + 1];
    for (int i = 0; i <= coins.size(); i++)
        for (int j = 0; j <= amount; j++)
        {
            if (j == 0)
                dp[i][j] = 0;
            if (i == 0)
                dp[i][j] = INT_MAX - 1;
        }

    for (int i = 1; i <= coins.size(); i++)
    {
        for (int j = 1; j <= amount; j++)
        {
            if (coins[i - 1] <= j)
            {
                dp[i][j] = min(1 + dp[i][j - coins[i - 1]], dp[i - 1][j]);
            }
            else
            {
                dp[i][j] = dp[i - 1][j];
            }
        }
    }
    return dp[coins.size()][amount] == INT_MAX - 1 ? -1 : dp[coins.size()][amount];
}

//  ? Coin Change Proble 2 Problem -> You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money. Return the number of combinations that make up that amount. If that amount of money cannot be made up by any combination of the coins, return 0.
//  * => code
int change(int amount, vector<int> &coins)
{
    int size = coins.size();
    int dp[size + 1][amount + 1];
    for (int i = 0; i <= size; i++)
    {
        for (int j = 0; j <= amount; j++)
        {
            if (i == 0)
                dp[i][j] = 0;
            if (j == 0)
                dp[i][j] = 1;
        }
    }

    for (int i = 1; i <= size; i++)
    {
        for (int j = 1; j <= amount; j++)
        {
            if (coins[i - 1] <= j)
            {
                dp[i][j] = dp[i][j - coins[i - 1]] + dp[i - 1][j];
            }
            else
            {
                dp[i][j] = dp[i - 1][j];
            }
        }
    }

    return dp[size][amount];
}

//   ? Maximum Ribbon Cut Problem ->
//   * => code
int cutRibbon(vector<int> coins,int amount){
    int dp[coins.size() + 1][amount + 1];
    for (int i = 0; i <= coins.size(); i++)
        for (int j = 0; j <= amount; j++)
        {
            if (j == 0)
                dp[i][j] = 0;
            if (i == 0)
                dp[i][j] = INT_MIN + 1;
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
    return dp[coins.size()][amount] == INT_MIN + 1 ? -1 : dp[coins.size()][amount];
}

//   ? Combination Sum IV Problem ->
//   * => code
int solve(vector<int> &nums,int target,vector<int> &dp){
    if(target<0) return 0;
    if(target==0) return 1;

    if(dp[target]!=-1) return dp[target];
    
    int ans = 0;

    for(int i: nums){
        ans += solve(nums,target-i,dp);
    }

    return dp[target]=ans;
}
int combinationSum4(vector<int>& nums, int target) {
    vector<int> dp(target+1,-1);
    return solve(nums,target,dp);
}

//   ? Perfect Squares Problem ->
//   * => code
int numSquares(int n)
{
    int val[100];
    for (int i = 1; i <= 100; i++)
    {
        val[i - 1] = i * i;
    }

    int dp[101][n + 1];
    for (int i = 0; i <= 100; i++)
    {
        for (int j = 0; j <= n; j++)
        {
            if (i == 0)
                dp[i][j] = INT_MAX - 1;
            if (j == 0)
                dp[i][j] = 0;
        }
    }

    for (int i = 1; i <= 100; i++)
    {
        for (int j = 1; j <= n; j++)
        {
            if (val[i - 1] <= j)
            {
                dp[i][j] = min(1 + dp[i][j - val[i - 1]], dp[i - 1][j]);
            }
            else
            {
                dp[i][j] = dp[i - 1][j];
            }
        }
    }

    return dp[100][n];
}

//   ? Integer Break Problem ->
//   * => code
// --> knapsack
int integerBreak(int c) {
    vector<int> dp(c+1,0);
    for(int n=2; n<=c; n++){
        for(int i=1; i<n; i++){
            dp[n] = max(dp[n],max(dp[n-i]*i, i*(n-i)));
        }
    }
    return dp[c];
}
// --> optimized
unordered_map<int,int> mp;
int solve(int n){
    if(n<=1) return 1;
    if(mp.find(n)!=mp.end()) return mp[n];
    int ans = 0;
    for(int i=1; i<n; i++){
        ans = max(ans,max(solve(n-i)*i, i*(n-i)));
    }
    return mp[n]=ans;
}

// TODO 3. Fibonacci / digit dp / ugly numbers
// Fibonacci
int fib(int n) {
    if(n<2) return n;
    vector<int> dp(n+1,0);
    dp[1]=1;
    for(int i=2; i<=n; i++) dp[i]=dp[i-1]+dp[i-2];
    return dp[n];
}

// digit dp
// resource :: https://leetcode.com/discuss/interview-question/4637245/Mastering-Digit-DP/

// ugly numbers
int uglyNumber(int n)
{
    vector<int> dp(n,1);
    int p2=0,p3=0,p5=0;

    for(int i=1; i<n; i++){
        int twoMultiple = dp[p2]*2;
        int threeMultiple = dp[p3]*3;
        int fiveMultiple = dp[p5]*5;

        dp[i] = min(twoMultiple, min(threeMultiple,fiveMultiple));

        if(dp[i]==twoMultiple) p2++;
        if(dp[i]==threeMultiple) p3++;
        if(dp[i]==fiveMultiple) p5++;
    }

    return dp[n-1];
}

// TODO 4. LCS => Longest Common Subsequence
//   * => Main Code

// ?**** <------------------   Recursive -------------------------------------------->
int lcs(string s1, string s2, int m, int n)
{
    if (m < 0 || n < 0)
        return 0;

    int ans = 0;
    if (s1[m] == s2[n])
        ans = 1 + lcs(s1, s2, m - 1, n - 1);
    else
        ans = max(lcs(s1, s2, m, n - 1), lcs(s1, s2, m - 1, n));
    return ans;
}

// ?**** <------------------   DP -------------------------------------------->
int longestCommonSubsequence(string text1, string text2)
{
    int m = text1.size();
    int n = text2.size();
    int dp[m + 1][n + 1];

    for (int i = 0; i <= m; i++)
    {
        for (int j = 0; j <= n; j++)
        {
            dp[i][j] = 0;
        }
    }

    for (int i = 1; i <= m; i++)
    {
        for (int j = 1; j <= n; j++)
        {
            if (text1[i - 1] == text2[j - 1])
            {
                dp[i][j] = 1 + dp[i - 1][j - 1];
            }
            else
            {
                dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
    }

    return dp[m][n];
}

// Longest Common Substring
int longestCommonSubstr (string S1, string S2, int n, int m)
{
    vector<vector<int>> dp(n+1,vector<int>(m+1));
    int ans = 0;
    
    for(int i=1; i<=n; i++){
        for(int j=1; j<=m; j++){
            if(S1[i-1]==S2[j-1]){
                dp[i][j] = 1 + dp[i-1][j-1];
            }else{
                dp[i][j] = 0;
            }
            ans = max(ans,dp[i][j]);
        }
    }
    
    return ans;
}

//   ? Print LCS ->
//   * => code
string printLongestCommonSubsequence(string text1, string text2)
{
    int m = text1.size();
    int n = text2.size();
    int dp[m + 1][n + 1];

    for (int i = 0; i <= m; i++)
    {
        for (int j = 0; j <= n; j++)
        {
            dp[i][j] = 0;
        }
    }

    for (int i = 1; i <= m; i++)
    {
        for (int j = 1; j <= n; j++)
        {
            if (text1[i - 1] == text2[j - 1])
            {
                dp[i][j] = 1 + dp[i - 1][j - 1];
            }
            else
            {
                dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
    }

    string ans = "";
    int i = m, j = n;
    while (i > 0 && j > 0)
    {
        if (text1[i - 1] == text2[j - 1])
        {
            ans += text1[i - 1];
            i--;
            j--;
        }
        else
        {
            if (dp[i][j - 1] > dp[i - 1][j])
            {
                j--;
            }
            else
            {
                i--;
            }
        }
    }
    reverse(ans.begin(), ans.end());

    return ans;
}

// Print all LCSs
void getLcsFromDp(vector<vector<int>> &dp,string s,string t,string &curr,int i,int j,set<string> &ans,unordered_map<string,bool> &mp){
    if(i==0 || j==0){
        reverse(curr.begin(),curr.end());
        ans.insert(curr);
        reverse(curr.begin(),curr.end());
        return;
    }
    string key = to_string(i)+'-'+to_string(j)+'-'+curr;
    if(mp.find(key)!=mp.end()) return;
    mp[key]=true;
    
    
    if(s[i-1]==t[j-1]){
        curr.push_back(s[i-1]);
        getLcsFromDp(dp,s,t,curr,i-1,j-1,ans,mp);
        curr.pop_back();
    }else{
        if(dp[i][j]==dp[i-1][j]){
            getLcsFromDp(dp,s,t,curr,i-1,j,ans,mp);
        }
        if(dp[i][j]==dp[i][j-1]){
            getLcsFromDp(dp,s,t,curr,i,j-1,ans,mp);
        }
    }
}
vector<string> all_longest_common_subsequences(string s, string t) {
    int m = s.size(),n=t.size();
    vector<vector<int>> dp(m+1,vector<int>(n+1,0));
    unordered_map<string,bool> mp;
    
    for(int i=1; i<=m; i++){
        for(int j=1; j<=n; j++){
            if(s[i-1]==t[j-1]){
                dp[i][j] = 1+ dp[i-1][j-1];
            }else{
                dp[i][j] = max(dp[i-1][j], dp[i][j-1]);
            }
        }
    }
    set<string> ans;
    string curr = "";
    getLcsFromDp(dp,s,t,curr,m,n,ans,mp);
    return vector<string>(ans.begin(),ans.end());
}

//   ? Length of Shortest Common Supersequence ->
//   * => code
int lengthOfSCS(string s1, string s2, int m, int n)
{
    int dp[m + 1][n + 1];

    for (int i = 0; i <= m; i++)
    {
        for (int j = 0; j <= n; j++)
        {
            dp[i][j] = 0;
        }
    }

    for (int i = 1; i <= m; i++)
    {
        for (int j = 1; j <= n; j++)
        {
            if (s1[i - 1] == s2[j - 1])
            {
                dp[i][j] = 1 + dp[i - 1][j - 1];
            }
            else
            {
                dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
    }

    int ans = m + n - dp[m][n];
    return ans;
}

//   ? Print Shortest Common Supersequence ->
//   * => code
string printSCS(string s1, string s2, int m, int n)
{
    int dp[m + 1][n + 1];

    for (int i = 0; i <= m; i++)
    {
        for (int j = 0; j <= n; j++)
        {
            dp[i][j] = 0;
        }
    }

    for (int i = 1; i <= m; i++)
    {
        for (int j = 1; j <= n; j++)
        {
            if (s1[i - 1] == s2[j - 1])
            {
                dp[i][j] = 1 + dp[i - 1][j - 1];
            }
            else
            {
                dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
    }

    int i = m, j = n;
    string ans = "";
    while (i > 0 && j > 0)
    {
        if (s1[i - 1] == s2[j - 1])
        {
            ans.push_back(s1[i - 1]);
            i--;
            j--;
        }
        else
        {
            if (dp[i - 1][j] > dp[i][j - 1])
            {
                ans.push_back(s1[i - 1]);
                i--;
            }
            else
            {
                ans.push_back(s2[j - 1]);
                j--;
            }
        }
    }

    while (i > 0)
    {
        ans.push_back(s1[i - 1]);
        i--;
    }
    while (j > 0)
    {
        ans.push_back(s2[j - 1]);
        j--;
    }

    reverse(ans.begin(), ans.end());
    return ans;
}

//   ? Length of Longest Common Substring ->
//   * => code
int longestCommonSubstr(string s1, string s2, int n, int m)
{
    int dp[n + 1][m + 1];
    for (int i = 0; i <= n; i++)
    {
        for (int j = 0; j <= m; j++)
        {
            dp[i][j] = 0;
        }
    }

    for (int i = 1; i <= n; i++)
    {
        for (int j = 1; j <= m; j++)
        {
            if (s1[i - 1] == s2[j - 1])
            {
                dp[i][j] = 1 + dp[i - 1][j - 1];
            }
            else
            {
                dp[i][j] = 0;
            }
        }
    }

    int ans = INT_MIN;
    for (int i = 0; i <= n; i++)
    {
        for (int j = 0; j <= m; j++)
        {
            ans = max(ans, dp[i][j]);
        }
    }
    return ans;
}

//   ? Print LC Substr ->
//   * => code
string printLongestCommonSubstr(string s1, string s2, int n, int m)
{
    int dp[n + 1][m + 1];
    for (int i = 0; i <= n; i++)
    {
        for (int j = 0; j <= m; j++)
        {
            dp[i][j] = 0;
        }
    }

    for (int i = 1; i <= n; i++)
    {
        for (int j = 1; j <= m; j++)
        {
            if (s1[i - 1] == s2[j - 1])
            {
                dp[i][j] = 1 + dp[i - 1][j - 1];
            }
            else
            {
                dp[i][j] = 0;
            }
        }
    }

    int ans = INT_MIN;
    pair<int, int> pr;
    for (int i = 0; i <= n; i++)
    {
        for (int j = 0; j <= m; j++)
        {
            if (dp[i][j] > ans)
            {
                ans = dp[i][j];
                pr = {i, j};
            }
        }
    }
    string sst = s2.substr(pr.second - ans, ans);
    return sst;
}

//   ? Minimum # of insertions and deletions to convert a to b ->
//   * => code
int minOperations(string s1, string s2)
{
    int m = s1.size();
    int n = s2.size();
    int dp[m + 1][n + 1];

    for (int i = 0; i <= m; i++)
    {
        for (int j = 0; j <= n; j++)
        {
            dp[i][j] = 0;
        }
    }

    for (int i = 1; i <= m; i++)
    {
        for (int j = 1; j <= n; j++)
        {
            if (s1[i - 1] == s2[j - 1])
            {
                dp[i][j] = 1 + dp[i - 1][j - 1];
            }
            else
            {
                dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
    }

    return m + n - 2 * dp[m][n];
}

//   ? largest Repeating subsequence ->
//   * => code
int LongestRepeatingSubsequence(string s)
{
    int n = s.size();
    int dp[n + 1][n + 1];

    memset(dp, 0, sizeof(dp));

    for (int i = 1; i <= n; i++)
    {
        for (int j = 1; j <= n; j++)
        {
            if (s[i - 1] == s[j - 1] && i != j)
            {
                dp[i][j] = 1 + dp[i - 1][j - 1];
            }
            else
            {
                dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
    }

    return dp[n][n];
}

//   ? Length of largest subsequence of a which is a substring in b ->
//   * => code
int maxSubsequenceSubstring(string X, string Y, int N, int M){
    vector<vector<int>> dp(N+1,vector<int>(M+1,0));
    int ans = INT_MIN;
    for(int i=1; i<=N; i++){
        for(int j=1; j<=M; j++){
            if(X[i-1]==Y[j-1]){
                dp[i][j] = 1 + dp[i-1][j-1];
            }else{
                dp[i][j] = dp[i-1][j];
            }
            ans = max(ans,dp[i][j]);
        }
    }
    
    return ans;
}

//   ? Subsequence pattern matching ->
//   * => code
// TODO just check if length of lcs and string a is same or not

//   ? Count how many times a appears as subsequence in b ->
//   * => code

//   ? Largest palindromic subsequence ->
//   * => code
int lcs(string s1, string s2)
{
    int m = s1.size();
    int dp[m + 1][m + 1];
    memset(dp, 0, sizeof(dp));

    for (int i = 1; i <= m; i++)
    {
        for (int j = 1; j <= m; j++)
        {
            if (s1[i - 1] == s2[j - 1])
            {
                dp[i][j] = 1 + dp[i - 1][j - 1];
            }
            else
            {
                dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
    }

    return dp[m][m];
}
int longestPalindromeSubseq(string s)
{
    string rs = s;
    reverse(rs.begin(), rs.end());
    return lcs(s, rs);
}


// ? longest palindromic substring
//   * => code
int longestPalindromeSubstr(string &s) {
    string rs = s;
    int n = s.size();
    reverse(rs.begin(),rs.end());
    vector<vector<int>> dp(n+1,vector<int>(n+1,0));
    int ans = INT_MIN;
    for(int i=1; i<=n; i++){
        for(int j=1; j<=n; j++){
            if(s[i-1]==rs[j-1]){
                dp[i][j] = 1 + dp[i-1][j-1];
            }else{
                dp[i][j] = 0;
            }
            ans = max(ans,dp[i][j]);
        }
    }
    return ans;
}

// ? print longest palindromic substring 
//   * => code

//   ? Count of palindromic substring ->
//   * => code





//   ? Minimum # of deletions/insertions in string to make it palindromic ->
//   * => code
int minDeletions(string s, int n)
{
    string rs = s;
    reverse(rs.begin(), rs.end());
    int dp[n + 1][n + 1];
    memset(dp, 0, sizeof(dp));

    for (int i = 1; i <= n; i++)
    {
        for (int j = 1; j <= n; j++)
        {
            if (s[i - 1] == rs[j - 1])
            {
                dp[i][j] = 1 + dp[i - 1][j - 1];
            }
            else
            {
                dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
    }

    return n - dp[n][n];
}

// TODO 5. LIS

// TODO 6. Kadane's Algorithm

// TODO 7. Matrix Chain Multiplication (Only recursive and memoize work well in this type of question, top down causes confusion)
//   * => Main Code
int solve(int arr[], int i, int j, vector<vector<int>> &dp)
{
    if (i >= j)
        return 0;
    int ans = INT_MAX;
    if (dp[i][j] != -1)
        return dp[i][j];
    for (int k = i; k < j; k++)
    {
        int t1 = 0, t2 = 0;
        if (dp[i][k] != -1)
            t1 = dp[i][k];
        else
            dp[i][k] = solve(arr, i, k, dp);
        if (dp[k + 1][j] != -1)
            t2 = dp[k + 1][j];
        else
            dp[k + 1][j] = solve(arr, k + 1, j, dp);
        int temp = dp[i][k] + dp[k + 1][j] + arr[i - 1] * arr[k] * arr[j];
        ans = min(ans, temp);
    }
    return ans;
}
int matrixMultiplication(int N, int arr[])
{
    vector<vector<int>> dp;
    for (int i = 0; i <= N; i++)
    {
        vector<int> temp;
        for (int j = 0; j <= N; j++)
        {
            temp.push_back(-1);
        }
        dp.push_back(temp);
    }
    return solve(arr, 1, N - 1, dp);
}

//   ? Printing MCM ->
//   * => code

//   ? Evaluate expression to true/ Boolean parenthesis ->
//   * => code

//   ? Min/Max value of a parenthesis ->
//   * => code

//   ? Palindrome Partitioning II->
//   * => code
bool isPalindrome(string s, int i, int j)
{
    if (i == j)
        return true;
    while (i < j)
    {
        if (s[i] != s[j])
            return false;
        i++;
        j--;
    }
    return true;
}
int solve(string arr, int i, int j, vector<vector<int>> &dp)
{
    if (i >= j)
        return 0;
    int ans = INT_MAX;
    if (isPalindrome(arr, i, j))
        return 0;
    if (dp[i][j] != -1)
        return dp[i][j];
    for (int k = i; k < j; k++)
    {
        int t1 = 0, t2 = 0;
        if (dp[i][k] != -1)
            t1 = dp[i][k];
        else
            dp[i][k] = solve(arr, i, k, dp);
        if (dp[k + 1][j] != -1)
            t2 = dp[k + 1][j];
        else
            dp[k + 1][j] = solve(arr, k + 1, j, dp);
        int temp = dp[i][k] + dp[k + 1][j] + 1;
        ans = min(ans, temp);
    }
    return ans;
}
int palindromicPartition(string str)
{
    int N = str.size();
    vector<vector<int>> dp;
    for (int i = 0; i <= N; i++)
    {
        vector<int> temp;
        for (int j = 0; j <= N; j++)
        {
            temp.push_back(-1);
        }
        dp.push_back(temp);
    }
    return solve(str, 0, N - 1, dp);
}

//   ? Scramble String ->
//   * => code

//   ? Egg Droping problem ->
//   * => code

//   ? Burst Balloons ->
//   * => code

// TODO 8. DP on trees



// TODO 9. DP on 1d/2d arrays / grids
// --> Climbing Stairs
int climbStairs(int n) {
    if(n<2) return 1;
    
    vector<int> dp(n+1,0);
    dp[0]=1;
    dp[1]=1;
    
    for(int i=2; i<=n; i++){
        dp[i] = dp[i-1]+dp[i-2];  // yha mai ya toh ek step phle se aaskta hu ya do step phle se
    }

    return dp[n];
}

// --> Geek Jump
int minimumEnergy(vector<int>& height, int n) {
    if(n==1) return 0;
    vector<int> dp(n+1,0);
    dp[1]=0;
    dp[2]=abs(height[1]-height[0]);
    for(int i=3; i<=n; i++){
        dp[i] = min( dp[i-1]+abs(height[i-1]-height[i-2]) , dp[i-2]+abs(height[i-1]-height[i-3]) );
    }
    
    return dp[n];
}

// --> House Robber
int rob(vector<int>& nums) {
    int n = nums.size();
    if(n==0) return 0;
    if(n==1) return nums[0];
    vector<int> dp(n+1,0);
    dp[0]=nums[0];
    dp[1]=max(nums[0],nums[1]);
    for(int i=2; i<n; i++){
        dp[i] = max(dp[i-2]+nums[i],dp[i-1]);
    }
    return dp[n-1];
}

// --> House Robber 2
int rob2(vector<int>& nums) {
    int n = nums.size();
    if(n==0) return 0;
    if(n==1) return nums[0];
    
    vector<int> temp1,temp2;
    for(int i=0; i<nums.size(); i++){
        if(i!=0) temp1.push_back(nums[i]);
        if(i!=nums.size()-1) temp2.push_back(nums[i]);
    }
    return max(rob(temp1),rob(temp2));
}

// DP on 2d arrays / Grids
// --> Geek's Training
int maximumPoints(vector<vector<int>>& arr, int n) {
    vector<vector<int>> dp(3,vector<int>(n+1,0));
    
    for(int j=1; j<=n; j++){
        
        dp[0][j] = arr[j-1][0] + max(dp[1][j-1],dp[2][j-1]);
        dp[1][j] = arr[j-1][1] + max(dp[0][j-1],dp[2][j-1]);
        dp[2][j] = arr[j-1][2] + max(dp[0][j-1],dp[1][j-1]);
        
    }
    
    return max({dp[0][n],dp[1][n],dp[2][n]});
}

// --> Unique Paths
int uniquePaths(int m, int n) {
    vector<vector<int>> dp(m,vector<int>(n,1));
    for(int i=m-2; i>=0; i--){
        for(int j=n-2; j>=0; j--){
            dp[i][j]=dp[i+1][j]+dp[i][j+1];
        }
    }
    return dp[0][0];
}
// a combinatorics solution in big o of n also exists

// --> Unique Paths II
int uniquePathsWithObstacles(vector<vector<int>>& grid) {
    int n = grid.size(),m=grid[0].size();
    vector<vector<long long>> dp(n+1,vector<long long>(m+1,1));

    for(int i=n-1; i>=0; i--){
        if(grid[i][m-1]==1){
            for(int j=0; j<=i; j++) dp[j][m-1]=0;
            break;
        }
    }
    for(int i=m-1; i>=0; i--){
        if(grid[n-1][i]==1){
            for(int j=0; j<=i; j++) dp[n-1][j]=0;
            break;
        }
    }

    if(grid[n-1][m-1]==1) return 0;

    for(int i=n-2; i>=0; i--){
        for(int j=m-2; j>=0; j--){
            if(grid[i][j]==1) dp[i][j]=0;
            else{
                dp[i][j] = dp[i+1][j] + dp[i][j+1];
            }
        }
    }

    return dp[0][0];
}

// --> Minimum Path Sum
int minPathSum(vector<vector<int>>& grid) {
    int n=grid.size(),m=grid[0].size();
    vector<vector<int>> dp(n+1,vector<int>(m+1,0));

    dp[n-1][m-1]=grid[n-1][m-1];

    for(int i=n-2; i>=0; i--){
        dp[i][m-1] = grid[i][m-1]+dp[i+1][m-1];
    }
    for(int i=m-2; i>=0; i--){
        dp[n-1][i] = grid[n-1][i]+dp[n-1][i+1];
    }

    for(int i=n-2; i>=0; i--){
        for(int j=m-2; j>=0; j--){
            dp[i][j] = grid[i][j] + min(dp[i+1][j],dp[i][j+1]);
        }
    }

    return dp[0][0];
}

// --> Triangle
int minimumTotal(vector<vector<int>>& triangle) {
    int n = triangle.size();
    int m = triangle[n-1].size();
    vector<vector<long long>> dp(n+1,vector<long long>(m+1,0));

    for(int i=0; i<=n; i++){
        for(int j=0; j<=m; j++){
            if(i==n || j==m) dp[i][j]=INT_MAX;
            if(i==n-1 && j<m) dp[i][j]=triangle[i][j];
        }      
    }
    int cnt = 0;
    for(int i=n-2; i>=0; i--){
        for(int j=m-2-cnt; j>=0; j--){
            if(i==n-1 && j==m-1) {
                continue;
            }
            dp[i][j] = triangle[i][j] + min(dp[i+1][j],dp[i+1][j+1]);
        }
        cnt++;
    }
    
    return dp[0][0];
}

// --> Minimum Falling Path
int minFallingPathSum(vector<vector<int>>& matrix) {
    int n = matrix.size(), m = matrix[0].size();
    vector<vector<int>> dp(n+1,vector<int>(m+1,INT_MAX));
    
    for(int i=0; i<m; i++){
        dp[n-1][i]=matrix[n-1][i];
    }

    for(int i=n-2; i>=0; i--){
        for(int j=m-1; j>=0; j--){
            int temp = INT_MAX;
            if(i+1<n && j-1>=0) temp = min(temp,dp[i+1][j-1]);
            if(i+1<n) temp = min(temp,dp[i+1][j]);
            if(i+1<n && j+1<m) temp = min(temp,dp[i+1][j+1]);
            
            dp[i][j]=temp + matrix[i][j];
            
        }
        
    }


    int ans = INT_MAX;
    for(int i=0; i<m; i++){
        ans = min(ans,dp[0][i]);
    }
    return ans;
}

// --> Cherry Pickup II
// -- memozization
int solve(vector<vector<int>>& grid, int row, int col1,int col2,vector<vector<vector<int>>> &dp){
    if(row==grid.size() || col1==grid[0].size() || col2==grid[0].size() || row<0 || col1<0 || col2<0){
        return INT_MIN;
    }
    if(row==grid.size()-1){
        if(col1==col2) return grid[row][col1];
        else return grid[row][col1]+grid[row][col2];
    }

    if(dp[row][col1][col2]!=-1) return dp[row][col1][col2];

    vector<int> delta = {-1,0,1};
    int base = INT_MIN;
    int currSum = 0;
    for(int i=0; i<3; i++){
        for(int j=0; j<3; j++){
            base = max(base,solve(grid,row+1,col1+delta[i],col2+delta[j],dp));
        }
    }
    if(col1==col2) currSum=grid[row][col1];
    else currSum=grid[row][col1]+grid[row][col2];

    return dp[row][col1][col2]=base+currSum;
}
int cherryPickup(vector<vector<int>>& grid) {
    int n = grid.size();
    int m = grid[0].size();
    vector<vector<vector<int>>> dp(n+1,vector<vector<int>>(m+1, vector<int>(m+1,-1)) );
    return solve(grid,0,0,grid[0].size()-1,dp);
}

// -- tabulation


// TODO 11. String Matching
// --- Distinct Subsequences
int numDistinct(string s, string t) {
    int n = s.size();
    int m = t.size();
    vector<vector<double>> dp(n+1,vector<double>(m+1,0));

    for(int i=0; i<n; i++) dp[i][0]=1;

    for(int i=1; i<=n; i++){
        for(int j=1; j<=m; j++){
            if(s[i-1]==t[j-1]) dp[i][j] = dp[i-1][j-1]+dp[i-1][j];
            else dp[i][j] = dp[i-1][j];
        }
    }
    return dp[n][m];
}

// --- Edit Distance
int minDistance(string word1, string word2) {
    if(word1==word2) return 0;
    int n = word1.size();
    int m = word2.size();
    vector<vector<int>> dp(n+1,vector<int>(m+1,0));

    for(int i=0; i<=n; i++){
        for(int j=0; j<=m; j++){
            if(i==0) dp[i][j]=j;
            if(j==0) dp[i][j]=i;
        }
    }

    for(int i=1; i<=n; i++){
        for(int j=1; j<=m; j++){
            if(word1[i-1]==word2[j-1]){
                dp[i][j] = dp[i-1][j-1];
            }else{
                dp[i][j] = 1 + min({dp[i-1][j-1],dp[i-1][j], dp[i][j-1]});
            }
        }
    }

    return dp[n][m];
}

// --- Wildcard Matching
bool isMatch(string s, string p) {
    int n = s.size();
    int m = p.size();
    vector<vector<bool>> dp(n+1,vector<bool>(m+1,false));

    dp[0][0]=true;
    for(int j=1; j<=m; j++){
        bool flag = true;
        for(int l=1; l<=j; l++){
            if(p[l-1]!='*'){
                flag = false;
                break;
            }
        }
        dp[0][j] = flag;
    }

    for(int i=1; i<=n; i++){
        for(int j=1; j<=m; j++){
            if(s[i-1]==p[j-1] || p[j-1]=='?'){
                dp[i][j] = dp[i-1][j-1];
            }else if(p[j-1]=='*'){
                dp[i][j] = dp[i][j-1] || dp[i-1][j];
            }else dp[i][j] = false;
        }
    }

    return dp[n][m];
}


// TODO 12: DP on stocks
// --- Best Time to Buy and Sell Stock
int maxProfit(vector<int>& prices) {
    int ans = 0;
    int tmp = prices[0];
    
    for(int i=1; i<prices.size(); i++){
        tmp = min(tmp,prices[i]);
        ans = max(ans,prices[i]-tmp);
    }
    return ans;
}

// --- Best Time to Buy and Sell Stock II
int maxProfit(vector<int>& prices) {
    int n = prices.size();
    vector<vector<int>> dp(n+1,vector<int>(2,0));
    for(int i=n-1; i>=0; i--){
        for(int j=0; j<2; j++){
            int profit = 0;
            if(j==1){
                profit = max(-prices[i] + dp[i+1][0], dp[i+1][1] );
            }else{
                profit = max( prices[i] + dp[i+1][1], dp[i+1][0] );
            }
            dp[i][j] = profit;
        }
    }
    return dp[0][1];
}
// -- space optimization
int maxProfit(vector<int>& prices) {
    int n = prices.size();
    vector<vector<int>> dp(n+1,vector<int>(2,0));
    vector<int> ahead(2,0), curr(2,0);

    for(int i=n-1; i>=0; i--){
        for(int j=0; j<2; j++){
            int profit = 0;
            if(j==1){
                profit = max(-prices[i] + ahead[0], ahead[1] );
            }else{
                profit = max( prices[i] + ahead[1], ahead[0] );
            }
            curr[j] = profit;
        }
        ahead = curr;
    }
    return curr[1];
}

// --- Buy and Sell Stocks III 
int maxProfit(vector<int>& prices) {
    int n = prices.size();
    vector<vector<vector<int>>> dp(n+1,vector<vector<int>>(3,vector<int>(3,-1)));
    for(int i=0; i<=n; i++){
        for(int j=0; j<3; j++){
            dp[i][0][j]=0;
        }
    }
    for(int i=0; i<3; i++){
        for(int j=0; j<3; j++){
            dp[n][i][j]=0;
        }
    }
    for(int i=n-1; i>=0; i--){
        for(int cap=1; cap<=2; cap++){
            for(int buy=0; buy<=1; buy++){
                if(buy){
                    dp[i][cap][buy]=max(-prices[i]+dp[i+1][cap][0], dp[i+1][cap][1]);
                }
                else dp[i][cap][buy]=max(prices[i]+dp[i+1][cap-1][1], dp[i+1][cap][0]);
            }
        }
    }
    return dp[0][2][1];
}

// --- Best Time to Buy and Sell Stock IV
int maxProfit(int k, vector<int>& prices) {
    int n = prices.size();
    vector<vector<vector<int>>> dp(n+1,vector<vector<int>>(k+1,vector<int>(3,-1)));
    for(int i=0; i<=n; i++){
        for(int j=0; j<3; j++){
            dp[i][0][j]=0;
        }
    }
    for(int i=0; i<=k; i++){
        for(int j=0; j<3; j++){
            dp[n][i][j]=0;
        }
    }
    for(int i=n-1; i>=0; i--){
        for(int cap=1; cap<=k; cap++){
            for(int buy=0; buy<=1; buy++){
                if(buy){
                    dp[i][cap][buy]=max(-prices[i]+dp[i+1][cap][0], dp[i+1][cap][1]);
                }
                else dp[i][cap][buy]=max(prices[i]+dp[i+1][cap-1][1], dp[i+1][cap][0]);
            }
        }
    }
    return dp[0][k][1];
}

// TODO : 13 Longest Increasing Subsequence

// --- Longest Increasing subsequence
// -- memoization
int solve(vector<int> &nums, int i, int prev,vector<vector<int>> &dp){
    if(i==nums.size()){
        return 0;
    }

    if(dp[i][prev+1]!=-1) return dp[i][prev+1];

    int length = 0;
    if(prev==-1 || nums[i]>nums[prev]){
        length = 1+solve(nums,i+1,i,dp);
    }

    length = max(length,solve(nums,i+1,prev,dp));

    return dp[i][prev+1]=length;

}
// --- tabulation
int lengthOfLIS(vector<int>& nums) {
    int n = nums.size();
    vector<vector<int>> dp(n+1,vector<int>(n+1,0));
    for(int i=n-1; i>=0; i--){
        for(int prev=i-1; prev>=-1; prev--){
            int length = dp[i+1][prev+1];
            if(prev==-1 || nums[i]>nums[prev]){
                length = max(length,1+dp[i+1][i+1]);
            }

            dp[i][prev+1]=length;
        }
    }

    return dp[0][-1+1];
}
// --- further optimized algo
int lengthOfLIS(vector<int>& nums) {
    int n = nums.size();
    if(n==0 || n==1) return n;
    vector<int> dp(n+1,1);
    for(int i=1; i<n; i++){
        for(int j=i-1; j>=0; j--){
            if(nums[i]>nums[j]){
                dp[i] = max(dp[i],dp[j]+1);
            }
        }
    }

    return *max_element(dp.begin(),dp.end());
}
// --- another approach is there using Binary search
int lengthOfLIS(vector<int>& nums) {
    int n = nums.size();
    if(n==0 || n==1) return n;
    vector<int> temp;
    temp.push_back(nums[0]);

    for(int i=1; i<n; i++){
        if(nums[i]>temp.back()){
            temp.push_back(nums[i]);
        }else{
            int idx = lower_bound(temp.begin(),temp.end(),nums[i]) - temp.begin();
            temp[idx] = nums[i];
        }
    }

    return temp.size();
}

// --- Printing Longest Increasing Subsequence
vector<int> longestIncreasingSubsequence(int n, vector<int>& nums) {
    vector<int> dp(n+1,1);
    vector<int> hash(n+1,0);
    vector<int> ans;
    int lastIndex = 0, maxi = 1;
    if(n==0 || n==1) return nums;
    for(int i=0; i<n; i++){
        hash[i]=i;
        for(int j=0; j<i; j++){
            if(nums[i]>nums[j]){
                if(dp[j]+1>dp[i]){
                    dp[i] = dp[j]+1;
                    hash[i] = j;
                }
            }
        }
        
        if(dp[i]>maxi){
            maxi=dp[i];
            lastIndex = i;
        }
    }

    ans.push_back(nums[lastIndex]);
    while(hash[lastIndex]!=lastIndex){
        lastIndex = hash[lastIndex];
        ans.push_back(nums[lastIndex]);
    }
    reverse(ans.begin(),ans.end());
    
    return ans;
}

// --- Largest Divisible Subset
vector<int> largestDivisibleSubset(vector<int>& nums) {
    int n = nums.size();
    int maxi = 0, lastIdx = 0;
    vector<int> hash(n+1,0);
    vector<int> ans;
    vector<int> dp(n+1,1);
    if(n<2) return nums;
    sort(nums.begin(),nums.end());

    for(int i=0; i<n; i++){
        hash[i]=i;
        for(int j=0; j<i; j++){
            if(nums[i]%nums[j]==0 && dp[i]<1+dp[j]){
                dp[i] = 1 + dp[j];
                hash[i] = j;
            }
        }
        if(dp[i]>maxi){
            maxi=dp[i];
            lastIdx = i;
        }
    }

    ans.push_back(nums[lastIdx]);
    while(hash[lastIdx]!=lastIdx){
        lastIdx = hash[lastIdx];
        ans.push_back(nums[lastIdx]);
    }

    reverse(ans.begin(),ans.end());

    return ans;
}

// --- Longest String Chain
bool isPredecessor(string a, string b){
    if(a.size()+1!=b.size()) return false;
    int first = 0;
    int second = 0;

    while(second<b.size()){
        if(a[first] == b[second]){
            first++;
            second++;
        }else{
            second++;
        }
    }
    if(first==a.size() && second==b.size()) return true;

    return false;
    
}
bool static comp(string &s1, string &s2){
    return s1.size()<s2.size();
}
int longestStrChain(vector<string>& words) {
    int n = words.size();
    if(n<2) return n;
    vector<int> dp(n,1);
    sort(words.begin(),words.end(),comp);
    for(int i=0; i<n; i++){
        for(int j=0; j<i; j++){
            if(isPredecessor(words[j],words[i]) && dp[i]<1+dp[j]){
                dp[i] = 1 + dp[j];
            }
        }
    }

    return *max_element(dp.begin(), dp.end());
}

// --- Longest Bitonic Subsequence
vector<int> LIS(vector<int>& nums) {
    int n = nums.size();
    if(n==0 || n==1) return nums;
    vector<int> dp(n+1,1);
    for(int i=1; i<n; i++){
        for(int j=i-1; j>=0; j--){
            if(nums[i]>nums[j]){
                dp[i] = max(dp[i],dp[j]+1);
            }
        }
    }

    return dp;
}
int LongestBitonicSequence(int n, vector<int> &nums) {
    vector<int> dp1 = LIS(nums);
    reverse(nums.begin(),nums.end());
    vector<int> dp2 = LIS(nums);
    int ans = 0;
    for(int i=0; i<n; i++){
        cout<<dp1[i]<<" "<<dp2[i]<<endl;
        ans = max(ans, dp1[i]+dp2[i]-1);
    }
    return ans;
}

// --- Number of Longest Increasing Subsequence
vector<int> LIS(vector<int>& nums) {
    int n = nums.size();
    vector<int> temp;
    temp.push_back(1);
    if(n==0 || n==1) return temp;
    vector<int> dp(n+1,1);
    for(int i=1; i<n; i++){
        for(int j=i-1; j>=0; j--){
            if(nums[i]>nums[j]){
                dp[i] = max(dp[i],dp[j]+1);
            }
        }
    }
    return dp;
} 
vector<int> LISReverse(vector<int>& nums) {
    int n = nums.size();
    vector<int> temp;
    temp.push_back(1);
    if(n==0 || n==1) return temp;
    vector<int> dp(n+1,1);
    for(int i=n-1; i>=0; i--){
        for(int j=n-1; j>i; j--){
            if(nums[i]>nums[j]){
                dp[i] = max(dp[i],dp[j]+1);
            }
        }
    }
    return dp;
} 
int LongestBitonicSequence(int n, vector<int> &nums) {
    vector<int> dp1 = LIS(nums);
    vector<int> dp2 = LISReverse(nums);
    int ans = 0;
    for(int i=0; i<n; i++){
        if(dp1[i]>1 && dp2[i]>1) ans = max(ans, dp1[i]+dp2[i]-1);
    }
    return ans;
}

// --- Number of Longest Increasing Subsequences
int findNumberOfLIS(vector<int>& nums) {
    int n = nums.size();
    if(n==0 || n==1) return n;
    vector<int> dp(n+1,1);
    vector<int> count(n+1,1);
    for(int i=0; i<n; i++){
        for(int j=0; j<i; j++){
            if(nums[i]>nums[j] && dp[i]<dp[j]+1){
                dp[i] = dp[j]+1;
                count[i]=count[j];
            }else if(nums[i]>nums[j] && dp[i]==1+dp[j]){
                count[i] += count[j];
            }
        }
    }
    int ans = 0;
    int maxElement = *max_element(dp.begin(),dp.end());

    for(int i=0; i<n; i++){
        if(dp[i]==maxElement){
            ans += count[i];
        }
    }

    return ans;
}