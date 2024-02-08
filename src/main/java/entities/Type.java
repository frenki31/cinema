package entities;

public class Type {
    private int typeId;
    private String typeLanguage;

    public Type(int typeId, String typeLanguage) {
        this.typeId = typeId;
        this.typeLanguage = typeLanguage;
    }
    public int getTypeId() {
        return typeId;
    }
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    public String getTypeLanguage() {
        return typeLanguage;
    }
    public void setTypeLanguage(String typeLanguage) {
        this.typeLanguage = typeLanguage;
    }

}
