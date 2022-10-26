package com.example.yoyoiq.Model;

import java.util.List;

public class WinnersListPOJO {
    String match_id;
    String title;
    String format_str;
    String date_start_ist;
    String logo_url_a;
    String name_a;
    String short_name_a;
    String logo_url_b;
    String name_b;
    String short_name_b;
    String prize_pool;
    public List<WinnersListChildItem> ChildItemList;

    public WinnersListPOJO(String match_id, String title, String format_str, String date_start_ist, String logo_url_a, String name_a, String short_name_a, String logo_url_b, String name_b, String short_name_b, String prize_pool, List<WinnersListChildItem> childItemList) {
        this.match_id = match_id;
        this.title = title;
        this.format_str = format_str;
        this.date_start_ist = date_start_ist;
        this.logo_url_a = logo_url_a;
        this.name_a = name_a;
        this.short_name_a = short_name_a;
        this.logo_url_b = logo_url_b;
        this.name_b = name_b;
        this.short_name_b = short_name_b;
        this.prize_pool = prize_pool;
        ChildItemList = childItemList;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormat_str() {
        return format_str;
    }

    public void setFormat_str(String format_str) {
        this.format_str = format_str;
    }

    public String getDate_start_ist() {
        return date_start_ist;
    }

    public void setDate_start_ist(String date_start_ist) {
        this.date_start_ist = date_start_ist;
    }

    public String getLogo_url_a() {
        return logo_url_a;
    }

    public void setLogo_url_a(String logo_url_a) {
        this.logo_url_a = logo_url_a;
    }

    public String getName_a() {
        return name_a;
    }

    public void setName_a(String name_a) {
        this.name_a = name_a;
    }

    public String getShort_name_a() {
        return short_name_a;
    }

    public void setShort_name_a(String short_name_a) {
        this.short_name_a = short_name_a;
    }

    public String getLogo_url_b() {
        return logo_url_b;
    }

    public void setLogo_url_b(String logo_url_b) {
        this.logo_url_b = logo_url_b;
    }

    public String getName_b() {
        return name_b;
    }

    public void setName_b(String name_b) {
        this.name_b = name_b;
    }

    public String getShort_name_b() {
        return short_name_b;
    }

    public void setShort_name_b(String short_name_b) {
        this.short_name_b = short_name_b;
    }

    public String getPrize_pool() {
        return prize_pool;
    }

    public void setPrize_pool(String prize_pool) {
        this.prize_pool = prize_pool;
    }

    public List<WinnersListChildItem> getChildItemList() {
        return ChildItemList;
    }

    public void setChildItemList(List<WinnersListChildItem> childItemList) {
        ChildItemList = childItemList;
    }
}
