# Libralibre
> A free, open-source library management system for community libraries.

This project was inicially developed for the **Programming Language 2 (LP2)** and **Coding Best Practices (BPP)** classes at the Instituto Métropole Digital (IMD/UFRN).

---

## About The Project

**Libralibre** is a desktop application designed to provide a simple, offline, and free management solution for small community libraries.

The goal is to replace manual ledgers and spreadsheets with a dedicated tool that is easy to use. The system handles book cataloging, user registration, and the complete check-out/check-in (loan) process. All application data is persisted locally using JSON files, requiring no external database or internet connection.

### Core Features
* **Acquisitions Management:** Register new books, supporting both physical (copy-controlled) and digital formats.
* **User Management:** Register and manage library patrons.
* **Loan Operations:** Check out books to users, with real-time availability validation.
* **Return Operations:** Check in returned books, automatically updating copy availability.
* **JSON Persistence:** All data is saved locally, ensuring the application is 100% offline.
* **Consolidated Reporting:** View a ranked report of the most popular books based on loan history.

---

## 🛠️ Built With

* **Java 17**
* **Maven** (Dependency Management and Build Tool)
* **GSON** (For JSON data persistence)

---

## ⚙️ Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites
* [Java JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Apache Maven](https://maven.apache.org/download.cgi)

### 1. Compiling the Project

Navigate to the project's root directory (where the `pom.xml` file is located) and run the following command in your terminal:

```bash
mvn compile
```

This will download all required dependencies (like GSON) and compile the project source code.

### 2. Running the Application (Console)

After a successful compilation, you can run the application (currently in console mode) using the following command:

```bash
mvn exec:java -Dexec.mainClass="br.ufrn.imd.libralibre.MainApp"
```

The interactive library menu will launch directly in your terminal.

---

## Author

* Felipe Reis | email: felipereis0724@gmail.com