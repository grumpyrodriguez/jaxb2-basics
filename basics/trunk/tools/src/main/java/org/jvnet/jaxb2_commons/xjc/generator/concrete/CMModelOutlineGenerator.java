package org.jvnet.jaxb2_commons.xjc.generator.concrete;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.generator.MClassOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.MElementOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.MEnumOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.MModelOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.MPackageOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MElementOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MEnumOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MModelOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPackageOutline;
import org.jvnet.jaxb2_commons.xjc.outline.concrete.CMModelOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;

import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.Outline;

public class CMModelOutlineGenerator implements MModelOutlineGenerator {

	private final Outline outline;
	private final Model model;

	public CMModelOutlineGenerator(Outline outline, Model model) {
		Validate.notNull(outline);
		Validate.notNull(model);
		this.outline = outline;
		this.model = model;
	}

	@Override
	public MModelOutline generate(MModelInfo modelInfo) {

		final CMModelOutline modelOutline = new CMModelOutline(modelInfo,
				outline.getCodeModel());

		for (MClassInfo classInfo : modelInfo.getClassInfos()) {
			generatePackageOutline(modelOutline, modelInfo,
					classInfo.getPackageInfo());
		}

		for (MElementInfo elementInfo : modelInfo.getElementInfos()) {
			generatePackageOutline(modelOutline, modelInfo,
					elementInfo.getPackageInfo());
		}

		for (MEnumLeafInfo enumLeafInfo : modelInfo.getEnumLeafInfos()) {
			generatePackageOutline(modelOutline, modelInfo,
					enumLeafInfo.getPackageInfo());
		}

		for (MClassInfo classInfo : modelInfo.getClassInfos()) {
			generateClassOutline(modelOutline, modelInfo, classInfo);
		}

		for (MElementInfo elementInfo : modelInfo.getElementInfos()) {
			generateElementOutline(modelOutline, modelInfo, elementInfo);
		}

		for (MEnumLeafInfo enumLeafInfo : modelInfo.getEnumLeafInfos()) {
			generateEnumOutline(modelOutline, modelInfo, enumLeafInfo);
		}

		// TODO Auto-generated method stub
		return modelOutline;
	}

	private void generatePackageOutline(CMModelOutline modelOutline,
			MModelInfo modelInfo, MPackageInfo packageInfo) {
		if (modelOutline.getPackageOutline(packageInfo) == null &&

		packageInfo.getOrigin() instanceof PackageOutlineGeneratorFactory) {
			final MPackageOutlineGenerator generator = ((PackageOutlineGeneratorFactory) packageInfo
					.getOrigin()).createGenerator(outline);
			final MPackageOutline packageOutline = generator.generate(
					modelOutline, modelInfo, packageInfo);
			modelOutline.addPackageOutline(packageOutline);

		}
	}

	private void generateClassOutline(CMModelOutline modelOutline,
			MModelInfo modelInfo, MClassInfo classInfo) {
		if (classInfo.getBaseTypeInfo() != null) {
			generateClassOutline(modelOutline, modelInfo,
					classInfo.getBaseTypeInfo());
		}

		if (classInfo.getOrigin() instanceof ClassOutlineGeneratorFactory) {
			final MClassOutlineGenerator generator = ((ClassOutlineGeneratorFactory) classInfo
					.getOrigin()).createGenerator(this.outline);
			final MClassOutline classOutline = generator.generate(
					modelOutline.getPackageOutline(classInfo.getPackageInfo()),
					modelInfo, classInfo);
			if (classOutline != null) {
				modelOutline.addClassOutline(classOutline);
			}
		}
	}

	private void generateElementOutline(CMModelOutline modelOutline,
			MModelInfo modelInfo, MElementInfo elementInfo)

	{
		if (elementInfo.getOrigin() instanceof ElementOutlineGeneratorFactory) {
			final MElementOutlineGenerator generator = ((ElementOutlineGeneratorFactory) elementInfo
					.getOrigin()).createGenerator(outline);
			final MElementOutline elementOutline = generator
					.generate(modelOutline.getPackageOutline(elementInfo
							.getPackageInfo()), modelInfo, elementInfo);
			if (elementOutline != null) {
				modelOutline.addElementOutline(elementOutline);
			}
		}
	}

	private void generateEnumOutline(CMModelOutline modelOutline,
			MModelInfo modelInfo, MEnumLeafInfo enumLeafInfo)

	{
		if (enumLeafInfo.getOrigin() instanceof EnumOutlineGeneratorFactory) {
			final MEnumOutlineGenerator generator = ((EnumOutlineGeneratorFactory) enumLeafInfo
					.getOrigin()).createGenerator(outline);
			final MEnumOutline enumOutline = generator.generate(modelOutline
					.getPackageOutline(enumLeafInfo.getPackageInfo()),
					modelInfo, enumLeafInfo);
			if (enumOutline != null) {
				modelOutline.addEnumOutline(enumOutline);
			}
		}
	}
}