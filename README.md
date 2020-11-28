# Tema-poo-2020
Tema Poo 2020 Stanca Adelin-Nicolae
Linkul catre repository: https://github.com/AdelinNicolaeS/Tema-poo-2020
Am folosit pentru propria implementare clase noi Actor, Movie, Serial, User, Video
care le inlocuiesc practic pe cele din schelet cu scopul de a adauga noi
functionalitati, plus o clasa DataCenter cu rolul de a uni toate clasele de mai sus.
***Folderul actor***
Am implementat clasa Actor alaturi de alte clase care au rolul de a implementa functii
de comparare ce au fost utilizate pe parcursul rularii programului. Pentru Actor, am
adaugat noi atribute, precum ratingul si numarul de premii castigate, atribute ce
trebuie recalculate si actualizate constant prin noi metode, precum setNumberAwards,
setRating, la care se adauga si metode aditionale, precum hasAwards si descriptionHasWords
care rezolva taskuri particulare din cadrul unor comenzi din Main.java, din cadrul
expresiilor lambda.
***Folderul dataset***
Am creat clasele Actors, Movies, Serials, Users, Videos care contin fiecare o lista cu
tipul de date pe care le reprezinta plus metode specifice ce trebuie aplicate pe toata
baza de date.
In Actors am implementat 3 metode al caror scop este de a actualiza rating-urile pentru
fiecare actor, de a recalcula numarul de premii primit de fiecare dintre actori si de
a genera mesajul de output necesar pentru operatiile de query-actors din Main.
De asemenea, clasa Movies implementeaza o metoda care imi detecteaza daca un film se
afla in lista de filme deja existenta, o metoda ce actualizeaza pentru fiecare film
numarul de aparitii in lista de favorite a utilizatorilor, una pentru numarul de
vizualizari ale filmelor si una care genereaza mesajul de afisat pentru comanda Query.
In mod asemanator, clasa Serials detine metodele ce updateaza numarul de prezente in
lista de favorite pentru fiecare serial, numarul de vizualizari si genereaza mesajul de
iesire cerut de comanda Query.
Clasa Users contine metoda findUserByName ce verifica daca un utilizator este in lista
de utilizatori si metoda usersListMessage ce genereaza mesajul pentru Query.
Clasa Videos contine o lista cu toate video-urile existente, plus un dictionar care
cuprinde fiecare gen si numarul sau de aparitii. Alaturi de metodele ce obtin acest
dictionar si care calculeaza practic cel mai popular gen, aceasta clasa implementeaza
cerintele de la Recommendation, rezolvate prin metodele de tip XRecommendation.
***Folderul entertainment***
La scheletul primit, am adaugat in clasa Season atributul de rating, alaturi de o metoda
ce calculeaza acest rating, cu scopul de a calcula ulterior rating-ul intregului serial
si de a realiza comenzile cerute in Main.java.
***shows***
Clasele cu rol in sortare reprezinta implementari ale interfetei Comparator cu scopul de
a automatiza procesul de sortare in functie de un anumit criteriu.
Clasa abstracta Video este scheletul pe care se vor construi clasele Movie si Serial si
contine atribute si metode ce apartin celor 2 clase mostenitoare, precum metodele ce
verifica daca un video respecta filtrele impuse in Query sau metoda ce calculeaza scorul
Favorite, lasand in schimb metoda gerDuration abstracta intrucat durata total se calculeaza
diferit pentru cele 2 clase.
Clasele Movie si Serial primesc pe langa cele de mai sus si o metoda ce actualizeaza
rating-ul, intrucat acesta se calculeaza diferit in cele 2 cazuri.
***Folderul user***
In clasa User, pe langa atributele din schelete, am adaugat o lista cu filmele care au fost
deja vazute si un dictionar cu fiecare serial si numarul sezonului care a fost vizionat. Am
adaugat metode ce trateaza toate cazurile de la cerintele de Command, adica rating, view si
favorite. 
***Folderul main***
In Main.java parcurg fiecare actiune din lista de actiuni, dupa care in functie de caz creez
o variabila auxiliara pe care o folosesc pentru manipularea listelor de actori, video-uri si
useri in functie de cerinte (sortare, eliminare). Am incercat sa folosesc expresii lambda.
