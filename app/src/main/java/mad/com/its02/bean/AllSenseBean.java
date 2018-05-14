package mad.com.its02.bean;

import com.google.gson.annotations.SerializedName;

public class AllSenseBean {


    @SerializedName("pm2.5")
    private int _$Pm25180; // FIXME check this code
    private int co2;
    private int temperature;
    private int LightIntensity;
    private int humidity;

    public int get_$Pm25180() {
        return _$Pm25180;
    }

    public void set_$Pm25180(int _$Pm25180) {
        this._$Pm25180 = _$Pm25180;
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
}
