(ns marketentry.registry
  "Pure-function market-entry filing-draft + filing-submit record
  construction -- an append-only market-entry book-of-record draft.

  Like every sibling actor's registry, there is no single international
  reference-number standard for a public-procurement market-entry
  filing -- every jurisdiction assigns its own format. This namespace
  does NOT invent one; it builds a jurisdiction-scoped sequence number
  and validates the record's required fields, the same honest,
  non-fabricating discipline `marketentry.facts` uses.

  `engagement-fee-matches-claim?` is an HONEST reapplication of the
  SAME ground-truth-recompute DISCIPLINE sibling actors use (verify a
  claimed monetary total against the entity's own recorded quantity x
  unit fields), reapplied to a market-entry engagement fee line.

  `required-preference-margin` / `compute-evaluated-price` /
  `evaluated-price-mismatches-claim?` are the SAME discipline applied to
  a genuinely Burkina Faso-specific mechanism: Décret n°2024-1748/PRES/
  PM/MEF du 05 décembre 2024 (implementing décret of Loi n°005-2024/ALT,
  the CURRENT procurement law -- see `marketentry.facts` for why this is
  NOT the abrogated 2016 law), Article 122, a five-percent (5%)
  bid-EVALUATION preference margin for an offer submitted BY a verified
  petite/moyenne entreprise (PME), artisan, or entreprise artisanale,
  Burkinabè or UEMOA-communautaire -- 'Une marge de préférence de cinq
  pour cent (5%) du montant des offres financières peut être accordée
  aux petites et moyennes entreprises burkinabè ou communautaires, aux
  artisans ou aux entreprises artisanales burkinabè ou communautaires.'
  The evaluated price is used ONLY to RANK/compare competing bids; it is
  never the real contract price the winning bidder is paid (`:base-price`
  stays the actual bid).

  This is the SAME check SHAPE Benin's `mpme-preference-margin-*`
  established for this family (a BID-EVALUATION PRICE ADJUSTMENT, not an
  ELIGIBILITY/EXCLUSION gate the way Bulgaria's/Albania's/Azerbaijan's/
  Armenia's/Antigua and Barbuda's flagship checks are) -- but grounded in
  a genuinely different citation with a genuinely different LEGAL
  STRENGTH: Benin's Art. 77 al.3 uses mandatory 'doit être attribuée'
  ('must be attributed'); Burkina Faso's Art. 122 uses discretionary
  'peut être accordée' ('may be accorded'). The QUANTUM (5%) is fixed
  and unambiguous once the margin applies, but whether it applies at all
  is, per the décret's own text, more conditional than Benin's
  unconditional-once-verified-MPME model. This namespace models it the
  SAME way Benin's is modeled anyway (conditional purely on the
  engagement's own declared `:pme-status?`), the same
  conditional-on-declared-ground-truth discipline this family's IFU
  check already uses (`:requires-ifu?`/`:ifu-verified?`) -- an honest
  simplification noted here, not silently assumed.

  Only Art. 122's PME/artisan branch is modeled. Décret n°2024-1748's
  OTHER preference articles (Art. 119's general UEMOA-communautaire
  travaux margin up to 10%, Art. 120's up-to-15% community-value-added
  supplies margin, Art. 121's collectivité-territoriale-financed local
  margin, and Art. 123's subcontracting-based margin explicitly capped
  at 'qui ne peut être supérieure à cinq pour cent (5%)') are
  deliberately NOT modeled: several of those are conditioned on
  additional criteria (community value-added percentage, financing
  source, subcontracting commitments) this repo has no ground-truth
  field for, and Art. 123's own cap-not-fixed-value language is the
  IDENTICAL discretionary-cap shape Benin's excluded Art. 77 OTHER
  branch already established as out of scope for an independent
  recompute. Modeling only the one unambiguous flat-percentage branch is
  an honest scope-narrowing, the same discipline this family's other
  honest-narrowing decisions already established.

  Separately, Décret n°2024-1748 Art. 57 establishes a MANDATORY annual
  PME set-aside quota (>= 15% of an autorité contractante's ENTIRE
  annual procurement-plan value, split >= 5%/5%/5% across
  micro/petite/moyenne entreprises) -- this is a PORTFOLIO-WIDE planning
  requirement spanning MANY contracts, not a single-engagement formula,
  so it is likewise NOT modeled here: no single engagement's own fields
  can validate or falsify an authority's AGGREGATE annual compliance.

  This namespace is pure data + pure functions -- no I/O, no network
  call to any real procurement portal. It builds the RECORD an
  operator would keep, not the act of submitting a portal registration
  itself (that is `marketentry.operation`'s `:filing/submit`, always
  human-gated -- see README Actuation)."
  (:require [clojure.string :as str]))

(defn- unsigned-certificate
  "Every certificate this actor produces is UNSIGNED -- signature is
  the market-entry operator's act, not this actor's."
  [kind subject record-id]
  {"@context" ["https://www.w3.org/ns/credentials/v2"]
   "type" ["VerifiableCredential" kind]
   "credentialSubject" {"id" subject "record" record-id}
   "proof" nil
   "issued_by_registry" false
   "status" "draft-unsigned"})

(defn- zero-pad [n w]
  (let [s (str n)]
    (str (apply str (repeat (max 0 (- w (count s))) "0")) s)))

(defn compute-engagement-fee
  "The ground-truth engagement fee for `engagement`'s own `:base-fee`
  and `:monitoring-months` x `:monthly-rate` -- a single flat
  base + months x rate calculation, not a full pricing engine."
  [{:keys [base-fee monthly-rate monitoring-months]}]
  (+ (double base-fee)
     (* (double monthly-rate) (double monitoring-months))))

(defn engagement-fee-matches-claim?
  "Does `engagement`'s own `:claimed-fee` equal the independently
  recomputed `compute-engagement-fee`?"
  [{:keys [claimed-fee] :as engagement}]
  (== (double claimed-fee) (compute-engagement-fee engagement)))

(def pme-preference-margin-pct
  "Décret n°2024-1748/PRES/PM/MEF du 05 décembre 2024, Art. 122
  (curl/OCR-verified 2026-07-22 against arcop.bf's official hosting --
  the PDF is a flatbed scan with no text layer, read via `tesseract -l
  fra`): the bid-evaluation preference margin an offer submitted BY a
  verified petite/moyenne entreprise (PME), artisan, or entreprise
  artisanale, Burkinabè or UEMOA-communautaire, may be accorded."
  0.05)

(defn required-preference-margin
  "The Art. 122 preference margin `engagement` is entitled to: 5% when
  its own declared `:pme-status?` is a verified true, 0.0 otherwise.
  Missing/nil `:pme-status?` -> 0.0 (no free preference unless declared
  AND true)."
  [{:keys [pme-status?]}]
  (if (true? pme-status?) pme-preference-margin-pct 0.0))

(defn compute-evaluated-price
  "The ground-truth EVALUATION-ONLY price used to rank/compare competing
  bids -- NEVER the actual contract price. `:base-price` is the real bid
  the contract would be signed at; only the price used to compare offers
  is reduced by the Art. 122 preference margin the engagement is
  actually entitled to."
  [{:keys [base-price] :as engagement}]
  (* (double base-price) (- 1.0 (required-preference-margin engagement))))

(defn evaluated-price-mismatches-claim?
  "Does `engagement`'s own declared `:claimed-evaluated-price` differ
  from the INDEPENDENTLY recomputed Art. 122 evaluated price? Catches
  BOTH directions honestly: a non-PME claiming a preference it is not
  entitled to, and a verified PME being denied the preference it IS
  entitled to."
  [{:keys [claimed-evaluated-price] :as engagement}]
  (not (== (double claimed-evaluated-price) (compute-evaluated-price engagement))))

(defn register-draft
  "Validate + construct the FILING-DRAFT registration DRAFT -- the
  market-entry operator's own act of preparing a portal registration
  package. Pure function -- does not touch any real procurement
  portal."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "draft: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "draft: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "draft: sequence must be >= 0" {})))
  (let [draft-number (str (str/upper-case jurisdiction) "-DFT-" (zero-pad sequence 6))
        record {"record_id" draft-number
                "kind" "filing-draft"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "draft_number" draft-number
     "certificate" (unsigned-certificate "FilingDraft" draft-number draft-number)}))

(defn register-submit
  "Validate + construct the FILING-SUBMIT registration DRAFT -- the
  market-entry operator's own act of actually submitting a portal
  registration (always human-gated upstream)."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "submit: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "submit: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "submit: sequence must be >= 0" {})))
  (let [submit-number (str (str/upper-case jurisdiction) "-SUB-" (zero-pad sequence 6))
        record {"record_id" submit-number
                "kind" "filing-submit"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "submit_number" submit-number
     "certificate" (unsigned-certificate "FilingSubmit" submit-number submit-number)}))

(defn append [history result]
  (conj (vec history) (get result "record")))
