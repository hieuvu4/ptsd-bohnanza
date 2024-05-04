

public enum Card {
    RED("Red", new int[1], new int[1]);


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

    public int coinValue(int count){
        return 0;
    }
}
