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
                if (className.contains("CalculationClassImpl")) {
                    try (OutputStream fos = new FileOutputStream(className.substring(className.lastIndexOf('/') + 1) + "ASM.class")) {
                        fos.write(finalClass);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return finalClass;
            }
        });
    }
}