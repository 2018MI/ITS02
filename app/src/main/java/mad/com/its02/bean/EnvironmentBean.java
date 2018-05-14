package mad.com.its02.bean;

import com.google.gson.annotations.SerializedName;

public class EnvironmentBean {

    @SerializedName("pm2.5")
    private int _$Pm25233; // FIXME check this code
    private int co2;
    private int temperature;
    private int LightIntensity;
    private int humidity;

    public int get_$Pm25233() {
        return _$Pm25233;
    }

    public void set_$Pm25233(int _$Pm25233) {
        this._$Pm25233 = _$Pm25233;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getLightIntensity() {
        return LightIntensity;
    }

    public void setLightIntensity(int LightIntensity) {
        this.LightIntensity = LightIntensity;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "EnvironmentBean{" +
                "_$Pm25233=" + _$Pm25233 +
                ", co2=" + co2 +
                ", temperature=" + temperature +
                ", LightIntensity=" + LightIntensity +
                ", humidity=" + humidity +
                '}';
    }
}
