package gui;

import io.bitbucket.plt.sdp.bohnanza.gui.CardType;

public class Util {

    public static CardType toGUIType(game.cards.CardType card) {
        return switch (card){
            case BLAUE_BOHNE -> CardType.BLAUE_BOHNE;
            case FEUERBOHNE -> CardType.FEUER_BOHNE;
            case SAUBOHNE -> CardType.SAU_BOHNE;
            case BRECHBOHNE -> CardType.BRECH_BOHNE;
            case SOJABOHNE -> CardType.SOJA_BOHNE;
            case AUGENBOHNE -> CardType.AUGEN_BOHNE;
            case KIDNEYBOHNE -> CardType.KIDNEY_BOHNE;
            case PUFFBOHNE -> CardType.PUFF_BOHNE;
            case ROTE_BOHNE -> CardType.ROTE_BOHNE;
            case GARTENBOHNE -> CardType.GARTEN_BOHNE;
            case STANGENBOHNE -> CardType.STANGEN_BOHNE;
        };
    }

    public static game.cards.CardType toGameType(CardType type) {
        return switch (type) {
            case AUGEN_BOHNE -> game.cards.CardType.AUGENBOHNE;
            case BLAUE_BOHNE -> game.cards.CardType.BLAUE_BOHNE;
            case BRECH_BOHNE -> game.cards.CardType.BRECHBOHNE;
            case FEUER_BOHNE -> game.cards.CardType.FEUERBOHNE;
            case GARTEN_BOHNE -> game.cards.CardType.GARTENBOHNE;
            case ROTE_BOHNE -> game.cards.CardType.ROTE_BOHNE;
            case SAU_BOHNE -> game.cards.CardType.SAUBOHNE;
            case SOJA_BOHNE -> game.cards.CardType.SOJABOHNE;
            case KIDNEY_BOHNE -> game.cards.CardType.KIDNEYBOHNE;
            case PUFF_BOHNE -> game.cards.CardType.PUFFBOHNE;
            case STANGEN_BOHNE -> game.cards.CardType.STANGENBOHNE;
            default -> null;
        };
    }
}
