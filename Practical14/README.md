# MiniBank - Practical 14 Final Capstone

A console banking application demonstrating Object-Oriented Programming in Java.

## Key Features
- **Layered Architecture**: Model, Exception, Util (Validation), Repository, Service, and Console UI layers.
- **Repository Pattern**: Generic `Repository<T>` utilizing reflection on `@Id` annotated fields.
- **Robust Exception Handling**: Custom checked exceptions (`BankException`, `InsufficientFundsException`, `AccountNotFoundException`, `InvalidAmountException`).
- **Data Validation**: Regular-expression based input validation (`Validator`).
- **Thread Safety & Concurrency**: Thread-safe operations using `ConcurrentHashMap` and `TransactionProcessor` with fixed thread pools.
- **Persistence & Audit Reporting**: Binary state serialization (`StatePersister`) across restarts and NIO-based transaction logging and summary reports (`ReportGenerator`).

## Compilation
From the workspace root (`d:\oop practicals`):
```bash
javac -cp ".;lib/junit-platform-console-standalone-1.10.2.jar" Practical14/*.java Practical14/parta/*.java
```

## Running the Unit Tests
Execute JUnit 5 tests via the standalone runner:
```bash
java -jar lib/junit-platform-console-standalone-1.10.2.jar execute -cp Practical14 --select-class MiniBankTest
java -jar lib/junit-platform-console-standalone-1.10.2.jar execute -cp Practical14 --select-class parta.ItemServiceTest
```

## Packaging the Runnable JAR
```bash
cd Practical14
jar cfe minibank.jar MiniBank *.class
```

## Running MiniBank Application
```bash
java -jar Practical14/minibank.jar
```
Or directly from bytecode:
```bash
java -cp Practical14 MiniBank
```
