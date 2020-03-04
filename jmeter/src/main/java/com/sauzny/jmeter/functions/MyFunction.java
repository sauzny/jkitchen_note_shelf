package com.sauzny.jmeter.functions;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class MyFunction extends AbstractFunction {

    private CompoundVariable[] values;

    @Override
    public String getReferenceKey() {
        return "__MyFunc01";
    }

    @Override
    public List<String> getArgumentDesc() {
        List<String> desc = new LinkedList<String>();
        desc.add("参数1的描述");
        desc.add("参数2的描述");
        return desc;
    }

    @Override
    public void setParameters(Collection<CompoundVariable> collection) throws InvalidVariableException {
        // 检查参数的个数是否正确
        checkParameterCount(collection, 1, 2);
        values = new CompoundVariable[collection.size()];
        collection.toArray(values);
    }

    @Override
    public String execute(SampleResult sampleResult, Sampler sampler) throws InvalidVariableException {
        String p1 = values[0].execute();
        String p2 = values[0].execute();
        return p1+p2;
    }
}
