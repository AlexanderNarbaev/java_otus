package ru.otus.hw04.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class PrintMessageMethodVisitor extends MethodVisitor {
    private boolean isAnnotationPresent;
    private String methodName;
    private String methodDescriptor;

    public PrintMessageMethodVisitor(MethodVisitor mv, String name, String methodDescriptor) {
        super(Opcodes.ASM5, mv);
        this.methodName = name;
        this.methodDescriptor = methodDescriptor;

    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if ("Lru/otus/hw04/classloader/Log;".equals(desc)) {
            isAnnotationPresent = true;
        }
        return super.visitAnnotation(desc, visible);
    }

    public void visitCode() {
        super.visitCode();
        if (isAnnotationPresent) {
            try {
                Type methodType = Type.getMethodType(methodDescriptor);
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System",
                        "out", "Ljava/io/PrintStream;");
                mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
                mv.visitInsn(Opcodes.DUP);
                mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                mv.visitLdcInsn("A call was made to method \"");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        "java/lang/StringBuilder", "append",
                        "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                mv.visitLdcInsn(methodName);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        "java/lang/StringBuilder", "append",
                        "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                mv.visitLdcInsn("\" and params:");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        "java/lang/StringBuilder", "append",
                        "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                int paramsCount = 1;
                for (Type argumentType : methodType.getArgumentTypes()) {
                    mv.visitLdcInsn("\" [" + paramsCount + "] Type:");
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                            "java/lang/StringBuilder", "append",
                            "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    mv.visitLdcInsn(argumentType.getClassName());
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    mv.visitLdcInsn(", Value:");
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    try {
                        if (argumentType.equals(Type.BOOLEAN_TYPE)) {
                            mv.visitVarInsn(Opcodes.ILOAD, paramsCount);
//                            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String", "valueOf", "(Z)Ljava/lang/String;", false);
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Z)Ljava/lang/StringBuilder;", false);
                        } else if (argumentType.equals(Type.BYTE_TYPE)) {
                            mv.visitVarInsn(Opcodes.ILOAD, paramsCount);
//                            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String", "valueOf", "(I)Ljava/lang/String;", false);
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
                        } else if (argumentType.equals(Type.CHAR_TYPE)) {
                            mv.visitVarInsn(Opcodes.ILOAD, paramsCount);
//                            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String", "valueOf", "(I)Ljava/lang/String;", false);
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
                        } else if (argumentType.equals(Type.SHORT_TYPE)) {
                            mv.visitVarInsn(Opcodes.ILOAD, paramsCount);
//                            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String", "valueOf", "(I)Ljava/lang/String;", false);
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
                        } else if (argumentType.equals(Type.INT_TYPE)) {
                            mv.visitVarInsn(Opcodes.ILOAD, paramsCount);
//                            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String", "valueOf", "(I)Ljava/lang/String;", false);
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
                        } else if (argumentType.equals(Type.LONG_TYPE)) {
                            mv.visitVarInsn(Opcodes.LLOAD, paramsCount);
//                            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String", "valueOf", "(J)Ljava/lang/String;", false);
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
                            paramsCount++;
                        } else if (argumentType.equals(Type.FLOAT_TYPE)) {
                            mv.visitVarInsn(Opcodes.FLOAD, paramsCount);
//                            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String", "valueOf", "(F)Ljava/lang/String;", false);
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(F)Ljava/lang/StringBuilder;", false);
                        } else if (argumentType.equals(Type.DOUBLE_TYPE)) {
                            mv.visitVarInsn(Opcodes.DLOAD, paramsCount);
//                            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String", "valueOf", "(D)Ljava/lang/String;", false);
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(D)Ljava/lang/StringBuilder;", false);
                            paramsCount++;
                        } else {
                            mv.visitVarInsn(Opcodes.ALOAD, paramsCount);
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    mv.visitLdcInsn(";");
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                            "java/lang/StringBuilder", "append",
                            "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    paramsCount++;
                }
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int getRightOperation(String descriptor) {
        switch (descriptor) {
            case "Z":
            case "C":
            case "B":
            case "S":
            case "I":
                return Opcodes.ILOAD;
            case "J":
                return Opcodes.LLOAD;
            case "F":
                return Opcodes.FLOAD;
            case "D":
                return Opcodes.DLOAD;
            default:
                return Opcodes.ALOAD;
        }
    }
}