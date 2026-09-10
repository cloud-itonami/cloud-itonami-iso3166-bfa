(ns statute.facts
  "General-law compliance catalog for Burkina Faso (BFA) -- extends this
  repo's existing `marketentry.facts` (public-procurement market-entry
  only, narrow scope) with a second, orthogonal catalog of statutes a
  company operating in this jurisdiction must generally track for
  compliance. Mirrors cloud-itonami-iso3166-jpn/-deu/-bgr/-aze/-alb/-arm/
  -atg/-ben/-bdi's `statute.facts` (ADR-2607141700,
  cloud-itonami-compliance-fact-federation).

  Every entry cites an OFFICIAL government-hosted (or, for the OHADA
  entry, official supranational-body-hosted) URL -- never fabricated.
  Confidence is reported per-entry below because Burkina Faso's own web
  presence was genuinely MIXED for this iteration (2026-07-22): some
  primary sources (fonction-publique.gov.bf, academiedepolice.bf,
  dgi.bf) served native-text PDFs that `pdftotext -layout` read directly
  on the first attempt (HIGH confidence, no OCR needed); the procurement
  law/decrees at arcop.bf, by contrast, are flatbed-SCANNED PDFs (Creator
  metadata: 'KONICA MINOLTA bizhub 958') with NO text layer at all --
  `pdftotext -layout` returned zero lines of output -- so those had to be
  rendered to PNG (`pdftoppm -r 150/200/400`) and read via `tesseract -l
  fra` (French-language tessdata fetched fresh; this machine's tesseract
  install shipped with only `eng`/`osd`/`snum`), the same OCR-fallback
  discipline a prior sibling iteration (Burundi) established for a
  scanned Journal Officiel PDF. OCR text is legible and internally
  consistent (cross-checked against independent secondary press sources
  for the same law/decree numbers and dates) but is flagged MODERATE
  rather than HIGH confidence below, and article numbers that OCR could
  not cleanly resolve are described by position/context rather than
  invented.

  - Companies/commercial-entity law: this iteration specifically
    investigated, rather than assumed by analogy to Benin (also an
    OHADA member with the identical AUSCGIE finding), whether Burkina
    Faso is actually a current OHADA member state -- confirmed directly
    against OHADA's own official state-members page
    (https://www.ohada.org/en/state-members/, WebFetch-read directly,
    listing Burkina Faso among the 17 current members). Burkina Faso
    has NO domestic 'Commerce Act' of its own; company law is governed
    DIRECTLY by the SAME supranational instrument as Benin's: the OHADA
    Uniform Act on Commercial Companies and Economic Interest Groupings
    (Acte uniforme relatif au droit des sociétés commerciales et du
    groupement d'intérêt économique, AUSCGIE), adopted 30 January 2014
    in Ouagadougou (notably: IN Burkina Faso's own capital), in force
    since 5 May 2014. Article 10 of the OHADA Treaty (Traité de
    Port-Louis, 17 October 1993, revised in Québec 17 October 2008)
    gives every Uniform Act direct and obligatory effect in every member
    state, 'nonobstant toutes dispositions contraires de droit interne,
    antérieure ou postérieure' -- so AUSCGIE applies in Burkina Faso
    without any domestic transposition act, the identical finding
    Benin's catalog already documents (WebSearch-corroborated wording,
    not fetched from a primary OHADA treaty-text page directly --
    MODERATE-HIGH confidence on the exact wording quoted, HIGH
    confidence on the substantive direct-effect fact itself). Separately,
    RCCM/business-entity REGISTRATION is governed by the Acte Uniforme
    relatif au Droit Commercial Général (AUDCG), administered at the
    greffe of a Tribunal de Commerce -- Burkina Faso created two
    dedicated commercial courts (Ouagadougou and Bobo-Dioulasso) by Loi
    n°022-2009/AN du 12 mai 2009 (MODERATE confidence: confirmed via
    servicepublic.gov.bf's own official service catalog plus a secondary
    law-firm-hosted copy of the text, not fetched from a .gouv.bf
    primary hosting of the 2009 law directly); see `marketentry.facts`
    for AUDCG cited against RCCM specifically.
  - Code du Travail (Labour Code): Loi n°028-2008/AN du 13 mai 2008
    portant Code du Travail au Burkina Faso (promulguée par le décret
    n°2008-331 du 19 juin 2008) -- downloaded directly from the official
    Ministère de la Fonction Publique hosting
    (fonction-publique.gov.bf) and read via `pdftotext -layout` (a
    native, non-scanned PDF -- no OCR needed). Article 1er, read
    directly: 'La présente loi est applicable aux travailleurs et aux
    employeurs exerçant leur activité professionnelle au Burkina Faso.'
    HIGH confidence -- primary source, native text, official ministry
    hosting.
  - Data protection: this iteration specifically investigated, rather
    than assumed, whether Burkina Faso's early Loi n°010-2004/AN du 20
    avril 2004 (one of the first data-protection laws in West Africa,
    widely cited in secondary literature) is STILL the current law in
    force -- and found it is NOT: Loi n°010-2004/AN was ABROGATED and
    replaced by Loi n°001-2021/AN du 30 mars 2021 portant protection des
    personnes à l'égard du traitement des données à caractère personnel,
    downloaded from academiedepolice.bf and read via `pdftotext -layout`
    (native text, no OCR needed). The 2021 law's OWN Article 82, read
    directly: 'La présente loi abroge la loi n°010-2004/AN du 20 avril
    2004 portant protection des données à caractère personnel.' This
    catalog cites ONLY the current 2021 law, not the superseded 2004
    text some secondary sources still reference. HIGH confidence --
    primary source, native text, self-authenticating (the law's own
    adoption/promulgation language at Ouagadougou, 30 mars 2021, is
    internally consistent and the PDF is not a scan). Separately,
    genuinely different from Benin's CNIL-renamed-to-APDP transition:
    the 2021 law's Article 45 ('Il est créé une Commission de
    l'informatique et des libertés en abrégé CIL.') PRESERVES the same
    'CIL' name the 2004 law originally used -- the authority was not
    renamed the way Benin's was, only re-legislated with a more detailed
    (36-page vs 17-page) statute.

  A law not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of statute entries. `:statute/url` + `:statute/law-number`
  are the citation the governor requires before any compliance-fact
  proposal referencing this law can commit."
  {"BFA"
   [{:statute/id "bfa.ohada-auscgie"
     :statute/title "Acte uniforme relatif au droit des sociétés commerciales et du groupement d'intérêt économique (AUSCGIE)"
     :statute/jurisdiction "BFA"
     :statute/kind :law
     :statute/law-number "OHADA Uniform Act -- adopted 30 January 2014 (Ouagadougou), in force 5 May 2014; directly applicable in Burkina Faso as an OHADA member state per Traité de Port-Louis Art. 10, no domestic transposition act required"
     :statute/url "https://www.ohada.org/en/commercial-companies-and-economic-interest-groups/"
     :statute/url-provenance :official-ohada-org
     :statute/enacted-date "2014-01-30"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:corporate-governance :incorporation}}
    {:statute/id "bfa.code-du-travail"
     :statute/title "Code du Travail au Burkina Faso"
     :statute/jurisdiction "BFA"
     :statute/kind :law
     :statute/law-number "Loi n°028-2008/AN du 13 mai 2008 (promulguée par le décret n°2008-331 du 19 juin 2008)"
     :statute/url "https://www.fonction-publique.gov.bf/fileadmin/user_upload/storage/22-08-2021-C7-Loi_28-2008AN_du_13_mai_2008_portant_code_du_travail_compressed.pdf"
     :statute/url-provenance :official-fonction-publique-gov-bf
     :statute/enacted-date "2008-05-13"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:labor :employment}}
    {:statute/id "bfa.loi-protection-donnees-2021"
     :statute/title "Loi portant protection des personnes à l'égard du traitement des données à caractère personnel"
     :statute/jurisdiction "BFA"
     :statute/kind :law
     :statute/law-number "Loi n°001-2021/AN du 30 mars 2021 (abroge la loi n°010-2004/AN du 20 avril 2004)"
     :statute/url "https://academiedepolice.bf/index.php/telechargement/category/43-cybercriminalite?download=201%3Aloi-n-001-2021-an-portant-sur-la-protection-des-donnees-a-caractere-personnel"
     :statute/url-provenance :official-academiedepolice-bf
     :statute/enacted-date "2021-03-30"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:data-protection :privacy}}]})

(defn spec-basis
  "The jurisdiction's statute vector, or nil -- nil means NO spec-basis
  for that jurisdiction yet."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report, same shape/discipline as `marketentry.facts/coverage`:
  never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-bfa statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "BFA")) " BFA statutes seeded with an "
                 "official citation. Extend "
                 "`statute.facts/catalog`, never fabricate a law-id or URL.")})))

(defn by-topic
  "Statutes for `iso3` tagged with `topic` (e.g. :labor, :data-protection)."
  [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))
