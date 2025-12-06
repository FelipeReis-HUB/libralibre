# <img src="src/main/java/br/ufrn/imd/libralibre/images/logo.png" alt="Libralibre Logo" width="40" height="40"/> Libralibre
> A free, open-source library management system for community libraries.

This project was inicially developed for the **Programming Language 2 (LP2)** and **Coding Best Practices (BPP)** classes at the Instituto Métropole Digital (IMD/UFRN).

## About The Project

**Libralibre** is a desktop application designed to provide a simple, offline, and free management solution for small community libraries.

The goal is to replace manual ledgers and spreadsheets with a dedicated tool that is easy to use. The system handles book cataloging, user registration, and the complete check-out/check-in (loan) process. All application data is persisted locally using JSON files, requiring no external database or internet connection.

### 📸 Screenshots
*(Add screenshots)*

## 🛠️ Built With

* **Java 17**
* **JavaFX** (Graphical User Interface)
* **Maven** (Dependency Management)
* **GSON** (JSON Data Persistence)

## ⚙️ Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites
* [Java JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Apache Maven](https://maven.apache.org/download.cgi)

### 1. Compiling the Project

Navigate to the project's root directory and run:

```bash
mvn clean compile
```

This will download all required dependencies (like GSON) and compile the project source code.

### 2. Running the Application

After a successful compilation, you can run the application using the following command:

```bash
mvn exec:java -Dexec.mainClass="br.ufrn.imd.libralibre.Launcher"
```

The interactive library menu will launch the menu interface.

## Author

* Felipe Reis | email: felipereis0724@gmail.com