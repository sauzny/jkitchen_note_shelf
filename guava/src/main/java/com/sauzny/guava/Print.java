package com.sauzny.guava;

public final class Print {

    private Print(){}
    
    public static void sysoutThree(final Object... args){
        sysout("方法：{}\t说明：{}\t结果：{}", args);;
    }
    
    public static void sysout(final String format, final Object... args) {
        
        if(!format.contains("{}")){
            System.out.println(format);
        }else{
            String[] strs = format.split("\\{\\}");
            String[] result = new String[strs.length];
            for(int i=0;i<strs.length;i++){
                if(args.length>i){
                    result[i] = strs[i] + args[i]; 
                }else{
                    result[i] = strs[i] + "{}";
                }
            }

            StringBuilder sb = new StringBuilder();
            for(int i=0;i<result.length;i++){
                sb.append(result[i]);
            }
            
            System.out.println(sb.toString());
        }
        
    }
}
