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
    private String description;
    private Boolean contractsR;
    private Boolean contractsI;
    private Boolean contractsD;
    private Boolean contractsU;
    private Boolean invoicesR;
    private Boolean invoicesI;
    private Boolean invoicesD;
    private Boolean invoicesU;
    private Boolean carsR;
    private Boolean carsI;
    private Boolean carsD;
    private Boolean carsU;
    private Boolean accessoriesR;
    private Boolean accessoriesI;
    private Boolean accessoriesD;
    private Boolean accessoriesU;
    private Boolean contractorsR;
    private Boolean contractorsI;
    private Boolean contractorsD;
    private Boolean contractorsU;
    private Boolean reportsR;
    private Boolean reportsI;
    private Boolean reportsD;
    private Boolean reportsU;
    private Boolean showroomsR;
    private Boolean showroomsI;
    private Boolean showroomsD;
    private Boolean showroomsU;
    private Boolean workersR;
    private Boolean workersI;
    private Boolean workersD;
    private Boolean workersU;
    private Boolean promotionsR;
    private Boolean promotionsI;
    private Boolean promotionsD;
    private Boolean promotionsU;
    private Boolean servicesR;
    private Boolean servicesI;
    private Boolean servicesD;
    private Boolean servicesU;
    private Boolean privilegesR;
    private Boolean privilegesI;
    private Boolean privilegesD;
    private Boolean privilegesU;
    private Boolean dictionaryR;
    private Boolean dictionaryI;
    private Boolean dictionaryD;
    private Boolean dictionaryU;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    public Boolean getContractsR() {
        return contractsR;
    }

    public void setContractsR(Boolean contractsR) {
        this.contractsR = contractsR;
    }

    @Basic
    @Column(name = "contracts_i")
    public Boolean getContractsI() {
        return contractsI;
    }

    public void setContractsI(Boolean contractsI) {
        this.contractsI = contractsI;
    }

    @Basic
    @Column(name = "contracts_d")
    public Boolean getContractsD() {
        return contractsD;
    }

    public void setContractsD(Boolean contractsD) {
        this.contractsD = contractsD;
    }

    @Basic
    @Column(name = "contracts_u")
    public Boolean getContractsU() {
        return contractsU;
    }

    public void setContractsU(Boolean contractsU) {
        this.contractsU = contractsU;
    }

    @Basic
    @Column(name = "invoices_r")
    public Boolean getInvoicesR() {
        return invoicesR;
    }

    public void setInvoicesR(Boolean invoicesR) {
        this.invoicesR = invoicesR;
    }

    @Basic
    @Column(name = "invoices_i")
    public Boolean getInvoicesI() {
        return invoicesI;
    }

    public void setInvoicesI(Boolean invoicesI) {
        this.invoicesI = invoicesI;
    }

    @Basic
    @Column(name = "invoices_d")
    public Boolean getInvoicesD() {
        return invoicesD;
    }

    public void setInvoicesD(Boolean invoicesD) {
        this.invoicesD = invoicesD;
    }

    @Basic
    @Column(name = "invoices_u")
    public Boolean getInvoicesU() {
        return invoicesU;
    }

    public void setInvoicesU(Boolean invoicesU) {
        this.invoicesU = invoicesU;
    }

    @Basic
    @Column(name = "cars_r")
    public Boolean getCarsR() {
        return carsR;
    }

    public void setCarsR(Boolean carsR) {
        this.carsR = carsR;
    }

    @Basic
    @Column(name = "cars_i")
    public Boolean getCarsI() {
        return carsI;
    }

    public void setCarsI(Boolean carsI) {
        this.carsI = carsI;
    }

    @Basic
    @Column(name = "cars_d")
    public Boolean getCarsD() {
        return carsD;
    }

    public void setCarsD(Boolean carsD) {
        this.carsD = carsD;
    }

    @Basic
    @Column(name = "cars_u")
    public Boolean getCarsU() {
        return carsU;
    }

    public void setCarsU(Boolean carsU) {
        this.carsU = carsU;
    }

    @Basic
    @Column(name = "accessories_r")
    public Boolean getAccessoriesR() {
        return accessoriesR;
    }

    public void setAccessoriesR(Boolean accessoriesR) {
        this.accessoriesR = accessoriesR;
    }

    @Basic
    @Column(name = "accessories_i")
    public Boolean getAccessoriesI() {
        return accessoriesI;
    }

    public void setAccessoriesI(Boolean accessoriesI) {
        this.accessoriesI = accessoriesI;
    }


    @Column(name = "accessories_d")
    public Boolean getAccessoriesD() {
        return accessoriesD;
    }

    public void setAccessoriesD(Boolean accessoriesD) {
        this.accessoriesD = accessoriesD;
    }

    @Basic
    @Column(name = "accessories_u")
    public Boolean getAccessoriesU() {
        return accessoriesU;
    }

    public void setAccessoriesU(Boolean accessoriesU) {
        this.accessoriesU = accessoriesU;
    }

    @Basic
    @Column(name = "contractors_r")
    public Boolean getContractorsR() {
        return contractorsR;
    }

    public void setContractorsR(Boolean contractorsR) {
        this.contractorsR = contractorsR;
    }

    @Basic
    @Column(name = "contractors_i")
    public Boolean getContractorsI() {
        return contractorsI;
    }

    public void setContractorsI(Boolean contractorsI) {
        this.contractorsI = contractorsI;
    }

    @Basic
    @Column(name = "contractors_d")
    public Boolean getContractorsD() {
        return contractorsD;
    }

    public void setContractorsD(Boolean contractorsD) {
        this.contractorsD = contractorsD;
    }

    @Basic
    @Column(name = "contractors_u")
    public Boolean getContractorsU() {
        return contractorsU;
    }

    public void setContractorsU(Boolean contractorsU) {
        this.contractorsU = contractorsU;
    }

    @Basic
    @Column(name = "reports_r")
    public Boolean getReportsR() {
        return reportsR;
    }

    public void setReportsR(Boolean reportsR) {
        this.reportsR = reportsR;
    }

    @Basic
    @Column(name = "reports_i")
    public Boolean getReportsI() {
        return reportsI;
    }

    public void setReportsI(Boolean reportsR) {
        this.reportsI = reportsI;
    }

    @Basic
    @Column(name = "reports_d")
    public Boolean getReportsD() {
        return reportsD;
    }

    public void setReportsD(Boolean reportsD) {
        this.reportsD = reportsD;
    }

    @Basic
    @Column(name = "reports_u")
    public Boolean getReportsU() {
        return reportsU;
    }

    public void setReportsU(Boolean reportsU) {
        this.reportsU = reportsU;
    }

    @Basic
    @Column(name = "showrooms_r")
    public Boolean getShowroomsR() {
        return showroomsR;
    }

    public void setShowroomsR(Boolean showroomsR) {
        this.showroomsR = showroomsR;
    }

    @Basic
    @Column(name = "showrooms_i")
    public Boolean getShowroomsI() {
        return showroomsI;
    }

    public void setShowroomsI(Boolean showroomsI) {
        this.showroomsI = showroomsI;
    }

    @Basic
    @Column(name = "showrooms_d")
    public Boolean getShowroomsD() {
        return showroomsD;
    }

    public void setShowroomsD(Boolean showroomsD) {
        this.showroomsD = showroomsD;
    }

    @Basic
    @Column(name = "showrooms_u")
    public Boolean getShowroomsU() {
        return showroomsU;
    }

    public void setShowroomsU(Boolean showroomsU) {
        this.showroomsU = showroomsU;
    }

    @Basic
    @Column(name = "workers_r")
    public Boolean getWorkersR() {
        return workersR;
    }

    public void setWorkersR(Boolean workersR) {
        this.workersR = workersR;
    }

    @Basic
    @Column(name = "workers_i")
    public Boolean getWorkersI() {
        return workersI;
    }

    public void setWorkersI(Boolean workersI) {
        this.workersI = workersI;
    }

    @Basic
    @Column(name = "workers_d")
    public Boolean getWorkersD() {
        return workersD;
    }

    public void setWorkersD(Boolean workersD) {
        this.workersD = workersD;
    }

    @Basic
    @Column(name = "workers_u")
    public Boolean getWorkersU() {
        return workersU;
    }

    public void setWorkersU(Boolean workersU) {
        this.workersU = workersU;
    }

    @Basic
    @Column(name = "promotions_r")
    public Boolean getPromotionsR() {
        return promotionsR;
    }

    public void setPromotionsR(Boolean promotionsR) {
        this.promotionsR = promotionsR;
    }

    @Basic
    @Column(name = "promotions_i")
    public Boolean getPromotionsI() {
        return promotionsI;
    }

    public void setPromotionsI(Boolean promotionsI) {
        this.promotionsI = promotionsI;
    }

    @Basic
    @Column(name = "promotions_d")
    public Boolean getPromotionsD() {
        return promotionsD;
    }

    public void setPromotionsD(Boolean promotionsD) {
        this.promotionsD = promotionsD;
    }

    @Basic
    @Column(name = "promotions_u")
    public Boolean getPromotionsU() {return promotionsU;}

    public void setPromotionsU(Boolean promotionsU) {
        this.promotionsU = promotionsU;
    }

    @Basic
    @Column(name = "services_r")
    public Boolean getServicesR() {
        return servicesR;
    }

    public void setServicesR(Boolean servicesR) {
        this.servicesR = servicesR;
    }

    @Basic
    @Column(name = "services_i")
    public Boolean getServicesI() {
        return servicesI;
    }

    public void setServicesI(Boolean servicesI) {
        this.servicesI = servicesI;
    }

    @Basic
    @Column(name = "services_d")
    public Boolean getServicesD() {
        return servicesD;
    }

    public void setServicesD(Boolean servicesD) {
        this.servicesD = servicesD;
    }

    @Basic
    @Column(name = "services_u")
    public Boolean getServicesU() {
        return servicesU;
    }

    public void setServicesU(Boolean servicesU) {
        this.servicesU = servicesU;
    }

    @Basic
    @Column(name = "dictionary_r")
    public Boolean getDictionaryR() {
        return dictionaryR;
    }

    public void setDictionaryR(Boolean dictionaryR) {
        this.dictionaryR = dictionaryR;
    }

    @Basic
    @Column(name = "dictionary_i")
    public Boolean getDictionaryI(){ return dictionaryI;}

    public void setDictionaryI(Boolean dictionaryI) {
        this.dictionaryI = dictionaryI;
    }

    @Basic
    @Column(name = "dictionary_d")
    public Boolean getDictionaryD() {
        return dictionaryD;
    }

    public void setDictionaryD(Boolean dictionaryD) {
        this.dictionaryD = dictionaryD;
    }

    @Basic
    @Column(name = "dictionary_u")
    public Boolean getDictionaryU() {
        return dictionaryU;
    }

    public void setDictionaryU(Boolean dictionaryU) {
        this.dictionaryU = dictionaryU;
    }

    @Column(name = "privileges_r")
    public Boolean getPrivilegesR() {
        return privilegesR;
    }

    public void setPrivilegesR(Boolean privilegesR) {
        this.privilegesR = privilegesR;
    }

    @Basic
    @Column(name = "privileges_i")
    public Boolean getPrivilegesI(){ return privilegesI;}

    public void setPrivilegesI(Boolean privilegesI) {
        this.privilegesI = privilegesI;
    }

    @Basic
    @Column(name = "privileges_d")
    public Boolean getPrivilegesD() {
        return privilegesD;
    }

    public void setPrivilegesD(Boolean privilegesD) {
        this.privilegesD = privilegesD;
    }

    @Basic
    @Column(name = "privileges_u")
    public Boolean getPrivilegesU() {
        return privilegesU;
    }

    public void setPrivilegesU(Boolean privilegesU) {
        this.privilegesU = privilegesU;
    }

}
