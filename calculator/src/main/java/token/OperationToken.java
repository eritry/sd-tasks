package token;

import visitor.TokenVisitor;

public class OperationToken implements Token {
    private final TokenType operationType;

    public OperationToken(TokenType operationType) {
        this.operationType = operationType;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public TokenType getTokenType() {
        return operationType;
    }

    public int evaluate(int a, int b) {
        switch (operationType) {
            case PLUS:
                return a + b;
            case MINUS:
                return a - b;
            case MUL:
                return a * b;
            case DIV:
                return a / b;
        }
        throw new IllegalStateException();
    }

    @Override
    public String toString() {
        return operationType.toString();
    }
}
