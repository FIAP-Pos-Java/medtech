package br.com.medtech.ms_medtech.converters;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDUtils {

    private final String MESSAGE_ID_INVALIDO = "id invalido";

    public UUID retornaStringSemHifen(String idStr){
        if(idStr == null || idStr.isEmpty()){
            throw new IllegalArgumentException(MESSAGE_ID_INVALIDO);
        }

        return UUID.fromString(idStr.replaceFirst(
                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                "$1-$2-$3-$4-$5"
        ));
    }
}