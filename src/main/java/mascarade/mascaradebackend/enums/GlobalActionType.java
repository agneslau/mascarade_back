package mascarade.mascaradebackend.enums;

public enum GlobalActionType {
    PEGRE_1(1, Category.PEGRE, "* Course gratuite"),
    PEGRE_2(2, Category.PEGRE, "** La rumeur"),
    PEGRE_3(3, Category.PEGRE, "*** Faveurs illégales"),
    PEGRE_4(4, Category.PEGRE, "**** Déballer son linge sale en public"),
    PEGRE_5(5, Category.PEGRE, "***** Etat d'urgence"),
    PEGRE_6(6, Category.PEGRE, "***** * Tirer les ficelles"),
    PEGRE_7(7, Category.PEGRE, "***** ** Détourner le regard"),
    PEGRE_8(8, Category.PEGRE, "***** *** Ce dont tu t'empares est tien"),
    PEGRE_9(9, Category.PEGRE, "***** **** Une offre que vous ne pouvez refuser"),
    PEGRE_10(10, Category.PEGRE, "***** ***** Influence Régionale"),
    GOTHA_1(1, Category.GOTHA, "* Un ami dans le besoin"),
    GOTHA_2(2, Category.GOTHA, "** Potins et délit d'initié"),
    GOTHA_3(3, Category.GOTHA, "*** Erreurs administratives"),
    GOTHA_4(4, Category.GOTHA, "**** Désamorcer les problèmes"),
    GOTHA_5(5, Category.GOTHA, "***** Pas de pique-assiettes"),
    GOTHA_6(6, Category.GOTHA, "***** * Tout a un prix"),
    GOTHA_7(7, Category.GOTHA, "***** ** Collection privée"),
    GOTHA_8(8, Category.GOTHA, "***** *** La débâcle d'une vedette"),
    GOTHA_9(9, Category.GOTHA, "***** **** Le pouvoir derrière le trône"),
    GOTHA_10(10, Category.GOTHA, "***** ***** Influence Régionale");

    public final int value;
    public final Category category;
    public final String label;

    GlobalActionType(int value, Category category, String label) {
        this.value = value;
        this.category = category;
        this.label = label;
    }
}
