# Sistem-energetic

## Despre

Proiectare Orientata pe Obiecte, Seriile CA, CD 2020-2021

<https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa2>

Student: Simion Diana-Maria, 321CA

## Rulare teste

Clasa Test#main
  * ruleaza solutia pe testele din checker/, comparand rezultatele cu cele de referinta
  * ruleaza checkstyle

Detalii despre teste: checker/README

Biblioteci necesare pentru implementare:
* Jackson Core 
* Jackson Databind 
* Jackson Annotations

Tutorial Jackson JSON: 
<https://ocw.cs.pub.ro/courses/poo-ca-cd/laboratoare/tutorial-json-jackson>

## Implementare

### Entitati

* Clase Intermediare
    * Pentru a gestiona datele de input si de output am folosit urmatoarea
	abordare: dat fiind faptul ca datele de intrare au un format, iar cele de 
	iesire un format diferit, entitatile prezentand doar anumite proprietati pe
	care le au, am ales sa creez mai multe entitati intermediare numite CurrentState
	Consummer, CurrentStateDistributor, CurrentStateProducer etc. Aceste entitati monitorizeaza starea
	fiecarui consumator, distributor, producator de-a lungul unei simulari, deci a rularii
	unui test.
  
* Simulation si FinalState
    * De-a lungul unei simulari, au loc cele n + 1 runde. Consumatorii
	si distribuitorii care nu au dat faliment sunt pastrati in 2 liste care
	simuleaza o baza de date cu participantii inca aflati in joc. In momentul 
	in care acestia dau faliment, ei sunt adaugati in FinalState, care retine
	status ul final al partipantilor, deci fie pana cand au dat faliment, fie
	pana ce simularea curenta a luat sfarsit.
    * Exista si o lista cu toti producatorii din joc, care insa nu pot da faliment.
	  * De asemenea, Simulation si FinalState sunt implementate cu ajutorul
	Singleton.
    * La finalul executiei tuturor rundelor, continutul din FinalState este
	filtrat catre output, urmand apoi scrierea in fisier in format json a rezulatelor.
  

### Flow
   * In fiecare runda, este posibil ca produactorii sa faca modificari la cantitatea de energie pe care o ofera,
   asadar distributorii acestora vor fi notificati, pentru a-si aplica strategiile de alegere a producatorilor din nou,
   runda urmatoare.
   
   * In fiecare luna dupa ce se fac update-urile necesare, se genereaza cel mai bun contract pentru consumatorii care nu sunt deja 
  anagajati intr-un contract. Astfel, contractul generat este unul "standard", urmand sa fie aplicat fiecarui consumator care are nevoie
  de el, contractul avand acum id-ul consumatorului. Contractul este de asemenea adaugat distributorului corespunzator.
  
   * De asemenea consumatorii primesc salariu si platesc contractul daca au posibilitatea. Dupa aceasta etapa, sunt eliminate din joc contractele care au expirat. Urmeaza ca distributorii sa si plateasca cheltuielile.
	 * La finalul rundei, sunt eliminati din simulare atat consumatorii, catsi distributorii faliti, iar status-ul lor este inregistrat in FinalState.
   
   * Distribuitorii care stiu ca trebuie sa isi aleaga alti producatori, isi aplica din nou strategiile.

### Design patterns

* Factory
  * Pentru a crea obiecte de tipul consumator, distribuitor sau producer, am folosit un factory. Astfel, de fiecare data cand am introdus un obiect nou in simulare
  am folosit o functie de creare a acestora. De asemenea, am folosit factory si pentru a crea strategii personalizate fiecarui distribuitor.
  
* Singleton
  * Am folosit design pattern-ul Singleton pentru a avea o singura instanta din anumite clase, precum factory-urile create, care sunt aceleasi pe tot parcursul unei simulari.

* Strategy
  * Cele 3 strategii de alegere a producatorilor difera in functie de tipul de strategie aleasa de distribuitor si iau in considerare factori precum tipul de energie
  furnizata, cantitatea de energie sau pretul. Astfel, am creat interfata Strategy care este implementata de cele 3 variante: Green, Price sau Quantity.

* Observer
  * Deoarece atunci cand producatorii fac modificari, distributorii trebuie sa fie instiintati, am folosit Observer si Observable, desi sunt deprecated.
  Producatorii sunt de tip Observable si isi notifica distribuitorii, iar distribuitorii sunt de tip Observer si au drept actiune de update actualizarea unui camp
  care le spune ca vor fi nevoiti sa isi schimbe producatorii pentru runda urmatoare.
