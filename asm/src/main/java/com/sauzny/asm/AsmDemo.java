package com.sauzny.asm;

import org.objectweb.asm.*;
import static org.objectweb.asm.Opcodes.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/***************************************************************************
 *
 * @时间: 2019/10/24 - 13:40
 *
 * @描述: TODO
 *
 ***************************************************************************/
public class AsmDemo {

    // 增加 get set
    public static void sg(String className){

        String fileSeparator = System.getProperty("file.separator");
        String classPath = System.getProperty("java.class.path").split(System.getProperty("path.separator"))[0] + fileSeparator + className.replaceAll("\\.", "\\\\") + ".class";


        try {
            ClassReader classReader = new ClassReader(Files.readAllBytes(Paths.get(classPath)));
            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);

            // 使用ASMPlugin生成的代码
            AsmDemo.genGetFromASMPlugin(classWriter);
            AsmDemo.genSetFromASMPlugin(classWriter);

            classReader.accept(classWriter, ClassReader.EXPAND_FRAMES);

            // 获取修改后的 class 文件对应的字节数组
            // 覆盖原来的class文件

            Files.write(Paths.get(classPath), classWriter.toByteArray());

            System.out.println("修改成功");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failure!");
        }
    }

    public static void genGetFromASMPlugin(ClassWriter classWriter){

        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "getName", "()Ljava/lang/String;", null, null);
        methodVisitor.visitCode();
        Label label0 = new Label();
        methodVisitor.visitLabel(label0);
        methodVisitor.visitLineNumber(17, label0);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitFieldInsn(GETFIELD, "com/sauzny/asm/Target", "name", "Ljava/lang/String;");
        methodVisitor.visitInsn(ARETURN);
        Label label1 = new Label();
        methodVisitor.visitLabel(label1);
        methodVisitor.visitLocalVariable("this", "Lcom/sauzny/asm/Target;", null, label0, label1, 0);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();

    }

    public static void genSetFromASMPlugin(ClassWriter classWriter){

        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "setName", "(Ljava/lang/String;)V", null, null);
        methodVisitor.visitCode();
        Label label0 = new Label();
        methodVisitor.visitLabel(label0);
        methodVisitor.visitLineNumber(21, label0);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitFieldInsn(PUTFIELD, "com/sauzny/asm/Target", "name", "Ljava/lang/String;");
        Label label1 = new Label();
        methodVisitor.visitLabel(label1);
        methodVisitor.visitLineNumber(22, label1);
        methodVisitor.visitInsn(RETURN);
        Label label2 = new Label();
        methodVisitor.visitLabel(label2);
        methodVisitor.visitLocalVariable("this", "Lcom/sauzny/asm/Target;", null, label0, label2, 0);
        methodVisitor.visitLocalVariable("name", "Ljava/lang/String;", null, label0, label2, 1);
        methodVisitor.visitMaxs(2, 2);
        methodVisitor.visitEnd();
    }

    public static void main(String[] args) {

        String className = "com.sauzny.asm.Target";
        AsmDemo.sg(className);
    }
}
