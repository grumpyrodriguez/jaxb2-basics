package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public abstract class ValueBasedHashCodeCodeGenerator extends
		ValueBasedBlockHashCodeCodeGenerator {

	public ValueBasedHashCodeCodeGenerator(
			TypedHashCodeCodeGeneratorFactory factory, JCodeModel codeModel) {
		super(factory, codeModel);
	}
	
	@Override
	protected JExpression valueHashCode(JBlock block, JType type, JVar value) {
		return valueHashCode(type, value);
	}

	protected abstract JExpression valueHashCode(JType type, JVar value);
}