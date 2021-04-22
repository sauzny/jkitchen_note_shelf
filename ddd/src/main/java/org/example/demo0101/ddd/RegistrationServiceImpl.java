package org.example.demo0101.ddd;

import lombok.NonNull;

public class RegistrationServiceImpl implements RegistrationService {

    /**
     * 需求：
     *
     * 一个新应用在全国通过 地推业务员 做推广，需要做一个用户的注册系统，在用户注册后能够通过用户电话号的区号对业务员发奖金。
     */

    private SalesRepRepository salesRepRepo;
    private UserRepository userRepo;

    // ** 评估1 - 接口的清晰度

    // ** 评估2 - 数据验证和错误处理
    // ** 代码遵循了 DRY 原则和单一性原则，
    // ** 如果未来需要修改 PhoneNumber 的校验逻辑，只需要在一个文件里修改即可，所有使用到了 PhoneNumber 的地方都会生效。

    // ** 评估4 - 可测试性
    // ** 单个方法的 TC 从原来的 N * M 变成了今天的 N + M
    // ** 多个方法的 TC 数量变成了  N + M + P
    public User register(
            @NonNull Name name,
            @NonNull PhoneNumber phone,
            @NonNull Address address) {

        // ** 评估3 - 业务代码的清晰度

        // 找到区域内的SalesRep
        SalesRep rep = salesRepRepo.findRep(phone.getAreaCode());

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

}
