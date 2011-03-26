/*******************************************************************************
 * Copyright (c) 2011 Jesper Moller, and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jesper Moller - initial API and implementation
 *******************************************************************************/

package org.eclipse.wst.xml.xpath2.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import org.eclipse.wst.xml.xpath2.api.typesystem.ItemType;
import org.eclipse.wst.xml.xpath2.api.typesystem.TypeDefinition;

/**
 * @since 2.0
 */
public class ResultBuffer {

	private ArrayList/*<Item>*/ values = new ArrayList/*<Item>*/();
	
	public ResultSequence getSequence() {
		if (values.size() == 0) return EMPTY;
		if (values.size() == 1) return new SingleResultSequence((Item) values.get(0));
		return new ArrayResultSequence((Item[]) values.toArray(new Item[values.size()]));
	}
	
	public void clear() {
		values.clear();
	}
	
	public ResultBuffer with(Item at) {
		values.add(at);
		return this;
	}

	public void add(Item at) {
		values.add(at);
	}

	public void append(Item at) {
		values.add(at);
	}

	public void concat(ResultSequence rs) {
		values.addAll(collectionWrapper(rs));
	}

	public static final class SingleResultSequence implements ResultSequence {
		
		public SingleResultSequence(Item at) {
			value = at;
		}
		
		private final Item value;
	
		/* (non-Javadoc)
		 * @see org.eclipse.wst.xml.xpath2.api.ResultSequence#getLength()
		 */
		public int size() {
			return 1;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.wst.xml.xpath2.api.ResultSequence#getItem(int)
		 */
		public Item item(int index) {
			if (index != 0) throw new IndexOutOfBoundsException("Length is one, you looked up number "+ index);
			return value;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.wst.xml.xpath2.api.ResultSequence#getItem(int)
		 */
		public Object value(int index) {
			if (index != 0) throw new IndexOutOfBoundsException("Length is one, you looked up number "+ index);
			return value.getNativeValue();
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.wst.xml.xpath2.api.ResultSequence#empty()
		 */
		public boolean empty() {
			return false;
		}
	
		/* (non-Javadoc)
		 * @see org.eclipse.wst.xml.xpath2.api.ResultSequence#first()
		 */
		public Item first() {
			return value;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.wst.xml.xpath2.api.ResultSequence#iterator()
		 */
		public Iterator iterator() {
			return new Iterator() {
				boolean seenIt = false;
				
				public final void remove() {
					throw new UnsupportedOperationException("ResultSequences are immutable");
				}
				
				public final Object next() {
					if (! seenIt) {
						seenIt = true;
						return SingleResultSequence.this;
					}
					throw new IllegalStateException("This iterator is at its end");
				}
				
				public final boolean hasNext() {
					return !seenIt;
				}
			};
		}

		public ItemType itemType(int index) {
			// TODO - we should have this directly
			return item(index).getItemType();
		}

		public TypeDefinition sequenceType() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static final class ArrayResultSequence implements ResultSequence {
		
		private final Item[] results;
	
		public ArrayResultSequence(Item results[]) {
			this.results = results;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.wst.xml.xpath2.api.ResultSequence#getLength()
		 */
		public int size() {
			return results.length;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.wst.xml.xpath2.api.ResultSequence#getItem(int)
		 */
		public Item item(int index) {
			if (index < 0 && index >= results.length) throw new IndexOutOfBoundsException("Index " + index + " is out of alllowed bounds (less that " + results.length);
			return results[index];
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.wst.xml.xpath2.api.ResultSequence#empty()
		 */
		public boolean empty() {
			return false;
		}
	
		/* (non-Javadoc)
		 * @see org.eclipse.wst.xml.xpath2.api.ResultSequence#first()
		 */
		public Item first() {
			return item(0);
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.wst.xml.xpath2.api.ResultSequence#iterator()
		 */
		public Iterator iterator() {
			return new Iterator() {
				int nextIndex = 0;
				
				public final void remove() {
					throw new UnsupportedOperationException("ResultSequences are immutable");
				}
				
				public final Object next() {
					if (nextIndex < results.length) {
						return results[nextIndex++];
					}
					throw new IllegalStateException("This iterator is at its end");
				}
				
				public final boolean hasNext() {
					return nextIndex < results.length;
				}
			};
		}

		public ItemType itemType(int index) {
			if (index < 0 && index >= results.length) throw new IndexOutOfBoundsException("Index " + index + " is out of alllowed bounds (less that " + results.length);
			return results[index].getItemType();
		}

		public TypeDefinition sequenceType() {
			return null;
		}

		public Object value(int index) {
			return item(index).getNativeValue();
		}
	}

	public int size() {
		return values.size();
	}

	public ListIterator iterator() {
		return values.listIterator();
	}

	public void prepend(ResultSequence rs) {
		values.addAll(0, collectionWrapper(rs));
	}

	private Collection collectionWrapper(final ResultSequence rs) {
		// This is a dummy collections, solely exists for faster inserts into our array
		return new Collection() {

			public boolean add(Object arg0) {
				return false;
			}

			public boolean addAll(Collection arg0) {
				return false;
			}

			public void clear() {
			}

			public boolean contains(Object arg0) {
				return false;
			}

			public boolean containsAll(Collection arg0) {
				return false;
			}

			public boolean isEmpty() {
				return rs.empty();
			}

			public Iterator iterator() {
				return rs.iterator();
			}

			public boolean remove(Object arg0) {
				return false;
			}

			public boolean removeAll(Collection arg0) {
				return false;
			}

			public boolean retainAll(Collection arg0) {
				return false;
			}

			public int size() {
				return rs.size();
			}

			public Object[] toArray() {
				return null;
			}

			public Object[] toArray(Object[] arg0) {
				return null;
			}
		};
	}
	
	final static ResultSequence EMPTY = new ResultSequence() {

		public int size() {
			return 0;
		}

		public Item item(int index) {
			throw new IndexOutOfBoundsException("Sequence is empty!");
		}

		public boolean empty() {
			return true;
		}
		
		public ItemType itemType(int index) {
			throw new IndexOutOfBoundsException("Sequence is empty!");
		}
		
		public TypeDefinition sequenceType() {
			throw new IndexOutOfBoundsException("Sequence is empty!");
		}
		
		public Object value(int index) {
			throw new IndexOutOfBoundsException("Sequence is empty!");
		}
		
		public Item first() {
			throw new IndexOutOfBoundsException("Sequence is empty!");
		}
		
		public Iterator iterator() {
			return new Iterator() {
				
				public void remove() {
					throw new UnsupportedOperationException("ResultSequences are immutable");
				}
				
				public Object next() {
					throw new IllegalStateException("This ResultSequence is empty");
				}
				
				public boolean hasNext() {
					return false;
				}
			};
		}
	};

}
