import java.util.LinkedList;
import java.util.HashMap;
import java.util.Queue;
import java.util.Scanner;


class Customer {
    private int accountNumber;
    private String name;
    private double balance;
    private LinkedList<String> transactionHistory;

    public Customer(int accountNumber, String name, double initialBalance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = initialBalance;
        this.transactionHistory = new LinkedList<>();
        transactionHistory.add("Account created with initial balance: " + initialBalance);
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public LinkedList<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: " + amount + ", Balance: " + balance);
    }

    public void withdraw(double amount) throws Exception {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add("Withdrew: " + amount + ", Balance: " + balance);
        } else {
            throw new Exception("Insufficient balance!");
        }
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }
}

														// Second class 



class BankingSystem {
    private HashMap<Integer, Customer> accounts;
    private Queue<String> customerServiceQueue;

    public BankingSystem() {
        accounts = new HashMap<>();
        customerServiceQueue = new LinkedList<>();
    }

    public void createAccount(int accountNumber, String name, double initialBalance) {
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account number already exists!");
        } else {
            Customer newCustomer = new Customer(accountNumber, name, initialBalance);
            accounts.put(accountNumber, newCustomer);
            System.out.println("Account created successfully for " + name);
        }
    }

    public void deposit(int accountNumber, double amount) {
        Customer customer = accounts.get(accountNumber);
        if (customer != null) {
            customer.deposit(amount);
            System.out.println("Deposited " + amount + " into account " + accountNumber);
        } else {
            System.out.println("Account not found!");
        }
    }

    public void withdraw(int accountNumber, double amount) {
        Customer customer = accounts.get(accountNumber);
        if (customer != null) {
            try {
                customer.withdraw(amount);
                System.out.println("Withdrew " + amount + " from account " + accountNumber);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Account not found!");
        }
    }

    public void transfer(int fromAccount, int toAccount, double amount) {
        Customer sender = accounts.get(fromAccount);
        Customer receiver = accounts.get(toAccount);

        if (sender != null && receiver != null) {
            try {
                sender.withdraw(amount);
                receiver.deposit(amount);
                sender.addTransaction("Transferred " + amount + " to account " + toAccount);
                receiver.addTransaction("Received " + amount + " from account " + fromAccount);
                System.out.println("Transfer successful!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("One or both accounts not found!");
        }
    }

    public void printAccountDetails(int accountNumber) {
        Customer customer = accounts.get(accountNumber);
        if (customer != null) {
            System.out.println("Account Number: " + customer.getAccountNumber());
            System.out.println("Name: " + customer.getName());
            System.out.println("Balance: " + customer.getBalance());
            System.out.println("Transaction History: " + customer.getTransactionHistory());
        } else {
            System.out.println("Account not found!");
        }
    }

    public void addToCustomerServiceQueue(String customerName) {
        customerServiceQueue.add(customerName);
        System.out.println(customerName + " added to the service queue.");
    }

    public void serveNextCustomer() {
        if (!customerServiceQueue.isEmpty()) {
            System.out.println("Serving customer: " + customerServiceQueue.poll());
        } else {
            System.out.println("No customers in the queue.");
        }
    }
}


													// Main Class



class Main {
    public static void main(String[] args) {
        BankingSystem bankingSystem = new BankingSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Account");
            System.out.println("6. Add to Customer Service Queue");
            System.out.println("7. Serve Next Customer");
            System.out.println("8. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter Account Number: ");
                    int accountNumber = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Initial Balance: ");
                    double balance = scanner.nextDouble();
                    bankingSystem.createAccount(accountNumber, name, balance);
                    break;
                case 2:
                    System.out.print("Enter Account Number: ");
                    accountNumber = scanner.nextInt();
                    System.out.print("Enter Amount: ");
                    double deposit = scanner.nextDouble();
                    bankingSystem.deposit(accountNumber, deposit);
                    break;
                case 3:
                    System.out.print("Enter Account Number: ");
                    accountNumber = scanner.nextInt();
                    System.out.print("Enter Amount: ");
                    double withdraw = scanner.nextDouble();
                    bankingSystem.withdraw(accountNumber, withdraw);
                    break;
                case 4:
                    System.out.print("Enter Sender Account Number: ");
                    int fromAccount = scanner.nextInt();
                    System.out.print("Enter Receiver Account Number: ");
                    int toAccount = scanner.nextInt();
                    System.out.print("Enter Amount: ");
                    double amount = scanner.nextDouble();
                    bankingSystem.transfer(fromAccount, toAccount, amount);
                    break;
                case 5:
                    System.out.print("Enter Account Number: ");
                    accountNumber = scanner.nextInt();
                    bankingSystem.printAccountDetails(accountNumber);
                    break;
                case 6:
                    scanner.nextLine();
                    System.out.print("Enter Customer Name: ");
                    String customerName = scanner.nextLine();
                    bankingSystem.addToCustomerServiceQueue(customerName);
                    break;
                case 7:
                    bankingSystem.serveNextCustomer();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}


