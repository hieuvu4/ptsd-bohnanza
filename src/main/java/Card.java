public record  Card(String name, int[] numberNeedToHarvest, int[] coins) {
    public Card(String name, int[] numberNeedToHarvest, int[] coins) {
        this.name = name;
        this.numberNeedToHarvest = numberNeedToHarvest;
        this.coins = coins;
        if (numberNeedToHarvest.length != coins.length) {
            throw new IllegalArgumentException();
        }
    }
}
