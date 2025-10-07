import java.util.*;
import java.io.*;

public class Expenses {
    public static void saveExpenses(String filename, ArrayList<Expense> expenses) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for ( Expense exp : expenses ) {
                pw.println(exp.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    public static void addExpense(ArrayList<Expense> expenses, String description, double amount) {
        int newId = expenses.size() > 0 ? expenses.get(expenses.size() - 1).getId() + 1 : 1;
        String date = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Expense exp = new Expense(newId, date, description, amount);
        expenses.add(exp);
        System.out.println("Added expense (ID: " + newId + ") successfully.");
    }

    public static void viewExpenses(ArrayList<Expense> expenses) {
        System.out.println("ID   Date         Description  Amount");
        System.out.println("===================================================");
        for ( Expense exp : expenses ) {
            System.out.println(exp);
        }
    }

    public static void updateExpense(ArrayList<Expense> expenses, int id, String newDesc, double newAmount) {
        boolean found = false;
        for ( Expense exp : expenses ) {
            if ( exp.getId() == id ) {
                if ( newDesc != null && !newDesc.isEmpty() ) {
                    exp.setDescription(newDesc);
                }
                if (newAmount >= 0 ) {
                    exp.setAmount(newAmount);
                }
                found = true;
                System.out.println("Updated expense:\nID: " + id + "\nDescription: " + newDesc + "\nAmount: " + newAmount + "\n successfully.");
            }
        }
        if (!found) {
            System.out.println("Expense with ID " + id + " not found.");
        }
    }

    public static void exportExpenses(String filename, ArrayList<Expense> expenses) {
        saveExpenses(filename, expenses);
        System.out.println("Expenses exported successfully to " + filename);
    }



	public static void main(String[] args) {
        ArrayList<Expense> expenses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("expenses.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 4);
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String date = parts[1];
                    String description = parts[2];
                    double amount = Double.parseDouble(parts[3]);
                    expenses.add(new Expense(id, date, description, amount));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (args.length == 0) {
            System.out.println("Input the command you want to execute (add/view , default: view):");
            return;
        }

        String command = args[0];
        switch (command) {
            case "add":
                // Format: java Main add --description "Lunch" --amount 20.5
                String description = "";
                double amount = 0;
                for (int i = 1; i < args.length; i++) {
                    if (args[i].equals("--description") && i + 1 < args.length) {
                        description = args[++i];
                    } else if (args[i].equals("--amount") && i + 1 < args.length) {
                        amount = Double.parseDouble(args[++i]);
                    }   
                }
                addExpense(expenses, description, amount);
                saveExpenses("expenses.csv", expenses);
                break;
            case "view":
                viewExpenses(expenses);
                break;
            case "delete":
                // Format: java Main delete --id integer
                int tempDeleteId = -1; 
                for (int i = 1; i < args.length; i++) {
                    if (args[i].equals("--id") && i + 1 < args.length) {
                        tempDeleteId = Integer.parseInt(args[++i]);
                    }
                }
                final int deleteId = tempDeleteId; // final, tidak berubah lagi
                boolean removed = expenses.removeIf(exp -> exp.getId() == deleteId);
                if (removed) {
                    System.out.println("Deleted expense with ID: " + deleteId);
                    saveExpenses("expenses.csv", expenses);
                } else {
                    System.out.println("Expense with ID " + deleteId + " not found.");
                }
                break;

            case "summary":
                double total = 0;
                for (Expense exp : expenses) {
                    total += exp.getAmount();
                }
                System.out.printf("Total expenses: Rp. %.2f\n", total);
                break;
            case "sum-month":
                double totalMonth = 0;
                int month = -1;
                for (int i = 1; i < args.length; i++) {
                    if (args[i].equals("--month") && i + 1 < args.length) {
                        month = Integer.parseInt(args[++i]);
                    }
                }
                for (Expense exp : expenses) {
                    String[] dateParts = exp.getDate().split("-");
                    if (dateParts.length == 3 && Integer.parseInt(dateParts[1]) == month) {
                        totalMonth += exp.getAmount();
                    }
                }
                System.out.printf("Total expenses for month %02d: Rp. %.2f\n", month, totalMonth);
                break;
            case "update":
                // Format: java Main update --id integer --description "New Desc" --amount newAmount
                int idToUpdate = -1;
                String newDesc = null;
                double newAmount = -1;
                for (int i = 1; i < args.length; i++) {
                    if (args[i].equals("--id") && i + 1 < args.length) {
                        idToUpdate = Integer.parseInt(args[++i]);
                    } else if (args[i].equals("--description") && i + 1 < args.length) {
                        newDesc = args[++i];
                    } else if (args[i].equals("--amount") && i + 1 < args.length) {
                        newAmount = Double.parseDouble(args[++i]);
                    }
                }
                if (idToUpdate != -1) {
                    updateExpense(expenses, idToUpdate, newDesc, newAmount);
                    saveExpenses("expenses.csv", expenses);
                } else {
                    System.out.println("You must specify --id to update.");
                }
                break;
            case "export":
                // Format: java Main export --file exported_expenses.csv
                String export = "exported_expenses.csv";
                for (int i = 1; i < args.length; i++) {
                    if (args[i].equals("--file") && i + 1 < args.length) {
                        export = args[++i];
                    }
                }
                exportExpenses(export, expenses);
                break;
            case "help":
                System.out.println("Available commands:");
                System.out.println(" add --description \"desc\" --amount n.n");
                System.out.println(" view");
                System.out.println(" delete --id integer");
                System.out.println(" summary");
                System.out.println(" sum-month --month integer(1-12)");
                System.out.println(" update --id integer [--description \"new desc\"] [--amount newAmount]");
                System.out.println(" export --file filename.csv");
                System.out.println(" help");
                break;
            default:
                viewExpenses(expenses);
                break;
        }
        saveExpenses("expenses.csv", expenses);
    }
}