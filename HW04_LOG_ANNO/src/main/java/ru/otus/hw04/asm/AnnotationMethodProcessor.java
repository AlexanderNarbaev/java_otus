package ru.otus.hw04.asm;

import org.objectweb.asm.*;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import static org.objectweb.asm.Opcodes.H_INVOKESTATIC;

public class AnnotationMethodProcessor extends MethodVisitor {
    private boolean isAnnotationPresent;
    private MethodVisitor methodVisitor;
    private int access;
    private String name;
    private String className;
    private String descriptor;
    private ClassVisitor cw;
    private String signature;
    private String[] exceptions;

    public AnnotationMethodProcessor(MethodVisitor methodVisitor, int access, String name, String className, String descriptor, ClassVisitor cw, String signature, String[] exceptions) {
        super(Opcodes.ASM5, methodVisitor);
        this.methodVisitor = methodVisitor;
        this.access = access;
        this.name = name;
        this.className = className;
        this.descriptor = descriptor;
        this.cw = cw;
        this.signature = signature;
        this.exceptions = exceptions;
    }


    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if ("Lru/otus/hw04/classloader/Log;".equals(desc)) {
            isAnnotationPresent = true;
            return super.visitAnnotation(desc, visible);
//            return new AnnotationVisitor(Opcodes.ASM5,
//                    super.visitAnnotation(desc, visible)) {
//                public AnnotationVisitor visitArray(String name) {
//                    if ("fields".equals(name)) {
//                        return new AnnotationVisitor(Opcodes.ASM5,
//                                super.visitArray(name)) {
//                            public void visit(String name, Object value) {
////                                parameterIndexes.add((String) value);
//                                super.visit(name, value);
//                            }
//                        };
//                    } else {
//                        return super.visitArray(name);
//                    }
//                }
//            };
        }
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitCode() {
        if (isAnnotationPresent) {
            cw.visitMethod(access, name + "Proxied", descriptor, signature, exceptions);
            MethodVisitor localMethodVisitor = cw.visitMethod(Opcodes.ACC_PUBLIC, name, descriptor, null, exceptions);
            Handle handle = new Handle(
                    H_INVOKESTATIC,
                    Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
                    "makeConcatWithConstants",
                    MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
                    false);

            System.out.println("MethodDescriptor:\t" + MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString());

            localMethodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            localMethodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            localMethodVisitor.visitInvokeDynamicInsn("makeConcatWithConstants", "(I)Ljava/lang/String;", handle, "executed method: " + name + ", param:\u0001");

            localMethodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

            localMethodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
            localMethodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            localMethodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, className, name + "Proxied", descriptor, false);

            localMethodVisitor.visitInsn(Opcodes.RETURN);
            localMethodVisitor.visitMaxs(0, 0);
            localMethodVisitor.visitEnd();
        } else {
            super.visitCode();
        }
    }
}