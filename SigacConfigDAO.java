package br.edu.utfpr.dv.siacoes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.edu.utfpr.dv.siacoes.log.UpdateEvent;

public class SigacConfigDAO extends SigMasterDAO<SigacConfig> {
	
    protected String getSqlFindByDepartment() {
        return "SELECT * FROM sigacconfig WHERE idDepartment = ?";
    }

    @Override
    protected String getSqlSaveInsert() {
        return "INSERT INTO sigacconfig(minimumScore, maxfilesize, idDepartment) VALUES(?, ?, ?)";
    }

    @Override
    protected String getSqlSaveUpdate() {
        return "UPDATE sigacconfig SET minimumScore=?, maxfilesize=? WHERE idDepartment=?";
    }

    protected void ormSave(PreparedStatement statement, SigacConfig config) throws SQLException {
        statement.setDouble(1, config.getMinimumScore());
        statement.setInt(2, config.getMaxFileSize());
        statement.setInt(3, config.getDepartment().getIdDepartment());
    }

    protected int getIdDepartment(SigacConfig obj) {
        return obj.getDepartment().getIdDepartment();
    }

    protected SigacConfig loadObject(ResultSet rs) throws SQLException {
        SigacConfig config = new SigacConfig();

        config.getDepartment().setIdDepartment(rs.getInt("idDepartment"));
        config.setMinimumScore(rs.getDouble("minimumScore"));
        config.setMaxFileSize(rs.getInt("maxfilesize"));

        return config;
    }

}
