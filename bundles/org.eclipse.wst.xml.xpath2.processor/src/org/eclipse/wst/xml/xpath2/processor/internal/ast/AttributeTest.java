/*******************************************************************************
 * Copyright (c) 2005, 2009 Andrea Bittau, University College London, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andrea Bittau - initial API and implementation from the PsychoPath XPath 2.0 
 *     David Carver - bug 298535 - Attribute instance of improvements 
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.processor.internal.ast;

import org.eclipse.wst.xml.xpath2.processor.ResultSequence;
import org.eclipse.wst.xml.xpath2.processor.StaticContext;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AnyType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.AttrType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.NodeType;
import org.eclipse.wst.xml.xpath2.processor.internal.types.QName;
import org.eclipse.wst.xml.xpath2.api.typesystem.TypeDefinition;
import org.eclipse.wst.xml.xpath2.api.typesystem.TypeModel;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;

/**
 * Class used to match an attribute node by its name and/or type.
 */
public class AttributeTest extends AttrElemTest {

	private AnyType anyType = null;

	/**
	 * Constructor for AttributeTest. This one takes in 3 inputs, Name, wildcard
	 * test(true/false) and type.
	 * 
	 * @param name
	 *            QName.
	 * @param wild
	 *            Wildcard test, True/False.
	 * @param type
	 *            QName type.
	 */
	public AttributeTest(QName name, boolean wild, QName type) {
		super(name, wild, type);
	}

	/**
	 * Constructor for AttributeTest. This one takes in 2 inputs, Name and
	 * wildcard test(true/false).
	 * 
	 * @param name
	 *            QName.
	 * @param wild
	 *            Wildcard test, True/False.
	 */
	public AttributeTest(QName name, boolean wild) {
		super(name, wild);
	}

	/**
	 * Default Constructor for AttributeTest.
	 */
	public AttributeTest() {
		super();
	}

	/**
	 * Support for Visitor interface.
	 * 
	 * @return Result of Visitor operation.
	 */
	@Override
	public Object accept(XPathVisitor v) {
		return v.visit(this);
	}

	@Override
	public AnyType createTestType(ResultSequence rs, StaticContext sc) {
		if (name() == null && !wild()) {
			return new AttrType();
		}

		AnyType at = rs.first();

		if (!(at instanceof NodeType)) {
			return new AttrType();
		}

		return createAttrType(at, sc);
	}

	private AnyType createAttrType(AnyType at, StaticContext sc) {
		anyType = new AttrType();
		NodeType nodeType = (NodeType) at;
		Node node = nodeType.node_value();
		if (node == null) {
			return anyType;
		}

		String nodeName = node.getLocalName();

		if (wild()) {
			if (type() != null) {
				anyType = createAttrForXSDType(node, sc);
			}
		} else if (nodeName.equals(name().local())) {
			if (type() != null) {
				anyType = createAttrForXSDType(node, sc);
			} else {
				anyType = new AttrType((Attr) node, sc.getTypeModel(node));
			}
		}
		return anyType;
	}

	private AnyType createAttrForXSDType(Node node, StaticContext sc) {
		Attr attr = (Attr) node;
		
		TypeModel typeModel = sc.getTypeModel(attr);
		TypeDefinition typedef = typeModel.getType(attr);

		if (typedef != null) {
			if (typedef.derivedFrom(type().namespace(), type().local(),
					getDerviationTypes())) {
				anyType = new AttrType(attr, sc.getTypeModel(node));
			}
		} else {
			anyType = new AttrType(attr, sc.getTypeModel(node));
		}
		return anyType;
	}

	@Override
	public boolean isWild() {
		return wild();
	}

	@Override
	public Class getXDMClassType() {
		return AttrType.class;
	}

}
