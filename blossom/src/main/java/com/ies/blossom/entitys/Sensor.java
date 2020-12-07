package com.ies.blossom.entitys;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "sensors")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sensorId;

    @Column(name = "type")
    private String type;

    @Column(name = "assoc_date")
    private Date assocDate;

    public Sensor() {}

    public Sensor(String type, Date assocDate) {
        this.type = type;
        this.assocDate = assocDate;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getAssocDate() {
        return assocDate;
    }

    public void setAssocDate(Date assocDate) {
        this.assocDate = assocDate;
    }
}
