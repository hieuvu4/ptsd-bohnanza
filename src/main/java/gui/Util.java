package gui;

import game.Card;
import io.bitbucket.plt.sdp.bohnanza.gui.CardType;

public class Util {

    public static CardType toGUIType(Card card) {
        return switch (card){
            case BLAUE_BOHNE -> CardType.BLAUE_BOHNE;
            case FEUERBOHNE -> CardType.FEUER_BOHNE;
            case SAUBOHNE -> CardType.SAU_BOHNE;
            case BRECHBOHNE -> CardType.BRECH_BOHNE;
            case SOJABOHNE -> CardType.SOJA_BOHNE;
            case AUGENBOHNE -> CardType.AUGEN_BOHNE;
            case ROTE_BOHNE -> CardType.ROTE_BOHNE;
            case GARTENBOHNE -> CardType.GARTEN_BOHNE;
        };
    }

    public static Card toGameType(CardType type) {
        return switch (type) {
            case AUGEN_BOHNE -> Card.AUGENBOHNE;
            case BLAUE_BOHNE -> Card.BLAUE_BOHNE;
            case BRECH_BOHNE -> Card.BRECHBOHNE;
            case FEUER_BOHNE -> Card.FEUERBOHNE;
            case GARTEN_BOHNE -> Card.GARTENBOHNE;
            case ROTE_BOHNE -> Card.ROTE_BOHNE;
            case SAU_BOHNE -> Card.SAUBOHNE;
            case SOJA_BOHNE -> Card.SOJABOHNE;
            default -> null;
        };
    }
}
