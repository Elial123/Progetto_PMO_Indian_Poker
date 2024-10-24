# Progetto PMO Poker Indiano

> Repository per il Progetto di Programmazione e Modellazione ad Oggetti.<br>
> Membri del team:<br>
> Udan Priyankarage, matricola 320118<br>
> Federico De Piano, matricola 321339<br>
> Elia Lini, matricola 320128<br>

## Analisi del Problema :
Specifica del Problema:<br>
<br>
L'applicazione da noi sviluppata è un gioco singolo utente con 2 modalità di gioco disponibili, l'obbiettivo di questultimo è accumulare il maggior numero di fiches possibile.<br>
Un utente può scegliere se giocare tutti contro tutti o a coppie.<br>
La partita è composta da 10 round ognuno con un tempo di 15 secondi, in questi round il giocatore dovrà osservare le carte visibili dei bot e determinare se con la sua mano (coppia di carte)
li convine passare o puntare. <br>
Per poter continuare a stare in partita devi pagare una fiches di tassa all'inizio di ogni round e per poter continuare a giocatre devi avere almeno 2 fiches altrimenti si viene eliminati.<br>
<br>
Le scelte sono le seguenti : 
<br>
 * Puntare: scommettere delle fiches conferisce il diritto di contendersi le fiches puntate da tutti i giocatori 
 * Passare: paghi comunque la tassa ma perdi il diritto a partecipare al round 
 * Quittare: uscire completamente dalla partita senza possibilità di rientrarvi 

<br>

## Funzionalitá :  
```diff
+ Creazione e predisposizione del match
+ Creazione dei giocatori e dealer
+ Consentire scelte all'utente
+ Contare lo scorrere del tempo
+ Contare il numero di fiches dei giocatori
+ Creazione e gestione delle bid dei bot e del player
+ Creazione e gestione risultati di ogni giocatore a fine round e a fine partita
```
## Challenge Principali :
```diff
+ Implementazione algoritmo per l'associazione della scelta del bot (bid, fold) con corrispettiva tassa e bid (se avese scelto bid)
+ Implementare l'algoritmo per la ricerca del vincitore
+ Implementare l'interfaccia grafica  
+ Implementare il Controller  
+ Fare in modo che non si vinca troppo facilmente contro i bot
```