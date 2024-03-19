package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultaDAO extends DAO {
    private static ConsultaDAO instance;
    
    private ConsultaDAO(){
        getConnection();
        createTable();
    }
    
    public static ConsultaDAO getInstance(){
        return (instance==null?(instance = new ConsultaDAO()):instance);
    }
    
    public Consulta create(Calendar data, String horario, String comentario, int idAnimal, int idVeterinario, int terminado){
        try{
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO consulta (data, horario, comentario, id_animal, id_vet, terminado) VALUES (?,?,?,?,?,?)");
            stmt.setDate(1, new java.sql.Date(data.getTimeInMillis()));
            stmt.setString(2, horario);
            stmt.setString(3,comentario);
            stmt.setInt(4,idAnimal);
            stmt.setInt(5, idVeterinario);
            //stmt.setInt(6, idTratamento);
            stmt.setInt(6, terminado);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("consulta","id"));
    }
    
    private Consulta buildObject(ResultSet rs){
        Consulta consulta = null;
        try{            
            Calendar cal = Calendar.getInstance();
            cal.setTime(rs.getDate("data"));
            consulta = new Consulta(rs.getInt("id"), cal, rs.getString("horario"), rs.getString("comentario"), 
                    rs.getInt("id_animal"), rs.getInt("id_vet"), rs.getInt("terminado"));/*, 
                    rs.getString("animal"), rs.getString("vet"));*/
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consulta;
    }
    
    public List retrieve(String query){
        List<Consulta> consultas = new ArrayList();
        ResultSet rs = getResultSet(query);
        try{
            while(rs.next()) {
                consultas.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consultas;
    }
    
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM consulta");
    }
    
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM consulta WHERE id = " + lastId("consulta", "id"));
    }
    
    public Consulta retrieveById(int id){
        List<Consulta> consultas = this.retrieve("SELECT * FROM consulta WHERE id = " + id);
        return (consultas.isEmpty() ? null : consultas.get(0));
    }
    
    public List retrieveByAnimalId(int id) {
        return this.retrieve("SELECT c.*, a.nome AS \"animal\", v.nome AS \"vet\" FROM "
                + "consulta c INNER JOIN animal a ON (c.id_animal = a.id) "
                + "INNER JOIN vet v ON (c.id_vet = v.id) WHERE a.id = " + id);
    }
    
    public List retrieveOngoing() {
        return this.retrieve("SELECT * FROM consulta WHERE terminado = 0");
    }
    
    public List retrieveOngoingByAnimalId(int id) {
        return this.retrieve("SELECT * FROM consulta WHERE terminado = 0 AND "
                + "consulta.id_animal = " + id);
    }
    
    public List retrieveByDate(String date) {
        String query = "SELECT c.*, a.nome AS \"animal\", v.nome AS \"vet\" FROM consulta c "
                + "INNER JOIN animal a ON (c.id_animal = a.id) INNER JOIN vet v "
                + "ON (c.id_vet = v.id) WHERE data=?";
        ResultSet rs = null;
        Calendar c = Calendar.getInstance();     
        try {
            c.setTime(dateFormat.parse(date));
        } catch (ParseException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setDate(1, new java.sql.Date(c.getTimeInMillis()));
            rs = preparedStatement.executeQuery();
        } catch(SQLException e){
            System.err.println("Exception: " + e.getMessage());            
        }
        List<Consulta> consultas = new ArrayList();
        try{
            while(rs.next()) {
                consultas.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consultas;
    }
    
    public List retrieveByVetName(String name) {
        return this.retrieve("SELECT * FROM consulta INNER JOIN vet ON (consulta.id_vet = vet.id) "
                + "WHERE vet.nome LIKE '%" + name + "%'");
    }
    
    public List retrieveByOngoingAndVetId(int id) {
        return this.retrieve("SELECT * FROM consulta WHERE id_vet = " + id + " AND terminado = 0");
    }
    
    public List retrieveByOwnerName(String name) {
    return this.retrieve("SELECT * FROM consulta " +
            "INNER JOIN animal ON (consulta.id_animal = animal.id) " +
            "INNER JOIN cliente ON (animal.id_cliente = cliente.id) " + 
            "WHERE cliente.nome LIKE '%" + name + "%'");
    }
    
    public List retrieveByOwnerId(int id) {
    return this.retrieve("SELECT * FROM consulta " +
            "INNER JOIN animal ON (consulta.id_animal = animal.id) " +
            "INNER JOIN cliente ON (animal.id_cliente = cliente.id) " + 
            "WHERE cliente.id = " + id);
    }
    
    
    public List retrieveAppointmentsByAnimalName(String name) {
        return this.retrieve("SELECT * FROM consulta INNER JOIN animal ON (consulta.id_animal = animal.id) "
                + "WHERE animal.nome LIKE '%" + name + "%'");
    }
    
    public void update(Consulta consulta){
        try{
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE consulta SET data=?, horario=?, "
                    + "comentario=?, id_animal=?, id_vet=?, terminado=? WHERE id=?");
            stmt.setDate(1, new java.sql.Date(consulta.getCalendarDate().getTimeInMillis()));
            stmt.setString(2, consulta.getHorario());
            stmt.setString(3, consulta.getComentario());
            stmt.setInt(4, consulta.getIdAnimal());
            stmt.setInt(5, consulta.getIdVeterinario());
            //stmt.setInt(6, consulta.getIdTratamento());
            stmt.setInt(6, consulta.getTerminado());
            stmt.setInt(7, consulta.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    public void delete(Consulta consulta){
        PreparedStatement stmt;
        try{
           stmt = DAO.getConnection().prepareStatement("DELETE FROM consulta WHERE id = ?");
            stmt.setInt(1, consulta.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
