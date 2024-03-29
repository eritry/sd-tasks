package visitor;

import token.BraceToken;
import token.NumberToken;
import token.OperationToken;
import token.Token;

import java.util.List;
import java.util.Stack;

public class CalcVisitor implements TokenVisitor {
    private final Stack<Integer> stack = new Stack<>();

    public int calculate(List<Token> tokens) {
        if (tokens.isEmpty()) {
            return 0;
        }
        tokens.forEach(token -> token.accept(this));
        if (stack.size() != 1) {
            throw new IllegalStateException("Invalid tokens sequence");
        }
        return stack.pop();
    }

    @Override
    public void visit(NumberToken token) {
        stack.push(token.getValue());
    }

    @Override
    public void visit(BraceToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(OperationToken token) {
        if (stack.size() < 2) {
            throw new IllegalStateException();
        }
        int b = stack.pop();
        int a = stack.pop();
        stack.push(token.evaluate(a, b));
    }
}
