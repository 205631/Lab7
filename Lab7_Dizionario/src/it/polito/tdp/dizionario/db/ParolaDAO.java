package it.polito.tdp.dizionario.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParolaDAO {
	
	
	public List<String> trovaParole(int num){
		
		List<String> l=new ArrayList();
		
		Connection conn= DBConnect.getConnection();
		
		String sql="select nome from parola where LENGTH(nome)=?";
		
		PreparedStatement st;
		
		try {
			st=conn.prepareStatement(sql);
			
			st.setInt(1,num);
			
			ResultSet rs=st.executeQuery();
			while(rs.next()==true){
				l.add(rs.getString("nome"));
			}
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return l;
	}
	
	public List<String> checkParole(List<String> s){
		
		List<String> l=new ArrayList();
		
		
		Connection conn=DBConnect.getConnection();
		
		String sql="select nome from parola where nome LIKE ?";
		
		PreparedStatement st;
		try {
				st=conn.prepareStatement(sql);
				for(int i=0;i<s.size();i++){
					st.setString(1, s.get(i));
					ResultSet rs=st.executeQuery();
					while(rs.next()){
						l.add(rs.getString("nome"));
					}
				}
				
				conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return l;
	}
	

}
