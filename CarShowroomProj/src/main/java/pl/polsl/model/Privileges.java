package pl.polsl.model;

import javax.persistence.*;

/**
 * Created by Kuba on 04.04.2016.
 */
@Entity
@Table(name="privileges", schema = "salonydb")
public class Privileges {
    private int id;
    private String priviliegescol;
    private String name;
    private String contractsR;
    private String contractsI;
    private String contractsD;
    private String contractsU;
    private String invoicesR;
    private String invoicesI;
    private String invoicesD;
    private String invoicesU;
    private String carsR;
    private String carsI;
    private String carsD;
    private String carsU;
    private String accessoriesR;
    private String accessoriesI;
    private String accessoriesD;
    private String accessoriesU;
    private String contractorsR;
    private String contractorsI;
    private String contractorsD;
    private String contractorsU;
    private String reportsR;
    private String reportsD;
    private String showroomsR;
    private String showroomsI;
    private String showroomsD;
    private String showroomsU;
    private String workersR;
    private String workersI;
    private String workersD;
    private String workersU;
    private String promotionsR;
    private String promotionsI;
    private String promotionsD;
    private String promotionsU;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "priviliegescol")
    public String getPriviliegescol() {
        return priviliegescol;
    }

    public void setPriviliegescol(String priviliegescol) {
        this.priviliegescol = priviliegescol;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "contracts_r")
    public String getContractsR() {
        return contractsR;
    }

    public void setContractsR(String contractsR) {
        this.contractsR = contractsR;
    }

    @Basic
    @Column(name = "contracts_i")
    public String getContractsI() {
        return contractsI;
    }

    public void setContractsI(String contractsI) {
        this.contractsI = contractsI;
    }

    @Basic
    @Column(name = "contracts_d")
    public String getContractsD() {
        return contractsD;
    }

    public void setContractsD(String contractsD) {
        this.contractsD = contractsD;
    }

    @Basic
    @Column(name = "contracts_u")
    public String getContractsU() {
        return contractsU;
    }

    public void setContractsU(String contractsU) {
        this.contractsU = contractsU;
    }

    @Basic
    @Column(name = "invoices_r")
    public String getInvoicesR() {
        return invoicesR;
    }

    public void setInvoicesR(String invoicesR) {
        this.invoicesR = invoicesR;
    }

    @Basic
    @Column(name = "invoices_i")
    public String getInvoicesI() {
        return invoicesI;
    }

    public void setInvoicesI(String invoicesI) {
        this.invoicesI = invoicesI;
    }

    @Basic
    @Column(name = "invoices_d")
    public String getInvoicesD() {
        return invoicesD;
    }

    public void setInvoicesD(String invoicesD) {
        this.invoicesD = invoicesD;
    }

    @Basic
    @Column(name = "invoices_u")
    public String getInvoicesU() {
        return invoicesU;
    }

    public void setInvoicesU(String invoicesU) {
        this.invoicesU = invoicesU;
    }

    @Basic
    @Column(name = "cars_r")
    public String getCarsR() {
        return carsR;
    }

    public void setCarsR(String carsR) {
        this.carsR = carsR;
    }

    @Basic
    @Column(name = "cars_i")
    public String getCarsI() {
        return carsI;
    }

    public void setCarsI(String carsI) {
        this.carsI = carsI;
    }

    @Basic
    @Column(name = "cars_d")
    public String getCarsD() {
        return carsD;
    }

    public void setCarsD(String carsD) {
        this.carsD = carsD;
    }

    @Basic
    @Column(name = "cars_u")
    public String getCarsU() {
        return carsU;
    }

    public void setCarsU(String carsU) {
        this.carsU = carsU;
    }

    @Basic
    @Column(name = "accessories_r")
    public String getAccessoriesR() {
        return accessoriesR;
    }

    public void setAccessoriesR(String accessoriesR) {
        this.accessoriesR = accessoriesR;
    }

    @Basic
    @Column(name = "accessories_i")
    public String getAccessoriesI() {
        return accessoriesI;
    }

    public void setAccessoriesI(String accessoriesI) {
        this.accessoriesI = accessoriesI;
    }

    @Basic
    @Column(name = "accessories_d")
    public String getAccessoriesD() {
        return accessoriesD;
    }

    public void setAccessoriesD(String accessoriesD) {
        this.accessoriesD = accessoriesD;
    }

    @Basic
    @Column(name = "accessories_u")
    public String getAccessoriesU() {
        return accessoriesU;
    }

    public void setAccessoriesU(String accessoriesU) {
        this.accessoriesU = accessoriesU;
    }

    @Basic
    @Column(name = "contractors_r")
    public String getContractorsR() {
        return contractorsR;
    }

    public void setContractorsR(String contractorsR) {
        this.contractorsR = contractorsR;
    }

    @Basic
    @Column(name = "contractors_i")
    public String getContractorsI() {
        return contractorsI;
    }

    public void setContractorsI(String contractorsI) {
        this.contractorsI = contractorsI;
    }

    @Basic
    @Column(name = "contractors_d")
    public String getContractorsD() {
        return contractorsD;
    }

    public void setContractorsD(String contractorsD) {
        this.contractorsD = contractorsD;
    }

    @Basic
    @Column(name = "contractors_u")
    public String getContractorsU() {
        return contractorsU;
    }

    public void setContractorsU(String contractorsU) {
        this.contractorsU = contractorsU;
    }

    @Basic
    @Column(name = "reports_r")
    public String getReportsR() {
        return reportsR;
    }

    public void setReportsR(String reportsR) {
        this.reportsR = reportsR;
    }

    @Basic
    @Column(name = "reports_d")
    public String getReportsD() {
        return reportsD;
    }

    public void setReportsD(String reportsD) {
        this.reportsD = reportsD;
    }

    @Basic
    @Column(name = "showrooms_r")
    public String getShowroomsR() {
        return showroomsR;
    }

    public void setShowroomsR(String showroomsR) {
        this.showroomsR = showroomsR;
    }

    @Basic
    @Column(name = "showrooms_i")
    public String getShowroomsI() {
        return showroomsI;
    }

    public void setShowroomsI(String showroomsI) {
        this.showroomsI = showroomsI;
    }

    @Basic
    @Column(name = "showrooms_d")
    public String getShowroomsD() {
        return showroomsD;
    }

    public void setShowroomsD(String showroomsD) {
        this.showroomsD = showroomsD;
    }

    @Basic
    @Column(name = "showrooms_u")
    public String getShowroomsU() {
        return showroomsU;
    }

    public void setShowroomsU(String showroomsU) {
        this.showroomsU = showroomsU;
    }

    @Basic
    @Column(name = "workers_r")
    public String getWorkersR() {
        return workersR;
    }

    public void setWorkersR(String workersR) {
        this.workersR = workersR;
    }

    @Basic
    @Column(name = "workers_i")
    public String getWorkersI() {
        return workersI;
    }

    public void setWorkersI(String workersI) {
        this.workersI = workersI;
    }

    @Basic
    @Column(name = "workers_d")
    public String getWorkersD() {
        return workersD;
    }

    public void setWorkersD(String workersD) {
        this.workersD = workersD;
    }

    @Basic
    @Column(name = "workers_u")
    public String getWorkersU() {
        return workersU;
    }

    public void setWorkersU(String workersU) {
        this.workersU = workersU;
    }

    @Basic
    @Column(name = "promotions_r")
    public String getPromotionsR() {
        return promotionsR;
    }

    public void setPromotionsR(String promotionsR) {
        this.promotionsR = promotionsR;
    }

    @Basic
    @Column(name = "promotions_i")
    public String getPromotionsI() {
        return promotionsI;
    }

    public void setPromotionsI(String promotionsI) {
        this.promotionsI = promotionsI;
    }

    @Basic
    @Column(name = "promotions_d")
    public String getPromotionsD() {
        return promotionsD;
    }

    public void setPromotionsD(String promotionsD) {
        this.promotionsD = promotionsD;
    }

    @Basic
    @Column(name = "promotions_u")
    public String getPromotionsU() {
        return promotionsU;
    }

    public void setPromotionsU(String promotionsU) {
        this.promotionsU = promotionsU;
    }

}
