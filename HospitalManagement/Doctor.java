package HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Doctor {
    private Connection connection;
    public Doctor(Connection connection){
        this.connection=connection;
    }
    // view the Doctor section
    public void viewDoctor(){
        String Query = "SELECT * FROM doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(" Doctor: ");
            System.out.println("|------------+-------------------------------+------------------------+");
            System.out.println("| Doctor id  | Name                           | Specialization        |");
            System.out.println("| ------------+--------------------------------+-----------------------+");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("| %-12s| %-32s| %-23s |\n",id,name,specialization);
                System.out.println("------------+--------------------------------+-----------------------+");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    // Check Doctor by id
    public boolean getDoctorById(int id){
        String Query = "SELECT * FROM doctors WHERE id = ?";
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
