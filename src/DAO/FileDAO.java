package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.API;
import model.UnitComp;
import util.DBUtil;

public class FileDAO {
	private static FileDAO instancia;
	private String dbcon;
	private String user;
	private String pswd;
	
	private FileDAO(String dbcon, String user, String pswd){
		this.dbcon = dbcon;
		this.user = user;
		this.pswd = pswd;
	}
	
	public static FileDAO getInstancia(String dbcon, String user, String pswd){
		if (instancia == null){
			instancia = new FileDAO(dbcon, user, pswd);
		}
		return instancia;
	}
	
	public boolean buscaFile(String file){
		Connection con = DBUtil.getConnection(dbcon, user, pswd);
		boolean achou = false;
		try {
			Statement comandoSql = con.createStatement();
			
			String sql = "select * from file where file_name = '" + file + "'";
			System.out.println(sql);
			
			ResultSet rs = comandoSql.executeQuery(sql);
			
			if(rs.next()){
				achou = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return achou;
		
	}
	
	public String insertFile(UnitComp uc, String project){
		Connection con = DBUtil.getConnection(dbcon, user, pswd);
		
		try {
			Statement comandoSql = con.createStatement();
			
			String sql = "insert into file values ('"+uc.getName()+"','"+uc.getDir()+uc.getName()+"','"+project+"')";
			System.out.println(sql);
			
			comandoSql.executeUpdate(sql);
			return sql;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Duplicate File"+ uc.getName()+" - "+uc.getDir()+" - "+project+ e.getMessage();
		}
		
	}
	public String insertAPI(API api){
		Connection con = DBUtil.getConnection(dbcon, user, pswd);
		
		try {
			Statement comandoSql = con.createStatement();
			
			String sql = "insert into \"API\" values ('"+api.getFullName()+"','"+api.getClassName()+"')";
			System.out.println(sql);
			
			comandoSql.executeUpdate(sql);
			return sql;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			System.out.println("API duplicate:"+api.getFullName()+"- "+ e.getMessage());
			return "API duplicate:"+api.getFullName()+"- "+ e.getMessage();
		}
		
	}
	public String insertFileAPI(UnitComp uc, API api){
		Connection con = DBUtil.getConnection(dbcon, user, pswd);
		
		try {
			Statement comandoSql = con.createStatement();
			
			String sql = "insert into \"file_API\" values ('"+uc.getDir()+uc.getName()+"','"+api.getFullName()+"')";
			System.out.println(sql);
			
			comandoSql.executeUpdate(sql);
			return sql;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Duplicate File_API "+uc.getName()+"','"+api.getFullName()+e.getMessage();
		}
		
	}
	public String updateFileAPICount(UnitComp uc, API api, int count){
		Connection con = DBUtil.getConnection(dbcon, user, pswd);
		
		try {
			Statement comandoSql = con.createStatement();
			
			String sql = "update \"file_API\" set count = "+count+" where api_name = '"+api.getFullName()+"' and file_name = '"+uc.getDir()+uc.getName()+"'";
			System.out.println(sql);
			
			comandoSql.executeUpdate(sql);
			return sql;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error Updating count: "+ count + " - " + api.getFullName()+ " - " + uc.getName();
		}
		
	}

	public String insertProject(String project, String dirTrab, String format) {
		// TODO Auto-generated method stub
		Connection con = DBUtil.getConnection(dbcon, user, pswd);
		
		try {
			Statement comandoSql = con.createStatement();
			
			String sql = "insert into project values ('"+project+"','"+dirTrab+"','"+format+"')";
			System.out.println(sql);
			
			comandoSql.executeUpdate(sql);
			return sql;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Duplicate project "+project+" - "+dirTrab+" - "+format+e.getMessage();
		}
	}

	
}

