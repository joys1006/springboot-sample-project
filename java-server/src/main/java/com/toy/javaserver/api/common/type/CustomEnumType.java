package com.toy.javaserver.api.common.type;

import com.toy.javaserver.api.common.enums.BaseEnum;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;

/**
 * BaseEnum Converter
 * BaseEnum <-> DB value 컨버팅
 */
public class CustomEnumType implements UserType, DynamicParameterizedType {

    public static final String NAME = "com.toy.javaserver.api.common.type.CustomEnumType";

    private Class<? extends BaseEnum> enumClass;
    private String entityName;
    private String propertyName;

    @Override
    public void setParameterValues(Properties parameters) {
        ParameterType params = (ParameterType) parameters.get(PARAMETER_TYPE);

        enumClass = params.getReturnedClass();
        entityName = (String) parameters.get(ENTITY);
        propertyName = (String) parameters.get(PROPERTY);
    }

    /**
     * DB -> Enum
     */
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        Object label = rs.getObject(names[0]);

        if (Objects.isNull(label)) return null;

        // DB -> Enum
        return BaseEnum.getEnum(enumClass, label);
    }

    /**
     * ENUM -> DB
     */
    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        // Enum -> DB
        Object v = value;

        if (!Objects.isNull(value)) {
            v = ((BaseEnum) value).getCode();
        }

        st.setObject(index, v);
    }

    @Override
    public Class returnedClass() { return enumClass; }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException { return x == y; }

    @Override
    public int hashCode(Object x) throws HibernateException { return x.hashCode(); }

    @Override
    public Object deepCopy(Object value) throws HibernateException { return value; }

    @Override
    public boolean isMutable() { return false; }

    @Override
    public Serializable disassemble(Object value) throws HibernateException { return (Serializable) value; }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException { return cached; }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException { return original; }
}
