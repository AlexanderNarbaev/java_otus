package ru.otus.hw04.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * cd ..
 * mvn clean install
 * cd target
 * java -javaagent:HW04_LOG_ANNO-jar-with-dependencies.jar -jar HW04_LOG_ANNO-jar-with-dependencies.jar
 */
public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className,
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classfileBuffer) {
                ClassReader cr = new ClassReader(classfileBuffer);
                ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
                ClassVisitor cv = new LogMethodClassVisitor(cw, className);
                cr.accept(cv, 0);

                byte[] finalClass = cw.toByteArray();
//
                if (className.contains("CalculationClassImpl")) {
                    try {
//                        System.out.println("Try to write class `:" + className);
//                        System.out.println("Try to write class2 :" + className.lastIndexOf('/') );
//                        System.out.println("Try to write class3 " + className.lastIndexOf("class") );
                        String substring = className.substring(className.lastIndexOf('/') + 1);
//                        System.out.println("Try to write class:" + substring + "ASM.class");
                        try (OutputStream fos = new FileOutputStream(substring + "ASM.class")) {
                            fos.write(finalClass);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
//                    System.out.println("Not contains:\t" + className);
                }
                return finalClass;
//                return addLogging(classfileBuffer, className);
            }
        });
    }

//    private static byte[] addLogging(byte[] originalClass, String className) {
//        ClassReader cr = new ClassReader(originalClass);
//        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
//        boolean isAnnotationPresent;
//        ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw) {
//
//            @Override
//            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//
////                if (name.equals("calculation")) {
////                    return super.visitMethod(access, "calculationProxied", descriptor, signature, exceptions);
////                } else {
////                    return super.visitMethod(access, name, descriptor, signature, exceptions);
////                }
//            }
//        };
//        cr.accept(cv, Opcodes.ASM5);
//
//        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "calculation", "(I)V", null, null);
//
//        Handle handle = new Handle(
//                H_INVOKESTATIC,
//                Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
//                "makeConcatWithConstants",
//                MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
//                false);
//
//        System.out.println("MethodDescriptor:\t" + MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString());
//
//        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//        mv.visitVarInsn(Opcodes.ILOAD, 1);
//        mv.visitInvokeDynamicInsn("makeConcatWithConstants", "(I)Ljava/lang/String;", handle, "executed method: calculation, param:\u0001");
//
//        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//
//        mv.visitVarInsn(Opcodes.ALOAD, 0);
//        mv.visitVarInsn(Opcodes.ILOAD, 1);
//        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "ru/otus/hw04/asm/CalculationClassImpl", "calculationProxied", "(I)V", false);
//
//        mv.visitInsn(Opcodes.RETURN);
//        mv.visitMaxs(0, 0);
//        mv.visitEnd();
//
//
//        byte[] finalClass = cw.toByteArray();
//
//        try (OutputStream fos = new FileOutputStream("CalculationClassImplASM.class")) {
//            fos.write(finalClass);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return finalClass;
//    }

}


