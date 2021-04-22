package org.example.demo0101.ddd;

import java.util.Arrays;

public class PhoneNumber {

    // 1. 通过 private final String number 确保 PhoneNumber 是一个（Immutable）Value Object。
    // （一般来说 VO 都是 Immutable 的，这里只是重点强调一下）
    private final String number;
    public String getNumber() {
        return number;
    }

    public PhoneNumber(String number) throws ValidationException {
        // 2. 校验逻辑都放在了 constructor 里面，确保只要 PhoneNumber 类被创建出来后，一定是校验通过的
        if (number == null) {
            throw new ValidationException("number不能为空");
        } else if (isValid(number)) {
            throw new ValidationException("number格式错误");
        }
        this.number = number;
    }

    // 3. 之前的 findAreaCode 方法变成了 PhoneNumber 类里的 getAreaCode
    // 突出了 areaCode 是  PhoneNumber 的一个计算属性
    public String getAreaCode() {
        for (int i = 0; i < number.length(); i++) {
            String prefix = number.substring(0, i);
            if (isAreaCode(prefix)) {
                return prefix;
            }
        }
        return null;
    }

    private static boolean isAreaCode(String prefix) {
        String[] areas = new String[]{"0571", "021", "010"};
        return Arrays.asList(areas).contains(prefix);
    }

    public static boolean isValid(String number) {
        String pattern = "^0?[1-9]{2,3}-?\\d{8}$";
        return number.matches(pattern);
    }

}
