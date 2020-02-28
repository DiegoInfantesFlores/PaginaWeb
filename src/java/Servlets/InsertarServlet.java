
package Servlets;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
public class InsertarServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
           PrintWriter out = response.getWriter();
           String nombre = request.getParameter("nombre");
           double precio = Double.parseDouble(request.getParameter("precio"));
           String color = request.getParameter("color");
           //
           Class.forName("com.mysql.cj.jdbc.Driver");
           String url = "jdbc:mysql://localhost/tesla?user=root&password=mysqladmin";
           Connection connect = DriverManager.getConnection(url);
           
           String query = "SELECT MAX(idvehiculo) + 1 AS new_id FROM vehiculo";
           Statement statement = connect.createStatement();
           ResultSet resultSet = statement.executeQuery(query);
           
           int idProd = 0;
           while(resultSet.next()) {
               idProd = resultSet.getInt("new_id");
           }
           if(idProd == 0) {
               idProd = 1;
           }
           
           query = "INSERT INTO vehiculo VALUES (?,?,?,?)";
           PreparedStatement ps = connect.prepareStatement(query);
           
           
           ps.setInt(1, idProd);
           ps.setString(2,nombre);
           ps.setDouble(3, precio);
           ps.setString(4,color);
           ps.executeUpdate();
           
           JsonObject gson = new JsonObject();
           gson.addProperty("mensaje", "Vehiculo registrado.");
           gson.addProperty("id_prod", idProd);
           gson.addProperty("saludo", "CHAU");
           out.print(gson.toString()); // Enviar rpta al JS
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
