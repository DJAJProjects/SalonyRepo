package pl.polsl;

import pl.polsl.model.Worker;

/**
 * Created by Dominika Błasiak on 02.05.2016.
 */
public class Data {
    /**
     * Przechowuje dane o aktualnie zalogowanym użytkowniku
     */
    public static Worker user;

    public static int adminId ;
    public static int directorId;
    public static String adminValue = "Admininstrator";
    public static String directorValue = "Dyrektor";
<<<<<<< HEAD
    public static String workerModuleValue = "Pracownicy";
    public static String showroomModuleValue = "Salony";

=======
    public static String servicemanValue = "Serwisant";
    public static String salesmanValue = "Sprzedawca";

    public static Integer[] defAdminPrivKeys     = new Integer[]{28,29,30,31,32,33,34,35,36,40,44,45};
    public static Integer[] defSalesmanPrivKeys   = new Integer[]{1,3,7,11,14,17,20,23,26,38,42,46};
    public static Integer[] defServicemanPrivKeys = new Integer[]{2,6,8,12,15,18,21,24,27,37,41,47};
    public static Integer[] defDirectorPrivKeys   = new Integer[]{4,5,9,10,13,16,19,22,25,39,43,48};
>>>>>>> c46d6fa89b3d20a2edea2cf177d8560ba9cf8ed9
}
