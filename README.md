# Sistema Informativo per Ricette e Cuochi

## Descrizione del progetto 2
Questo progetto ha lo scopo di progettare e implementare un sistema informativo che fornisca informazioni relative a ricette e cuochi. Il sistema consente agli utenti di consultare informazioni dettagliate sulle ricette disponibili, sugli ingredienti necessari e sui cuochi coinvolti nella preparazione.

## Funzionalità del sistema
- **Utenti Occasionali**: Possono consultare tutte le informazioni sulle ricette e sui cuochi, ma non hanno la possibilità di apportare modifiche ai dati.
  
- **Cuochi (Utenti Registrati)**: Possono consultare tutte le ricette, aggiungere nuovi ingredienti e nuove ricette, nonché modificare e cancellare le proprie ricette.

- **Amministratori**: Possono gestire il sistema aggiungendo, modificando o cancellando ingredienti, cuochi e ricette.

## Dettagli delle informazioni
- **Ricette**:
  - Nome
  - Una o più immagini (caricate direttamente nel sistema)
  - Ingredienti (nome e quantità)
  - Descrizione
  - Cuoco che l'ha proposta

- **Cuochi**:
  - Nome
  - Cognome
  - Data di nascita
  - Fotografia (caricata direttamente nel sistema)
 
## Casi d'uso implementati:

1. **Inserimento di nuove ricette** (Attore: Cuoco Utente Registrato)
   - **Descrizione**: Il cuoco utente registrato può inserire una nuova ricetta nel sistema.
   - **Azioni**:
     - L'utente accede alla sezione di inserimento delle ricette.
     - L'utente compila i campi richiesti (nome, immagini, ingredienti, descrizione).
     - L'utente conferma l'inserimento della ricetta nel sistema.

2. **Aggiornamento di una ricetta esistente** (Attore: Cuoco Utente Registrato)
   - **Descrizione**: Il cuoco utente registrato può aggiornare una ricetta esistente nel sistema.
   - **Azioni**:
     - L'utente accede alla propria lista di ricette.
     - L'utente seleziona la ricetta da modificare.
     - L'utente apporta le modifiche necessarie (ingredienti, descrizione, immagini, etc.).
     - L'utente conferma l'aggiornamento della ricetta nel sistema.

3. **Consultazione delle ricette** (Attore: Utente Generico)
   - **Descrizione**: Gli utenti generici possono consultare le informazioni sulle ricette disponibili nel sistema.
   - **Azioni**:
     - L'utente accede alla sezione delle ricette disponibili.
     - L'utente visualizza le varie ricette con i relativi dettagli (nome, ingredienti, immagini, descrizione, cuoco).
     - L'utente può filtrare o cercare ricette specifiche.

4. **Inserimento di nuovi cuochi** (Attore: Amministratore)
   - **Descrizione**: Gli amministratori possono aggiungere nuovi cuochi al sistema.
   - **Azioni**:
     - L'amministratore accede alla sezione di gestione dei cuochi.
     - L'amministratore inserisce i dettagli del nuovo cuoco (nome, cognome, data di nascita, foto).
     - L'amministratore conferma l'aggiunta del nuovo cuoco nel sistema.

5. **Modifica dei dati di un cuoco esistente** (Attore: Amministratore)
   - **Descrizione**: Gli amministratori possono modificare i dati di un cuoco esistente nel sistema.
   - **Azioni**:
     - L'amministratore accede alla lista dei cuochi registrati.
     - L'amministratore seleziona il cuoco da modificare.
     - L'amministratore apporta le modifiche necessarie ai dati del cuoco (nome, cognome, data di nascita, foto).
     - L'amministratore conferma le modifiche al profilo del cuoco nel sistema.

6. **Consultazione delle informazioni sui cuochi** (Attore: Utente Generico)
   - **Descrizione**: Gli utenti generici possono consultare le informazioni sui cuochi registrati nel sistema.
   - **Azioni**:
     - L'utente accede alla sezione dei cuochi registrati.
     - L'utente visualizza i profili dei cuochi con i relativi dettagli (nome, cognome, data di nascita, foto).
     - L'utente può selezionare un cuoco specifico per vedere le ricette associate.


## Requisiti aggiuntivi
- Le immagini devono essere caricate direttamente nel sistema, non è consentito l'uso di link esterni per le immagini.
- Il sistema deve gestire correttamente i diversi ruoli degli utenti per garantire la sicurezza e l'integrità dei dati.

## Note aggiuntive
Le specifiche possono essere ulteriormente dettagliate o modificate a discrezione del candidato, per garantire una migliore comprensione e implementazione del sistema.

