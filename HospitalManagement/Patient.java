package HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private  Connection connection;
    private  Scanner scanner;
    public Patient(Connection connection, Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }
    // Add the patient section
    public void addPatient(){
        System.out.println("Enter patient name: ");
        String name = scanner.next();
        System.out.println("Enter patient Age: ");
        int age = scanner.nextInt();
        System.out.println("Enter patient Gender: ");
        String Gender = scanner.next();

        // try and catch section to handel the terminate the program
        try{
            // sql section to insert the patient data
            String Query = "INSERT INTO Patients(name, age, Gender) Values(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, Gender);
            int AddRow = preparedStatement.executeUpdate();
            if(AddRow > 0){
                System.out.println("Patient Add Successfully: ");
            }else {
                System.out.println("Failed to Add Patient: ");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // view the patient section
    public void viewPatient(){
        String Query = "SELECT * FROM patients";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients: ");
            // print the data
            System.out.println("------------+-------------------------------+----------+------------+");
            System.out.println(" Patient id | Name                           | Age     | Gender      |");
            System.out.println("------------+--------------------------------+----------+------------+");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("|%-12s|%-32s|%-9s|%-13s|\n",id,name,age,gender);
                System.out.println("------------+--------------------------------+----------+------------+");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    // Check patient by id
    public boolean getPatientById(int id){
        String Query = "SELECT * FROM patients WHERE id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else {
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
