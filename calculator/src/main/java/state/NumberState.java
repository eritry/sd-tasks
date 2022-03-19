package state;

import token.NumberToken;
import token.Token;
import token.Tokenizer;

public class NumberState implements State {
    @Override
    public Token getToken(Tokenizer tokenizer) {
        StringBuilder value = new StringBuilder();
        while (!tokenizer.isEndOfInput() && Character.isDigit(tokenizer.getCurChar())) {
            value.append(tokenizer.getCurChar());
            tokenizer.nextCharacter();
        }
        return new NumberToken(Integer.parseInt(value.toString()));
    }

    @Override
    public void nextState(Tokenizer tokenizer) {
        if (tokenizer.isEndOfInput()) {
            tokenizer.setState(new EndState());
        } else if (tokenizer.isOperationOrBrace()) {
            tokenizer.setState(new StartState());
        } else {
            tokenizer.setState(new ErrorState("Unexpected symbol : " + tokenizer.getCurChar()));
        }
    }
}
