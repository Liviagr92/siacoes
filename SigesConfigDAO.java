package br.edu.utfpr.dv.siacoes.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import br.edu.utfpr.dv.siacoes.log.UpdateEvent;

public class SigesConfigDAO extends SigMasterDAO<SigesConfig>{

    protected String getSqlFindByDepartment() {
        return "SELECT * FROM sigesconfig WHERE idDepartment = ?";
    }

    @Override
    protected String getSqlSaveInsert() {
        return "INSERT INTO sigesconfig(minimumScore, supervisorPonderosity, companySupervisorPonderosity, " +
                "showgradestostudent, supervisorfilter, supervisorFillJuryForm, maxfilesize, jurytime, idDepartment) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getSqlSaveUpdate() {
        return "UPDATE sigesconfig SET minimumScore=?, supervisorPonderosity=?, companySupervisorPonderosity=?, " +
                "showgradestostudent=?, supervisorfilter=?, supervisorFillJuryForm=?, maxfilesize=?, jurytime=? " +
                "WHERE idDepartment=?";
    }

    protected void ormSave(PreparedStatement statement, SigesConfig config) throws SQLException {
        statement.setDouble(1, config.getMinimumScore());
        statement.setDouble(2, config.getSupervisorPonderosity());
        statement.setDouble(3, config.getCompanySupervisorPonderosity());
        statement.setInt(4, config.isShowGradesToStudent() ? 1 : 0);
        statement.setInt(5, config.getSupervisorFilter().getValue());
        statement.setInt(6, config.isSupervisorFillJuryForm() ? 1 : 0);
        statement.setInt(7, config.getMaxFileSize());
        statement.setInt(8, config.getJuryTime());
        statement.setInt(9, config.getDepartment().getIdDepartment());
    }

    protected int getIdDepartment(SigesConfig obj) {
        return obj.getDepartment().getIdDepartment();
    }

    protected SigesConfig loadObject(ResultSet rs) throws SQLException {
        SigesConfig config = new SigesConfig();

        config.getDepartment().setIdDepartment(rs.getInt("idDepartment"));
        config.setMinimumScore(rs.getDouble("minimumScore"));
        config.setSupervisorPonderosity(rs.getDouble("supervisorPonderosity"));
        config.setCompanySupervisorPonderosity(rs.getDouble("companySupervisorPonderosity"));
        config.setShowGradesToStudent(rs.getInt("showgradestostudent") == 1);
        config.setSupervisorFilter(SupervisorFilter.valueOf(rs.getInt("supervisorfilter")));
        config.setSupervisorFillJuryForm(rs.getInt("supervisorFillJuryForm") == 1);
        config.setMaxFileSize(rs.getInt("maxfilesize"));
        config.setJuryTime(rs.getInt("jurytime"));

        return config;
    }
	
}
