# Risk
## Projektindító dokumentum
**Projekt megnevezése:** Rizikó játék

**Projekten dolgozó hallgatók:** Dudás Orsolya, Ferenczi John, Horváth Eszter, Sajti Tamás

**Kurzus:** Szoftverfejlesztés a gyakorlatban, 2. csoport, 2017/2018 tavaszi félév

**Projekt konkrét célja:** Java nyelven írt Rizikó játék megvalósítása több klienses hálózatra.

**Projekt elvárt eredménye:** Az első félév során közösen elemezzük, majd megtervezzük a programot, illetve elkészítjük a prototípust. Utóbbit a félév végére az előre egyeztetett módszerek alapján teszteljük.

**Fejlesztői eszközök:** NetBeans IDE, Enterprise Architect

**Programnyelv:** Java

## Projekt várható ütemezése
[A projekt ütemezése ide kattintva megtekinthető!](https://docs.google.com/spreadsheets/d/1P_jhHhmncnhNFmenk3FIkTzbcDrHqRjHlk0aFfQaWfQ/edit?usp=sharing)

## Funkcionális követelmények

**Felhasználói követelmények:** 
- A szoftver lehetővé teszi, hogy 3-6 felhasználó elkezdjen egy játékot.
- A szoftver automatikusan előkészíti a játékot, miután a megfelelő számú felhasználó belépett.
- A szoftver automatikusan váltja a soron következő játékost.
- A szoftver minden kör első szakaszában automatikusan kiosztja a soron következő játékosnak járó hadosztályokat. Ezután várakozik, ha a felhasználónak van beváltható rizikó kártyája. Amennyiben ezt a játékos még nem szeretné igénybe venni, lehetősége van kihagyni.
- A szoftver minden kör második szakaszában lehetővé teszi az aktuális felhasználónak, hogy megtámadjon egy szomszédos területet 1, 2, vagy 3 hadosztállyal. Ha egy csata eldőlt, a játékos újra támadhat, de lehetősége van megállni is. 
- A szoftver automatikusan felismeri, ha az aktuális játékos meghódított egy területet, és a csatában felhasznált támadó hadosztályok elfoglalják a körzetet.
- A szoftver automatikusan felismeri, ha egy felhasználó kiesett a játékból, és átadja a rizikó kártyáit az őt kiejtő játékosnak.
- A szoftver minden kör harmadik szakaszában lehetővé teszi a soron következő felhasználónak, hogy átcsoportosítsa a hadosztályait pontosan egy körzetből egy szomszédosba. A játékosnak lehetősége van kihagyni ezt az opciót.
- A szoftver automatikusan felismeri, ha egy felhasználó megnyerte a játékot, és kijelzi az eredményt.

**Rendszerkövetelmények:**
- A szoftver használatához szükség van minimum 2 GB RAM-ra. A merevlemez szükséglet legalább 500 KB.
- A szoftver Windows 8 és Windows 10 platformon működik.
- A szoftver Google Chrome böngészőben működik.
- A szoftver maximum 6 klienst tud kezelni.
- A szoftver Java nyelven kerül implementálásra.
