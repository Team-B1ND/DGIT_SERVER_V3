package dodam.b1nd.dgit.global.lib.webclient.parser;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HeaderParser {
    private String type;
    private String value;
}
