package org.hrsh.vehiclerental;

import org.hrsh.vehiclerental.equipment.Equipment;
import org.hrsh.vehiclerental.service.Service;
import org.hrsh.vehiclerental.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.List;

public class Reservation {
    private String id;
    private Vehicle vehicle;
    private List<Equipment> equipmentList;
    private List<Service> serviceList;
    private Member member;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double totalPrice;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
