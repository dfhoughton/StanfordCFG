package dfh.grammar.stanfordnlp;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import dfh.grammar.tokens.TokenSequence;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class CnlpTokenSequenceFactory {
	private StanfordCoreNLP pipeline;

	/**
	 * Initializes a {@link StanfordCoreNLP} pipeline to sentence, tokenize,
	 * lemmatize, and tag text.
	 */
	public CnlpTokenSequenceFactory() {
		// creates a StanfordCoreNLP object, with POS tagging, and lemmatization
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		pipeline = new StanfordCoreNLP(props);
	}

	/**
	 * Generate a token sequence factory using a pre-existing pipeline. If this
	 * pipeline does not sentence, tokenize, and tag, an error will be thrown.
	 * 
	 * @param pipeline
	 *            Stanford annotation pipeline
	 */
	public CnlpTokenSequenceFactory(StanfordCoreNLP pipeline) {
		this.pipeline = pipeline;
		Properties p = pipeline.getProperties();
		String annotators = p.getProperty("annotators");
		if (annotators == null)
			throw new StanfordCFGException("no annotators");
		if (annotators.indexOf("tokenize") == -1)
			throw new StanfordCFGException("no tokenization annotator");
		if (annotators.indexOf("ssplit") == -1)
			throw new StanfordCFGException("no ssplit annotator");
		if (annotators.indexOf("pos") == -1)
			throw new StanfordCFGException("no pos annotator");
	}

	/**
	 * Annotates a document, converting it into a token sequence.
	 * 
	 * @param text
	 * @return token sequence
	 */
	public TokenSequence<CnlpToken<?>> sequence(String text) {
		// create an empty Annotation just with the given text
		Annotation document = new Annotation(text);

		// run all Annotators on this text
		pipeline.annotate(document);
		return sequence(document);
	}

	/**
	 * Converts an annotated document into a token sequence.
	 * 
	 * @param document
	 * @return token sequence
	 */
	public TokenSequence<CnlpToken<?>> sequence(Annotation document) {
		String text = document.get(TextAnnotation.class);
		// these are all the sentences in this document
		// a CoreMap is essentially a Map that uses class objects as keys and
		// has values with custom types
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		List<CnlpToken<?>> tokens = new LinkedList<CnlpToken<?>>();
		for (CoreMap sentence : sentences) {
			Integer sstart = null, send = null;
			// traversing the words in the current sentence
			// a CoreLabel is a CoreMap with additional token-specific methods
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				tokens.add(new WordToken(token));
				if (sstart == null)
					sstart = token.beginPosition();
				send = token.endPosition();
			}
			if (sstart != null)
				tokens.add(new SentenceToken(sstart, send, sentence));
		}
		return new TokenSequence<CnlpToken<?>>(text, tokens);
	}
}
