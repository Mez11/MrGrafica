package mx.ipn.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import mx.ipn.web.dao.AlumnoDAO;
import mx.ipn.web.dao.EscuelaDAO;
import mx.ipn.web.model.Alumno;
import mx.ipn.web.model.Escuela;

/**
 * Servlet implementation class TablaAgregar
 */
@WebServlet("/TablaAgregar")
@MultipartConfig
public class TablaAgregar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TablaAgregar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		EscuelaDAO escuelaDAO = new EscuelaDAO();
		escuelaDAO.inicializarConexion();
		
		List<Escuela> list = escuelaDAO.getEscuelas();
	    response.setContentType("text/html");
	    
	    out.println( "<!DOCTYPE HTML>" );
	    out.println( "<html>" );
	    out.println( "<head>" );
	    out.println( "<title>Agregar alumno @.@</title>" );
	    out.println( "</head>" );
	    out.println( "<body>" );
	    out.println("<form method= 'post' action='TablaAgregar' enctype='multipart/form-data' >");
	    out.println("Escuela: <select name='idEscuela' >");//Identificador de select
	    
	    for( Escuela escuela : list ){
	    	//out.println("<option value='${idEscuela}' > ${nombreEscuela}</option>");
	    	out.println("<option value ='" + escuela.getId() +"'>" + escuela.getNombre() + "</option></br>");
	    }
	    
	    out.println("</select><br/>");
		out.println("Nombre <input type='text' name='Nombre'><br/>");
		out.println("Apellido<input type='text' name='Apellido' ><br/>");
		out.println("Foto<input type='file' name='Foto' value='foto'><br/>");
		out.println("<input type='submit' value='Guardar'>");
		out.println("</form>");
		out.println( "</body> ");
		out.println( "</html>" );
		
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		Alumno alumno = getAlumnoInfo( request );
		AlumnoDAO alumnoDAO =new AlumnoDAO( );
		
		
		alumnoDAO.inicializarConexion();
		
		alumnoDAO.create(alumno);
		
		response.sendRedirect("TablaFormulario");//Redireciona a usuario
	}
	
	/**
	 * Metodo para obtener la informacion del alumno enviada por el formulario.
	 * @param request Objeto con la peticion
	 * @return Un objeto de tipo "Alumno" con la informacion del formulario.
	 * @throws ServletException 
	 * @throws IOException 
	 */
	private Alumno getAlumnoInfo( HttpServletRequest request ) throws IOException, ServletException{
		Alumno alumno = new Alumno();
		//ENCASULAR LO DE FORMULARIO EN EL BEAN 
		
		alumno.setNombre(request.getParameter("Nombre"));
		alumno.setApellido(request.getParameter("Apellido"));
		alumno.setFoto( getFile( request ) );
		
		alumno.setEscuela( new Escuela( Integer.parseInt(request.getParameter("idEscuela") ) ) );
		
		return alumno;
	}

	/**
	 * Obtener el archivo del formulario y almacenarlo
	 * en un arreglo de bytes
	 */
	private byte[] getFile( HttpServletRequest request ) throws IOException, ServletException{
		byte [] file = null;
		Part filePart = null;
		InputStream inputStream = null;
		
		//Obtener la "parte" de archivo del formulario
		filePart = request.getPart( "Foto" );
		
		if( filePart.getSize() > Integer.MAX_VALUE ){
			System.err.println( "El tamaño del archvio es mayor al soportado" );
			return null;
		}
		//obtener el flujo de datos asociado a la "parte"
		inputStream = filePart.getInputStream( );
		//inicializar el arreglo de bytes
		file = new byte[ (int)filePart.getSize() ];
		//Leer el flujo de entrada almacenado en el inputStream, y almacenarlo
		//en el arreglo de bytes file.
		inputStream.read( file );
		
		return file;
	}

}
