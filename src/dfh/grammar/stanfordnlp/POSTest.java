package dfh.grammar.stanfordnlp;

import dfh.grammar.Rule;

/**
 * Tests whether a given POS tag suits some {@link Rule}.
 * 
 * Note that this interface extends {@link Cloneable}. It is sufficient for a
 * test to return itself as a clone if it is immutable.
 * 
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
public interface POSTest extends Cloneable {
	/**
	 * @param tag
	 * @return whether tag is suitable
	 */
	boolean test(String tag);

	/**
	 * @return unique identifier for this test; used by
	 *         {@link CnlpRule#uniqueId()}.
	 */
	String id();

	Object clone();
}
