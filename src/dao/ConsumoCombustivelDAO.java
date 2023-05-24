package dao;

import model.ConsumoCombustivel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import factory.ConnectionFactory;

public class ConsumoCombustivelDAO {
    public void inserir(ConsumoCombustivel consumoCombustivel) {
            String sql = "INSERT INTO carros (capacidade, portador, combustivelDisponivel) VALUES (?, ?, ?)";
            
        try (Connection connection = ConnectionFactory.createConnectionToMySQL();
        	PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql)) {
        	
        	statement.setDouble(1, consumoCombustivel.getCapacidade());
        	statement.setString(2, consumoCombustivel.getPortador());
        	statement.setDouble(3, consumoCombustivel.contar());

            statement.execute();
            System.out.println("Carro inserido com sucesso!");
        } catch (ClassNotFoundException e) {
        	System.out.println("Driver de banco de dados não encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
        	System.out.println("Erro ao conectar ao banco de dados.");
            e.printStackTrace();
        }
    }
    
    public List<ConsumoCombustivel> read() {
    	String sql = "SELECT * FROM carros";
    	List<ConsumoCombustivel> lista = new ArrayList<>();
    	
    	try (Connection connection = ConnectionFactory.createConnectionToMySQL();
    		PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
    		ResultSet rset = statement.executeQuery()) {
    		
    		while (rset.next()) {
    			ConsumoCombustivel consumo = new ConsumoCombustivel();
    			
    			consumo.setNumeroDeSerie(rset.getInt("numeroDeSerie"));
    			consumo.setCapacidade(rset.getDouble("capacidade"));
    			consumo.setPortador(rset.getString("portador"));
    			consumo.setCombustivelDisponivel(rset.getDouble("combustivelDisponivel"));
    			
    			lista.add(consumo);
			}
    		System.out.println("Busca realizada com sucesso!");
    	} catch (ClassNotFoundException e) {
        	System.out.println("Driver de banco de dados não encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
        	System.out.println("Erro ao conectar ao banco de dados.");
            e.printStackTrace();
        }
    	return lista;
    }
    
    public void update(ConsumoCombustivel consumoCombustivel) {
    	String sql = "UPDATE carros SET capacidade = ?, portador = ?, combustivelDisponivel = ? WHERE numeroDeSerie = ?";

        
        try (Connection connection = ConnectionFactory.createConnectionToMySQL();
        	PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);) {
        	
        	statement.setDouble(1, consumoCombustivel.getCapacidade());
        	statement.setString(2, consumoCombustivel.getPortador());
        	statement.setDouble(3, consumoCombustivel.contar());
        	statement.setInt(4, consumoCombustivel.getNumeroDeSerie());
        	
        	statement.execute();
        	System.out.println("Carro editado com sucesso!");
        } catch (ClassNotFoundException e) {
        	System.out.println("Driver de banco de dados não encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
        	System.out.println("Erro ao conectar ao banco de dados.");
            e.printStackTrace();
        }
    }
    
    public void delete(int numeroDeSerie) {
    	String sql = "DELETE FROM carros WHERE numeroDeSerie = ?";
        
        try (Connection connection = ConnectionFactory.createConnectionToMySQL();
        	PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql)) {
        	
        	statement.setInt(1, numeroDeSerie);
        	
        	statement.execute();
        	System.out.println("Carro deletado com sucesso!");
        } catch (ClassNotFoundException e) {
        	System.out.println("Driver de banco de dados não encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
        	System.out.println("Erro ao conectar ao banco de dados.");
            e.printStackTrace();
        }
    }
}
