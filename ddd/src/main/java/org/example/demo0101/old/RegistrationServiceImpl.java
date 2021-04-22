package org.example.demo0101.old;

import java.util.Arrays;

public class RegistrationServiceImpl implements RegistrationService {

    /**
     * 需求：
     *
     * 一个新应用在全国通过 地推业务员 做推广，需要做一个用户的注册系统，在用户注册后能够通过用户电话号的区号对业务员发奖金。
     */

    private SalesRepRepository salesRepRepo;
    private UserRepository userRepo;

    // ** 问题1 - 接口的清晰度
    // ** 有没有办法让方法入参一目了然，避免入参错误导致的 bug ？
    // ** 有没有办法在编码时就避免这种可能会出现的问题？

    // ** 问题4 - 可测试性
    // ** 假如一个方法有 N 个参数，每个参数有 M 个校验逻辑，至少要有 N * M  个 TC
    // ** 而假设有 P 个方法中都用到了 phone 这个字段，则整体需要： P * N * M 个 TC
    // ** 在这个情况下，降低测试成本 == 提升代码质量，如何能够降低测试的成本呢？
    public User register(String name, String phone, String address) throws ValidationException {
        // ** 问题2 - 数据验证和错误处理
        // ** 有没有一种方法，能够一劳永逸的解决所有校验的问题以及降低后续的维护成本和异常处理成本呢？
        // ** 业务逻辑异常和数据校验异常被混在了一起，是否是合理的？
        // 校验逻辑
        if (name == null || name.length() == 0) {
            throw new ValidationException("name");
        }
        if (phone == null || !isValidPhoneNumber(phone)) {
            throw new ValidationException("phone");
        }
        // 此处省略address的校验逻辑

        // ** 问题3 - 业务代码的清晰度
        // ** 这是一段【胶水代码】，其本质是由于外部依赖的服务的入参并不符合我们原始的入参导致的
        // ** 常见的办法是将这段代码抽离出来，变成独立的一个或多个方法。可能会抽离出一个静态工具类 PhoneUtils
        // ** 静态工具类是否是最好的实现方式呢？
        // 取电话号里的区号，然后通过区号找到区域内的SalesRep
        String areaCode = null;
        String[] areas = new String[]{"0571", "021", "010"};
        for (int i = 0; i < phone.length(); i++) {
            String prefix = phone.substring(0, i);
            if (Arrays.asList(areas).contains(prefix)) {
                areaCode = prefix;
                break;
            }
        }
        SalesRep rep = salesRepRepo.findRep(areaCode);

        // 最后创建用户，落盘，然后返回
        User user = new User();
        user.name = name;
        user.phone = phone;
        user.address = address;
        if (rep != null) {
            user.repId = rep.repId;
        }

        return userRepo.save(user);
    }

    private boolean isValidPhoneNumber(String phone) {
        String pattern = "^0[1-9]{2,3}-?\\d{8}$";
        return phone.matches(pattern);
    }
}
