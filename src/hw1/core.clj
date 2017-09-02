(ns hw1.core
  (:require [clojure.tools.cli :as cli])
  (:gen-class)
)

(defn compute-frequencies [filename]
  []                                        ; TODO: IMPLEMENT LATER
)

(defn print-frequencies [freqs]
  (println freqs)                           ; TODO: IMPLEMENT LATER
)

(defn -main [& args]
  (let [ usage "Usage: java -jar hw1.jar -f brown_sample.txt"
         [options other-args flag-usage]
           (cli/cli args
             ["-f"   "--file"      "Input text file in Brown Corpus format" :flag true]
             ["-h"   "--help"      "Show usage message for this program" :flag true]
             ["-v"   "--verbose"   "Operate in verbose mode" :flag true] )]

    ;; if user asks for help, print usage messages and exit
    (if (:h options)
      (do
        (println usage)
        (println flag-usage)
        (System/exit 1)))

    ;; check for missing required output file basename argument
    (if (not (:file options))
      (do
        (println "ERROR: Required input file path argument is missing.")
        (println usage)
        (println flag-usage)
        (System/exit 2)))

    (let [freqs (compute-frequencies (:file options)) ]
      (print-frequencies freqs) )
))


(comment
  ;; Call program on sample file in the resources subdirectory:
  (-main -f "resources/brown_sample.txt")
)
