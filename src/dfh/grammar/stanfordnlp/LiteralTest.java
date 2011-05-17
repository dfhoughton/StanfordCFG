package dfh.grammar.stanfordnlp;

/**
 * Simple String equality based {@link POSTest}.
 * 
 * <b>Creation date:</b> May 17, 2011
 * 
 * @author David Houghton
 * 
 */
public class LiteralTest implements POSTest {

	protected final String tag;

	public LiteralTest(String tag) {
		this.tag = tag;
	}

	@Override
	public boolean test(String tag) {
		return this.tag.equals(tag);
	}

	@Override
	public String id() {
		return "tag eq " + tag;
	}

	@Override
	public Object clone() {
		return this;
	}
}
