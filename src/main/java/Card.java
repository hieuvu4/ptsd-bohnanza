public enum Card {
    KAFFEEBOHNE("Kaffeebohne", new int[]{4, 7, 10, 12}, new int[]{1, 2, 3, 4}, 24),
    WEINBRANDBOHNE("Weinbrandbohne", new int[]{4, 7, 9, 11}, new int[]{1, 2, 3, 4}, 22),
    BLAUE_BOHNE("Blaue Bohne", new int[]{2, 3, 4, 5}, new int[]{1, 2, 3, 4}, 20),
    FEUERBOHNE("Feuerbohne", new int[]{2, 3, 4, 5}, new int[]{1, 2, 3, 4}, 18),
    SAUBOHNE("Saubohne", new int[]{2, 3, 4, 5}, new int[]{1, 2, 3, 4}, 16),
    BRECHBOHNE("Brechbohne", new int[]{2, 3, 4, 5}, new int[]{1, 2, 3, 4}, 14),
    SOJABOHNE("Sojabohne", new int[]{2, 4, 5, 7}, new int[]{1, 2, 3, 4}, 12),
    AUGENBOHNE("Augenbohne", new int[]{2, 4, 5, 6}, new int[]{1, 2, 3, 4}, 10),
    ROTE_BOHNE("Rote Bohne", new int[]{2, 3, 4, 5}, new int[]{1, 2, 3, 4}, 8),
    GARTENBOHNE("Gartenbohne", new int[]{2, 3}, new int[]{2, 3}, 6),
    KAKAOBOHNE("Kakaobohne", new int[]{2, 3, 4}, new int[]{2, 3, 4}, 4),
    ACKERBOHNE("Ackerbohne", new int[]{2, 3}, new int[]{2, 3}, 3);

    private final String name;
    private final int[] numberNeedToHarvest;
    private final int[] coins;
    private final int amount;

    Card(String name, int[] numberNeedToHarvest, int[] coins, int amount) {
        this.name = name;
        this.numberNeedToHarvest = numberNeedToHarvest;
        this.coins = coins;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    public int coinValue(int count){
        return 0;
    }
}
