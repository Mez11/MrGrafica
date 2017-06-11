package mx.ipn.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.ipn.web.dao.AlumnoDAO;
import mx.ipn.web.model.Alumno;

/**
 * Servlet implementation class TablaFormulario
 */
@WebServlet("/TablaFormulario")
public class TablaFormulario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TablaFormulario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#oGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();//FLujo para poder escribir caracteres
		response.setContentType("text/html");
	
		AlumnoDAO dao =new AlumnoDAO();
		dao.inicializarConexion();
		List<Alumno> alumnos = dao.getAlumnos();
		
		out.println("<html>");
		out.println("<head>");
		out.println("</head>");
		out.println("<body>");
		out.println("<a href ='TablaAgregar'>");
		out.println("<button>Agregar</button>");
		out.println("</a>");
		out.println("<table border='1'>");
		out.println("<tr>");
		out.println("<td>");
		out.println("ID");
		out.println("</td>");
		out.println("<td>");
		out.println("Nombre");
		out.println("</td>");
		out.println("<td>");
		out.println("Apellido");
		out.println("</td>");
		out.println("<td>");
		out.println("Foto");
		out.println("</td>");
		out.println("</tr>");
		
		for( Alumno alumno : alumnos ){
			out.println( "<tr>" );
			
			out.println("<td>");
			out.println(alumno.getId());
			out.println("</td>");
			
			out.println( "<td>" );
			out.println( alumno.getNombre( ) );
			out.println( "</td>" );
			
			out.println("<td>");
			out.println(alumno.getApellido());
			out.println("</td>");
			
			out.println("<td>");
			if( alumno.getFoto() == null ){
				//este codigo se ejecuta si no se encontro la foto 
				out.println("No se encontro foto");
			}
			else{
				//este codigo se ejecuta cuando si se encontro la foto
				out.println("<img src='PhotoServlet?id="+alumno.getId()+"'/>");
			}
			out.println("</td>");
			
			out.println( "</tr>" );
		}
		out.println("</table>");
		out.println("</body>");
		out.println("</html>");
		
	
	
	
	}
}
