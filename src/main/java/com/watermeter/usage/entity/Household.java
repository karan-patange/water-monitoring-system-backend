package com.watermeter.usage.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Household {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String houseNumber;
    private String ownerName;
    private String mobileNumber;
    private String email;
    private String location;

    @OneToOne(mappedBy = "household", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Login login;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<WaterUsage> waterUsageList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public List<WaterUsage> getWaterUsageList() {
        return waterUsageList;
    }

    public void setWaterUsageList(List<WaterUsage> waterUsageList) {
        this.waterUsageList = waterUsageList;
    }

    @Override
    public String toString() {
        return "Household{" +
                "id=" + id +
                ", houseNumber='" + houseNumber + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", login=" + login +
                ", waterUsageList=" + waterUsageList +
                '}';
    }
}
