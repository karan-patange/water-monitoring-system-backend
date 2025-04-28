package com.watermeter.usage.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class WaterUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double litersUsed;

    private LocalDateTime date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "household_id")
    @JsonBackReference
    private Household household;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLitersUsed() {
        return litersUsed;
    }

    public void setLitersUsed(Double litersUsed) {
        this.litersUsed = litersUsed;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }


    @Override
    public String toString() {
        return "WaterUsage{" +
                "id=" + id +
                ", litersUsed=" + litersUsed +
                ", timestamp=" + date +
                ", household=" + household +
                '}';
    }
}
