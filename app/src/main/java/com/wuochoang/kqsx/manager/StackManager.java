package com.wuochoang.kqsx.manager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuyenLx on 8/9/2017.
 */

public class StackManager {
    public List<Integer> stack;

    public StackManager() {
        stack = new ArrayList<>();
        stack.add(0);
    }

    public void add(int position) {
        if (position == 0) {
            stack.add(0);
        } else {
            for (int i = 0; i < stack.size(); i++) {
                if (stack.get(i) == position) {
                    stack.remove(i);
                }
            }
            stack.add(position);
        }
    }

    public int getTabBefore() {
        if (stack.size() > 2) {
            int result = stack.get(stack.size() - 2);
            stack.remove(stack.size() - 1);
            return result;
        } else if (stack.size() == 2 && stack.get(0) == stack.get(1)) {
            stack.clear();
            return 0;
        } else if (stack.size() == 2) {
            stack.clear();
            return 0;
        } else return -1;
    }

    public boolean isEmpty() {
        return stack.size() == 0;
    }

    public List<Integer> getStack() {
        return stack;
    }
}
