package token;

import visitor.TokenVisitor;

public class BraceToken implements Token {
    private final TokenType braceType;

    public BraceToken(TokenType braceType) {
        this.braceType = braceType;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public TokenType getTokenType() {
        return braceType;
    }

    @Override
    public String toString() {
        return braceType.toString();
    }
}
