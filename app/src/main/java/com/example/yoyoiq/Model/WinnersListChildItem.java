package com.example.yoyoiq.Model;

public class WinnersListChildItem {
    String userid;
    String rank;
    String money;
    String name;
    String image;

    public WinnersListChildItem(String userid, String rank, String money, String name, String image) {
        this.userid = userid;
        this.rank = rank;
        this.money = money;
        this.name = name;
        this.image = image;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
