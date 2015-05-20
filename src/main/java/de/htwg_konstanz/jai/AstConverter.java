package de.htwg_konstanz.jai;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.BasicType;
import org.apache.bcel.generic.CodeExceptionGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ObjectType;

import de.htwg_konstanz.jai.gen.ByteCodeClass;
import de.htwg_konstanz.jai.gen.EntryPoint;
import de.htwg_konstanz.jai.gen.ExitPoint;
import de.htwg_konstanz.jai.gen.Instruction;
import de.htwg_konstanz.jai.gen.Method;
import de.htwg_konstanz.jai.gen.PrimitiveType;
import de.htwg_konstanz.jai.gen.ReferenceType;
import de.htwg_konstanz.jai.gen.SimpleInstruction;
import de.htwg_konstanz.jai.gen.Type;
import de.htwg_konstanz.jai.gen.Void;

public class AstConverter {
	private final JavaClass bcelClass;

	public AstConverter(JavaClass bcelClass) {
		this.bcelClass = bcelClass;
	}

	public ByteCodeClass convert() {
		ByteCodeClass clazz = new ByteCodeClass();
		clazz.setName(bcelClass.getClassName());
		clazz.setSuperClass(bcelClass.getSuperclassName());
		clazz.setIsFinal(bcelClass.isFinal());

		// for (org.apache.bcel.classfile.Field bcelField :
		// bcelClass.getFields()) {
		// Field field = new Field(bcelField.getName());
		// clazz.addField(field);
		// }

		ConstantPoolGen cpg = new ConstantPoolGen(bcelClass.getConstantPool());
		AstConverterVisitor visitor = new AstConverterVisitor(cpg);

		for (org.apache.bcel.classfile.Method bcelMethod : bcelClass.getMethods()) {
			Method method = new Method();

			method.setIsAbstract(bcelMethod.isAbstract());
			method.setIsNative(bcelMethod.isNative());
			method.setMethodName(bcelMethod.getName());
			method.setSignatureIndex(bcelMethod.getSignatureIndex());
			method.setIsPrivate(bcelMethod.isPrivate());
			method.setIsFinal(bcelMethod.isFinal());

			if (!bcelMethod.isStatic()) {
				Type thisParam = new ReferenceType(bcelClass.getClassName());
				method.addParam(thisParam);
			}

			for (org.apache.bcel.generic.Type paramType : bcelMethod.getArgumentTypes())
				method.addParam(createJastAddType(paramType));

			org.apache.bcel.generic.Type returnType = bcelMethod.getReturnType();
			if (returnType.equals(BasicType.VOID))
				method.setReturnType(new Void());
			else
				method.setReturnType(createJastAddType(returnType));

			clazz.addMethod(method);

			if (method.getIsAbstract() || method.getIsNative())
				continue;

			method.setMaxLocals(bcelMethod.getCode().getMaxLocals());

			InstructionHandle[] instructionHandles = new InstructionList(bcelMethod.getCode()
					.getCode()).getInstructionHandles();

			method.addInstruction(new EntryPoint("entry point", -1, 0, 0, Collections
					.<Integer> emptySet()));

			Map<Integer, Set<Integer>> exceptionHandlers = getExceptionHandlers(bcelMethod);
			for (InstructionHandle instructionHandle : instructionHandles)
				method.addInstruction(convertInstruction(instructionHandle, visitor, cpg,
						exceptionHandlers));

			method.addInstruction(new ExitPoint("exit point", -1, 0, 0, Collections
					.<Integer> emptySet()));

		}
		return clazz;
	}

	/**
	 * Returns the ExceptionHandlers as a Map of instruction positions.
	 * 
	 * @return Map<instruction in try{}, List<Start of Handler>>
	 */
	private Map<Integer, Set<Integer>> getExceptionHandlers(
			org.apache.bcel.classfile.Method bcelMethod) {
		Map<Integer, Set<Integer>> exceptionHandlers = new HashMap<Integer, Set<Integer>>();
		MethodGen methodGen = new MethodGen(bcelMethod, bcelClass.getClassName(),
				new ConstantPoolGen(bcelClass.getConstantPool()));

		for (CodeExceptionGen codeExceptionGen : methodGen.getExceptionHandlers()) {
			for (InstructionHandle protectedInstruction = codeExceptionGen.getStartPC(); !protectedInstruction
					.equals(codeExceptionGen.getEndPC()); protectedInstruction = protectedInstruction
					.getNext()) {
				Integer position = protectedInstruction.getPosition();
				if (!exceptionHandlers.containsKey(position))
					exceptionHandlers.put(position, new HashSet<Integer>());
				exceptionHandlers.get(position).add(codeExceptionGen.getHandlerPC().getPosition());
			}
			Integer position = codeExceptionGen.getEndPC().getPosition();
			if (!exceptionHandlers.containsKey(position))
				exceptionHandlers.put(position, new HashSet<Integer>());
			exceptionHandlers.get(position).add(codeExceptionGen.getHandlerPC().getPosition());
		}
		return exceptionHandlers;
	}

	public static Type createJastAddType(org.apache.bcel.generic.Type type) {
		if (type instanceof BasicType)
			return new PrimitiveType(type.toString(), type.getSize());

		if (type instanceof ObjectType)
			return new ReferenceType(((ObjectType) type).getClassName());

		// TODO Array int[] / String[]
		if (type instanceof org.apache.bcel.generic.ArrayType) {
			return new ReferenceType(type.toString());
			// org.apache.bcel.generic.Type arrayType =
			// ((org.apache.bcel.generic.ArrayType) argType)
			// .getBasicType();
			// if (arrayType instanceof BasicType)
			// return new ReferenceType("Primitive[]");
			// if (arrayType instanceof ObjectType)
			// return new ReferenceType(((ObjectType) arrayType).getClassName()
			// + "[]");
		}

		throw new IllegalArgumentException();
	}

	private Instruction convertInstruction(InstructionHandle instructionHandle,
			AstConverterVisitor visitor, ConstantPoolGen cpg,
			Map<Integer, Set<Integer>> exceptionHandlers) {
		Instruction instruction;

		instructionHandle.accept(visitor);

		if (visitor.hasBeenVisited()) {
			instruction = visitor.getInstruction();
			visitor.clear();
		} else {
			instruction = new SimpleInstruction();
		}

		instruction.setLabel(instructionHandle.toString(false));
		instruction.setPosition(instructionHandle.getPosition());
		instruction.setConsumeStack(instructionHandle.getInstruction().consumeStack(cpg));
		instruction.setProduceStack(instructionHandle.getInstruction().produceStack(cpg));

		if (exceptionHandlers.containsKey(instructionHandle.getPosition()))
			instruction
					.setExceptionHandlers(exceptionHandlers.get(instructionHandle.getPosition()));
		else
			// instruction.setExceptionHandlers(new LinkedList<Integer>());
			instruction.setExceptionHandlers(Collections.<Integer> emptySet());

		return instruction;
	}
}
