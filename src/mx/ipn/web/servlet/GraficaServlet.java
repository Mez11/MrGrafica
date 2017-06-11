package mx.ipn.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import mx.ipn.web.dao.DatosDAO;
import mx.ipn.web.model.Datos;

/**
 * Servlet implementation class GraficaServlet
 */
@WebServlet("/GraficaServlet")
public class GraficaServlet extends HttpServlet {
	private static final String RUTA_ARCHIVO  = "/home/pma/Desktop/grafica.png";
	private static final long serialVersionUID = 1L;
	
	private DefaultPieDataset getGraficaAlumnos(){
		DefaultPieDataset pie = new DefaultPieDataset();
		DatosDAO  dao = new DatosDAO();
			try{
				List<Datos> datos = dao.grafica();
				for(int i=0; i<datos.size();i++){
					Datos losDatos = datos.get(i);
					pie.setValue(losDatos.getNombreEscuela(), 
							losDatos.getAlumnosTotales());
				}
			}catch (Exception e){
				e.printStackTrace( );
			}
		return pie;
	}
	
	private void generarGrafica( ) {
		JFreeChart chart = ChartFactory.createPieChart3D("Alumnos por Escuela", getGraficaAlumnos() );
		
		try {
			ChartUtilities.saveChartAsPNG( new File( RUTA_ARCHIVO ), chart,700,400);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//"main"
		generarGrafica();
		byte[] file = getFile( );
		//PrintWriter out = response.getWriter( ) <- Abrir canal de comunicacion con cliente en modo texto
		OutputStream os = response.getOutputStream();//<- abrir canal de comunicacion con cliente en modo bytes
		
		response.setContentType("image/png");
		os.write(file);
		
	}//fin doGet
	
	private byte [] getFile( ) throws IOException{
		File file = new File( RUTA_ARCHIVO );
		FileInputStream fis = new FileInputStream( file );
		byte[] buffer = new byte[(int)file.length()];
		fis.read( buffer );
		fis.close();
		
		return buffer;
		
	}
}///fin clase
