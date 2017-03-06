 
�etta er alveg sk�r l�sing � �v� sem ma�ur vill sj� gerast, en m�r finnst vanta sk�rari l�singu � �v� hvernig �i� �tli� a� nota semaf�rur til a� f� �essa virkni fram. T.d. hva� eru margar semaf�rur, sem bi�l�sar eru settir � o.s.frv. �i� tali� um bi�l�s � pers�nur, eins og �a� s� bara einn, en �a� �arf a� vera ein counting semaf�ra per bi�r��, �ar sem ma�ur getur hugsa� s�r bi�r�� inn � h��, og bi�r�� �t af h�� (sama pers�na b��ur samt ekki � s�mu inn og �t semaf�ru).

�a� �arf a� koma fram � n�kv�mlega hva�a semaf�ru pers�nu�r��ur kallar � acquire() � � hvert sinn, og n�kv�mlega hva�a semaf�ru lyftan kallar � release() � og hversu oft (�v� �etta eru counting semaf�rur). Og svo n�kv�mlega hvernig h�n lokar � eftir s�r, �.e. �arf h�n a� kalla oft � acquire sj�lf til a� loka �rugglega r��inni sem b��ur. �a� g�ti veri� a� �i� s�u� a� hugsa �etta allt � �kve�inn h�tt, en m�r finnst �etta ekki koma alveg n�gu detailed fram , �annig a� �g �tla a� l�sa a�einsminni hugmynd um hvernig �etta �arf a� gerast, sem �i� geti� svo bori� ykkar hugmyndir saman vi�.

Sko�um til a� byrja me� pers�nu. H�n er mj�g einf�ld keyrsla:
B��a eftir a� komast inn � lyftu (acquire() � inSem)
Skr� sig inn � lyftu svo kerfi� s� up to date
B��a eftir a� komast �t �r lyftu (acquire() � outSem)
Skr� sig �t �r lyftu svo kerfi� s� up to date.

Engar if-setningar e�a neitt, bara wait � semaf�rum til a� st�ra �v� hven�r hva� gerist. Lyftan opnar s��an semaf�rurnar og kemur pers�nunni �annig �fram � sinni keyrslu.

�a� �arf �v� a� vera semaf�ra fyrir hverja h�� sem hleypir inn � lyftuna. Ef einhver kemur inn � h�� source �� �arf hann a� kalla � inSemaphore[source].acquire(), e�a eitthva� sl�kt. Semaf�rurnar geta ekki ra�a� neinu, e�a sent menn �t � annarri r�� en �eir komu inn. �v� er ekki h�gt a� samn�ta in semaf�rur � milli h��a e�a samn�ta inn og �t semaf�rur.

Pers�na b��ur � sl�kri semaf�ru og �egar lyfta kemur � �essa h�� �� hleypir (kallar � inSemaphore[currentFloor].release()) h�n eins oft og h�n hefur laus pl�ss. Svo �arf h�n � loka � eftir s�r me� acquire eins oft og h�n hefur laus pl�ss sem enginn f�r �.

A� sama skapi �arf a� vera �t semaf�ra � hverri h��, sem pers�nan b��ur � �egar h�n er komin inn � lyftuna. �� myndi pers�na kalla � outSemaphore[destination].acquire(). Lyftan �arf �� l�ka, ��ur en h�n hleypir inn � sig � hverri h�� a� hleypa fyrst �t �r s�r me� outSemphore[currentFloor].release(), eins og oft og h�n hefur f�lk, og s��an a� loka semaf�runni me� acquire eins og oft og h�n hefur enn�� f�lk (�eir sem f�ru ekki �t � �essari h��).

Ef �i� fari� s��an a� spreyta ykkur � fl�kjustigi 5 �� �ttu� �i� a� p�la � einhverju eins og a� hafa s�r inn-hur�ir � hverri h��, en �� er gott a� hafa einhvern controller klasa sem s�r um a� opna inn � h��ina og l�ta pers�nur vita � hva�a lyftu �eir eiga a� fara, sem r��ur �v� �� � hva�a �t semaf�ru pers�nan b��ar, enda �urfa ��r a� vera ein per h�� per lyftu, sums� margar :)