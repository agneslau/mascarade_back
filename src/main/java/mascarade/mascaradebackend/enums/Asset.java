package mascarade.mascaradebackend.enums;

import java.util.List;

import static mascarade.mascaradebackend.enums.Clan.VENTRUE;

public enum Asset {
    MONOPOLISTE(1, Clan.getAllClans()),
    MARIONNETTISTE(2, Clan.getAllClans()),
    PARANGON(3, List.of(VENTRUE));


    Asset(int value, List<Clan> clans) {
    }
}
