public enum Card {
    KAFFEEBOHNE("Kaffeebohne", new int[]{4, 7, 10, 12}, new int[]{1, 2, 3, 4}),
    WEINBRANDBOHNE("Weinbrandbohne", new int[]{4, 7, 9, 11}, new int[]{1, 2, 3, 4}),
    BLAUE_BOHNE("Blaue Bohne", new int[]{2, 3, 4, 5}, new int[]{1, 2, 3, 4}),
    FEUERBOHNE("Feuerbohne", new int[]{2, 3, 4, 5}, new int[]{1, 2, 3, 4}),
    SAUBOHNE("Saubohne", new int[]{2, 3, 4, 5}, new int[]{1, 2, 3, 4}),
    BRECHBOHNE("Brechbohne", new int[]{2, 3, 4, 5}, new int[]{2, 3, 5, 7}),
    SOJABOHNE("Sojabohne", new int[]{2, 3, 4, 5}, new int[]{2, 3, 5, 7}),
    AUGENBOHNE("Augenbohne", new int[]{2, 4, 5, 6}, new int[]{1, 2, 3, 4}),
    ROTE_BOHNE("Rote Bohne", new int[]{2, 3, 4, 5}, new int[]{1, 2, 3, 4}),
    GARTENBOHNE("Gartenbohne", new int[]{2, 3}, new int[]{2, 3}),
    KAKAOBOHNE("Kakaobohne", new int[]{2, 3, 4}, new int[]{2, 3, 4}),
    ACKERBOHNE("Ackerbohne", new int[]{2, 3}, new int[]{2, 3});

    private final String name;
    private final int[] numberNeedToHarvest;
    private final int[] coins;

    Card(String name, int[] numberNeedToHarvest, int[] coins) {
        this.name = name;
        this.numberNeedToHarvest = numberNeedToHarvest;
        this.coins = coins;
        if (numberNeedToHarvest.length != coins.length) {
            throw new IllegalArgumentException();
        }
    }

    public String getName() {
        return name;
    }

    public int[] getNumberNeedToHarvest() {
        return numberNeedToHarvest;
    }

    public int[] getCoins() {
        return coins;
    }
}
