package dfh.grammar.stanfordnlp;

import java.util.List;
import java.util.NavigableMap;
import java.util.SortedSet;
import java.util.TreeMap;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

/**
 * {@link CharSequence} backed by Stanford'd tokenization, sentencing,
 * lemmatization, POS tagging, etc.
 * 
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
public class CnlpCharSequence implements CharSequence {

	private String text;
	private NavigableMap<Integer, CoreMap> sentenceStartMap, sentenceEndMap;
	private NavigableMap<Integer, CoreLabel> tokenStartMap, tokenEndMap;
	private CnlpCharSequence parent;
	protected int start;
	protected int end;

	/**
	 * Package-restricted constructor. The expectation is that
	 * {@link CnlpCharSequence} objects will be created by
	 * {@link CnlpCharSequenceFactory}.
	 * 
	 * @param document
	 */
	CnlpCharSequence(Annotation document) {
		sentenceStartMap = new TreeMap<Integer, CoreMap>();
		sentenceEndMap = new TreeMap<Integer, CoreMap>();
		tokenStartMap = new TreeMap<Integer, CoreLabel>();
		tokenEndMap = new TreeMap<Integer, CoreLabel>();
		this.start = 0;
		this.text = document.get(TextAnnotation.class);
		this.end = text.length();
		// these are all the sentences in this document
		// a CoreMap is essentially a Map that uses class objects as keys and
		// has values with custom types
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		for (CoreMap sentence : sentences) {
			Integer sstart = null, send = null;
			// traversing the words in the current sentence
			// a CoreLabel is a CoreMap with additional token-specific methods
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				if (sstart == null)
					sstart = token.beginPosition();
				send = token.endPosition();
				tokenStartMap.put(token.beginPosition(), token);
				tokenEndMap.put(token.endPosition(), token);
				// this is the text of the token
				// String word = token.get(TextAnnotation.class);
				// this is the POS tag of the token
				// String pos = token.get(PartOfSpeechAnnotation.class);
				// String lemma = token.get(LemmaAnnotation.class);
			}
			if (sstart != null) {
				sentenceStartMap.put(sstart, sentence);
				sentenceEndMap.put(send, sentence);
			}
		}
	}

	private CnlpCharSequence(CnlpCharSequence parent, int start, int end) {
		this.parent = parent;
		this.start = start;
		this.end = end;
		this.text = parent.text.substring(start, end);
	}

	/**
	 * @param i
	 * @return corresponding character offset in parent sequence
	 */
	private int translate(int i) {
		return start + i;
	}

	@Override
	public char charAt(int i) {
		return text.charAt(i);
	}

	@Override
	public int length() {
		return text.length();
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return new CnlpCharSequence(this, start, end);
	}

	/**
	 * @param i
	 * @return token beginning at the given offset
	 */
	public CoreLabel token(int i) {
		if (parent == null)
			return tokenStartMap.get(i);
		return parent.token(translate(i));
	}

	public boolean beginsToken(int i) {
		if (parent == null)
			return tokenStartMap.containsKey(i);
		return parent.beginsToken(translate(i));
	}

	public boolean endsToken(int i) {
		if (parent == null)
			return tokenEndMap.containsKey(i);
		return parent.endsToken(translate(i));
	}

	public boolean beginsSentence(int i) {
		if (parent == null)
			return sentenceStartMap.containsKey(i);
		return parent.beginsSentence(translate(i));
	}

	public boolean endsSentence(Integer i) {
		if (parent == null)
			return sentenceEndMap.containsKey(i);
		return parent.endsSentence(translate(i));
	}

	/**
	 * Convenience method delegating to {@link #tokenContaining(Integer)}. If
	 * you need the token, go straight there.
	 * 
	 * @param i
	 *            character offset
	 * @return whether the offset represents a token character
	 */
	public boolean withinToken(Integer i) {
		return tokenContaining(i) != null;
	}

	/**
	 * Returns POS tag for token containing the given offset. Convenience method
	 * delegating to {@link #tokenContaining(Integer)}.
	 * 
	 * @param i
	 *            character offset
	 * @return POS tag for token containing the given offset
	 */
	public String tag(Integer i) {
		CoreLabel token = tokenContaining(i);
		if (token == null)
			return null;
		return token.get(PartOfSpeechAnnotation.class);
	}

	/**
	 * Returns lemma for token containing the given offset. Convenience method
	 * delegating to {@link #tokenContaining(Integer)}.
	 * 
	 * @param i
	 *            character offset
	 * @return lemma for token containing the given offset
	 */
	public String lemma(Integer i) {
		CoreLabel token = tokenContaining(i);
		if (token == null)
			return null;
		return token.get(LemmaAnnotation.class);
	}

	/**
	 * Returns text of token containing the given offset. Convenience method
	 * delegating to {@link #tokenContaining(Integer)}.
	 * 
	 * @param i
	 *            character offset
	 * @return text of token containing the given offset
	 */
	public String text(Integer i) {
		CoreLabel token = tokenContaining(i);
		if (token == null)
			return null;
		return text.substring(token.beginPosition(), token.endPosition());
	}

	/**
	 * Convenience method delegating to {@link #tokenContaining(Integer)}. If
	 * you need the token, go straight there.
	 * 
	 * @param i
	 *            character offset
	 * @return whether the offset doesn't represent any token character
	 */
	public boolean betweenTokens(Integer i) {
		return tokenContaining(i) == null;
	}

	/**
	 * @param j
	 * @return sentence containing the given offset
	 */
	public CoreMap sentence(int j) {
		if (parent == null) {
			Integer i = j;
			if (sentenceStartMap.isEmpty())
				return null;
			// see if we're lucky
			CoreMap sentence = sentenceStartMap.get(i);
			if (sentence == null) {
				// is it after the last sentence?
				if (i > sentenceEndMap.lastKey())
					return null;
				// is it a known sentence end? (we know it isn't a start)
				if (sentenceEndMap.containsKey(i))
					return null;
				// find the sentence that might contain the offset
				NavigableMap<Integer, CoreMap> map = (NavigableMap<Integer, CoreMap>) sentenceStartMap
						.headMap(i);
				sentence = map.lastEntry().getValue();
				List<CoreLabel> words = sentence.get(TokensAnnotation.class);
				// is it between sentences?
				if (words.get(words.size() - 1).endPosition() < i)
					return null;
			}
			return sentence;
		}
		return parent.sentence(translate(j));
	}

	/**
	 * @param j
	 *            character offset
	 * @return token containing given offset
	 */
	public CoreLabel tokenContaining(int j) {
		if (parent == null) {
			Integer i = j;
			if (tokenStartMap.isEmpty())
				return null;
			// see if we're lucky
			CoreLabel token = tokenStartMap.get(i);
			if (token == null) {
				// is it after the last token?
				if (i > tokenEndMap.lastKey())
					return null;
				// is it a known token end? (we know it isn't a start)
				if (tokenEndMap.containsKey(i))
					return null;
				// find the token that might contain the offset
				NavigableMap<Integer, CoreLabel> map = (NavigableMap<Integer, CoreLabel>) tokenStartMap
						.headMap(i);
				token = map.lastEntry().getValue();
				// is it between tokens?
				if (token.endPosition() < i)
					return null;
			}
			return token;
		}
		return parent.tokenContaining(translate(j));
	}

	/**
	 * @return start offsets of all tokens
	 */
	public SortedSet<Integer> tokenOffsets() {
		if (parent == null)
			return (SortedSet<Integer>) tokenStartMap.keySet();
		return tokenOffsets(start, end);
	}

	/**
	 * @param s
	 * @param e
	 * @return start offsets of all tokens lying within given offsets
	 */
	public SortedSet<Integer> tokenOffsets(int s, int e) {
		if (parent == null) {
			NavigableMap<Integer, CoreLabel> map = tokenStartMap.tailMap(s,
					true);
			if (!map.isEmpty()) {
				map = map.headMap(e, false);
			}
			return (SortedSet<Integer>) map.keySet();
		}
		return parent.tokenOffsets(translate(s), translate(e));
	}

	@Override
	public String toString() {
		return text;
	}
}
