
package Servlets;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author l11m04
 */
public class ActualizarServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
try {
           PrintWriter out = response.getWriter();
           String idvehiculo = request.getParameter("id_ve");
           String nombre = request.getParameter("nombre");
           double precio = Double.parseDouble(request.getParameter("precio"));
           String color = request.getParameter("color");
           System.err.println("idvehiculo: "+idvehiculo);
           System.err.println("precio: "+precio);
           //
           Class.forName("com.mysql.cj.jdbc.Driver");
           String url = "jdbc:mysql://localhost/tesla?user=root&password=mysqladmin";
           Connection connect = DriverManager.getConnection(url);
           String query = "UPDATE vehiculo SET marca = ?, precio = ?, color=? WHERE idvehiculo = ?";
           PreparedStatement ps = connect.prepareStatement(query);
           ps.setString(1,nombre);
           ps.setDouble(2, precio);
           ps.setString(3,color);
           ps.setInt(4, Integer.parseInt(idvehiculo));
           ps.executeUpdate();
           
           JsonObject gson = new JsonObject();
           gson.addProperty("mensaje", "VEHICULO ACTUALIZADO ");
           out.print(gson.toString());
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
