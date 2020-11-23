package com.sauzny.jaxb.codegen;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JaxbClassLoader extends ClassLoader {

    private String rootPath;

    public JaxbClassLoader(String rootPath){
        this.rootPath = rootPath;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {

        Class<?> clazz = this.findLoadedClass(name);

        if(clazz == null){
            String path = this.rootPath + File.separatorChar + name.replace('.', File.separatorChar) + ".class";

            try{
                byte[] bytes = Files.readAllBytes(Paths.get(path));
                clazz = this.defineClass(null, bytes,0, bytes.length);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return clazz;
    }
}

