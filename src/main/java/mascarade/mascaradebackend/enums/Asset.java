package mascarade.mascaradebackend.enums;

import java.util.List;

import static mascarade.mascaradebackend.enums.Clan.VENTRUE;

public enum Asset {
    MONOPOLISTE(1, Clan.getAllClans()),
    MARIONNETTISTE(2, Clan.getAllClans()),
    PARANGON(3, List.of(VENTRUE));

    public final int value;
    public final List<Clan> clans;


    Asset(int value, List<Clan> clans) {
        this.value = value;
        this.clans = clans;
    }
}
