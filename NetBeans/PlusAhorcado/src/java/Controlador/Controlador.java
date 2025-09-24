package Controlador;

import com.ahorcado.modelo.PalabrasDAO;
import com.ahorcado.modelo.Palabras;
import com.ahorcado.modelo.UsuariosDAO;
import com.ahorcado.modelo.Usuarios;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Controlador", urlPatterns = {"/Controlador"})
public class Controlador extends HttpServlet {

    Usuarios usuarios = new Usuarios();
    UsuariosDAO usuariosDao = new UsuariosDAO();
    Palabras palabras = new Palabras();
    PalabrasDAO palabrasDao = new PalabrasDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String menu = request.getParameter("menu");
        String accion = request.getParameter("accion");

        if (menu.equals("Principal")) {
            request.getRequestDispatcher("Index/Principal.jsp").forward(request, response);
        } else if (menu.equals("Index")) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else if (menu.equals("Usuarios")) {
            switch (accion) {
                case "Listar":
                    List listaUsuarios = usuariosDao.listar();
                    request.setAttribute("usuarios", listaUsuarios);
                    break;
                case "Agregar":
                    String nombreUsuario = request.getParameter("txtNombreUsuario");
                    String apellidoUsuario = request.getParameter("txtApellidoUsuario");
                    String correoUsuario = request.getParameter("txtCorreoUsuario");
                    String contraseñaUsuario = request.getParameter("txtPassword");
                    
                    usuarios.setNombreUsuario(nombreUsuario);
                    usuarios.setApellidoUsuario(apellidoUsuario);
                    usuarios.setCorreoUsuario(correoUsuario);
                    usuarios.setContraseñaUsuario(contraseñaUsuario);
                    
                    usuariosDao.agregar(usuarios);
                    request.getRequestDispatcher("Controlador?menu=Usuarios&accion=Listar").forward(request, response);
                    break;
                case "Editar":
                    int idEditar = Integer.parseInt(request.getParameter("id"));
                    Usuarios usuarioEditar = usuariosDao.buscar(idEditar);
                    request.setAttribute("usuario", usuarioEditar);
                    request.setAttribute("usuarios", usuariosDao.listar());
                    break;
                default:
                    request.getRequestDispatcher("/Index/VistaUsuarios.jsp").forward(request, response);
            }
            request.getRequestDispatcher("/Index/VistaUsuarios.jsp").forward(request, response);
            
        } else if (menu.equals("Palabras")) {
            switch (accion) {
                case "Listar":
                    List listaPalabras = palabrasDao.listar();
                    request.setAttribute("palabras", listaPalabras);
                    break;

                case "Agregar":
                    String palabra = request.getParameter("txtPalabra");
                    String pista = request.getParameter("txtPista");
                    String categoria = request.getParameter("txtCategoria");
                    
                    palabras.setPalabra(palabra);
                    palabras.setPista(pista);
                    palabras.setCategoria(categoria);
                    
                    palabrasDao.agregar(palabras);
                    request.getRequestDispatcher("Controlador?menu=Palabras&accion=Listar").forward(request, response);
                    break;
                case "ObtenerRandom":
                    Palabras palabraRandom = palabrasDao.obtenerPalabraRandom();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    String jsonResponse = String.format("{\"palabra\":\"%s\",\"pista\":\"%s\"}", 
                        palabraRandom.getPalabra(), 
                        palabraRandom.getPista());
                    response.getWriter().write(jsonResponse);
                    return; // Importante: retornar aquí para no hacer forward
                case "Editar":
                    int idEditar = Integer.parseInt(request.getParameter("id"));
                    Palabras palabraEditar = palabrasDao.buscar(idEditar);
                    request.setAttribute("palabra", palabraEditar);
                    request.setAttribute("palabras", palabrasDao.listar());
                    break;
                default:
                    request.getRequestDispatcher("/Index/VistaPalabras.jsp").forward(request, response);
            }
            request.getRequestDispatcher("/Index/VistaPalabras.jsp").forward(request, response);
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
