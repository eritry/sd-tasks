package visitor;

import com.google.common.collect.Lists;
import token.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParserVisitor implements TokenVisitor {
    private final List<Token> rpnTokens = new ArrayList<>();
    private final Stack<Token> stack = new Stack<>();

    public List<Token> parse(List<Token> tokens) {
        tokens.forEach(token -> token.accept(this));
        return Stream.concat(rpnTokens.stream(),
                Lists.reverse(new ArrayList<>(stack)).stream()).collect(Collectors.toList());
    }

    @Override
    public void visit(NumberToken token) {
        rpnTokens.add(token);
    }

    @Override
    public void visit(BraceToken token) {
        if (token.getTokenType().equals(TokenType.LEFT_BRACE)) {
            stack.push(token);
        } else {
            if (stack.isEmpty()) {
                throw new IllegalStateException();
            }
            while (!stack.isEmpty() && !stack.peek().getTokenType().equals(TokenType.LEFT_BRACE)) {
                rpnTokens.add(stack.pop());
            }
            stack.pop();
        }
    }

    @Override
    public void visit(OperationToken token) {
        while (!stack.isEmpty() && token.getTokenType().getPriority() <= stack.peek().getTokenType().getPriority()) {
            rpnTokens.add(stack.pop());
        }
        stack.push(token);
    }
}
