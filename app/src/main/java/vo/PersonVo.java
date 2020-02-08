package vo;

import java.io.Serializable;

//Serializable => Activity간에 정보 전달목적..
public class PersonVo implements Serializable{
    int    idx;
    String name;
    String tel;

    public PersonVo() {
    }

    public PersonVo(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    public PersonVo(int idx, String name, String tel) {
        this.idx = idx;
        this.name = name;
        this.tel = tel;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
