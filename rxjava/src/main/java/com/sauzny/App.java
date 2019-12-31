package com.sauzny;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.*;

/**
 * Hello world!
 */
public class App {

    private static List<PolicyConvertor.XDMOper> groupWrite(Collection<PolicyConvertor.XDMOper> group) {
        List<PolicyConvertor.XDMOper> errorDatas = new ArrayList<>();
        System.out.println(group);
        return errorDatas;
    }

    public static void main(String[] args) {

        PolicyConvertor policyConvertor = new PolicyConvertor();

        List<Data> dataList = new ArrayList<Data>();
        Data d1 = new Data();
        d1.setDc("A");
        d1.setValue("1,2");

        Data d2 = new Data();
        d2.setDc("B");
        d2.setValue("3,4");

        Data d3 = new Data();
        d3.setDc("A");
        d3.setValue("5,6");

        Data d4 = new Data();
        d4.setDc("C");
        d4.setValue("7,8");

        dataList.add(d1);
        dataList.add(d2);
        dataList.add(d3);
        dataList.add(d4);

        for (int i = 0; i < 1; i++) {
            /*
            Map<String, Collection<PolicyConvertor.XDMOper>> map = Flowable.fromIterable(dataList)
                    .parallel(8)
                    .runOn(Schedulers.computation())
                    .concatMap(txData -> Flowable.fromIterable(policyConvertor.convert(txData)))
                    .sequential()
                    .observeOn(Schedulers.computation())
                    .toMultimap(oper -> oper.getDc())
                    //+ oper.getSValue() % xdmConfig.getConnSizePerDC())
                    .blockingGet();
            */
            Map<String, Collection<PolicyConvertor.XDMOper>> map = new HashMap<>();
            dataList.forEach(txData ->
                    policyConvertor.convert(txData).forEach(oper -> {
                                String key = oper.getDc();
                                map.putIfAbsent(key, new ArrayList<>());
                                map.get(key).add(oper);
                            }
                    )
            );


            List<PolicyConvertor.XDMOper> errorList = Flowable.fromIterable(map.values())
                    .parallel(20)
                    .runOn(Schedulers.io())
                    .flatMap(datas -> Flowable.fromIterable(groupWrite(datas)))
                    .sequential()
                    .toList()
                    .blockingGet();

            System.out.println(map);
        }
    }
}
