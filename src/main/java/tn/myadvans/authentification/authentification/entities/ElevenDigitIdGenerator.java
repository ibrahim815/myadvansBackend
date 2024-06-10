package tn.myadvans.authentification.authentification.entities;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Random;

public class ElevenDigitIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Random random = new Random();
        long generatedId = 10000000000L + (long)(random.nextDouble() * 90000000000L); // Génère un nombre entre 10^10 et 10^11 - 1
        return generatedId;
    }
}
