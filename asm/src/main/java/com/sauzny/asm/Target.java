package com.sauzny.asm;

/***************************************************************************
 *
 * @时间: 2019/10/24 - 13:35
 *
 * @描述: TODO
 *
 ***************************************************************************/
public class Target {

    public Target(){}

    private String name;

    public void sayHello() {
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void say(){
        System.out.println("介绍我的名字");
    }
}
