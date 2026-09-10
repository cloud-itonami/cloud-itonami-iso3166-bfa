(ns marketentry.facts-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.facts :as facts]))

(deftest bfa-has-spec-basis
  (let [sb (facts/spec-basis "BFA")]
    (is (some? sb))
    (is (string? (:provenance sb)))
    (is (seq (:required-evidence sb)))
    (is (some? (facts/corporate-number-spec-basis "BFA")))
    (is (some? (facts/pme-preference-margin-spec-basis "BFA")))))

(deftest bfa-rep-spec-basis-is-genuinely-present
  (testing "Burkina Faso's own administrative-sanctions provision extends exclusion grounds to a bidder's own representatives, unlike Benin's consortium/subcontractor-only scope"
    (is (some? (facts/rep-spec-basis "BFA")))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest required-evidence-satisfied
  (let [sb (facts/spec-basis "BFA")
        all (:required-evidence sb)]
    (is (true? (facts/required-evidence-satisfied? "BFA" all)))
    (is (not (facts/required-evidence-satisfied? "BFA" (take 1 all))))
    (is (nil? (facts/required-evidence-satisfied? "ATL" all)))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["BFA" "USA" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 2 (:covered c)))
    (is (= ["ATL"] (:missing-jurisdictions c)))))
