package pt.slowly.metas.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pt.slowly.metas.Main;
import pt.slowly.metas.manager.Meta;

public class SQLite {
	
	private Main plugin;
	
	public SQLite(Main plugin) {
		this.plugin = plugin;
	}
	
	private Connection connection() {

		String url = "jdbc:sqlite:" + plugin.getDataFolder() + "/database.db";
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	
	public void connect() {
        Connection conn = null;
        try {
        	
            String url = "jdbc:sqlite:" + plugin.getDataFolder() + "/database.db";
            conn = DriverManager.getConnection(url);
            createNewTable();
            
            System.out.println("A conexão com o SQLite foi efetuada com sucesso!");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
	
	public void createNewTable() {

        String url = "jdbc:sqlite:" + plugin.getDataFolder() + "/database.db";
        String sql = "CREATE TABLE IF NOT EXISTS METAS(jogador,metatotal,metapaga,faction_tag)";
        
        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void saveJogador(int metatotal, int metapaga, String faction_tag, String jogador) {
        String sql = "UPDATE METAS SET metatotal = ? , "
                + "metapaga = ? "
        		+ "faction_tag = ? "
                + "WHERE jogador = ?";

        try (Connection conn = this.connection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, metatotal);
            pstmt.setInt(2, metapaga);
            pstmt.setString(3, faction_tag);
            pstmt.setString(4, jogador);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void loadJogador(String jogador){
        String sql = "SELECT * FROM METAS WHERE JOGADOR = ?";
        
        try (Connection conn = this.connection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            	 stmt.setString(1, jogador);
            	 ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Meta meta = new Meta(rs.getInt("metatotal"), rs.getInt("metapaga"), rs.getString("faction_tag"));
                Main.metas.put(rs.getString("jogador"), meta);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public boolean hasJogador(String jogador) {
		
		String sql = "SELECT * FROM METAS WHERE JOGADOR = ?";
		
		try (Connection conn = this.connection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            	 stmt.setString(1, jogador);
	            	 ResultSet rs = stmt.executeQuery();
	            
	            while (rs.next()) {
	                return true;
	            }
	            return false;
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            return false;
	        }
		
	}

}
