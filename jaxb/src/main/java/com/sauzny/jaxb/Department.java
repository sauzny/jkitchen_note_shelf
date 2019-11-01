package com.sauzny.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="department")  
public class Department {
    
    private String name;    //部门名称  
    private List<Staff> staffs;           // 其实staff是单复同型，这里是加's'是为了区别staff  
      
    public String getName() {  
        return name;  
    }  
    @XmlAttribute  
    public void setName(String name) {  
        this.name = name;  
    }  
    public List<Staff> getStaffs() {  
        return staffs;  
    }  
    @XmlElement(name="staff")  
    public void setStaffs(List<Staff> staffs) {  
        this.staffs = staffs;  
    } 
}
