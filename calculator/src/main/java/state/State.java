package state;

import token.Token;
import token.Tokenizer;

public interface State {
    default Token getToken(Tokenizer tokenizer) {
        throw new UnsupportedOperationException();
    }

    default void nextState(Tokenizer tokenizer) {
        throw new UnsupportedOperationException();
    }
}
