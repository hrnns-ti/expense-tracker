# 🚀 Expense Tracker Java CLI

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE) [![Java](https://img.shields.io/badge/Java-CLI-blue.svg)](https://www.java.com/) [![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)]()

A simple, fast, and extensible expense tracker for your daily needs.  
Track, update, and export your expenses easily from the command line!

---

## ✨ Features

- 📥 **Add** new expense with auto-generated ID and current date
- 📋 **View** all expenses in a formatted table
- ✏️ **Update** description and amount by ID
- 🗑️ **Delete** expense by ID
- 📊 **Summary**: total and monthly expense calculation
- 📤 **Export** all data to another CSV file
- ⚡ **Fast CLI** for automation and scripting

---

## 📁 Project Structure
```expense-tracker/
│
├── src/
│ ├── Main.java
│ ├── Expense.java
│ └── expenses.csv
│
├── .gitignore
├── .classpath
├── .project
└── README.md
```
---

## 🛠️ Getting Started

### 1. Compile

```bash
javac Main.java Expense.java
```

### 2. Run Commands
```
java Expenses add --description "[String description]" --amount [Double amount]
java Expenses view
java Expenses update --id [int ID] --description [String description] --amount [Double amount]
java Expenses delete --id [int ID]
java Expenses summary
java Expenses sum-month --month [int month]
java Expenses export --file exported_expenses.csv

```


---

## 👤 Author

**@Haerunnas**  
- [GitHub](https://github.com/hrnns-ti)
- [LinkedIn](https://linkedin.com/in/haerunnas)
- Email: nassjourney@gmail.com

---

## 📄 License

This project is licensed under the MIT License.