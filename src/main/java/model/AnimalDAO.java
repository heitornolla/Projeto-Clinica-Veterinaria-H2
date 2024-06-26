package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnimalDAO extends DAO {
    private static AnimalDAO instance;
    
    private AnimalDAO(){
        getConnection();
        createTable();
    }
    
    public static AnimalDAO getInstance(){
        return (instance==null?(instance = new AnimalDAO()):instance);
    }
    
    public Animal create(String nome, int anoNasc, String sexo, int idEspecie, int idCliente){
        try{
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO animal (nome, anoNasc, sexo, id_especie, id_cliente) VALUES (?,?,?,?,?)");
            stmt.setString(1,nome);
            stmt.setInt(2, anoNasc);
            stmt.setString(3,sexo);
            stmt.setInt(4,idEspecie);
            stmt.setInt(5, idCliente);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("animal","id"));
    }
    
    private Animal buildObject(ResultSet rs){
        Animal animal = null;
        try{
            animal = new Animal(rs.getInt("id"), rs.getString("nome"), rs.getInt("anoNasc"), rs.getString("sexo"), rs.getInt("id_especie"),rs.getInt("id_cliente"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return animal;
    }
    
    public List retrieve(String query){
        List<Animal> animais = new ArrayList();
        ResultSet rs = getResultSet(query);
        try{
            while(rs.next()) {
                animais.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return animais;
    }
    
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM animal");
    }
    
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM animal WHERE id = " + lastId("animal", "id"));
    }
    
    public Animal retrieveById(int id){
        List<Animal> animais = this.retrieve("SELECT * FROM animal WHERE id = " + id);
        return (animais.isEmpty() ? null : animais.get(0));
    }
    
    public List retrieveBySimilarName(String nome){
        return this.retrieve("SELECT * FROM animal WHERE nome LIKE '%" + nome + "%'");
    }
    
    public List retrieveByClientId(int id){
        return this.retrieve("SELECT * FROM animal WHERE id_cliente = " + id + "");
    }
    
    public List retrieveBySimilarSpecieName(String name){
        return this.retrieve("SELECT a.*, e.nome AS \"especie\" FROM animal a INNER JOIN especie e ON (a.id_especie = e.id) WHERE e.nome LIKE '%" + name + "%'");
    }
    
    public void update(Animal animal){
        try{
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE animal SET nome=?, anoNasc=?, sexo=?, id_especie=?, id_cliente=? WHERE id=?");
            stmt.setString(1, animal.getNome());
            stmt.setInt(2, animal.getAnoNasc());
            stmt.setString(3, animal.getSexo());
            stmt.setInt(4, animal.getIdEspecie());
            stmt.setInt(5, animal.getIdCliente());
            stmt.setInt(6, animal.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    public void delete(Animal animal){
        PreparedStatement stmt;
        try{
           stmt = DAO.getConnection().prepareStatement("DELETE FROM animal WHERE id = ?");
            stmt.setInt(1, animal.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}