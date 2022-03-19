import token.Token;
import token.Tokenizer;
import visitor.CalcVisitor;
import visitor.ParserVisitor;
import visitor.PrintVisitor;

import java.io.*;
import java.text.ParseException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        while (!(input = reader.readLine()).isEmpty()) {
            Tokenizer tokenizer = new Tokenizer();
            PrintVisitor printVisitor = new PrintVisitor();
            CalcVisitor calcVisitor = new CalcVisitor();
            ParserVisitor parserVisitor = new ParserVisitor();

            List<Token> tokens = tokenizer.parse(input);
            System.out.println(printVisitor.print(tokens));

            List<Token> rpnTokens = parserVisitor.parse(tokens);
            System.out.println(printVisitor.print(rpnTokens));

            System.out.println(calcVisitor.calculate(rpnTokens));
        }
    }

}
