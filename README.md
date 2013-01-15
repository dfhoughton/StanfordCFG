StanfordCFG
===========

David Houghton
May 12, 2011

This is a project to wed the [Stanford NLP Group's][stanford] sentencing, lemmatization, and POS tagging tools with my Grammar project to facilitate constructing rule-based grammars for natural languages. The idea is that you can write a grammar like so

    NP := <D>? <Nb> | <NP> <PP>
    Nb := <AP>* <N>
    AP := 'very'? <A>
    PP := <P> <NP>

and get this grammar to pull noun phrases matching this grammar out of text tokenized and POS tagged by the CoreNLP tagger.

Dependencies
------------

This code depends on the Stanford [CoreNLP][] libraries and models.

It also depends on my [Grammar][] project:

To run the tests in the `test/` directory, you will need [JUnit 4][junit].

Main Documentation
------------------

To see examples, the API, and so forth, please go to [StanfordCFG][].

License and Copyright
---------------------

This project is licensed on the the LGPL version 2.1. See the included lgpl.txt file.  The Grammar project is similarly licensed. The CoreNLP libraries are licensed under the full GPL. See the address above.

Copyright © David F. Houghton, 2011
All Rights Reserved

[CoreNLP]: http://nlp.stanford.edu/software/corenlp.shtml
[Grammar]: https://github.com/dfhoughton/Grammar
[junit]: http://junit.sourceforge.net/
[StanfordCFG]: http://dfhoughton.org/stanfordcfg/
[stanford]: http://nlp.stanford.edu/
