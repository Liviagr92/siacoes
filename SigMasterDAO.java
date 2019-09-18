package br.edu.utfpr.dv.siacoes.dao;
import br.edu.utfpr.dv.siacoes.log.UpdateEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class SigMasterDAO<T> {

    public final T findByDepartment(int idDepartment) throws SQLException{
        Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
        
        try{
            conn = ConnectionDAO.getInstance().getConnection();
            String sql = this.getSqlFindByDepartament();
            stmt = conn.prepareStatement(sql);
		
            stmt.setInt(1, idDepartment);
	
            rs = stmt.executeQuery();
			
            if(rs.next()){
                return this.loadObject(rs);
            }else{
		return null;
            }
	}finally{
            if((rs != null) && !rs.isClosed())
		rs.close();
            if((stmt != null) && !stmt.isClosed())
            	stmt.close();
            if((conn != null) && !conn.isClosed())
		conn.close();
	}
	
    }
    
    protected abstract String getSqlSaveInsert();
    protected abstract String getSqlSaveUpdate();
    protected abstract void ormSave(PreparedStatement statement, T config);
    protected abstract int getIdDepartment(T obj);
	
 
    public int save(int idUser, T config) throws SQLException{
        boolean insert = (this.findByDepartment(getIdDepartment(config)) == null);
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionDAO.getInstance().getConnection();

            if (insert) {
                String sql = this.getSqlSaveInsert();
                stmt = conn.prepareStatement(sql);
            } else {
                String sql = this.getSqlSaveUpdate();
                stmt = conn.prepareStatement(sql);
            }

        this.ormSave(stmt, config);
        stmt.execute();

        new UpdateEvent(conn).registerUpdate(idUser, config);
        return this.getIdDepartment(config);
        } finally{
		if((stmt != null) && !stmt.isClosed())
                    stmt.close();
		if((conn != null) && !conn.isClosed())
                    conn.close();
	}
	
    }
        
        private final T loadObject(ResultSet rs) throws SQLException{
        return null;
            
	}    

}
