package py.edu.ucsa.ejercicio11;

import java.util.Date;

public class Test 
{
	public static void main( String ... args)
	{
		CorreoParaguayo correo = new CorreoParaguayo();							
		
		//Email
		Email email = new Email();
		email.setAsunto("asunto");
		email.setCuerpo("Cuerpo");		
		email.setDestinatario("Destinatario");
		email.setFecha(new Date());
		correo.leerCosas(email);
		correo.enviarCosas(email);
		
		//Mensaje
		MensajeTexto mensajeTexto = new MensajeTexto();
		mensajeTexto.setCantMaxCaracteres(10);
		mensajeTexto.setCuerpo("Cuerpo");
		mensajeTexto.setDestinatario("Destinatario");		
		mensajeTexto.setFecha(new Date());
		correo.leerCosas(mensajeTexto);
		correo.enviarCosas(mensajeTexto);
		
		//Periodico
		Periodico periodico = new Periodico();
		periodico.setDestinatario("destinatario");
		periodico.setFecha(new Date());
		periodico.setNombre("Nombre");
		correo.hojearCosas(periodico);
		correo.enviarCosas(periodico);
		
		//Paquete
		Paquete paquete = new Paquete();
		paquete.setContenido("Contenido");
		paquete.setDestinatario("destinatario");
		paquete.setPeso(30);		
		correo.enviarCosas(paquete);
	}

}
