package dfh.grammar.stanfordnlp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple regular expression-based {@link POSTest}. The
 * {@link Matcher#matches()} method is used, so the entire tag must match the
 * expression.
 * 
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
public class RegexTest implements POSTest {

	protected final Pattern pat;

	public RegexTest(String re) {
		pat = Pattern.compile(re);
	}

	@Override
	public boolean test(String tag) {
		return pat.matcher(tag).matches();
	}

	@Override
	public String id() {
		return "tag =~ " + pat;
	}

	@Override
	public Object clone() {
		return this;
	}
}
