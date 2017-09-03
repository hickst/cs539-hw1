# Introduction to Homework 1

## Part 1

Process the brown sample.txt corpus (now found in resources/brown_sample.txt)
The corpus is formatted as a sequence of tokens separated by white space.

For example, the text:

```
	The/at Fulton/np-tl County/nn-tl Grand/jj-tl Jury/nn-tl said/vbd Friday/nr
an/at investigation/nn of/in Atlanta’s/np$ recent/jj primary/nn election/nn
produced/vbd ‘‘/‘‘ no/at evidence/nn ’’/’’ that/cs any/dti irregularities/nns
took/vbd place/nn ./.
```

shows one sentence from this corpus.

Each token contains a word and its part-of-speech (POS) tag separated by slash.
For example, the token "investigation/nn" contains the word "investigation" and
its POS tag "nn", which indicates that it is a noun ("nn\*" POS tags indicate nouns,
"vb\*" indicate verbs, "jj\*" indicate adjectives, etc.).

Write code that answers the following questions:

1. What are the top 10 most frequent words in this file (independent of POS tags)?
2. What are the top 10 most frequent POS tags?
3. What the the top 10 most frequent word-POS tag pairs?

Notes:

1. Ignore blank lines which separate text lines.
2. Some text lines begin with whitespace. This will affect tokens unless removed.
3. Do not count punctuation tokens.
4. Words within tokens may, themselves, contain slashes!
5. Counts should be case-insensitive..
