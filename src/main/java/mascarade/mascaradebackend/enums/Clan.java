package mascarade.mascaradebackend.enums;

import java.util.List;

public enum Clan {
    VENTRUE, MALKAVIAN, GANGREL, BRUJAH, TOREADOR, TREMERE, NOSFERATU, CAITIFF, ASSAMITE, GIOVANNI, TZIMISCE, RAVNOS, LASOMBRA, SETHITE;

    public static List<Clan> getAllClans() {
        return List.of(VENTRUE, MALKAVIAN, GANGREL, BRUJAH, TOREADOR, TREMERE, NOSFERATU, CAITIFF, ASSAMITE, GIOVANNI, TZIMISCE, RAVNOS, LASOMBRA, SETHITE);
    }
}
