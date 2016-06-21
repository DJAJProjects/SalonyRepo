package pl.polsl;

import pl.polsl.model.Worker;

/**
 * Class contains static data in application, contains a lot of readonly variable
 * @author Dominika Błasiak
 * @version 1.0
 */
public class Data {
    /**
     * Current log in user
     */
    public static Worker user;

    /**
     * Administrator id
     */
    public static int adminId ;

    /**
     * Director id
     */
    public static int directorId;


    //region worker position
    public static final String adminValue = "Admininstrator";
    public static final String directorValue = "Dyrektor";
    public static final String servicemanValue = "Serwisant";
    public static final String salesmanValue = "Sprzedawca";
    //endregion

    //region module name
    public static final String workerModuleValue = "Pracownicy";
    public static final String dictionaryModuleValue = "Słownik";
    public static final String invoiceModuleValue = "Sprzedaże";
    public static final String contractModuleValue = "Sprzedaże";
    public static final String showroomModuleValue = "Salony";
    //endregion


    //region privileges keys
    public static final Integer[] defAdminPrivKeys     = new Integer[]{28,29,30,31,32,33,34,35,36,40,44,45};
    public static final Integer[] defSalesmanPrivKeys   = new Integer[]{1,3,7,11,14,17,20,23,26,38,42,46};
    public static final Integer[] defServicemanPrivKeys = new Integer[]{2,6,8,12,15,18,21,24,27,37,41,47};
    public static final Integer[] defDirectorPrivKeys   = new Integer[]{4,5,9,10,13,16,19,22,25,39,43,48};
    //endregion
}
