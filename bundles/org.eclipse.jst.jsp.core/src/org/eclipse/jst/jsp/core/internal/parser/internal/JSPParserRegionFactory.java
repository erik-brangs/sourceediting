/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.jsp.core.internal.parser.internal;

import org.eclipse.jst.jsp.core.internal.parser.JSPCodeRegion;
import org.eclipse.wst.sse.core.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.parser.regions.XMLParserRegionFactory;
import org.eclipse.wst.xml.core.jsp.model.parser.temp.XMLJSPRegionContexts;

/**
 * 
 * This region factory is very specific to the parser output, 
 * and the specific implementation classes for various regions.
 */
public class JSPParserRegionFactory extends XMLParserRegionFactory {
	public JSPParserRegionFactory() {
		super();
	}

	public ITextRegion createToken(String context, int start, int textLength, int length, String lang, String surroundingTag) {
		ITextRegion newRegion = null;
		if (context == XMLJSPRegionContexts.JSP_CONTENT) {
			newRegion = new JSPCodeRegion(context, start, textLength, length);
		}
		else
			newRegion = super.createToken(context, start, textLength, length, lang, surroundingTag);
		return newRegion;
	}
}