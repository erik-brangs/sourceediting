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
package org.eclipse.jst.jsp.core.internal.tasks;

import org.eclipse.wst.sse.core.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.text.ITextRegion;
import org.eclipse.wst.xml.core.builder.delegates.XMLTaskTagSeeker;
import org.eclipse.wst.xml.core.jsp.model.parser.temp.XMLJSPRegionContexts;

public class JSPTaskTagSeeker extends XMLTaskTagSeeker {
	protected boolean isCommentRegion(IStructuredDocumentRegion region, ITextRegion textRegion) {
		return super.isCommentRegion(region, textRegion) || textRegion.getType().equals(XMLJSPRegionContexts.JSP_COMMENT_TEXT);
	}
}
