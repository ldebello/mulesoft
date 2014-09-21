package ar.com.mulesoft.nicecopyandpaste.ui.propertytesters;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jface.text.ITextSelection;

public class TextSelectionTester extends PropertyTester {

	private static final String PROPERTY_NAME = "text";

	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof ITextSelection && PROPERTY_NAME.equals(property)) {
			return ((ITextSelection) receiver).getLength() != 0;
		}
		return false;
	}
}
