package dfh.grammar.stanfordnlp;

import java.util.List;

import dfh.grammar.tokens.TokenSequence;

public class CnlpTagTest implements CnlpTokenTest {

	private final String tag;

	public CnlpTagTest(String tag) {
		this.tag = tag;
	}

	@Override
	public String id() {
		return tag;
	}

	@Override
	public int test(List<CnlpToken<?>> tokens,
			TokenSequence<CnlpToken<?>> sequence, boolean reversed) {
		for (CnlpToken<?> t : tokens) {
			if (t instanceof WordToken) {
				WordToken w = (WordToken) t;
				if (tag.equals(w.tag())) {
					return reversed ? w.start() : w.end();
				}
			}
		}
		return -1;
	}

}
