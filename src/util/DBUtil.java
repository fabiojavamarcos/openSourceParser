package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtil 
{
	private static Connection con;
	
	public static Connection getConnection(String dbConnect, String user, String pswd)
	{
		// Se a conexão não existe ainda
		if (con == null)
		{
			try
			{
				// Carrega a classe do driver JDBC   
				Class.forName("org.postgresql.Driver");   
		    
				// Cria uma conexão através do Driver   
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbConnect,user,pswd);
			}
			catch(ClassNotFoundException e)
			{
				System.out.println("O driver não foi importado");
				e.printStackTrace();
			}
			catch(SQLException e)
			{
				System.out.println("Erro de conexão com o banco");
				e.printStackTrace();
			}
		}
		
		// Retorna a conexão
		return con;
	}
}