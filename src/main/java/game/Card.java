package game;

public enum Card {

    BLAUE_BOHNE("Blaue Bohne", new int[]{4, 6, 8, 10}, new int[]{1, 2, 3, 4}, 20),
    KIDNEYBOHNE("Kidneybohne", new int[]{5, 6, 7, 8}, new int[]{1, 2, 3, 4}, 19),
    FEUERBOHNE("Feuerbohne", new int[]{3, 6, 8, 9}, new int[]{1, 2, 3, 4}, 18),
    PUFFBOHNE("Puffbohne", new int[]{4, 5, 6, 7}, new int[]{1, 2, 3, 4}, 16),
    SAUBOHNE("Saubohne", new int[]{3, 5, 7, 8}, new int[]{1, 2, 3, 4}, 16),
    BRECHBOHNE("Brechbohne", new int[]{3, 5, 6, 7}, new int[]{1, 2, 3, 4}, 14),
    STANGENBOHNE("Stangenbohne", new int[]{3, 4, 5, 6}, new int[]{1, 2, 3, 4}, 14);

    private final String name;
    private final int[] numberNeedToHarvest;
    private final int[] coins;
    private final int overallAmount;

    Card(final String name, final int[] numberNeedToHarvest, final int[] coins, final int overallAmount) {
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
    public int getCoinValue(final int amountOfCards){
        if(amountOfCards < 1) throw new IllegalArgumentException("Count must be greater than zero.");
        for(int i = numberNeedToHarvest.length-1; i >= 0; i--){
            if(numberNeedToHarvest[i] <= amountOfCards) return coins[i];
        }
        return 0;
    }
}
