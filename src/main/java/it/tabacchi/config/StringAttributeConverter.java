package it.tabacchi.config;

import jakarta.persistence.AttributeConverter;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class StringAttributeConverter implements AttributeConverter<String, String> {

    // Usiamo @Lazy per evitare problemi di dipendenze circolari all'avvio
    @Lazy
    @Autowired
    private StringEncryptor stringEncryptor;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }
        return stringEncryptor.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return stringEncryptor.decrypt(dbData);
        } catch (EncryptionOperationNotPossibleException e) {
            // If decryption fails, assume the data is not encrypted (for backward compatibility)
            // In production, you should log this and potentially handle it differently
            return dbData;
        }
    }
}
