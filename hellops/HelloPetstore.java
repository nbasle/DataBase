
import java.sql.*;

public class HelloPetstore {

    public static void main(String[] args) {
        // Charge la classe du driver JDBC
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (args[0].equals("ecrire")) {
            ecrire();
        } else {
            lire();
        }
    }

    private static void ecrire() {
        Connection connection = null;
        Statement statement = null;

        try {
            // Récupère une connexion à la base de données
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/petstore", "root", "");
            statement = connection.createStatement();

            // Insert une ligne en base
            statement.executeUpdate("INSERT INTO HELLO_PETSTORE (cle, valeur) VALUES ('Hello', 'PetStore!')");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Ferme la connexion
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void lire() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Récupère une connection à la base de données
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/petstore", "root", "");
            statement = connection.createStatement();

            // Récupère une ligne de la base
            resultSet = statement.executeQuery("SELECT valeur FROM HELLO_PETSTORE WHERE cle = 'Hello' ");
            if (!resultSet.next())
                System.out.println("Non trouvé !!!");

            // Affiche le résultat
            System.out.println("Hello " + resultSet.getString(1));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Ferme la connexion
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
