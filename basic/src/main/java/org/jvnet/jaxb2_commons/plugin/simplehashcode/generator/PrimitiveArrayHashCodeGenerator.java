package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JForEach;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class PrimitiveArrayHashCodeGenerator extends BlockHashCodeCodeGenerator {

	public PrimitiveArrayHashCodeGenerator(
			TypedHashCodeCodeGeneratorFactory factory, JCodeModel codeModel) {
		super(factory, codeModel);
	}

	@Override
	protected void generate(JBlock block, JVar currentHashCode, JType type,
			JVar value) {

		final JType elementType = type.elementType();
		final JForEach forEachElement = block.forEach(elementType, value.name()
				+ "Element", value);
		getFactory().getCodeGenerator(elementType).generate(
				forEachElement.body(), currentHashCode, elementType,
				forEachElement.var(), JExpr.TRUE, true);
	}

}
