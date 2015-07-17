package com.genius.flashcard.hibernate.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import com.genius.flashcard.common.enums.Enumable;   
  
public abstract class EnumUserType<E extends Enumable> implements UserType {   
    protected EnumUserType() {   
    }   
    
    private static final int[] SQL_TYPES = {Types.VARCHAR};
    
    @Override
    public int[] sqlTypes() {   
        return SQL_TYPES;   
    }   
   
    @Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
    	String name = rs.getString(names[0]);   
        E result = null;   
        
        try {
        	Enumable en = (Enumable)returnedClass().getEnumConstants()[0];
        	return en.getEnum(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return result;   
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException {
		if (null == value) {   
            st.setNull(index, Types.VARCHAR);   
        } else {   
            st.setString(index, ((Enumable)value).getValue());   
        }   
	}   
   
	@Override
    public Object deepCopy(Object value) throws HibernateException{   
        return value;   
    }   
   
	@Override
    public boolean isMutable() {   
        return false;   
    }   
   
	@Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {    
         return cached;  
    }   
  
	@Override
    public Serializable disassemble(Object value) throws HibernateException {   
        return (Serializable)value;   
    }   
   
	@Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {   
        return original;   
    }   
	
	@Override
    public int hashCode(Object x) throws HibernateException {   
        return x.hashCode();   
    }   
	
	@Override
    public boolean equals(Object x, Object y) throws HibernateException {   
        if (x == y)   
            return true;   
        if (null == x || null == y)   
            return false;   
        return x.equals(y);   
    }
}  