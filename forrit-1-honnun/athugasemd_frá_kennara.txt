 
Þetta er alveg skýr lýsing á því sem maður vill sjá gerast, en mér finnst vanta skýrari lýsingu á því hvernig þið ætlið að nota semafórur til að fá þessa virkni fram. T.d. hvað eru margar semafórur, sem biðlásar eru settir á o.s.frv. Þið talið um biðlás á persónur, eins og það sé bara einn, en það þarf að vera ein counting semafóra per biðröð, þar sem maður getur hugsað sér biðröð inn á hæð, og biðröð út af hæð (sama persóna bíður samt ekki á sömu inn og út semafóru).

Það þarf að koma fram á nákvæmlega hvaða semafóru persónuþráður kallar á acquire() á í hvert sinn, og nákvæmlega hvaða semafóru lyftan kallar á release() á og hversu oft (því þetta eru counting semafórur). Og svo nákvæmlega hvernig hún lokar á eftir sér, þ.e. Þarf hún að kalla oft á acquire sjálf til að loka örugglega röðinni sem bíður. Það gæti verið að þið séuð að hugsa þetta allt á ákveðinn hátt, en mér finnst þetta ekki koma alveg nógu detailed fram , þannig að ég ætla að lýsa aðeinsminni hugmynd um hvernig þetta þarf að gerast, sem þið getið svo borið ykkar hugmyndir saman við.

Skoðum til að byrja með persónu. Hún er mjög einföld keyrsla:
Bíða eftir að komast inn í lyftu (acquire() á inSem)
Skrá sig inn í lyftu svo kerfið sé up to date
Bíða eftir að komast út úr lyftu (acquire() á outSem)
Skrá sig út úr lyftu svo kerfið sé up to date.

Engar if-setningar eða neitt, bara wait á semafórum til að stýra því hvenær hvað gerist. Lyftan opnar síðan semafórurnar og kemur persónunni þannig áfram í sinni keyrslu.

Það þarf því að vera semafóra fyrir hverja hæð sem hleypir inn í lyftuna. Ef einhver kemur inn á hæð source þá þarf hann að kalla á inSemaphore[source].acquire(), eða eitthvað slíkt. Semafórurnar geta ekki raðað neinu, eða sent menn út í annarri röð en þeir komu inn. Því er ekki hægt að samnýta in semafórur á milli hæða eða samnýta inn og út semafórur.

Persóna bíður á slíkri semafóru og þegar lyfta kemur á þessa hæð þá hleypir (kallar á inSemaphore[currentFloor].release()) hún eins oft og hún hefur laus pláss. Svo þarf hún á loka á eftir sér með acquire eins oft og hún hefur laus pláss sem enginn fór í.

Að sama skapi þarf að vera út semafóra á hverri hæð, sem persónan bíður á þegar hún er komin inn í lyftuna. Þá myndi persóna kalla á outSemaphore[destination].acquire(). Lyftan þarf þá líka, áður en hún hleypir inn í sig á hverri hæð að hleypa fyrst út úr sér með outSemphore[currentFloor].release(), eins og oft og hún hefur fólk, og síðan að loka semafórunni með acquire eins og oft og hún hefur ennþá fólk (þeir sem fóru ekki út á þessari hæð).

Ef þið farið síðan að spreyta ykkur á flækjustigi 5 þá ættuð þið að pæla í einhverju eins og að hafa sér inn-hurðir á hverri hæð, en þá er gott að hafa einhvern controller klasa sem sér um að opna inn á hæðina og láta persónur vita í hvaða lyftu þeir eiga að fara, sem ræður því þá á hvaða út semafóru persónan bíðar, enda þurfa þær að vera ein per hæð per lyftu, sumsé margar :)