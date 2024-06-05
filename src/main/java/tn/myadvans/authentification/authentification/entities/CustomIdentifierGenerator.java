package tn.myadvans.authentification.authentification.entities;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class CustomIdentifierGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        // Query to get the max current value from the database
        String query = "SELECT MAX(customer) FROM individual";
        Long maxId = Long.parseLong(session.createNativeQuery(query).getSingleResult() == null ? "0": session.createNativeQuery(query).getSingleResult().toString());

        // Increment the current max value or start with 1 if null
        long nextId = (maxId == null) ? 1L : maxId + 1L;

        // Format the id as a string with leading zeros
        return String.format("%08d", nextId);
    }
}
