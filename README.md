# Sistema Informativo per Ricette e Cuochi

[![Build](https://img.shields.io/badge/build-passing-brightgreen.svg)](#)
[![Java](https://img.shields.io/badge/Java-17-blue.svg)](#)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](#)
[![Database](https://img.shields.io/badge/Database-PostgreSQL-blue.svg)](#)
[![License](https://img.shields.io/badge/license-MIT-lightgrey.svg)](#)

Un gestionale web dedicato a **ricette e cuochi**, dove chiunque può scoprire piatti, ingredienti e chef, mentre cuochi e admin possono arricchire e gestire il catalogo.  

---

## ✨ Funzionalità principali

- 👀 **Utenti occasionali**
  - Consultano ricette e profili cuochi
  - Cercano e filtrano ricette disponibili

- 👨‍🍳 **Cuochi (utenti registrati)**
  - Inseriscono nuove ricette con immagini e ingredienti
  - Aggiornano o cancellano le proprie ricette
  - Gestiscono ingredienti e descrizioni

- 🛠️ **Amministratori**
  - Aggiungono, modificano o eliminano cuochi
  - Gestiscono ingredienti, ricette e profili
  - Mantengono il sistema ordinato e sicuro

---

## 🧩 Dettagli delle informazioni

- **Ricette**
  - Nome, immagini, ingredienti (nome + quantità)
  - Descrizione dettagliata
  - Cuoco autore della ricetta

- **Cuochi**
  - Nome, cognome, data di nascita
  - Fotografia profilo
  - Ricette associate

---

## 📚 Casi d’uso implementati

1. ➕ **Inserimento ricetta** – un cuoco aggiunge un nuovo piatto con ingredienti, immagini e descrizione.  
2. ✏️ **Aggiornamento ricetta** – il cuoco modifica una sua ricetta esistente.  
3. 🔍 **Consultazione ricette** – utenti generici esplorano ricette filtrandole per dettagli o autore.  
4. 👨‍🍳 **Inserimento cuoco** – l’amministratore registra un nuovo chef con foto e dati personali.  
5. 📝 **Modifica cuoco** – l’amministratore aggiorna dati o immagine di un cuoco esistente.  
6. 👥 **Consultazione cuochi** – utenti visualizzano i profili dei cuochi e le loro ricette.  

---

## ⚙️ Requisiti

- ☕ Java 17  
- 🛠️ Maven 3+  
- 🐘 Database relazionale (es. PostgreSQL/MySQL)  
- 📂 Upload immagini direttamente dal sistema (no link esterni)

---

## 📌 Note

- Ruoli e permessi gestiti per garantire integrità dei dati  
- Le specifiche possono essere estese o adattate a seconda delle esigenze progettuali  

---

Un sistema **semplice, potente e intuitivo** per mettere in connessione **ricette, ingredienti e cuochi** 👨‍🍳✨
