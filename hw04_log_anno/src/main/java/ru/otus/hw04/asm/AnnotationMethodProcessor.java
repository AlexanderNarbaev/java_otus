package ru.otus.hw04.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AnnotationMethodProcessor extends MethodVisitor {

    private boolean isAnnotationPresent;

    public AnnotationMethodProcessor(MethodVisitor methodVisitor, boolean isAnnotationPresent) {
        super(Opcodes.ASM5, methodVisitor);
        this.isAnnotationPresent = isAnnotationPresent;
    }


    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if ("Lru/otus/hw04/classloader/Log;".equals(desc)) {
            isAnnotationPresent = true;
        }
        return super.visitAnnotation(desc, visible);
    }
}