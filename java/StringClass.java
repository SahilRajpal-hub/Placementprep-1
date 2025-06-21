package Placementprep.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class StringClass {
    

    // ** Longest Palindrome
    public int longestPalindrome(String s) {
        HashMap<Character,Integer> mp = new HashMap<>();
        int ans = 0;
        boolean hasSingleChar = false;
        for(char c : s.toCharArray()){
            mp.put(c,mp.getOrDefault(c,0)+1);
        }
        for(Integer val : mp.values()){
            // calculate pairs
            ans += ((val/2)*2);
            
            if(val%2==1){
                hasSingleChar = true;
            }
        }

        return hasSingleChar ? ans+1 : ans;
    }
    // using array instead of map(as map is little slower)
    public int longestPalindrome2(String s) {
        int[] mp = new int[128];
        int ans = 0;
        boolean hasSingleChar = false;
        for(char c : s.toCharArray()){
            mp[c]++;
        }
        for(int val : mp){
            // calculate pairs
            ans += ((val/2)*2);
            
            if(val%2==1){
                hasSingleChar = true;
            }
        }

        return hasSingleChar ? ans+1 : ans;
    }

    //Sorting the Sentence
    public String sortSentence(String s) {
        StringBuilder temp = new StringBuilder();
        String[] words = new String[10];
        for(int i=0;  i<s.length(); i++){
            char c = s.charAt(i);

            if(c!=' ') temp.append(c);

            if(c==' ' || i==s.length()-1){
                int idx = temp.charAt(temp.length()-1) - '0';
                temp.deleteCharAt(temp.length()-1);
                words[idx] = temp.toString();
                temp = new StringBuilder();
            }
        }
        StringBuilder ans = new StringBuilder();
        for(String word : words){
            if(word!=null){
                ans.append(word);
                ans.append(' ');
            }
        }
        ans.deleteCharAt(ans.length()-1);
        return ans.toString();
    }

    // Defanging an IP Address
    public String defangIPaddr(String address) {
        StringBuilder ans = new StringBuilder();
        for(char c:address.toCharArray()){
            if(c=='.') ans.append("[.]");
            else ans.append(c);
        }
        return ans.toString();
    }

    // String Rotated by 2 Places
    public static boolean isRotated(String s1, String s2) {
        if(s1.length()<2) return s2.equals(s1);
        String clockwiser = s1.substring(2) + s1.substring(0,2);
        String antiClockwise = s1.substring(s1.length()-2) + s1.substring(0,s1.length()-2);
        return s2.equals(clockwiser) || s2.equals(antiClockwise);
    }

    // Check if the Sentence Is Pangram
    public boolean checkIfPangram(String sentence) {
        int n = sentence.length();
        if(n<26) return false;
        HashMap<Character,Integer> mp = new HashMap<>();

        for(int i=0; i<n; i++){
            mp.put(sentence.charAt(i),mp.getOrDefault(sentence.charAt(i),0)+1);
        }

        return mp.size()==26;
    }
    // little optimized with use of array instead of map as array of primitive type takes less space
    public boolean checkIfPangram2(String sentence) {
        int n = sentence.length();
        if(n<26) return false;
        boolean[] seen = new boolean[26];
        int unique = 0;

        for(int i=0; i<n; i++){
            int idx = sentence.charAt(i)-'a';
            if(!seen[idx]){
                unique++;
                seen[idx]=true;
                if(unique==26) return true;
            }
        }

        return false;
    }

    // sort a string
    // instead of real sorting we are keeping using the count of each alphabet
    String sort(String s) 
    {
        int[] count = new int[26];
        for(char c:s.toCharArray()){
            count[c-'a']++;
        }
        
        String ans = "";
        for(int i=0; i<26; i++){
            if(count[i]==0) continue;
            char c = (char)(i+97);
            for(int j=0; j<count[i]; j++){
                ans += c;
            }
        }
        
        return ans;
    }
    
    // Sort Vowels in a String
    String sortString(String str){
        int[] mp = new int[128];
        for (char c : str.toCharArray()) {
            mp[c]++;
        }

        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < 128; i++) {
            for (int j = 0; j < mp[i]; j++) {
                ans.append((char) i);
            }
        }

        return ans.toString();
    }
    boolean isVowel(char c){
        return "aeiouAEIOU".indexOf(c) != -1;
    }
    public String sortVowels(String s) {
        StringBuilder vowelStr = new StringBuilder();
        for(char c:s.toCharArray()){
            if(isVowel(c)) vowelStr.append(c);
        }

        String sortedVowelStr = sortString(vowelStr.toString());

        StringBuilder ansStr = new StringBuilder();
        int vowelIdx = 0;
        for(char c:s.toCharArray()){
            if(isVowel(c)){
                ansStr.append(sortedVowelStr.charAt(vowelIdx++));
            }else{
                ansStr.append(c);
            }
        }
        return ansStr.toString();
    }

    // add strings
    public String addStrings(String num1, String num2) {
        int n = Math.max(num1.length(),num2.length());
        StringBuilder s1 = new StringBuilder(num1);
        StringBuilder s2 = new StringBuilder(num2);

        // making the string equal in length i.e.  "34","266" => "034","266"  
        if(s1.length()<n){
            int diff = n-s1.length();
            for(int i=0; i<diff; i++) s1.insert(0,'0');
        }else if(s2.length()<n){
            int diff = n-s2.length();
            for(int i=0; i<diff; i++) s2.insert(0,'0');
        }

        int carry = 0;
        StringBuilder ans = new StringBuilder();
        for(int i=n-1; i>=0; i--){
            int digit1 = s1.charAt(i)-'0';
            int digit2 = s2.charAt(i)-'0';
            int sum = carry+digit1+digit2;
            carry=0;
            if(sum>9) carry=1;
            sum = sum%10;
            ans.append((char)('0'+sum));
        }
        if(carry==1) ans.append('1');
        return ans.reverse().toString();
    }
    // cleaner and better (complexity is same though)
    public String addStrings2(String num1, String num2) {
        int i = num1.length()-1;
        int j = num2.length()-1;
        
        int carry = 0;
        StringBuilder ans = new StringBuilder();
        while(i>=0 || j>=0){
            int digit1 = i>=0 ? num1.charAt(i--)-'0' : 0;
            int digit2 = j>=0 ? num2.charAt(j--)-'0' : 0;
            int sum = carry+digit1+digit2;
            carry=0;
            if(sum>9) carry=1;
            ans.append(sum%10);
        }
        if(carry==1) ans.append('1');
        return ans.reverse().toString();
    }


    // Roman to Integer
    // intution :: if next element is greater then current then current is negative else positive
    public int getIntFromRomanDigit(char c){
        if(c=='I') return 1;
        else if(c=='V') return 5;
        else if(c=='X') return 10;
        else if(c=='L') return 50;
        else if(c=='C') return 100;
        else if(c=='D') return 500;
        else if(c=='M') return 1000;
        return -1;
    }
    public int romanToInt(String s) {
        int ans = 0;
        for(int i=0; i<s.length(); i++){
            int curr = getIntFromRomanDigit(s.charAt(i));
            int next = i<s.length()-1 ? getIntFromRomanDigit(s.charAt(i+1)) : 0;
            if(next>curr){
                ans -= curr;
            }else ans+= curr;
        }
        return ans;
    }

    // integer to roman
    public String intToRoman(int num) {
        final int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        final String[] symbols = {"M", "CM", "D",  "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < values.length; ++i) {
            if (num == 0)
                break;
            while (num >= values[i]) {
                sb.append(symbols[i]);
                num -= values[i];
            }
        }

        return sb.toString();        
    }

    // Factorials of large numbers
    public static ArrayList<Integer> factorial(int n) {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        arr.add(1);
        int carry = 0;
        while(n>1 || carry!=0){
            int sz = arr.size();
            for(int i=0; i<sz; i++){
                int digit = arr.get(i);
                int mul = (n*digit)+carry;
                carry = mul/10;
                arr.set(i,mul%10);
            }
            while(carry!=0){
                arr.add(carry%10);
                carry=carry/10;
            }
            n--;
        }
        Collections.reverse(arr);
        return arr;
    }

    
    // Sort Characters By Frequency
    public String frequencySort(String s) {
        int[] freq = new int[128];
        // store the freq : TC = O(N), SC = O(1) as character can't be more than 128
        for(char c : s.toCharArray()) freq[(int)c]++;

        // make list of chars : TC and SC both O(1) as character can't be more than 128
        List<Character> arl = new ArrayList<>();
        for(int i=0; i<128; i++){
            if(freq[i]>0) arl.add((char)i);
        }

        // sort that list accrording to freq
        // sorting a list of 128 charcs so TC = O(1)
        arl.sort((a,b) -> freq[b]-freq[a]);

        // construct answer from them , TC : O(N) and SC : O(N)
        StringBuilder ans = new StringBuilder();
        for(char c : arl){
            for(int i=0; i<freq[c]; i++) ans.append(c);
        }

        return ans.toString();
    }
    

    // TOPIC :: String matching
    // Rotate String
    public boolean rotateString(String s, String goal) {
        int n = s.length();
        if(goal.length() != s.length()) return false;
        String temp = s + s;
        
        for(int i=0; i<n; i++){
            if(temp.substring(i,i+n).equals(goal)) return true;
        }

        return false;
    }
    

    // ** TOPIC :: Sliding window

    // Longest Substring Without Repeating Characters
    public int lengthOfLongestSubstring(String s) {
        HashMap<Character,Integer> mp = new HashMap<>();
        int start=0,end=0,n=s.length(),ans=0;
        boolean isRepeating=false;
        
        while(end<n){
            int endFreq = mp.getOrDefault(s.charAt(end),0);
            mp.put(s.charAt(end),endFreq+1);
            if(endFreq>=1) isRepeating=true;

            while(isRepeating && start<n){
                int startFreq = mp.getOrDefault(s.charAt(start),0);
                mp.put(s.charAt(start),startFreq-1);
                start++;
                if(startFreq>1) isRepeating=false;
            }

            ans = Math.max(ans,end-start+1);
            end++;
        }

        return ans;
    }

    // Smallest distinct window :: find the smallest window length that contains all the characters of the given string at least one time.
    public int findSubString(String s) {
        HashMap<Character,Integer> mp = new HashMap<>();
        
        for(char c:s.toCharArray()) mp.put(c,0);
        int uniqueElements = mp.size();
        mp.clear();
        
        boolean isValid = false;
        int start=0,end=0,ans=Integer.MAX_VALUE,n=s.length();
        
        while(end<n){
            mp.put(s.charAt(end),mp.getOrDefault(s.charAt(end),0)+1);
            if(mp.size()==uniqueElements) isValid=true;
            
            while(isValid){
                ans = Math.min(ans,end-start+1);
                int freq = mp.getOrDefault(s.charAt(start),0);
                if(freq<=1){
                    mp.remove(s.charAt(start));
                }else mp.put(s.charAt(start),freq-1);
                start++;
                if(mp.size()!=uniqueElements) isValid=false;
            }
            
            end++;
        }
        
        return ans;
    }
    




    // KMP Algorithm

    // Longest Prefix Suffix
    // naive approach -> TC : O(N^2), SC : O(N^2)
    int longestPrefixSuffix(String str) {
        StringBuilder s = new StringBuilder(str);
        int n = s.length();
        int ans = 0;
        for(int i=1; i<n; i++){
            if(s.substring(0,i).equals(s.substring(n-i,n)))
                ans = i;
        }
        return ans;
    }
    // Optimised *KMP Method*
    


}
