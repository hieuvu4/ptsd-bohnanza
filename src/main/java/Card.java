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
    private final int overallAmount;

    Card(String name, int[] numberNeedToHarvest, int[] coins, int overallAmount) {
        this.name = name;
        this.numberNeedToHarvest = numberNeedToHarvest;
        this.coins = coins;
        this.overallAmount = overallAmount;
        if (numberNeedToHarvest.length != coins.length) {
            throw new IllegalArgumentException("Size of coins must be equal to size of amount.");
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

    public int getOverallAmount() {
        return overallAmount;
    }

    /**
     * Returns the amount of coins for a specific amounts of cards. If the amount of cards is below 1, an
     * IllegalMoveException will be thrown.
     * @param   amountOfCards the specific amount of cards
     * @return  the value of the specific amount of cards
     */
    public int getCoinValue(int amountOfCards){
        if(amountOfCards < 1) throw new IllegalArgumentException("Count must be greater than zero.");
        for(int i = numberNeedToHarvest.length-1; i >= 0; i--){
            if(numberNeedToHarvest[i] <= amountOfCards) return coins[i];
        }
        return 0;
    }
}
