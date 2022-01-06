/**
 * Design a bank account management system that implements the following three functions:
 * 1.void deposite(int id,int amount,long timestamp)
 * 2.boolean withdraw(int id,int amount,long timestamp)
 * 3.int[] check(int id, long startTime, long endTime)
 */

import java.util.*;

class Account {
    private int id;
    private int currentBalance;
    private List<Integer> balances;
    private List<Long> timestamps;

    public Account(int _id) {
        id = _id;
        currentBalance = 0;
        balances = new ArrayList<>();
        timestamps = new ArrayList<>();
    }

    public void deposite(int amount, long timestamp) {
        currentBalance += amount;
        balances.add(currentBalance);
        timestamps.add(timestamp);
    }

    public boolean withdraw(int amount , long timestamp) {
        if (currentBalance < amount) {
            return false;
        }

        currentBalance -= amount;
        balances.add(currentBalance);
        timestamps.add(timestamp);
        return true;
    }

    public int[] check(long startTime, long endTime) {
        return new int[]{findLeftClosest(startTime), findLeftClosest(endTime)};
    }

    private int findLeftClosest(long target){
        int len = timestamps.size();
        if (len == 0) {
            return 0;
        }

        int left = 0, right = len - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (timestamps.get(mid) == target) {
                return balances.get(mid);
            } else if (timestamps.get(mid) < target) {
                left = mid;
            } else {
                right = mid;
            }
        }

        if (timestamps.get(left) > target){
            return left == 0 ? 0 : balances.get(left - 1);
        }

        if (timestamps.get(right) > target) {
            return right == 0 ? 0 : balances.get(right - 1);
        }

        return balances.get(right);
    }
}

public class BankSystem {
    private Map<Integer, Account> idToAccount;

    public BankSystem() {
        // Write your code here
        idToAccount = new HashMap<>();
    }

    /**
     * @param id:        user account id
     * @param amount:    the number of bank deposits
     * @param timestamp: the data of bank transaction
     * @return: nothing
     */
    public void deposite(int id, int amount, long timestamp) {
        // Write your code here
        if (!idToAccount.containsKey(id)) {
            idToAccount.put(id, new Account(id));
        }
        idToAccount.get(id).deposite(amount, timestamp);
    }

    /**
     * @param id:        user account id
     * @param amount     : the number of bank withdraw
     * @param timestamp: the data of bank transaction
     * @return: if user account can not withdraw the number of amount,return false. else return true
     */
    public boolean withdraw(int id, int amount, long timestamp) {
        // Write your code here
        if (!idToAccount.containsKey(id)) {
            return false;
        }
        return idToAccount.get(id).withdraw(amount, timestamp);
    }

    /**
     * @param id:        user account id
     * @param startTime: start time
     * @param endTime:   end time
     * @return: need return two numbers,the first one is start time account balance,the second is end time account balance
     */
    public int[] check(int id, long startTime, long endTime) {
        // Write your code here
        if (!idToAccount.containsKey(id)) {
            return new int[0];
        }
        return idToAccount.get(id).check(startTime, endTime);
    }
}
