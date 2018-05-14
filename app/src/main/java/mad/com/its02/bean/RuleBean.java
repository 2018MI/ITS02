package mad.com.its02.bean;

public class RuleBean {


    public static final Integer ASC = 100;
    public static final Integer DESC = 101;
    private String desc;
    private Integer level;
    private String columnField;

    public RuleBean(String desc, Integer level, String columnField) {
        this.desc = desc;
        this.level = level;
        this.columnField = columnField;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getColumnField() {
        return columnField;
    }

    public void setColumnField(String columnField) {
        this.columnField = columnField;
    }
}
