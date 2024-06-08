package fonctions;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Fy
 */
public class Utilitaire {

    public static String formatDuree(long dureeMillis) {
        Duration duration = Duration.ofMillis(dureeMillis);

        long hours = duration.toHours();
        long minutes = duration.toMinutes();
        long seconds = duration.getSeconds();

        return String.format("%02d h %02d min %02d s", hours, minutes, seconds);
    }

    public static String getDateTimeStamp(Timestamp t) {
        LocalDate localDate = t.toLocalDateTime().toLocalDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formattedDate = localDate.format(formatter);

        return formattedDate;
    }

    public static String getHeureTimeStamp(Timestamp t) {
        LocalTime localTime = t.toLocalDateTime().toLocalTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = localTime.format(formatter);
        return formattedTime;
    }

    public static String traiterDate(String date, String separateur) throws Exception {
        String[] split = date.split(separateur);
        String mm = split[0];
        String jj = split[1];
        String YYYY = split[2];

        return YYYY + "-" + mm + "-" + jj;
    }

    public static double abs(double d) {
        if (d < 0) {
            return -d;
        }
        return d;
    }

}
