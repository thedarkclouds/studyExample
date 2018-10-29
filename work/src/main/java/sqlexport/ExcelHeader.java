package sqlexport;

public class ExcelHeader {

    private String name;
    private String tittle;

    private String dataType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public ExcelHeader(String tittle , String name, String dataType) {
        this.name = name;
        this.tittle = tittle;
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "ExcelHeader{" +
                "name='" + name + '\'' +
                ", tittle='" + tittle + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }
}
