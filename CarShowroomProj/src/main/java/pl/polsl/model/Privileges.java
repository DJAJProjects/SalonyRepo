package pl.polsl.model;

import javax.persistence.*;

/**
 * Created by Kuba on 04.04.2016.
 */
@Entity
@Table(name="privileges", schema = "salonydb")
public class Privileges {
    private int id;
    private String name;
    private boolean contractsR;
    private boolean contractsI;
    private boolean contractsD;
    private boolean contractsU;
    private boolean invoicesR;
    private boolean invoicesI;
    private boolean invoicesD;
    private boolean invoicesU;
    private boolean carsR;
    private boolean carsI;
    private boolean carsD;
    private boolean carsU;
    private boolean accessoriesR;
    private boolean accessoriesI;
    private boolean accessoriesD;
    private boolean accessoriesU;
    private boolean contractorsR;
    private boolean contractorsI;
    private boolean contractorsD;
    private boolean contractorsU;
    private boolean reportsR;
    private boolean reportsD;
    private boolean showroomsR;
    private boolean showroomsI;
    private boolean showroomsD;
    private boolean showroomsU;
    private boolean workersR;
    private boolean workersI;
    private boolean workersD;
    private boolean workersU;
    private boolean promotionsR;
    private boolean promotionsI;
    private boolean promotionsD;
    private boolean promotionsU;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public boolean getContractsR() {
        return contractsR;
    }

    public void setContractsR(boolean contractsR) {
        this.contractsR = contractsR;
    }

    @Basic
    @Column(name = "contracts_i")
    public boolean getContractsI() {
        return contractsI;
    }

    public void setContractsI(boolean contractsI) {
        this.contractsI = contractsI;
    }

    @Basic
    @Column(name = "contracts_d")
    public boolean getContractsD() {
        return contractsD;
    }

    public void setContractsD(boolean contractsD) {
        this.contractsD = contractsD;
    }

    @Basic
    @Column(name = "contracts_u")
    public boolean getContractsU() {
        return contractsU;
    }

    public void setContractsU(boolean contractsU) {
        this.contractsU = contractsU;
    }

    @Basic
    @Column(name = "invoices_r")
    public boolean getInvoicesR() {
        return invoicesR;
    }

    public void setInvoicesR(boolean invoicesR) {
        this.invoicesR = invoicesR;
    }

    @Basic
    @Column(name = "invoices_i")
    public boolean getInvoicesI() {
        return invoicesI;
    }

    public void setInvoicesI(boolean invoicesI) {
        this.invoicesI = invoicesI;
    }

    @Basic
    @Column(name = "invoices_d")
    public boolean getInvoicesD() {
        return invoicesD;
    }

    public void setInvoicesD(boolean invoicesD) {
        this.invoicesD = invoicesD;
    }

    @Basic
    @Column(name = "invoices_u")
    public boolean getInvoicesU() {
        return invoicesU;
    }

    public void setInvoicesU(boolean invoicesU) {
        this.invoicesU = invoicesU;
    }

    @Basic
    @Column(name = "cars_r")
    public boolean getCarsR() {
        return carsR;
    }

    public void setCarsR(boolean carsR) {
        this.carsR = carsR;
    }

    @Basic
    @Column(name = "cars_i")
    public boolean getCarsI() {
        return carsI;
    }

    public void setCarsI(boolean carsI) {
        this.carsI = carsI;
    }

    @Basic
    @Column(name = "cars_d")
    public boolean getCarsD() {
        return carsD;
    }

    public void setCarsD(boolean carsD) {
        this.carsD = carsD;
    }

    @Basic
    @Column(name = "cars_u")
    public boolean getCarsU() {
        return carsU;
    }

    public void setCarsU(boolean carsU) {
        this.carsU = carsU;
    }

    @Basic
    @Column(name = "accessories_r")
    public boolean getAccessoriesR() {
        return accessoriesR;
    }

    public void setAccessoriesR(boolean accessoriesR) {
        this.accessoriesR = accessoriesR;
    }

    @Basic
    @Column(name = "accessories_i")
    public boolean getAccessoriesI() {
        return accessoriesI;
    }

    public void setAccessoriesI(boolean accessoriesI) {
        this.accessoriesI = accessoriesI;
    }

    @Basic
    @Column(name = "accessories_d")
    public boolean getAccessoriesD() {
        return accessoriesD;
    }

    public void setAccessoriesD(boolean accessoriesD) {
        this.accessoriesD = accessoriesD;
    }

    @Basic
    @Column(name = "accessories_u")
    public boolean getAccessoriesU() {
        return accessoriesU;
    }

    public void setAccessoriesU(boolean accessoriesU) {
        this.accessoriesU = accessoriesU;
    }

    @Basic
    @Column(name = "contractors_r")
    public boolean getContractorsR() {
        return contractorsR;
    }

    public void setContractorsR(boolean contractorsR) {
        this.contractorsR = contractorsR;
    }

    @Basic
    @Column(name = "contractors_i")
    public boolean getContractorsI() {
        return contractorsI;
    }

    public void setContractorsI(boolean contractorsI) {
        this.contractorsI = contractorsI;
    }

    @Basic
    @Column(name = "contractors_d")
    public boolean getContractorsD() {
        return contractorsD;
    }

    public void setContractorsD(boolean contractorsD) {
        this.contractorsD = contractorsD;
    }

    @Basic
    @Column(name = "contractors_u")
    public boolean getContractorsU() {
        return contractorsU;
    }

    public void setContractorsU(boolean contractorsU) {
        this.contractorsU = contractorsU;
    }

    @Basic
    @Column(name = "reports_r")
    public boolean getReportsR() {
        return reportsR;
    }

    public void setReportsR(boolean reportsR) {
        this.reportsR = reportsR;
    }

    @Basic
    @Column(name = "reports_d")
    public boolean getReportsD() {
        return reportsD;
    }

    public void setReportsD(boolean reportsD) {
        this.reportsD = reportsD;
    }

    @Basic
    @Column(name = "showrooms_r")
    public boolean getShowroomsR() {
        return showroomsR;
    }

    public void setShowroomsR(boolean showroomsR) {
        this.showroomsR = showroomsR;
    }

    @Basic
    @Column(name = "showrooms_i")
    public boolean getShowroomsI() {
        return showroomsI;
    }

    public void setShowroomsI(boolean showroomsI) {
        this.showroomsI = showroomsI;
    }

    @Basic
    @Column(name = "showrooms_d")
    public boolean getShowroomsD() {
        return showroomsD;
    }

    public void setShowroomsD(boolean showroomsD) {
        this.showroomsD = showroomsD;
    }

    @Basic
    @Column(name = "showrooms_u")
    public boolean getShowroomsU() {
        return showroomsU;
    }

    public void setShowroomsU(boolean showroomsU) {
        this.showroomsU = showroomsU;
    }

    @Basic
    @Column(name = "workers_r")
    public boolean getWorkersR() {
        return workersR;
    }

    public void setWorkersR(boolean workersR) {
        this.workersR = workersR;
    }

    @Basic
    @Column(name = "workers_i")
    public boolean getWorkersI() {
        return workersI;
    }

    public void setWorkersI(boolean workersI) {
        this.workersI = workersI;
    }

    @Basic
    @Column(name = "workers_d")
    public boolean getWorkersD() {
        return workersD;
    }

    public void setWorkersD(boolean workersD) {
        this.workersD = workersD;
    }

    @Basic
    @Column(name = "workers_u")
    public boolean getWorkersU() {
        return workersU;
    }

    public void setWorkersU(boolean workersU) {
        this.workersU = workersU;
    }

    @Basic
    @Column(name = "promotions_r")
    public boolean getPromotionsR() {
        return promotionsR;
    }

    public void setPromotionsR(boolean promotionsR) {
        this.promotionsR = promotionsR;
    }

    @Basic
    @Column(name = "promotions_i")
    public boolean getPromotionsI() {
        return promotionsI;
    }

    public void setPromotionsI(boolean promotionsI) {
        this.promotionsI = promotionsI;
    }

    @Basic
    @Column(name = "promotions_d")
    public boolean getPromotionsD() {
        return promotionsD;
    }

    public void setPromotionsD(boolean promotionsD) {
        this.promotionsD = promotionsD;
    }

    @Basic
    @Column(name = "promotions_u")
    public boolean getPromotionsU() {
        return promotionsU;
    }

    public void setPromotionsU(boolean promotionsU) {
        this.promotionsU = promotionsU;
    }

}
