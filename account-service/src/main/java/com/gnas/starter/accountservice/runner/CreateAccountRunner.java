package com.gnas.starter.accountservice.runner;

import com.gnas.starter.accountservice.application.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Order(1)
@RequiredArgsConstructor
@Slf4j
public class CreateAccountRunner implements CommandLineRunner {
    private final AccountService accountService;

    private final String CUSTOMER_ID = "c496223a-e33f-48ba-ad19-67fdf900fe3b";

    @Override
    public void run(String... args) throws Exception {
//        accountService.openAccount(CUSTOMER_ID, "VND");
    }

    /**
     * Input: nums = [1,3,4,6,8,10,13], target = 13
     * Output: True (3 + 10 = 13)
     * Input: nums = [1,3,4,6,8,10,13], target = 6
     * Output: False
     * This is solved by two-pointer pattern with time complexity bigO(n)
     */
    public boolean isPairSum(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        boolean isFound = false;

        while (left < right) {
            int leftVal = nums[left];
            int rightVal = nums[right];
            if (leftVal + rightVal < target) {
                left++;
            } else if (leftVal + rightVal > target) {
                right--;
            } else {
                isFound = true;
                break;
            }
        }

        return isFound;
    }

    /**
     * Input: nums = [-1,0,1,2,-1,-1]
     * Output: [[-1,-1,2],[-1,0,1]]
     * Step 1: we are gonna sort the input by ASC -> [-1,-1,-1,0,1,2]
     * Step 2: we pick fix nums[i] as the first number of triple. Therefore, here becomes two-sum problem
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i-1]) continue;

            int target = -nums[i];
            int left = i+1;
            int right = nums.length - 1;
            while (left < right) {
                int leftVal = nums[left];
                int rightVal = nums[right];
                int sum = leftVal + rightVal;

                if (sum == target) {
                    List<Integer> metList = List.of(-target, leftVal, rightVal);
                    result.add(metList);

                    // avoid duplication
                    while (left < right && nums[left] == leftVal) left++;
                    while (left < right && nums[right] == rightVal) right--;
                } else if (sum > target) {
                    right--;
                } else {
                    left++;
                }
            }
        }

        return result;
    }
}
