#include <bits/stdc++.h>
using namespace std;



https://leetcode.com/problems/permutations/solutions/18239/a-general-approach-to-backtracking-questions-in-java-subsets-permutations-combination-sum-palindrome-partioning/


// *** Find all subsequences
void printSubsequence(string input, string output, vector<string> &ans)
{
    if (input.empty()) {
        ans.push_back(output);
        return;
    }
 
    printSubsequence(input.substr(1), output + input[0], ans);
 
    printSubsequence(input.substr(1), output, ans);
}

// *** Subsets -> Given an integer array nums of unique elements, return all possible subsets (the power set).s
void solve(vector<int> nums,vector<vector<int>> &ans,vector<int> output,int i){
    if(i>=nums.size()){
        ans.push_back(output);
        return;
    }
    
    // *** leave the element at index i 
    solve(nums,ans,output,i+1);
    
    // *** take the element at index i 
    output.push_back(nums[i]);
    solve(nums,ans,output,i+1);
}
vector<vector<int>> subsets(vector<int>& nums) {
    vector<vector<int>> ans;
    vector<int> output;
    solve(nums,ans,output,0);
    return ans;
}

// *** Letter Combinations of a Phone Number
void solve(vector<string> &ans, string digits, int index, string output, vector<string> values){
    if(index==digits.size()){
        ans.push_back(output);
        return;
    }
    string value = values[digits[index]-'0'];
    for(int i=0; i<value.size(); i++){
        output.push_back(value[i]);
        solve(ans,digits,index+1,output,values);
        output.pop_back();
    }
}
vector<string> letterCombinations(string digits) {
    if(digits=="") return {}; 
    vector<string> values = {"","","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
    vector<string> ans;
    int startingIndex = 0;
    string output = "";
    solve(ans, digits, startingIndex, output, values);
    return ans;
}

// *** Permutation of string
void solve(string s, set<string> &st, int idx){
    if(idx==s.size()){
        st.insert(s);
        return;
    }
    
    for(int i=idx; i<s.size(); i++){
        swap(s[idx],s[i]);
        solve(s,st,idx+1);
        swap(s[i],s[idx]);
    }
    
}
vector<string> find_permutation(string s)
{
    set<string> st;
    
    solve(s,st,0);
    vector<string> ans(st.begin(),st.end());
    
    return ans;
}

// *** Permutation of set
void solve(vector<vector<int>> &ans, vector<int> nums, int idx){
    if(idx==nums.size()){
        ans.push_back(nums);
        return;
    }
    
    for(int i=idx; i<nums.size(); i++){
        swap(nums[i],nums[idx]);
        solve(ans,nums,idx+1);
        swap(nums[idx],nums[i]);
    }
}
vector<vector<int>> permute(vector<int>& nums) {
    vector<vector<int>> ans;
    
    solve(ans,nums,0);
    
    return ans;
}

// *** Largest number in K swaps
void solve(string &ans,string s,int k){
    if(k==0) return;
    for(int i=0; i<s.size()-1; i++){
        for(int j=i+1; j<s.size(); j++){
            if(s[j]>s[i]){
                swap(s[i],s[j]);
                ans=max(ans,s);
                solve(ans,s,k-1);
                swap(s[i],s[j]);
            }
        }
    }
}
string findMaximumNum(string& s, int k) {
    string ans=s;
    solve(ans,s,k);
    return ans;
}


// *** N Digit numbers with digits in increasing order
void solve(int n,vector<int> &ans,int res,int idx){
    if(idx==n){
        ans.push_back(res);
        return;
    }
    
    for(int i=0; i<=9; i++){
        if(i>res%10 || n==1){
            res = res*10 + i;
            solve(n,ans,res,idx+1);
            res = (res-i)/10;
        }
    }
}
vector<int> increasingNumbers(int n) {
    vector<int> ans;
    solve(n,ans,0,0);
    return ans;
}



// *** N queens problem
bool isValid(vector<pair<int,int>> choice, pair<int,int> pos){
    if(choice.size()==0) return true;

    for(auto ch:choice){
        if(ch.first==pos.first || ch.second==pos.second   || ( abs(ch.first-pos.first)==abs(ch.second-pos.second) )){
            return false;
        }
    }
    
    return true;
}
bool solve(int n, int row, vector<pair<int,int>> &choice,vector<vector<pair<int,int>>> &ans){
    if(row==n){
        ans.push_back(choice);
        return true;
    }

    bool solved = false;

    for(int i=0; i<n; i++){
        if(isValid(choice,{i,row})){
            choice.push_back({i,row});
            if(solve(n,row+1,choice,ans)){
                solved = true;
                choice.pop_back();
                // we are not returning here to get all the possible solutions
            }else{
                choice.pop_back();
            }
        }
    }

    return solved;
}
vector<vector<string>> solveNQueens(int n) {
    vector<pair<int,int>> choice;
    vector<vector<string>> ans;
    string dm(n,'.');
    vector<vector<pair<int,int>>> anst;

    solve(n,0,choice,anst);
    for(auto at:anst){
        vector<string> temp(n,dm);
        for(auto a:at){
            temp[a.second][a.first]='Q';
        }
        ans.push_back(temp);
    }
    return ans;
}

// *** Sudoku Solver
bool isValid(vector<vector<char>>& board, int row, int col, char c){
    for(int i = 0; i < 9; i++) {
        if(board[i][col] != '.' && board[i][col] == c) return false; //check row
        if(board[row][i] != '.' && board[row][i] == c) return false; //check column
    }
    int gridRowNum = 3*(row/3);
    int gridColNum = 3*(col/3);
    for(int i=gridRowNum; i<gridRowNum+3; i++){
        for(int j=gridColNum; j<gridColNum+3; j++){
            if(board[i][j]==c) return false;
        }
    }
    return true;
}
bool solve(vector<vector<char>>& board){

    for(int i=0; i<9; i++){
        for(int j=0; j<9; j++){
            if(board[i][j]=='.'){
                for(char n='1'; n<='9'; n++){
                    if(isValid(board,i,j,n)){
                        board[i][j]=n;
                        if(solve(board)){
                            return true;
                        }else{
                            board[i][j]='.';
                        }
                    }
                }

                return false;
            }
        }
    }

    return true;
}
void solveSudoku(vector<vector<char>>& board) {
    solve(board);
}


// *** Rat in a Maze Problem - I
bool isValid(pair<int,int> pr, int n,vector<vector<int>> m){
    return (pr.first>=0 && pr.second>=0 && pr.first<n && pr.second<n && m[pr.first][pr.second]==1);
}
void solve(vector<string> &ans, vector<vector<int>> m, pair<int,int> pr, int n, string path){
    if(!isValid({pr.first,pr.second},n,m)) return;
    
    if(pr.first==n-1 && pr.second==n-1){
        ans.push_back(path);
        return;
    }
    m[pr.first][pr.second]=0;
    
    // left
    path.push_back('L');
    solve(ans,m,{pr.first,pr.second-1},n,path);
    path.pop_back();
    
    // right
    path.push_back('R');
    solve(ans,m,{pr.first,pr.second+1},n,path);
    path.pop_back();

    
    // up
    path.push_back('U');
    solve(ans,m,{pr.first-1,pr.second},n,path);
    path.pop_back();
    
    //down
    path.push_back('D');
    solve(ans,m,{pr.first+1,pr.second},n,path);
    path.pop_back();
    
    
    m[pr.first][pr.second]=1;
}
vector<string> findPath(vector<vector<int>> &m, int n) {
    vector<string> ans;
    if(n==0 || m[0][0]==0) return ans;
    solve(ans,m,{0,0},n,"");
    return ans;
}


// *** M-Coloring Problem
bool isValid(vector<int> &color,int c,vector<vector<int>> adj,int node){
    for(int i:adj[node]){
        if(color[i]==c) return false;
    }
    return true;
}
bool solve(vector<vector<int>> adj,int m,int v,int i,vector<int> &color){
    if(i==v) return true;
    for(int c=1; c<=m; c++){
        if(isValid(color,c,adj,i)){
            color[i]=c;
            if(solve(adj,m,v,i+1,color)) return true;
            color[i]=-1;
        }
    }
    return false;
}
bool graphColoring(int v, vector<pair<int, int>>& edges, int m) {
    vector<int> color(v,-1);
    vector<vector<int>> adj;
    vector<int> dm;
    for(int i=0; i<v; i++) adj.push_back(dm);
    for(auto pr:edges){
        adj[pr.first].push_back(pr.second);
        adj[pr.second].push_back(pr.first);
    }
    return solve(adj,m,v,0,color);
}



int main(){
    
}