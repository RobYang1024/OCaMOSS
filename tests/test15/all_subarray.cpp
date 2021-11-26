// https://www.geeksforgeeks.org/print-all-subarrays-with-sum-in-a-given-range/
#include <bits/stdc++.h>
using namespace std;
  
// Function to find subarrrays in given range
void subArraySum(int arr[], int n,
                 int leftsum, int rightsum)
{
    int curr_sum, i, j, res = 0;
  
    // Pick a starting point
    for (i = 0; i < n; i++) {
        curr_sum = arr[i];
  
        // Try all subarrays starting with 'i'
        for (j = i + 1; j <= n; j++) {
            if (curr_sum > leftsum
                && curr_sum < rightsum) {
                cout << "{ ";
  
                for (int k = i; k < j; k++)
                    cout << arr[k] << " ";
  
                cout << "}\n";
            }
            if (curr_sum > rightsum || j == n)
                break;
            curr_sum = curr_sum + arr[j];
        }
    }
}
  
// Driver Code
int main()
{
    int arr[] = { 15, 2, 4, 8, 9, 5, 10, 23 };
    int N = sizeof(arr) / sizeof(arr[0]);
  
    int L = 10, R = 23;
  
    subArraySum(arr, N, L, R);
  
    return 0;
}