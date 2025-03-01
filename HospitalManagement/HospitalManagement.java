package HospitalManagement;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagement {
   private static final String url = "jdbc:mysql://localhost:3306/hospital";
   private static final String username = "root";
   private static final String password = "Root@123";

   public static void main(String[] args) {
      try{
         // load driver
         Class.forName("com.mysql.cj.jdbc.Driver");
      }catch (ClassNotFoundException e){
        e.printStackTrace();
      }
      Scanner scanner = new Scanner(System.in);
      try{
        // connection section
         Connection connection = DriverManager.getConnection(url, username, password);
         // create the object of both class and pass the value
         Patient patient = new Patient(connection, scanner);
         Doctor doctor = new Doctor(connection);
         while (true){
            System.out.println("----HOSPITAL MANAGEMENT SYSTEM -----");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. View Doctors");
            System.out.println("4. Book Appointment");
            System.out.println("5. Exit");
            System.out.println("Enter Your Choice: ");
            int choice = scanner.nextInt();
            switch (choice){
               case 1:
                  // Add patient
                  patient.addPatient();
                  System.out.println();
                  break;
               case 2:
                  // View Patients
                  patient.viewPatient();
                  System.out.println();
                  break;
               case 3:
                  // View Doctors
                  doctor.viewDoctor();
                  System.out.println();
                  break;
               case 4:
                  // Book Appointment
                  bookAppointment(patient,doctor,connection,scanner);
                  System.out.println();
                  break;
               case 5:
                  return;
               default:
                  System.out.println("Enter valid choice !!! ");
            }
         }
      }catch (SQLException e){
         e.printStackTrace();
      }
   }
   // THIS SECTION IS USED TO bookAppointment
   public static void bookAppointment(Patient patient,Doctor doctor, Connection connection,Scanner scanner){
      System.out.print("Enter patient id: ");
      int patientId = scanner.nextInt();
      System.out.print("Enter Doctor Id: ");
      int doctorId = scanner.nextInt();
      System.out.print("Enter appointment Date(YYYY-MM-DD): ");
      String appointmentDate = scanner.next();
      // if statement is  to check the doctor and patient are exit
      if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)){
         if(checkDoctor(doctorId, appointmentDate, connection)){
           String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
           // prepared statement
            try{
               PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
               preparedStatement.setInt(1, patientId);
               preparedStatement.setInt(2,doctorId);
               preparedStatement.setString(3, appointmentDate);
               int rowsAffected = preparedStatement.executeUpdate();
               if(rowsAffected > 0){
                  System.out.println("Appointment Booked!");
               }else {
                  System.out.println("Failed to Book Appointment!");
               }
            }catch (SQLException e){
               e.printStackTrace();
            }
         }else{
            System.out.println("Doctor not available on this date:");
         }
      }else {
         System.out.println("Either doctor or patient does not exits!!!");
      }
   }

   public static boolean checkDoctor(int doctorId, String appointmentDate, Connection connection){
       String Query = "SELECT COUNT(*) FROM appointment WHERE doctor_id = ? AND appointment_Date = ? ";
       try{
          PreparedStatement preparedStatement = connection.prepareStatement(Query);
          preparedStatement.setInt(1, doctorId);
          preparedStatement.setString(2,appointmentDate);
          ResultSet resultSet = preparedStatement.executeQuery();
          if(resultSet.next()){
             int count = resultSet.getInt(1);
             // any appointment on same date return false otherwise return true 
             if(count == 0){
                return true;
             }else {
                return false;
             }
          }

       }catch (SQLException e){
          e.printStackTrace();
       }
       return false;
   }
}
