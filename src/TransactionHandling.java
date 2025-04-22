import java.sql.*;
import java.util.Scanner;

public class TransactionHandling {

  private static final String url = "jdbc:mysql://127.0.0.1:3306/lenden";
  private static final String username = "root";
  private static final String password = "Admin@123";

  public static void main(String[] args) {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("err1 " + e.getMessage());
    }

    try{
      Connection connection = DriverManager.getConnection(url,username,password);
      connection.setAutoCommit(false);
      String debit_query = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
      String credit_query = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
      PreparedStatement debitPreparedStatement = connection.prepareStatement(debit_query);
      PreparedStatement creditPreparedStatement = connection.prepareStatement(credit_query);

      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter Account Number: ");
      int account_number = scanner.nextInt();
      System.out.println("Enter Withdrawal Amount: ");
      double amount = scanner.nextDouble();

      debitPreparedStatement.setDouble(1,amount);
      debitPreparedStatement.setInt(2,account_number);
      creditPreparedStatement.setDouble(1,amount);
      creditPreparedStatement.setInt(2,102);  // 102 is account no where we are transferring amt
      debitPreparedStatement.executeUpdate();
      creditPreparedStatement.executeUpdate();
      if (isSufficient(connection, account_number, amount)){
        connection.commit();
        System.out.println("Transaction Successful!");
      } else {
        connection.rollback();
        System.out.println("Transaction Failed!!");
      }

      debitPreparedStatement.close();
      creditPreparedStatement.close();
      scanner.close();
      connection.close();

      } catch (SQLException e) {
      System.out.println("err2 " + e.getMessage());
    }
  }

  static boolean isSufficient(Connection connection, int account_number, double amount) {
    try {
      String query = "SELECT balance FROM accounts WHERE account_number = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, account_number);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        double current_balance = resultSet.getDouble("balance");
        if (amount > current_balance) {
          return false;
        } else {
          return true;
        }
      }
      resultSet.close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return false; // Default to false on error
  }
}
