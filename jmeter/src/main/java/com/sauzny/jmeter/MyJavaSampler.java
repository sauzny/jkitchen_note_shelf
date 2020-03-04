package com.sauzny.jmeter;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class MyJavaSampler extends AbstractJavaSamplerClient {

    private static final Logger log = LoggerFactory.getLogger(MyJavaSampler.class);

    /**
     * 必需实现的方法
     * public SampleResult runTest(JavaSamplerContext context)
     *
     * 可选实现的方法
     *
     * 在运行测试的时候，需要用户提供一些输入
     * public Arguments getDefaultParameters()
     *
     * 运行一些针对一个虚拟用户的一次性起始、准备性的操作
     * public void setupTest(JavaSamplerContext context)
     *
     * 运行一些针对一个虚拟用户的收尾的操作，比如清除连接等
     * 该方法的调用不是在单个虚拟用户的线程里执行的操作，而是所有虚拟用户在一个线程里顺序执行的。
     * public void teardownTest(JavaSamplerContext context)
     **/

    public Arguments getDefaultParameters(){
        log.info("MyJavaSampler getDefaultParameters");
        Arguments params = new Arguments();
        params.addArgument("inNum","");
        params.addArgument("resultNum","66");
        return params;
    }

    public void setupTest(JavaSamplerContext context){
        log.info("MyJavaSampler setupTest");
    }

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        log.info("MyJavaSampler runTest");
        SampleResult result = new SampleResult();
        result.sampleStart();
        try {
            // TODO
            String inNum = javaSamplerContext.getParameter("inNum");
            log.info("接收参数 inNum = {}", inNum);

            //发出请求
            result.sampleEnd();
            //请求成功，设置测试结果为成功
            result.setSuccessful(true);
            // 会展示在 results tree 中的 Response Data 中
            result.setResponseData("data...", StandardCharsets.UTF_8.name());
            result.setResponseMessage("message..");
            result.setResponseCodeOK();
        } catch (Exception e) {
            //请求失败，设置测试结果为失败
            result.sampleEnd();
            result.setSuccessful(false);
            result.setResponseCode("500");
            // TODO
        }
        return result;
    }

    public void teardownTest(JavaSamplerContext context){
        log.info("MyJavaSampler teardownTest");
    }

}
