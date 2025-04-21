import java.sql.*;

public class Main {
  private static final String url = "jdbc:mysql://127.0.0.1:3306/mydb";
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
      Statement statement = connection.createStatement();

      //  String query = "select * from students";
      //  ResultSet resultSet = statement.executeQuery(query); // to retreive data

      //  while (resultSet.next()){  // here while loop checks if row exists in table
      //    int id = resultSet.getInt("id");
      //    String name = resultSet.getString("name");
      //    int age = resultSet.getInt("age");
      //    double marks = resultSet.getDouble("marks");
      //    System.out.println("Id: "+id);
      //    System.out.println("Name: "+name);
      //    System.out.println("Age: "+age);
      //    System.out.println("Marks: "+marks);
      //  }

      //  String query = String.format("INSERT INTO students(name, age, marks) VALUES('%s', %o, %f)", "Rahul", 23, 74.5);
      //  String query = String.format("UPDATE students SET marks = %f where id = %d", 89.5, 2);
      // String query = "DELETE FROM students WHERE ID = 2";
      // String query = "INSERT INTO students(name, age, marks) VALUES(?, ?, ?)";
      //  String query = "SELECT marks FROM students WHERE id = ?";
        String query = "UPDATE students SET marks = ? WHERE id = ?";
      //  String query = "DELETE FROM students WHERE id = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      //  preparedStatement.setInt(1,1);
      //  preparedStatement.setString(1,  "Ankita");
      //  preparedStatement.setInt(2  ,25);
      //  preparedStatement.setDouble(3,  84.7);
      preparedStatement.setDouble(1,  87.5);
      preparedStatement.setInt(2,  3);

      //  ResultSet resultSet = preparedStatement.executeQuery();
      //  if (resultSet.next()){
      //     System.out.println("Marks: " + resultSet.getDouble("marks"));
      //  } else {
      //     System.out.println("Marks not found!!");
      //  }

        int rowsAffected = preparedStatement.executeUpdate(); // to add, update and delete data
      //  int rowsAffected = statement.executeUpdate(query); // to add, update and delete data
            if (rowsAffected>0){
              System.out.println("Data Inserted Successfully!");
            } else {
              System.out.println("Data Not Inserted");
            }
    } catch (SQLException e) {
      System.out.println("err2 " + e.getMessage());
    }
  }
}
