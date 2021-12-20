# Progetto-OOP
## Wind Speed Statistics
La seguente applicazione Java fornisce previsioni riguardo la velocità del vento e genera statistiche su di essa. Inoltre è
possibile, inserendo due città a scelta da un menù prestabilito, comparare le statistiche riguardanti la velocità del vento e 
filtrarle in base alla periodicità, range personalizzabile dall'utente. 
***
## Indice
1. [Descrizione](#Descrizione) 
2. [Installazione](#Installazione)
3. [Rotte](#Rotte)
4. [Test](#Test)
5. [Documentazione](#Documentazione)
6. [Autori](#Autori)
***
## Descrizione
Tramite l'API OpenWeather il programma riceve, salva e fa statistiche sui dati meteo riguardanti la velocità del vento di una città
cercata dall'utente; inoltre compara le statistiche, relative alla velocità del vento, di due città inserite da un utente da un
menù prestabilito.
***
A scopo dimostrativo, durante il periodo di sviluppo e testing dell'applicativo, sono stati raccolti i dati relativi ad Ancona, 
Milano, Roma e Pesaro a partire dal giorno 20/12/2021 alle ore 19:00 al giorno 22/12/2020 alle ore 19:00.
***
La chiamata all’API di OpenWeather utilizzata ha questa struttura:
http://api.openweathermap.org/data/2.5/weather?q={city%name}&appid={API%key}

* city name è il nome della città selezionata
* API key è il codice di accesso al servizio
***
# Rotte
L'utente può effettuare richieste tramite Postman all'indirizzo    
**localhost:8080**
***
Tipo | Rotta | Descrizione
---- | ---- | ----
GET | `/hourlySaving?nome={nomeCitta}` | Salva in un file, con frequenza oraria, i dati relativi alla velocità del vento di una determinata città.
GET | `/stats?nome={nomeCitta}&oraInizio={data inzio}&oraFine={data fine}` | Restituisce le statistiche fatte sulla velocità del vento di una determinata città, in una specifica fascia oraria, scelta dall'utente
GET | `/compare?nome1={nomeCitta1}&nome2={nomeCitta2}&oraInizio={data inzio}&oraFine={data fine}` | Confronta le statistiche relative alla velocità del vento, di due città inserite dall'utente, in una determinata fascia oraria
***
Per effettuare richieste, all'utente basterà avviare l'applicazione come SpringBoot App, successivamente usando Postman, fare richieste secondo le rotte prima specificate.  
In seguito forniremo la descrizione dettagliata di ogni rotta.

1. /hourlySaving?nome={nomeCitta}

Questa rotta permette di salvare le informazioni attuali sulla visibilità della città che volete. Il programma creerà un file col nome "CityName"SalvataggioOrario.json che si aggiornerà ogni ora. Se è già presente un file con lo stesso nome, il programma lo aprirà e, senza eliminare ciò che è presente, inizierà a scrivere le previsioni.  
In Postman se tutto è andato a buon fine, visualizzerete un messaggio del tipo:
```
Salvataggio iniziato!
```

2. /stats?nome={nomeCitta}&oraInizio={dataInzio}&oraFine={dataFine}

Questa rotta fornisce statistiche relative alla velocità del vento. In particolare, fornisce:  
* valore minimo raggiunto in una determinata fascia oraria
* valore massimo raggiunto in una determinata fascia oraria
* valore medio 
* varianza  

Al posto di {nomeCitta} deve essere inserito il nome della città di cui si vogliono fare le statistiche e al posto di {dataInizio} e {dataFine}, rispettivamente il giorno e l'orario dell'inizio e il giorno e l'orario della fine del periodo di cui si vogliono fare le statistiche.  

A scopo dimostrativo viene fornito all'utente già un database chiamato "CityName"SalvataggioOrario.json, contenente valori relativi al periodo che va dalle ore 19:00 del 20/12/2021 alle ore 19:00 del 22/12/2021, delle seguenti città: Ancona, Roma, Milano e Pesaro.  
Le statistiche vengono fatte sui dati contenuti nel file, se i parametri inseriti non risultano corretti verranno visualizzati messaggi di errore:  
* Se l'utente inserisce una città non ammessa viene generata un'eccezione di tipo cityException e verrà visualizzato un messaggio di errore del tipo:  
  ```
  nomeCitta + " non è disponibile. Puoi scegliere tra: Ancona, Roma, Milano, Pesaro"
  ```
* Se l'utente inserisce una data non valida viene generata un'eccezione di tipo dataException e verrà visualizzato un messaggio di errore del tipo: 
   ```
  La data inserita non è corretta.
   ```
* Se l'utente inserisce una data in un formato non ammesso viene generata un'eccezione di tipo ParseException

* Se l'utente inserisce una fascia oraria o una città di cui non sono presenti dati salvati viene generata un'eccezione di tipo vectorNullException e verrà visualizzato un messaggio di errore del tipo: 
  ```
  Dati non disponibili nell'arco di tempo selezionato
   ```
Se l'utente inserisce tutto correttamente, verrà visualizzato su Postman un JSONObject: 



