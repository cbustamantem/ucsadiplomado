package py.edu.ucsa.ejercicio07;

public class Note 
{
	private String nota;
	
	public static final Note
	DO = new Note("Nota DO"),
	RE = new Note("Nota RE"),
	MI = new Note("Nota MI");
	public Note(String p)
	{
		this.nota = p;
	}
	public String toString()
	{
		return this.nota;
	}
	
}
