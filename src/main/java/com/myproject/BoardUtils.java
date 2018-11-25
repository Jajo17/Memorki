package com.myproject;

import java.util.ArrayList;
import java.util.List;

class BoardUtils {

    static List<String> getBoardFields() {
        List<String> fields = new ArrayList<>();

        fields.add(BoardFields.AUTO.name());
        fields.add(BoardFields.SERCE.name());
        fields.add(BoardFields.SŁOŃCE.name());
        fields.add(BoardFields.CHMURA.name());
        fields.add(BoardFields.DIAMENT.name());
        fields.add(BoardFields.DOM.name());
        fields.add(BoardFields.DRZEWO.name());
        fields.add(BoardFields.KOT.name());
        fields.add(BoardFields.MIESZKA.name());
        fields.add(BoardFields.PIES.name());

        return fields;
    }

}
