package com.sauzny.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Xml2Bean {
    
    private static Department getSimpleDepartment() {  
        List<Staff> staffs = new ArrayList<Staff>();
          
        Staff stf = new Staff();  
        stf.setName("周杰伦");  
        stf.setAge(30);  
        stf.setSmoker(false);  
        staffs.add(stf);  
        stf.setName("周笔畅");  
        stf.setAge(28);  
        stf.setSmoker(false);  
        staffs.add(stf);  
        stf.setName("周星驰");  
        stf.setAge(40);  
        stf.setSmoker(true);  
        staffs.add(stf);  
          
        Department dept = new Department();  
        dept.setName("娱乐圈");  
        dept.setStaffs(staffs);  
          
        return dept;  
    }  
    
    public static void bean2String() throws Exception{
        
        JAXBContext context = JAXBContext.newInstance(Department.class,Staff.class);    // 获取上下文对象  
        Marshaller marshaller = context.createMarshaller(); // 根据上下文获取marshaller对象  
          
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");  // 设置编码字符集  
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 格式化XML输出，有分行和缩进  
        
        //marshaller.marshal(getSimpleDepartment(),System.out);   // 打印到控制台  
          
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        marshaller.marshal(getSimpleDepartment(), baos);  
        String xmlObj = new String(baos.toByteArray());         // 生成XML字符串  
        System.out.println(xmlObj);  
    }
    
    public static void string2bean() throws Exception{
        
        String xmlStr = "";
        
        JAXBContext context = JAXBContext.newInstance(Department.class, Staff.class);  
        Unmarshaller unmarshaller = context.createUnmarshaller();  
        Department department = (Department)unmarshaller.unmarshal(new StringReader(xmlStr));
    }
    
    public static void main(String[] args) throws Exception{
        
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        System.out.println(uuid);
        
        Xml2Bean.bean2String();  
    }
}
