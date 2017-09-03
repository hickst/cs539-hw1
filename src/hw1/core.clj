(ns hw1.core
  (:require [clojure.tools.cli :as cli])
  (:require [clojure.string :as str])
  (:gen-class)
)

(defn tokenize
  "Split given sequence of lines at whitespace. Ignore surrounding whitespace.
   Convert tokens to lowercase. Return a single sequence of tokens."
  [lines]
  (map str/lower-case (mapcat #(str/split (str/trim %) #"\s+") lines)) )

(defn split-tokens
  "Map each of the given WORD/POS tokens into a list of WORD and POS strings"
  [tokens]
  (map #(str/split %1 #"/") tokens) )

(defn remove-punctuation
  "Remove any token, from the given sequence, whose POS tag does not contain any letters"
  [split-tokens]
  (filter #(re-matches #"\w+" (second %)) split-tokens) )

(defn sort-by-freq-desc
  "Sort a map of item-count pairs in descending order by the frequency (value) component"
  [item-count-map]
  (sort (fn [x y] (compare (second y) (second x))) item-count-map) )

(defn compute-frequencies
  "Given a split-token sequence, return a map of WORD, POS, and WORD/POS sequences.
   Each sequence contains item-count pairs, sorted by frequency in descending order"
  [split-tokens]
  { :words (sort-by-freq-desc (frequencies (map first split-tokens)))
    :pos (sort-by-freq-desc (frequencies (map second split-tokens)))
    :word-pos (sort-by-freq-desc (frequencies split-tokens)) }
)

(defn make-frequencies [inFile]
  (->> (line-seq (clojure.java.io/reader inFile)) ; read all lines
       (filter #(not (str/blank? %)))       ; filter out blank lines
       (tokenize)                           ; returns token sequence
       (split-tokens)                       ; split each token into WORD and POS string pair
       (remove-punctuation)                 ; filter out punctuation tokens
       (compute-frequencies)                ; returns map of all frequency counts
))

(defn print-frequencies
  "Format and print the top N results in each of the categories stored in the counts map"
  [counts top-n]
  (let [ top-words (take top-n (:words counts))
         top-pos (take top-n (:pos counts))
         top-word-pos (take top-n (:word-pos counts)) ]
    (println (str "Top " top-n " Words:"))
    (doseq [wtok top-words] (println (format "  %-10s\t%5d" (first wtok) (second wtok))) )
    (println)
    (println (str "Top " top-n " POS:"))
    (doseq [ptok top-pos] (println (format "  %-10s\t%5d" (first ptok) (second ptok))) )
    (println)
    (println (str "Top " top-n " Word-POS:"))
    (doseq [wptok top-word-pos]
      (println (format "  %-10s\t%5d" (str (ffirst wptok) "/" (second (first wptok)))
                                      (second wptok))) )
))


(defn -main [& args]
  (let [ usage "java -jar hw1.jar [--help] [--num N] brown_sample.txt"
         [options other-args flag-usage]
           (cli/cli args
             ["-n"   "--num"      "Return the top N frequency counts" :default 10
                                  :parse-fn #(Integer/parseInt %) ]
             ["-h"   "--help"     "Show usage message for this program" :flag true]
;            ["-v"   "--verbose"  "Operate in verbose mode" :flag true]
           )]

    ;; if user asks for help, print usage messages and exit
    (if (:help options)
      (do
        (println usage)
        (println flag-usage)
        (System/exit 1)) )

    ;; check for reasonable integer
    (if (not (> (:num options) 0))
      (do
        (println "ERROR: N must be an integer greater than 0")
        (println usage)
        (println flag-usage)
        (System/exit 2)) )

    ;; check for missing required input file path
    (if (empty? other-args)
      (do
        (println "ERROR: Required input-file-path argument is missing.")
        (println usage)
        (println flag-usage)
        (System/exit 3)) )

    ;; call the main computational function, then format and print the results:
    (let [ freqs (make-frequencies (first other-args)) ]
       (print-frequencies freqs (:num options)) )
))

;; Usage Examples: calls from the REPL:
(comment
  ;; call on sample file in the resources subdirectory:
  (main "resources/brown_sample.txt")

  ;; call specifying non-default number of results:
  (main "-n" "20" "myFile.txt")
)
