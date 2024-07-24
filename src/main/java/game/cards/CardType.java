package game.cards;

public enum CardType {

    AUGENBOHNE("Augenbohne", new int[]{2, 4, 5, 6}, new int[]{1, 2, 3, 4}, 10),
    BLAUE_BOHNE("Blaue Bohne", new int[]{4, 6, 8, 10}, new int[]{1, 2, 3, 4}, 20),
    BRECHBOHNE("Brechbohne", new int[]{3, 5, 6, 7}, new int[]{1, 2, 3, 4}, 14),
    FEUERBOHNE("Feuerbohne", new int[]{3, 6, 8, 9}, new int[]{1, 2, 3, 4}, 18),
    GARTENBOHNE("Gartenbohne", new int[]{2, 3}, new int[]{2, 3}, 6),
    KIDNEYBOHNE("Kidneybohne", new int[]{5, 6, 7, 8}, new int[]{1, 2, 3, 4}, 19),
    PUFFBOHNE("Puffbohne", new int[]{4, 5, 6, 7}, new int[]{1, 2, 3, 4}, 16),
    ROTE_BOHNE("Rote Bohne", new int[]{2, 3, 4, 5}, new int[]{1, 2, 3, 4}, 8),
    SAUBOHNE("Saubohne", new int[]{3, 5, 7, 8}, new int[]{1, 2, 3, 4}, 16),
    SOJABOHNE("Stangenbohne", new int[]{2, 4, 6, 7}, new int[]{1, 2, 3, 4}, 12),
    STANGENBOHNE("Stangenbohne", new int[]{3, 4, 5, 6}, new int[]{1, 2, 3, 4}, 13);



    private final String name;
    private final int[] numberNeedToHarvest;
    private final int[] coins;
    private final int overallAmount;

    CardType(final String name, final int[] numberNeedToHarvest, final int[] coins, final int overallAmount) {
        this.name = name;
        this.numberNeedToHarvest = numberNeedToHarvest;
        this.coins = coins;
        this.overallAmount = overallAmount;
        if (numberNeedToHarvest.length != coins.length) {
            throw new IllegalArgumentException("Size of coins must be equal to size of amount.");
        }
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
