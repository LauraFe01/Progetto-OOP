# Progetto-OOP
## Wind Speed Statistics
La seguente applicazione Java fornisce previsioni riguardo la velocità del vento e genera statistiche su di essa. Inoltre è
possibile, inserendo due città a scelta da un menù prestabilito, comparare le statistiche riguardanti la velocità del vento e 
filtrarle in base alla periodicità, range personalizzabile dall'utente.   

## Indice
1. [Descrizione](#Descrizione) 
2. [Installazione](#Installazione)
3. [Rotte](#Rotte)
4. [Test](#Test)
5. [Documentazione](#Documentazione)
6. [Autori](#Autori)   

## Descrizione
Tramite l'API OpenWeather il programma riceve, salva e fa statistiche sui dati meteo riguardanti la velocità del vento di una città
cercata dall'utente; inoltre compara le statistiche, relative alla velocità del vento, di due città inserite da un utente da un
menù prestabilito.  

A scopo dimostrativo, durante il periodo di sviluppo e testing dell'applicativo, sono stati raccolti i dati relativi ad Ancona, 
Milano, Roma e Pesaro a partire dal giorno 20/12/2021 alle ore 19:00 al giorno 22/12/2020 alle ore 19:00.  

La chiamata all’API di OpenWeather utilizzata ha questa struttura:
http://api.openweathermap.org/data/2.5/weather?q={city%name}&appid={API%key}

* city name è il nome della città selezionata
* API key è il codice di accesso al servizio  

## Installazione 
Questa applicazione è installabile dal Prompt dei Comandi digitando:  
```
git clone https://github.com/LauraFe01/Progetto-OOP.git 
```


# Rotte
L'utente può effettuare richieste tramite Postman all'indirizzo    
**localhost:8080**   

Tipo | Rotta | Descrizione
---- | ---- | ----
GET | `/hourlySaving?nome={nomeCitta}` | Salva in un file, con frequenza oraria, i dati relativi alla velocità del vento di una determinata città.
GET | `/stats?nome={nomeCitta}&oraInizio={data inzio}&oraFine={data fine}` | Restituisce le statistiche fatte sulla velocità del vento di una determinata città, in una specifica fascia oraria, scelta dall'utente
GET | `/compare?nome1={nomeCitta1}&nome2={nomeCitta2}&oraInizio={data inzio}&oraFine={data fine}` | Confronta le statistiche relative alla velocità del vento, di due città inserite dall'utente, in una determinata fascia oraria   

Per effettuare richieste, all'utente basterà avviare l'applicazione come SpringBoot App, successivamente usando Postman, fare richieste secondo le rotte prima specificate.  
In seguito forniremo la descrizione dettagliata di ogni rotta.

## 1. /hourlySaving?nome={nomeCitta}

Questa rotta permette di salvare le informazioni attuali sulla velocità del vento della città che volete. Il programma creerà un file col nome "CityName"SalvataggioOrario.json che si aggiornerà ogni ora. Al posto di {nomeCitta} deve essere inserito il nome della città di cui si vogliono fare le statistiche, se è già presente un file con lo stesso nome, il programma lo aprirà e, senza eliminare ciò che è presente, inizierà a scrivere le previsioni.  
In Postman se tutto è andato a buon fine, visualizzerete un messaggio del tipo:
```
Salvataggio iniziato!
```
Il file verrà salvato nel formato JSON all'interno si presenta nel seguente modo:

![alt text](https://github.com/LauraFe01/Progetto-OOP/blob/master/esempio%20salvataggio%20su%20file.png)

## 2. /stats?nome={nomeCitta}&oraInizio={dataInzio}&oraFine={dataFine}

Questa rotta fornisce statistiche relative alla velocità del vento. In particolare, fornisce:  
* valore minimo raggiunto in una determinata fascia oraria
* valore massimo raggiunto in una determinata fascia oraria
* valore medio della velocità del vento in una determinata fascia oraria
* varianza della velocità del vento in una determinata fascia oraria 

Al posto di {nomeCitta} deve essere inserito il nome della città di cui si vogliono fare le statistiche e al posto di {dataInizio} e {dataFine}, rispettivamente il giorno e l'orario dell'inizio e il giorno e l'orario della fine del periodo di cui si vogliono fare le statistiche (nel formato "yyyy-MM-dd HH:mm:ss").  

A scopo dimostrativo viene fornito all'utente già un database chiamato "CityName"SalvataggioOrario.json, contenente valori relativi al periodo che va dalle ore 19:00 del 20/12/2021 alle ore 19:00 del 22/12/2021, aggiornati ogni ora, delle seguenti città: Ancona, Roma, Milano e Pesaro.  
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
Se l'utente inserisce tutto correttamente, verrà visualizzato su Postman un JSONObject del tipo: 

```
{
    "Nome città": "Roma",
    "Data inizio": "2021-12-15 16:00:00",
    "Data fine": "2021-12-15 18:00:00",
    "Statistiche velocità vento": {
        "Valore medio": 2.22,
        "Valore minimo": 0.68,
        "Varianza": 4.15,
        "Valore massimo": 4.53
    }
}
```
## 3. /compare?nome1={nomeCitta1}&nome2={nomeCitta2}&oraInizio={data inzio}&oraFine={data fine} 

Questa rotta fornisce statistiche relative alla velocità del vento delle due città e le compara. In particolare, fornisce:  
* valore minimo raggiunto dalla prima città in una determinata fascia oraria
* valore minimo raggiunto dalla seconda città in una determinata fascia oraria
* valore massimo raggiunto dalla prima città in una determinata fascia oraria
* valore massimo raggiunto dalla seconda città in una determinata fascia oraria
* valore medio della velocità del vento in una determinata fascia oraria
* varianza della velocità del vento in una determinata fascia oraria
* variazione percentuale di tutti i valori precedentemente scritti, della prima città rispetto alla seconda 

Al posto di {nomeCitta} deve essere inserito il nome della città di cui si vogliono fare le statistiche e al posto di {dataInizio}
e {dataFine}, rispettivamente il giorno e l'orario dell'inizio e il giorno e l'orario della fine del periodo di cui si vogliono 
fare le statistiche (nel formato "yyyy-MM-dd HH:mm:ss").  

A scopo dimostrativo viene fornito all'utente già un database chiamato "CityName"SalvataggioOrario.json, contenente valori relativi al periodo che va dalle ore 19:00 del 20/12/2021 alle ore 19:00 del 22/12/2021, aggiornati ogni ora, delle seguenti città: Ancona, Roma, Milano e Pesaro.  
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
Se l'utente inserisce tutto correttamente, verrà visualizzato su Postman un JSONObject del tipo: 
  ```
{
    "periodo di confronto ": {
        "dal ": "2021-12-15 16:00:00",
        "al ": "2021-12-15 18:00:00"
    },
    "dati": [
        {
            "Valori minimi velocità vento": {
                "Variazione percentuale del valore di Roma rispetto a quello di Milano": -23.59,
                "Valore minimo Roma": 0.68,
                "Valore minimo Milano": 0.89
            }
        },
        {
            "Valori massimi velocità vento": {
                "Valore massimo Milano": 2.34,
                "Valore massimo Roma": 4.53,
                "Variazione percentuale del valore di Roma rispetto a quello di Milano": "93.58%"
            }
        },
        {
            "Variazione percentuale del valore di Roma rispetto a quello di Milano": 50,
            "Valori medi velocità vento": {
                "Valore medio Milano": 1.48,
                "Valore medio Roma": 2.22
            }
        },
        {
            "Varianze velocità vento": {
                "Varianza Roma": 4.97,
                "Varianza Milano": 0.57,
                "Variazione Percentuale del valore di Roma rispetto a quello di Milano": 771.92
            }
        }
    ],
    "città": [
        "Roma",
        "Milano"
    ]
}
  ```
  
### Rotte secondarie
La nostra applicazione presenta anche rotte secondarie, create al momento dell'implementazione per favorire lo sviluppo del codice :
  
  Tipo | Rotta | Descrizione
---- | ---- | ----
GET | `/weatherInfo?nome={nomeCitta}` | Restituisce tutte le informazioni meteorologiche di una determinata città scelta dall'utente
GET | `/save?nome={nomeCitta}` | Salva su file le informazioni relative alla velocità del vento al momento della richiesta
GET | `/speedW?nome={nomeCitta}` | Restituisce le informazioni relative alla velocità del vento al momento della richiesta
GET |`/filtro?nome={nomeCitta}&oraInizio={data inzio}&oraFine={data fine}` | Restituisce tutte le informazioni relative alla velocità del vento di una determinata città, in una specifica fascia oraria, scelta dall'utente

### 1. /weatherInfo?nome={nomeCitta}

Questa rotta fornisce tutte le informazioni meteorologiche ricavate dall'API di OpenWeather di una determinata città scelta dall'utente e passata come parametro.

Se l'utente inserisce tutto correttamente, verrà visualizzato su Postman un JSONObject del tipo: 
```
{
    "visibility": 10000,
    "timezone": 3600,
    "main": {
        "temp": 279.87,
        "temp_min": 277.12,
        "grnd_level": 1023,
        "humidity": 55,
        "pressure": 1024,
        "sea_level": 1024,
        "feels_like": 276.4,
        "temp_max": 283.75
    },
    "clouds": {
        "all": 11
    },
    "sys": {
        "country": "IT",
        "sunrise": 1640068781,
        "sunset": 1640100803,
        "id": 6770,
        "type": 1
    },
    "dt": 1640079073,
    "coord": {
        "lon": 12.8903,
        "lat": 43.9036
    },
    "weather": [
        {
            "icon": "02d",
            "description": "few clouds",
            "main": "Clouds",
            "id": 801
        }
    ],
    "name": "Pesaro",
    "cod": 200,
    "id": 3171173,
    "base": "stations",
    "wind": {
        "deg": 137,
        "speed": 5.55,
        "gust": 7.25
    }
}
```

### 2. /save?nome={nomeCitta}

Questa rotta permette di salvare le informazioni attuali sulla velocità del vento di una città scelta dall'utente. Il programma creerà un file col nome "nomeCitta_todaysDate.txt" che si aggiornerà ogni ora. Se è già presente un file con lo stesso nome, il programma lo aprirà e, senza eliminare ciò che è presente, aggiungere le informazioni attuali.  
In Postman se tutto è andato a buon fine, visualizzerete un messaggio del tipo:
```
Il file è stato creato!
```
### 3. /speedW?nome={nomeCitta}
Questa rotta fornisce le informazioni sulla velocità del vento di una determinata città scelta dall'utente e passata come parametro.

Se l'utente inserisce tutto correttamente, verrà visualizzato su Postman un JSONObject del tipo: 
```
{
    "data": "2021-12-21 10:00:00",
    "name": "Pesaro",
    "Id": 3171173,
    "speed": 5.11
}
```
### 4. /filtro?nome={nomeCitta}&oraInizio={data inzio}&oraFine={data fine}

Questa rotta fornisce solo i valori relativi alla velocità del vento di una determinata città, in una specifica fascia oraria, scelta dall'utente.

Se l'utente inserisce tutto correttamente, verrà visualizzato su Postman un JSONArray del tipo: 
```
[
    1.45,
    0.68,
    4.53
]
```
## Test
Abbiamo implementato varie classi utili per svolgere test, principalmente per quanto riguarda alcuni metodi che fanno statistiche e comparazioni, ma anche per eccezioni e per controllare dei metodi che restituiscono JSONObject. 

## Documentazione
Il codice java è interamente documentato e commentato in Javadoc.  
Per conoscere le specifiche funzionalità di ogni classe e ogni metodo consultare il Javadoc.

## Autori
Laura Ferretti e Edoardo Cecchini
