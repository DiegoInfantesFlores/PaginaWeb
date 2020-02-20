package Servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author l11m04
 */
public class Consulta extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/tesla?user=root&password=mysqladmin";
            Connection connect = DriverManager.getConnection(url);
            Statement statement = connect.createStatement();
            String query = "SELECT * FROM vehiculo";
            ResultSet resultSet = statement.executeQuery(query);

            JsonArray jarry = new JsonArray();
            JsonObject gson = new JsonObject();

            while (resultSet.next()) {
                int idvehiculo = resultSet.getInt("idvehiculo");
                String marca = resultSet.getString("marca");
                int precio = resultSet.getInt("precio");
                String color = resultSet.getString("color");

                gson = new JsonObject();
                gson.addProperty("idvehiculo", idvehiculo);
                gson.addProperty("marca", marca);
                gson.addProperty("precio", precio);
                gson.addProperty("color", color);

                jarry.add(gson);
            }
            out.print(jarry.toString());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
