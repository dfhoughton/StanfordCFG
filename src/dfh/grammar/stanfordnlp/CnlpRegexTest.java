package dfh.grammar.stanfordnlp;

import java.util.List;
import java.util.regex.Pattern;

import dfh.grammar.tokens.TokenSequence;

public class CnlpRegexTest implements CnlpTokenTest {

	private final Pattern p;

	public CnlpRegexTest(String p) {
		this(Pattern.compile(p));
	}

	public CnlpRegexTest(Pattern p) {
		this.p = p;
	}

	@Override
	public String id() {
		return p.toString();
	}

	@Override
	public int test(List<CnlpToken<?>> tokens,
			TokenSequence<CnlpToken<?>> sequence, boolean reversed) {
		for (CnlpToken<?> t : tokens) {
			if (t instanceof WordToken) {
				WordToken w = (WordToken) t;
				if (p.matcher(w.tag()).matches()) {
					return reversed ? w.start() : w.end();
				}
			}
		}
		return -1;
	}

}
