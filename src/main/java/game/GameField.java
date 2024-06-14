package game;

import game.cards.Card;
import game.mafia.*;
import game.phases.*;

import java.util.*;

public class GameField implements Observer {

    private final Pile pile;
    private final List<Player> players;
    private DiscoverArea discoverArea;
    private TradingArea tradingArea;

    private Player turnPlayer;
    private Phase phase;

    private final boolean extension;

    private  MafiaBank mafiaBank;
    private AlCabohne alCabohne;
    private DonCorlebohne donCorlebohne;
    private JoeBohnano joeBohnano;

    public GameField(int playerAmount, boolean extension) {
        this.extension = extension;
        this.pile = new Pile(this);
        players = new ArrayList<>();

        if(extension) {
            if (playerAmount < 1 || playerAmount > 2)
                throw new IllegalArgumentException("playerAmount must be 1 or 2.");
            discoverArea = new DiscoverArea(this);

            mafiaBank = new MafiaBank();
            setUpExt(playerAmount);
        }
        else {
            if (playerAmount < 3 || playerAmount > 5)
                throw new IllegalArgumentException("playerAmount must be between 3 or 5.");
            setUp(playerAmount);
            tradingArea = new TradingArea(this);
        }


        turnPlayer = players.getFirst();
        turnPlayer.setPhase(new PhasePlanting());
        phase = turnPlayer.getPhase();
        System.out.println("Turn of Player " + (players.indexOf(turnPlayer)+1) + ".");
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getTurnPlayer() {
        return turnPlayer;
    }

    public MafiaBank getMafiaBank() {
        return mafiaBank;
    }

    public AlCabohne getAlCabohne() {
        return alCabohne;
    }

    public DonCorlebohne getDonCorlebohne() {
        return donCorlebohne;
    }

    public JoeBohnano getJoeBohnano() {
        return joeBohnano;
    }

    public Pile getPile() {
        return pile;
    }

    public DiscoverArea getDiscoverArea() {
        return discoverArea;
    }

    public TradingArea getTradingArea() {
        return tradingArea;
    }

    public boolean getExtension() {
        return extension;
    }

    private void setUp(int playerAmount) {
        for(int i = 0; i < playerAmount; i++) {
            players.add(new Player(i+1 + "", this));

            for (int j = 0; j < 5; j++) players.get(i).getHand().addCard(pile.drawCard());
            players.get(i).addObserver(this);
        }
    }

    private void setUpExt(int playerAmount) {
        setUpCardsExt(playerAmount);
        setUpBossExt(playerAmount);
        setUpCardsBossExt(playerAmount);
    }

    private void setUpBossExt(int playerAmount) {
        if(playerAmount == 1) {
            joeBohnano = new JoeBohnano(this);
        }
        alCabohne = new AlCabohne(this);
        donCorlebohne = new DonCorlebohne(this);
    }

    private void setUpCardsBossExt(int playerAmount) {
        Field alCabohnesField = alCabohne.getField();
        Field donCorlebohneField = donCorlebohne.getField();

        Card card = pile.drawCard();
        alCabohnesField.setCardType(card);
        alCabohnesField.increaseCardAmount();

        while(donCorlebohneField.getCardAmount() != 1) {
            Card nextCard = pile.drawCard();
            if (nextCard == card) {
                alCabohnesField.increaseCardAmount();
            } else {
                donCorlebohneField.setCardType(nextCard);
                donCorlebohneField.increaseCardAmount();
            }
        }

        if(playerAmount == 1) {
            Field joeBohnanoField = joeBohnano.getField();
            while(joeBohnanoField.getCardAmount() != 1) {
                Card nextCard = pile.drawCard();
                if(nextCard == card) {
                    alCabohnesField.increaseCardAmount();
                }
                else if(nextCard == donCorlebohneField.getCardType()) {
                    donCorlebohneField.increaseCardAmount();
                } else {
                    joeBohnanoField.setCardType(nextCard);
                    joeBohnanoField.increaseCardAmount();
                }
            }
        }

    }

    private void setUpCardsExt(int playerAmount) {
        if (playerAmount == 1) {
            players.add(new Player( "", this));
            for (int j = 0; j < 7; j++) players.getFirst().getHand().addCard(pile.drawCard());
            players.getFirst().addObserver(this);
        }

        if (playerAmount == 2) {
            players.add(new Player("1", this));
            players.add(new Player("2", this));

            for (Player player : players) {
                for (int j = 0; j < 5; j++) player.getHand().addCard(pile.drawCard());
                player.addObserver(this);
            }
        }
    }

    private void checkDiscoverCardForBoss(int index) {
        Field discoverField = discoverArea.getDiscoverFields().get(index);
        List<Field> bossFields;

        if (players.size() == 2) {
            bossFields = Arrays.asList(alCabohne.getField(), donCorlebohne.getField());
        } else {
            bossFields = Arrays.asList(alCabohne.getField(), donCorlebohne.getField(), joeBohnano.getField());
        }

        for (Field bossField : bossFields) {
            if (discoverField.getCardType() == bossField.getCardType()) {
                bossField.increaseCardAmount();
                System.out.println("A Boss took " + discoverField.getCardType() + "!");
                discoverField.clear();

                discoverField.setCardType(pile.drawCard());
                discoverField.increaseCardAmount();
                checkDiscoverCardForBoss(index);
                break;
            }
        }

        checkTopDiscardPileMatch();
    }

    private void checkTopDiscardPileMatch() {
        List<Card> discardPile = pile.getDiscardPile();
        if(!discardPile.isEmpty()) {
            for (Field discoverField : discoverArea.getDiscoverFields()) {
                if(discoverField.getCardType() == discardPile.getLast()) {
                    discoverField.increaseCardAmount();
                    discardPile.removeLast();
                    checkTopDiscardPileMatch();
                    break;
                }
            }
        }
    }

    private void tryHarvestBoss() throws IllegalMoveException {
        alCabohne.tryHarvest();
        donCorlebohne.tryHarvest();
        if (players.size() == 1) {
            joeBohnano.tryHarvest();
        }
    }

    /**
     * This method updates the attributes phase which represent the current phase of the current player's turn.
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        if(extension) {
            updatePhasesExt((Phase) arg);
        }
        else {
            this.phase = (Phase) arg;
            if(phase instanceof PhaseTrading) {
                this.tradingArea.fillTradingArea();
            }
            if(phase instanceof PhasePlantingTraded) {
                tradingArea.getOffersForTCard0().clear();
                tradingArea.getOffersForTCard1().clear();
            }
            if (phase instanceof PhaseOut) {
                int index = players.indexOf(turnPlayer);
                turnPlayer = (index+1 >= players.size()) ? players.getFirst() : players.get(index+1);
                turnPlayer.setPhase(new PhasePlanting());
                System.out.println("Turn of Player " + (players.indexOf(turnPlayer)+1) + ".");
            }
        }
    }

    private void updatePhasesExt(Phase arg) {
        this.phase = arg;

        try {
            tryHarvestBoss();
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }

        if(phase instanceof PhaseGiving) {
            try {
                giveBossCardIfAvailable();
                turnPlayer.nextPhase();
            } catch (IllegalMoveException e) {
                throw new RuntimeException(e);
            }
        }

        if(phase instanceof PhaseRevealing) {
            for(int i = 0; i < discoverArea.getDiscoverFields().size(); i++) {
                System.out.println("Filling Discover Card " + (i+1) + "...");
                discoverArea.fillDiscoverCard(i);
                checkDiscoverCardForBoss(i);
                System.out.println(discoverArea.getDiscoverFields().get(0).getCardType() + ", "
                        + discoverArea.getDiscoverFields().get(0).getCardAmount());
                System.out.println(discoverArea.getDiscoverFields().get(1).getCardType() + ", "
                        + discoverArea.getDiscoverFields().get(1).getCardAmount());
                System.out.println(discoverArea.getDiscoverFields().get(2).getCardType() + ", "
                        + discoverArea.getDiscoverFields().get(2).getCardAmount());
            }
            try {
                turnPlayer.nextPhase();
            } catch (IllegalMoveException e) {
                throw new RuntimeException(e);
            }
        }

        if(phase instanceof PhaseDrawing) {
            try {
                turnPlayer.drawCards(pile);
                System.out.println("Player " + turnPlayer.getName() + " drew cards.");
                turnPlayer.nextPhase();
            } catch (IllegalMoveException e) {
                e.printStackTrace();
            }
        }

        if (phase instanceof PhaseOut) {
            if(players.size() == 2) {
                turnPlayer = ("1".equals(turnPlayer.getName())) ? players.getLast() : players.getFirst();
                phase = new PhaseUsing();
                turnPlayer.setPhase(phase);
                System.out.println();
                System.out.println("Turn of Player " + turnPlayer.getName());
            }
            if(players.size() == 1) {
                phase = new PhaseGiving();
                turnPlayer.setPhase(phase);
            }
        }
    }

    private void giveBossCardIfAvailable() throws IllegalMoveException {
        List<Boss> bosses = new ArrayList<>();
        bosses.add(alCabohne);
        bosses.add(donCorlebohne);
        if(players.size() == 1) {
            bosses.add(joeBohnano);
        }
        for(int i = 0; i < turnPlayer.getFields().length; i++) {
            if (!turnPlayer.getField(i).isEmpty()) {
                for(Boss boss : bosses) {
                    if(turnPlayer.getField(i).getCardType() == boss.getField().getCardType()) {
                        System.out.println("Card " + boss.getField().getCardType() + " was given away!");
                        boss.getField().increaseCardAmount();
                        turnPlayer.getField(i).decreaseCardAmount();
                        break;
                    }
                }
            }
        }
    }

}
