(ns culture.facts
  "Country-level regional-culture catalog for Burkina Faso (BFA) -- national
  dishes, protected products, beverages, crafts, festivals and heritage
  sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"BFA"
   [{:culture/id "bfa.dish.to"
     :culture/name "Tô"
     :culture/country "BFA"
     :culture/kind :dish
     :culture/summary "Cooled polenta-style cakes made from ground millet, sorghum or corn, served with vegetable sauce; the staple of the Burkinabe diet."
     :culture/url "https://en.wikipedia.org/wiki/Burkinabe_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "bfa.dish.riz-gras"
     :culture/name "Riz gras"
     :culture/country "BFA"
     :culture/kind :dish
     :culture/summary "Rice-based dish in Burkinabé and other West African cuisines, prepared with significant amounts of meat and vegetables; often served at parties in urban areas of Burkina Faso."
     :culture/url "https://en.wikipedia.org/wiki/Riz_gras"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "bfa.dish.babenda"
     :culture/name "Babenda"
     :culture/country "BFA"
     :culture/kind :dish
     :culture/summary "Mossi stew traditionally made with millet, wild greens and potash, now modernized to include a variety of ingredients."
     :culture/url "https://en.wikipedia.org/wiki/Burkinabe_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "bfa.beverage.dolo"
     :culture/name "Dôlo"
     :culture/country "BFA"
     :culture/kind :beverage
     :culture/summary "Beer made from pearl millet or sorghum, a common beverage of Burkina Faso."
     :culture/url "https://en.wikipedia.org/wiki/Burkinabe_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "bfa.beverage.zomekome"
     :culture/name "Zomekome"
     :culture/country "BFA"
     :culture/kind :beverage
     :culture/summary "Burkinabe soft drink made from millet flour, ginger, lemon juice and tamarind."
     :culture/url "https://en.wikipedia.org/wiki/Burkinabe_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "bfa.festival.fespaco"
     :culture/name "FESPACO"
     :culture/name-local "Festival panafricain du cinéma et de la télévision de Ouagadougou"
     :culture/country "BFA"
     :culture/kind :festival
     :culture/summary "Panafrican Film and Television Festival of Ouagadougou, established in 1969 and held biennially in Ouagadougou; regarded as the main event of African cinema."
     :culture/url "https://en.wikipedia.org/wiki/FESPACO"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "bfa.heritage.ruins-of-loropeni"
     :culture/name "Ruins of Loropéni"
     :culture/country "BFA"
     :culture/kind :heritage
     :culture/summary "Stone walls of a medieval fortress near Loropéni in southern Burkina Faso, inscribed in 2009 as the country's first UNESCO World Heritage Site."
     :culture/url "https://en.wikipedia.org/wiki/Ruins_of_Lorop%C3%A9ni"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-bfa culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "BFA"))
                 " BFA entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
