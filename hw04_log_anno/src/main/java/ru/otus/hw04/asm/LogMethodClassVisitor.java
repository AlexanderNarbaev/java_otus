package ru.otus.hw04.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LogMethodClassVisitor extends ClassVisitor {
    private final String className;

    public LogMethodClassVisitor(ClassVisitor cv, String pClassName) {
        super(Opcodes.ASM5, cv);
        className = pClassName;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
                                     String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature,
                exceptions);
        return new PrintMessageMethodVisitor(mv, name, desc);
    }
}
