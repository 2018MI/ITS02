package mad.com.its02.bean;

public class WeatherBean {

    private String desc;
    private Integer resId;
    private Integer level;
    private Integer temperature;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public WeatherBean(String desc, Integer resId, Integer level) {
        this.desc = desc;
        this.resId = resId;
        this.level = level;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }
}
