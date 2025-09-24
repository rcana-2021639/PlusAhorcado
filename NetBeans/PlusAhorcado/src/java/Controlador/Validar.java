package Controlador;

import com.ahorcado.modelo.Usuarios;
import com.ahorcado.modelo.UsuariosDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author informatica
 */
@WebServlet("/Validar")
public class Validar extends HttpServlet {

    UsuariosDAO usuariosDAO = new UsuariosDAO();
    Usuarios usuarios = new Usuarios();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Controlador?menu=Index").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("Ingresar".equalsIgnoreCase(accion)) {
            String email = request.getParameter("txtCorreo");
            String pass = request.getParameter("txtPassword");

            usuarios = usuariosDAO.validar(email, pass);

            if (usuarios != null) {
                HttpSession session = request.getSession();
                session.setAttribute("codigoUsuario", usuarios.getCodigoUsuario());
                session.setAttribute("nombreUsuario", usuarios.getNombreUsuario());
                response.sendRedirect("Controlador?menu=Principal");
            } else {
                // Redirigir con parámetro de error
                response.sendRedirect("index.jsp?error=invalid");
            }
        } else {
            response.sendRedirect("index.jsp");
        }
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
