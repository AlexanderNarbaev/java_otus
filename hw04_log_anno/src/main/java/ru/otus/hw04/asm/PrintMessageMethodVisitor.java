package ru.otus.hw04.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class PrintMessageMethodVisitor extends MethodVisitor {
    private static final String JAVA_LANG_STRING_BUILDER = "java/lang/StringBuilder";
    private static final String APPEND = "append";
    private static final String LJAVA_LANG_STRING_LJAVA_LANG_STRING_BUILDER = "(Ljava/lang/String;)Ljava/lang/StringBuilder;";
    private boolean isAnnotationPresent;
    private final String methodName;
    private final String methodDescriptor;

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

    @Override
    public void visitCode() {
        super.visitCode();
        if (isAnnotationPresent) {
            try {
                Type methodType = Type.getMethodType(methodDescriptor);
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System",
                        "out", "Ljava/io/PrintStream;");
                mv.visitTypeInsn(Opcodes.NEW, JAVA_LANG_STRING_BUILDER);
                mv.visitInsn(Opcodes.DUP);
                mv.visitMethodInsn(Opcodes.INVOKESPECIAL, JAVA_LANG_STRING_BUILDER, "<init>", "()V", false);
                mv.visitLdcInsn("A call was made to method \"");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        JAVA_LANG_STRING_BUILDER, APPEND,
                        LJAVA_LANG_STRING_LJAVA_LANG_STRING_BUILDER, false);
                mv.visitLdcInsn(methodName);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        JAVA_LANG_STRING_BUILDER, APPEND,
                        LJAVA_LANG_STRING_LJAVA_LANG_STRING_BUILDER, false);
                mv.visitLdcInsn("\" and params:");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        JAVA_LANG_STRING_BUILDER, APPEND,
                        LJAVA_LANG_STRING_LJAVA_LANG_STRING_BUILDER, false);
                addParametersForLogging(methodType);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JAVA_LANG_STRING_BUILDER, "toString", "()Ljava/lang/String;", false);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addParametersForLogging(Type methodType) {
        int stackPostition = 1;
        int paramsNumber = 1;
        for (Type argumentType : methodType.getArgumentTypes()) {
            mv.visitLdcInsn("\" [" + paramsNumber + "] Type:");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                    JAVA_LANG_STRING_BUILDER, APPEND,
                    LJAVA_LANG_STRING_LJAVA_LANG_STRING_BUILDER, false);
            mv.visitLdcInsn(argumentType.getClassName());
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JAVA_LANG_STRING_BUILDER, APPEND, LJAVA_LANG_STRING_LJAVA_LANG_STRING_BUILDER, false);
            mv.visitLdcInsn(", Value:");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JAVA_LANG_STRING_BUILDER, APPEND, LJAVA_LANG_STRING_LJAVA_LANG_STRING_BUILDER, false);
            try {
                if (argumentType.equals(Type.BOOLEAN_TYPE)) {
                    mv.visitVarInsn(Opcodes.ILOAD, stackPostition);
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JAVA_LANG_STRING_BUILDER, APPEND, "(Z)Ljava/lang/StringBuilder;", false);
                } else if (argumentType.equals(Type.BYTE_TYPE) || argumentType.equals(Type.CHAR_TYPE) || argumentType.equals(Type.SHORT_TYPE) || argumentType.equals(Type.INT_TYPE)) {
                    mv.visitVarInsn(Opcodes.ILOAD, stackPostition);
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JAVA_LANG_STRING_BUILDER, APPEND, "(I)Ljava/lang/StringBuilder;", false);
                } else if (argumentType.equals(Type.LONG_TYPE)) {
                    mv.visitVarInsn(Opcodes.LLOAD, stackPostition);
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JAVA_LANG_STRING_BUILDER, APPEND, "(J)Ljava/lang/StringBuilder;", false);
                    stackPostition++;
                } else if (argumentType.equals(Type.FLOAT_TYPE)) {
                    mv.visitVarInsn(Opcodes.FLOAD, stackPostition);
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JAVA_LANG_STRING_BUILDER, APPEND, "(F)Ljava/lang/StringBuilder;", false);
                } else if (argumentType.equals(Type.DOUBLE_TYPE)) {
                    mv.visitVarInsn(Opcodes.DLOAD, stackPostition);
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JAVA_LANG_STRING_BUILDER, APPEND, "(D)Ljava/lang/StringBuilder;", false);
                    stackPostition++;
                } else {
                    mv.visitVarInsn(Opcodes.ALOAD, stackPostition);
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JAVA_LANG_STRING_BUILDER, APPEND, "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            mv.visitLdcInsn(";");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                    JAVA_LANG_STRING_BUILDER, APPEND,
                    LJAVA_LANG_STRING_LJAVA_LANG_STRING_BUILDER, false);
            stackPostition++;
            paramsNumber++;
        }
    }
}