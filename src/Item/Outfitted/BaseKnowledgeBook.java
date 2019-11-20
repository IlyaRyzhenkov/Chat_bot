package Item.Outfitted;

import Item.Accessory;

import java.util.HashMap;

public class BaseKnowledgeBook extends Accessory {
    public BaseKnowledgeBook() {
        super("Item/Outfitted/Accessory/BaseKnowledgeBook",
                "Элементарный учебник физики. Г. С. Ландсберг. Том 1",
                "Элементарный учебник физики. Механика. Теплота. Молекулярная физика. +1 к знаниям",
                new HashMap<String, Integer>() {
                    {
                        put("knowledge", 1);
                    }
                });
    }
}
