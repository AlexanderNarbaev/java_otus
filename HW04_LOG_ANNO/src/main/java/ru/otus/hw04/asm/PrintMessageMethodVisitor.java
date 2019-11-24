package ru.otus.hw04.asm;

import org.objectweb.asm.*;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;

public class PrintMessageMethodVisitor extends MethodVisitor {
    private final String className;
    private boolean isAnnotationPresent;
    private ArrayList<MethodParamDescriptor> parameterIndexes = new ArrayList<>();
    private String methodName;


    public PrintMessageMethodVisitor(MethodVisitor mv, String name, String className) {
        super(Opcodes.ASM5, mv);
        this.methodName = name;
        this.className = className;
//        System.out.println("mv = " + mv + ", name = " + name + ", className = " + className);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if ("Lru/otus/hw04/classloader/Log;".equals(desc)) {
            isAnnotationPresent = true;
//            System.out.println("desc = " + desc + ", visible = " + visible);
//            return new AnnotationVisitor(Opcodes.ASM5,
//                    super.visitAnnotation(desc, visible)) {
//                public AnnotationVisitor visitArray(String name) {
//                    System.out.println("name = " + name);
//                    if ("fields".equals(name)) {
//                        return new AnnotationVisitor(Opcodes.ASM5,
//                                super.visitArray(name)) {
//                            public void visit(String name, Object value) {
//                                parameterIndexes.put(name, (String) value);
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

    public void visitCode() {
        super.visitCode();
        if (isAnnotationPresent) {
            try {
                System.out.println("In visit Method");
                // create string builder
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System",
                        "out", "Ljava/io/PrintStream;");
                System.out.println("In visit Method 2");
                mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
                System.out.println("In visit Method 3");
                mv.visitInsn(Opcodes.DUP);
                System.out.println("In visit Method 4");
                mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                // add everything to the string builder
                mv.visitLdcInsn("A call was made to method \"");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        "java/lang/StringBuilder", "append",
                        "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
//                System.out.println("In visit Method 5");
                System.out.println("In visit Method 6");
                mv.visitLdcInsn(methodName);
                System.out.println("In visit Method 7");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        "java/lang/StringBuilder", "append",
                        "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                System.out.println("parameterIndexes.size() = " + parameterIndexes.size());
                for (MethodParamDescriptor paramName : parameterIndexes) {
                    System.out.println("MethodParamDescriptor" + paramName);
                    mv.visitLdcInsn(paramName.name);
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    mv.visitVarInsn(getRightOperation(paramName.descriptor), paramName.index);
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                }
                System.out.println("In visit Method 8");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                System.out.println("In visit Method 9");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                System.out.println("In visit Method 10");
                try {
                    Handle handle = new Handle(
                            Opcodes.H_INVOKESTATIC,
                            Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
                            "makeConcatWithConstants",
                            MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
                            false);

                    System.out.println("MethodDescriptor:\t" + MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString());

                    mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                    mv.visitVarInsn(Opcodes.ILOAD, 1);
                    mv.visitInvokeDynamicInsn("makeConcatWithConstants", "(I)Ljava/lang/String;", handle, "executed method: calculation, param:\u0001");

                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

                    mv.visitVarInsn(Opcodes.ALOAD, 0);
                    mv.visitVarInsn(Opcodes.ILOAD, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                mv.visitMaxs(4, 4);
//                System.out.println("In visit Method 11");
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

    @Override
    public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
        if (isAnnotationPresent) {
            System.out.println("name = " + name + ", descriptor = " + descriptor + ", signature = " + signature + ", start = " + start + ", end = " + end + ", index = " + index);
            if (index > 0) {
                parameterIndexes.add(new MethodParamDescriptor(name, descriptor, index));
            }
            System.out.println("parameterIndexes.size() = " + parameterIndexes.size());
        }
        super.visitLocalVariable(name, descriptor, signature, start, end, index);
    }

}

class MethodParamDescriptor {
    String name;
    String descriptor;
    int index;

    public MethodParamDescriptor(String name, String descriptor, int index) {
        this.name = name;
        this.descriptor = descriptor;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "MethodParamDescriptor{" +
                "name='" + name + '\'' +
                ", descriptor='" + descriptor + '\'' +
                ", index=" + index +
                '}';
    }
}
